package ru.kirillyarulin.gradientdescent;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;


/**
 * Helper methods for loading and showing data
 *
 * @author Kirill Yarulin
 */
public class GDUtils {
    /**
     * Loads labeled data from csv file
     *
     * @param sc JavaSparkContext object
     * @param path Path to the data file
     */
    public static JavaRDD<LabeledPoint> loadCSVFile(JavaSparkContext sc, String path) {
        JavaRDD<String> csvFile = sc.textFile(path);
        return csvFile.map(s -> {
            double[] x = Arrays.stream(s.substring(0, s.lastIndexOf(",")).split(","))
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            double y = Double.parseDouble(s.substring(s.lastIndexOf(",")+1));
            return new LabeledPoint(new Vector(x),y);
        });
    }

    /**
     * Plotting a graph of data with a vector obtained by applying the gradient descent algorithm
     *
     * @param labeledPoints List of source data
     * @param straight vector obtained by applying the gradient descent algorithm
     */
    public static void showGraph(List<LabeledPoint> labeledPoints, Vector straight) {
        if (labeledPoints.get(0).getX().size() != 1 || straight.size() != 2) {
            throw new IllegalArgumentException("Unsupported input parameter format");
        }

        int count = labeledPoints.size() < Integer.MAX_VALUE ? labeledPoints.size() : Integer.MAX_VALUE;
        double[] dataX = new double[count];
        double[] dataY = new double[count];
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;

        for (int i = 0; i < count; i++) {
            LabeledPoint lp = labeledPoints.get(i);
            dataX[i] = lp.getX().get(0);
            dataY[i] = lp.getY();

            if (dataX[i] < minX) minX = dataX[i];
            if (dataX[i] > maxX) maxX = dataX[i];
        }


        double[] lineX = new double[]{minX, straight.get(0) + straight.get(1)*minX};
        double[] lineY = new double[]{maxX, straight.get(0) + straight.get(1) * maxX};

        Plot2DPanel panel = new Plot2DPanel();
        panel.addScatterPlot("Point",dataX,dataY);
        panel.addLinePlot("Line", lineX, lineY);

        JFrame frame= new JFrame("Result");
        frame.setContentPane(panel);
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
