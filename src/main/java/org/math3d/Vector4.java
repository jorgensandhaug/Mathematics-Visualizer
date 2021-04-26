package org.math3d;

import org.math.Vector;



/**
 * Represents a four dimensional vector
 */
public class Vector4 extends Vector{

    public Vector4(double... ds){
        super(ds);
        if(ds.length != 4)
            throw new IllegalArgumentException("Illegal dimension!");
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
     * Returns the z coordinate of the vector
     */
    public double getZ(){
        return getElement(2);
    }

    /**
     * Returns the w coordinate of the vector
     */
    public double getW(){
        return getElement(3);
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

    /**
     * Sets the z coordinate of the vector
     */
    public void setZ(double z){
        setElement(2, z);
    }

    /**
     * Sets the w coordinate of the vector
     */
    public void setW(double w){
        setElement(3, w);
    }

    @Override
    public String toString(){
        return toString();
    }
}
