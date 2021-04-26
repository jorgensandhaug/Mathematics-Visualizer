package org.math2d;

import org.math.Editable;
import org.math.Point;



/**
 * Represents a two dimensional point
 */
public class Point2 extends Point {
    public Point2(double x, double y){
        super(x, y);
    }


    /**
     * Returns the x coordinate of the point
     */
    public double getX(){
        return getElement(0);
    }

    /**
     * Returns the y coordinate of the point
     */
    public double getY(){
        return getElement(1);
    }

    /**
     * Sets the x coordinate of the point
     */
    public void setX(double x){
        setElement(0, x);
    }
    /**
     * Sets the y coordinate of the point
     */
    public void setY(double y){
        setElement(1, y);
    }
}
