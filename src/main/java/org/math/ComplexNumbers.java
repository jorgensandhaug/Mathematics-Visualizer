package org.math;

/**
 * Support class for Complex
 */
public class ComplexNumbers {

    /**
     * Returns a Complex from a 2D vector
     */
    public static Complex fromVector(Vector v) throws IllegalArgumentException{
        if(v.getDimensions() != 2)
            throw new IllegalArgumentException("The number of dimensions must be 2");

        return new Complex(v.getElement(0), v.getElement(1));
    }

    /**
     * Returns a Complex from length and angle
     */
    public static Complex fromPolar(double length, double angle){
        if(length<0)
            throw new IllegalArgumentException("Length can not be negative");
        var c = new Complex(0,0);
        c.setLength(length);
        c.setAngle(angle);
        c.setStandardFromPolar();
        return c;
    }

    /**
     * Returns the product of two Complex
     */
    public static Complex multiply(Complex c1, Complex c2){
        return new Complex(c1.getRe()*c2.getRe() - c1.getIm()*c2.getIm(), c1.getRe()*c2.getIm() + c1.getIm()*c2.getRe());
    }

    /**
     * Returns the sum of two complex
     */
    public static Complex add(Complex c1, Complex c2){
        return new Complex(c1.getRe() + c2.getRe(), c1.getIm() + c2.getIm());
    }

    /**
     * Returns a copy of a Complex
     */
    public static Complex copyOf(Complex z){
        return new Complex(z.getRe(), z.getIm());
    }

    /**
     * Returns the a Complex to the power given as argument
     */
    public static Complex pow(Complex z, double exponent){
        Complex c = copyOf(z);
        c.pow(exponent);
        return c;
    }
}
