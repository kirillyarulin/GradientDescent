package ru.kirillyarulin.gradientdescent;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function2;


/**
 * Class used to solve an optimization problem using Gradient Descent
 *
 * @author Kirill Yarulin
 */
public class GradientDescent {
    private int numIterations;
    private double stepSize;
    private double accuracyOfCalculation;

    /**
     * Constructor - creating a new object with certain values
     *
     * @param numIterations the number of iterations which the algorithm will end
     * @param stepSize the step size (learning rate) - the rate at which the resulting vector is approximated
     * @param accuracyOfCalculation the accuracy of the computation that is sufficient to complete the algorithm
     */
    public GradientDescent(int numIterations, double stepSize, double accuracyOfCalculation) {
        this.numIterations = numIterations;
        this.stepSize = stepSize;
        this.accuracyOfCalculation = accuracyOfCalculation;
    }

    /**
     * Returns the number of iterations which the algorithm will end
     */
    public double getNumIterations() {
        return numIterations;
    }

    /**
     * Set the number of iterations after which the algorithm will end
     */
    public GradientDescent setNumIterations(int numIterations) {
        this.numIterations = numIterations;
        return this;
    }

    /**
     * Returns the step size (learning rate) - the rate at which the resulting vector is approximated
     */
    public double getStepSize() {
        return stepSize;
    }

    /**
     * Set the step size (learning rate) - the rate at which the resulting vector is approximated
     */
    public GradientDescent setStepSize(double stepSize) {
        this.stepSize = stepSize;
        return this;
    }

    /**
     * Returns the accuracy of the computation that is sufficient to complete the algorithm
     */
    public double getAccuracyOfCalculation() {
        return accuracyOfCalculation;
    }

    /**
     * Set the accuracy of the computation that is sufficient to complete the algorithm
     */
    public GradientDescent setAccuracyOfCalculation(double accuracyOfCalculation) {
        this.accuracyOfCalculation = accuracyOfCalculation;
        return this;
    }

    /**
     * Runs gradient descent on the given data
     *
     * @param data training data
     * @param initialWeights initial weights
     * @return solution vector
     */
    public Vector optimise(JavaRDD<LabeledPoint> data, Vector initialWeights) {

        Vector result = initialWeights;
        long m = data.count();
        int dimension = initialWeights.size();

        //adding 1 to the beginning of the vector to simplify the calculation.
        JavaRDD<LabeledPoint> labeledPoints = data.map(lp -> {
            lp.getX().addElementFirst(1);
            return lp;
        });
        labeledPoints.cache();

        int counter = 0;
        boolean convergence = false;
        while (counter < numIterations && !convergence) {
            Vector gradient = labeledPoints.treeAggregate(new Vector(dimension), new Gradient(result), Vector::add);
            Vector tmp = Vectors.subtract(result,Vectors.multiply(gradient, stepSize / m));
            convergence = true;
            for (int j = 0; j < tmp.size(); j++) {
                if (Math.abs(result.get(j) - tmp.get(j)) > accuracyOfCalculation) {
                    convergence = false;
                }
            }
            result = tmp;
            counter++;
        }
        return result;

    }

    /**
     * Class used to compute the gradient.
     */
    static class Gradient implements Function2<Vector, LabeledPoint, Vector> {

        private final Vector weights;

        public Gradient(Vector weights) {
            this.weights = weights;
        }

        @Override
        public Vector call(Vector vector, LabeledPoint lp) throws Exception {
            double h = Vectors.scalarProduct(weights,lp.getX());
            return vector.add(Vectors.multiply(lp.getX(),h-lp.getY()));
        }
    }

}
