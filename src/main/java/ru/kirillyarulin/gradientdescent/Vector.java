package ru.kirillyarulin.gradientdescent;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents a numeric vector, whose index type is int and value type is double.
 *
 * @author Kirill Yarulin
 */
public class Vector implements Serializable {
    private double[] elements;

    /**
     * Creates a vector from the given array
     */
    public Vector(double... elements) {
        this.elements = elements;
    }

    /**
     * Creates a vector of a given size consisting of zeros
     */
    public Vector(int dimension) {
        this.elements = new double[dimension];
    }

    /**
     * Returns size(dimension) of the vector.
     */
    public int size() {
        return elements.length;
    }

    /**
     * Gets the value of the ith element.
     */
    public double get(int i) {
        return elements[i];
    }

    /**
     * Adds a vector to the current vector
     */
    public Vector add(Vector vector) {
        if (this.size() != vector.size()) {
            throw new IllegalArgumentException("The dimensions of the vectors do not coincide.");
        }

        for (int i = 0; i < elements.length; i++) {
            elements[i] += vector.get(i);
        }
        return this;
    }

    /**
     * Subtracts a vector from the current vector
     */
    public Vector subtract(Vector vector) {
        if (this.size() != vector.size()) {
            throw new IllegalArgumentException("The dimensions of the vectors do not coincide.");
        }

        for (int i = 0; i < elements.length; i++) {
            elements[i] -= vector.get(i);
        }
        return this;
    }

    /**
     * Multiply the current vector by the given
     */
    public Vector multiply(double val) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] *= val;
        }
        return this;
    }

    /**
     * Divide the current vector by the given
     */
    public Vector divide(double val) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] /= val;
        }
        return this;
    }

    /**
     * Adds a value to the beginning of the current vector
     */
    public void addElementFirst(double val) {
        double[] res = new double[elements.length+1];
        res[0] = val;
        System.arraycopy(elements,0,res,1,elements.length);
        elements = res;
    }

    /**
     * Adds a value to the end of the current vector
     */
    public void addElement(double val) {
        double[] res = new double[elements.length+1];
        System.arraycopy(elements,0,res,0,elements.length);
        res[res.length-1] = val;
        elements = res;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Arrays.equals(elements, vector.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public String toString() {
        return "Vector" + Arrays.toString(elements);
    }
}
