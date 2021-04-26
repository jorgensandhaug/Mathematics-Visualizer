package org.math;

import org.math2d.Point2;
import org.math3d.Point3;
import org.linalgfx.Writable;
import org.utils.Utils;

import java.util.Arrays;

/**
 * Represents a mathematical point
 */
public class Point implements Transformable, Writable, Editable {
    private double[] point;

    public Point(double... args){
        point = args.clone();
    }

    /**
     * Returns the point as a an array
     */
    public double[] getPoint(){
        return point.clone();
    }

    /**
     * Returns a given element
     */
    public double getElement(int i){
        if(i>=point.length)
            throw new IllegalArgumentException("Index out of bounds");
        return point[i];
    }

    /**
     * Sets a given element
     */
    public void setElement(int i, double d){
        if(i>=point.length)
            throw new IllegalArgumentException("Index out of bounds");
        point[i] = d;
    }

    /**
     * Returns the dimension of the Point
     */
    public int getDimensions(){
        return point.length;
    }

    @Override
    public String toString(){
        String s = "";
        for(double element:point){
            s+=Double.toString(element)+", ";
        }
        return "("+s.substring(0,s.length()-2)+")";
    }

    /**
     * Returns the Point as a Vector
     */
    public Vector toVector(){
        return Vectors.fromPoint(this);
    }

    public Point2 toPoint2(){
        if(getDimensions()!=2)
            throw new IllegalArgumentException("Dimension must be 2");
        return new Point2(getElement(0), getElement(1));
    }

    public Point3 toPoint3(){
        if(getDimensions()!=3)
            throw new IllegalArgumentException("Dimension must be 3");
        return new Point3(getElement(0), getElement(1), getElement(2));
    }

    /**
     * Transforms the Point with a Matrix
     */
    @Override
    public void transform(Matrix m){
        point = m.transform(point);
    }

    @Override
    public String writeString() {
        StringBuilder str = new StringBuilder("org.math.Point---");
        for(Double d:point)
            str.append(d).append(" ");
        return str.toString();
    }

    //from file
    public Point(String fileString){
        String[] nums = fileString.split(" ");
        this.point = new double[nums.length];
        for(int i = 0; i < nums.length; i++){
            this.point[i] = Double.parseDouble(nums[i]);
        }
    }

    /**
     * Returns true if the Point is equivalent to another point, else false
     */
    public boolean equals(Point p){
        if(this.getDimensions()!=p.getDimensions())
            return false;

        for(int i = 0; i<point.length; i++){
            if(point[i] != p.getElement(i))
                return false;
        }
        return true;
    }

    @Override
    public double[] getCopy() {
        return getPoint();
    }

    @Override
    public void set(double[] doubles) {
        point = doubles.clone();
    }
}
