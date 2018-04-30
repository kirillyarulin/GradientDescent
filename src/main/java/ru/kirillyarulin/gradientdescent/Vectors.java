package ru.kirillyarulin.gradientdescent;

/**
 * The class contains methods for performing basic operations with vectors
 *
 * @author Kirill Yarulin
 */
public final class Vectors {

    private Vectors() {
    }

    /**
     * Returns a vector equal to the sum of two given vectors
     */
    public static Vector add(Vector vector1, Vector vector2) {
        if (vector1.size() != vector2.size()) {
            throw new IllegalArgumentException("The dimensions of the vectors do not coincide.");
        }
        double[] result = new double[vector1.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = vector1.get(i) + vector2.get(i);
        }
        return new Vector(result);
    }

    /**
     * Returns a vector equal to the difference of two given vectors
     */
    public static Vector subtract(Vector vector1, Vector vector2) {
        if (vector1.size() != vector2.size()) {
            throw new IllegalArgumentException("The dimensions of the vectors do not coincide.");
        }
        double[] result = new double[vector1.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = vector1.get(i) - vector2.get(i);
        }
        return new Vector(result);
    }

    /**
     * Returns a vector equal to the product of a given vector by a number
     */
    public static Vector multiply(Vector vector, double value) {
        double[] result = new double[vector.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i) * value;
        }
        return new Vector(result);
    }

    /**
     * Returns a vector equal to a given vector divided by a number
     */
    public static Vector divide(Vector vector, double value) {
        double[] result = new double[vector.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i) / value;
        }
        return new Vector(result);
    }

    /**
     * Returns a vector equal to the scalar product of given vectors
     */
    public static double scalarProduct(Vector vector1, Vector vector2) {
        if (vector1.size() != vector2.size()) {
            throw new IllegalArgumentException("The dimensions of the vectors do not coincide.");
        }
        double result = 0;

        for (int i = 0; i < vector1.size(); i++) {
            result += vector1.get(i) * vector2.get(i);
        }
        return result;
    }
}
