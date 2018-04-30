package ru.kirillyarulin.gradientdescent;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class that represents the features and labels of a data point.
 *
 * @author Kirill Yarulin
 */
public class LabeledPoint implements Serializable {
    private Vector x;
    private double y;

    /**
     * Creates LabeledPoint for the specified features and label
     */
    public LabeledPoint(Vector x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the vector of features for this data point
     */
    public Vector getX() {
        return x;
    }

    /**
     * Returns the label for this data point
     */
    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabeledPoint that = (LabeledPoint) o;
        return Double.compare(that.y, y) == 0 &&
                Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "LabeledPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
