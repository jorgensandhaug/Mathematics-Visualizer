package org.utils;

import org.canvas2d.CanvasRenderer2D;

/**
 * Handles interpolation for animations
 */
public class Interpolator {
    private final double[] starts;
    private final double[] ends;
    private final double[] actuals;
    private double progress;
    private double progressAngle;
    private final double millis;

    public Interpolator(double millis, double[] starts, double[] ends){
        if(ends.length != starts.length)
            throw new IllegalArgumentException("Invalid length");
        this.millis = millis;
        this.starts = starts;
        this.ends = ends;
        this.actuals = new double[ends.length];
    }

    /**
     * Handles the interpolation process for each double in the list of doubles, and sets the array of actual/current values
     * according to a sine function which moves in output from 0 to 1
     * (making the interpolation more "smooth", not so linear and rigid)
     */
    public void handle(){
        progressAngle += Math.PI/2 / millis * CanvasRenderer2D.deltaTime;
        progress = Math.sin(progressAngle);

        //lerping finished
        if(progressAngle >= Math.PI/2) //fix ending state
            progress = 1f;

        for(int i = 0; i < actuals.length; i++){
            actuals[i] = starts[i] + progress * (ends[i] - starts[i]);
        }
    }
    /**
     * Returns true if the interpolation is complete, else false
     */
    public boolean isFinished(){
        return progress == 1;
    }

    /**
     * Returns the current value for the interpolated double with index 4
     */
    public double get(int i){
        return actuals[i];
    }
}
