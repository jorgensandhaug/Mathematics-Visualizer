package org.math;

import org.linalgfx.Writable;

/**
 * Represents a complex number and contains methods that correlate to operations on complex numbers
 */
public class Complex implements Writable {
    private double re;
    private double im;
    private double length;
    private double angle;
    private boolean isHidden = false;

    public Complex(double re, double im){
        this.re = re;
        this.im = im;
        setPolarFromStandard();
    }

    public Complex(){
        this.re = 0;
        this.im = 0;
        setPolarFromStandard();
    }

    /**
     * Returns the sum of the Complex the method is called on and the one given as input
     */
    public void add(Complex other){
        Complex z = ComplexNumbers.add(this, other);
        setRe(z.getRe());
        setIm(z.getIm());
        setPolarFromStandard();
    }

    /**
     * Returns the product of the Complex the method is called on and the one given as input
     */
    public void multiply(Complex other){
        Complex z = ComplexNumbers.multiply(this, other);
        setRe(z.getRe());
        setIm(z.getIm());
        setPolarFromStandard();
    }

    /**
     * Returns the the Complex to the power of the argument
     */
    public void pow(double exponent){
        double l = Math.pow(this.length, exponent);
        double a = this.angle * exponent;
        setLength(l);
        setAngle(a);
        setStandardFromPolar();
    }

    /**
     * Return the polar form of the Complex as a string
     */
    public String asPolar(){
        return length + "e^i*" + angle;
    }

    /**
     * Sets the fields relevant to polar representation
     */
    public void setPolarFromStandard(){
        length = Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
        if(re > 0)
            angle = Math.atan(im / re);
        else if(re==0)
            angle = 0;
        else
            angle = Math.atan(im / re) + Math.PI;
    }

    /**
     * Sets the fields relevant to standard representation
     */
    public void setStandardFromPolar(){
        re = length * Math.cos(angle);
        im = length * Math.sin(angle);
    }


    @Override
    public String toString(){
        if(im>0)
            return re + " + " + im + "i";
        return re + " - " + Double.toString(im).replace("-","")+"i";
    }


    /**
     * Returns the real component
     */
    public double getRe() {
        return re;
    }

    /**
     * Sets the real component
     */
    public void setRe(double re) {
        this.re = re;
        setPolarFromStandard();
    }

    /**
     * Returns the imaginary component
     */
    public double getIm() {
        return im;
    }

    /**
     * Sets the imaginary component
     */
    public void setIm(double im) {
        this.im = im;
        setPolarFromStandard();
    }

    /**
     * Returns the length of the Complex
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets the length of the Complex
     */
    public void setLength(double length) {
        if(length<0)
            throw new IllegalArgumentException("Length can not be negative");
        this.length = length;
        setStandardFromPolar();
    }

    /**
     * Returns the angle in relation to the positive re-axis
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the Complex
     */
    public void setAngle(double angle) {
        this.angle = angle;
        setStandardFromPolar();
    }

    @Override
    public String writeString() {
        return "org.math.Complex---"+re+" "+im;
    }

    public Complex(String fileString){
        this.re = Double.parseDouble(fileString.split(" ")[0]);
        this.im = Double.parseDouble(fileString.split(" ")[1]);
        setPolarFromStandard();
    }


    public boolean equals(Complex other){
        return im == other.im && re == other.re;
    }
}

