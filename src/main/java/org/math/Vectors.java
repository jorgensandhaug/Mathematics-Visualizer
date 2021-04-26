package org.math;

import org.graphics.VariableContainer;
import org.math3d.Vector3;

import java.util.stream.Stream;

public class Vectors {
    /**
     * Returns the sum of Vectors
     */
    public static Vector sum(Vector... args) throws IllegalArgumentException {
        int d = args[0].getDimensions();
        double[] u = new double[d];
        for (Vector v : args) {
            if (d != v.getDimensions())
                throw new IllegalArgumentException("Vectors must have same dimensions");
            for (int i = 0; i < d; i++) {
                u[i] += v.getElement(i);
            }
        }
        return new Vector(u);
    }

    /**
     * Returns the difference of two Vectors
     */
    public static Vector difference(Vector v1, Vector v2) throws IllegalArgumentException {
        if (v1.getDimensions() != v2.getDimensions())
            throw new IllegalArgumentException("Vectors must have same dimensions");

        double[] u = new double[v1.getDimensions()];

        for (int i = 0; i < u.length; i++) {
            u[i] = v1.getElement(i) - v2.getElement(i);
        }
        return new Vector(u);
    }

    /**
     * Returns the dot product of two vectors
     */
    public static double dot(Vector v, Vector u) throws IllegalArgumentException {
        if (u.getDimensions() != v.getDimensions())
            throw new IllegalArgumentException("Vectors must have the same dimensions");
        double dot = 0;
        for (int i = 0; i < u.getDimensions(); i++) {
            dot += (u.getElement(i) * v.getElement(i));
        }
        return dot;
    }

    /**
     * Returns the angle between two vectors
     */
    public static double angle(Vector v, Vector u) {
        return v.angle(u);
    }

    /**
     * Returns the signed angle between two vectors (-PI to PI), aka how much the first vector has to rotate to reach the second
     */
    public static double angle2(double[] v1, double[] v2) {
        if (v1.length != 2 || v2.length != 2)
            throw new IllegalArgumentException();
        return Math.atan2(v1[0] * v2[1] - v1[1] * v2[0], v1[0] * v2[0] + v1[1] * v2[1]);
    }

    /**
     * Returns the product of a vector and a double
     */
    public static Vector scale(Vector v, double s) {
        double[] d = new double[v.getDimensions()];
        for (int i = 0; i < v.getDimensions(); i++) {
            d[i] = v.getElement(i) * s;
        }
        return new Vector(d);
    }

    public static Vector cross(Vector v, Vector u) {
        return v.cross(u);
    }

    /**
     * Returns the dot product a matrix and a Vector
     */
    public static Vector transform(Vector v, Matrix matrix) {
        return matrix.transform(v);
    }

    /**
     * Returns the magnitude of a Vector
     */
    public static double getMagnitude(Vector v) {
        double ans = 0;
        for (double element : v.getVector()) {
            ans += Math.pow(element, 2);
        }
        return Math.sqrt(ans);
    }

    /**
     * Returns a Vector from a point
     */
    public static Vector fromPoint(Point p) {
        return new Vector(p.getPoint());
    }

    /**
     * Returns a Vector from two Points
     */
    public static Vector fromPoints(Point p, Point q) throws IllegalArgumentException {
        if (p.getDimensions() != q.getDimensions()) throw new IllegalArgumentException("Illegal number of dimensions");
        double[] d = new double[p.getDimensions()];
        for (int i = 0; i < p.getDimensions(); i++) {
            d[i] = p.getElement(i) - q.getElement(i);
        }
        return new Vector(d);
    }

    /**
     * Returns a Point from a Vector
     */
    public static Point toPoint(Vector v) {
        return new Point(v.getVector());
    }

    /**
     * Returns a Complex from a Vector
     */
    public static Complex toComplex(Vector v) throws IllegalArgumentException {
        if (v.getDimensions() != 2)
            throw new IllegalArgumentException("Vector must be two dimensional");
        return new Complex(v.getElement(0), v.getElement(1));
    }

    /**
     * Returns a Vector from a Complex
     */
    public static Vector fromComplex(Complex c) {
        return new Vector(c.getRe(), c.getIm());
    }

    /**
     * ??
     */
    public static Vector parseVector(String... args) {
        return new Vector(Stream.of(args).mapToDouble(Double::parseDouble).toArray());
    }
}
