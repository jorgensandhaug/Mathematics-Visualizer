package org.math;

/**
 * Support class for Point
 */
public abstract class Points {
    //vær forsiktig her med entanglement!!
    /**
     * Returns a Vector from a Point
     */
    public static Vector toVector(Point p){
        return new Vector(p.getPoint());
    }

    /**
     * Returns a Point from a Vector
     */
    public static Point fromVector(Vector v){
        return new Point(v.getVector());
    }

    /**
     * Returns the difference of two Points
     */
    public static Point subtract(Point p1, Point p2){
        if(p1.getDimensions()!= p2.getDimensions())
            throw new IllegalArgumentException("Dimension must be equal");
        return add(p1, scale(p2, -1));
    }

    /**
     * Returns the product of a Point and a Matrix
     */
    public static Point transform(Point p, Matrix matrix){
        return matrix.transform(p.toVector()).toPoint();
    }

    /**
     * Returns the sum of two Points
     */
    public static Point add(Point p1, Point p2){
        if(p1.getDimensions()!= p2.getDimensions())
            throw new IllegalArgumentException("Dimension must be equal");
        return fromVector(Vectors.sum(toVector(p1), toVector(p2)));
    }

    /**
     * Returns a Point scaled by a double
     */
    public static Point scale(Point point, double s) {
        Vector newVector = toVector(point);
        newVector.scale(s);
        return fromVector(newVector);
    }
}
