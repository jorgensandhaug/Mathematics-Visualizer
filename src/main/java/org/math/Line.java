package org.math;

import org.graphics.VariableContainer;
import org.linalgfx.Writable;

/**
 * Represents a mathematical line
 */
public class Line implements Writable {
    private final Point start;
    private final Vector direction;

    public Line(Point p, Vector v){
        if(p.getDimensions()!=v.getDimensions())
            throw new IllegalArgumentException("Point and vector must be of equal dimension");
        start = p;
        direction = v;
    }

    public Line(double pointX, double pointY, double directionX, double directionY){
        start = new Point(pointX, pointY);
        direction = new Vector(directionX, directionY);
    }

    public int getDimensions(){
        return direction.getDimensions();
    }

    /**
     * Returns the Point of intersection with a given line
     */
    public Point intersection(Line l){
        double a = start.getElement(0);double c = start.getElement(1);double e = l.getStart().getElement(0);double g = l.getStart().getElement(1);
        double b = direction.getElement(0);double d = direction.getElement(1); double f = l.getDirection().getElement(0); double h = l.getDirection().getElement(1);
        double t = (-a*h+c*f+e*h-f*g)/(b*h-d*f);
        return getPoint(t);
    }

    /**
     * Returns the Point corresponding to the Line evaluated at a parameter t
     */
    public Point getPoint(double parameter){
        return new Point(start.getElement(0) + parameter * direction.getElement(0), start.getElement(1) + parameter * direction.getElement(1));
    }

    /**
     * Returns the direction-Vector of the Line
     */
    public Vector getDirection(){
        return direction;
    }

    /**
     * Returns the starting-Point of the Line
     */
    public Point getStart(){
        return start;
    }



    @Override
    public String toString(){
        return "Start: " + start + ", Direction: " + direction;
    }


    @Override
    public String writeString() {
        StringBuilder string = new StringBuilder("org.math.Line---" + getDimensions());
        for(double d:start.getPoint())
            string.append(d).append(" ");
        for(double d:direction.getVector())
            string.append(d).append(" ");
        return string.toString();
    }

    //From file
    public Line(String fileString){
        String[] nums = fileString.split(" ");
        int length = Integer.parseInt(nums[0]);
        double[] p = new double[(nums.length-1)/2];
        double[] v = new double[(nums.length-1)/2];
        for(int i = 1; i<(nums.length-1)/2+1; i++)
            p[i-1] = Double.parseDouble(nums[i]);
        for(int i = (nums.length-1)/2+1; i<nums.length; i++)
            p[i-(nums.length-1)/2+1] = Double.parseDouble(nums[i]);
        start = new Point(p);
        direction = new Vector(v);
    }

}
