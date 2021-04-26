package org.math2d;

import org.math.Vector;



/**
 * Represents a two dimensional vector
 */
public class Vector2 extends Vector  {
    public Vector2(double x, double y){
        super(x, y);
    }

    /**
     * Returns the x coordinate of the vector
     */
    public double getX(){
        return getElement(0);
    }
    /**
     * Returns the y coordinate of the vector
     */
    public double getY(){
        return getElement(1);
    }
    /**
     * Sets the x coordinate of the vector
     */
    public void setX(double x){
        setElement(0, x);
    }
    /**
     * Sets the y coordinate of the vector
     */
    public void setY(double y){
        setElement(1, y);
    }
}
