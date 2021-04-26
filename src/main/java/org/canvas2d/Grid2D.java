package org.canvas2d;

import org.utils.Interpolatable;
import javafx.scene.canvas.GraphicsContext;
import org.math.Matrix;
import org.math.Vector;
import org.linalgfx.Writable;

/**
 * Represents a two dimensional grid
 */
public class Grid2D extends Render2D implements Interpolatable, Writable {
    private final Vector iHat;
    private final Vector jHat;
    private final LineSegment2D[] lineSegments;
    private double startX;
    private double startY;
    private int h;
    private int w;
    private double sizeX;
    private double sizeY;

    public Grid2D(double startX, double startY, int h, int w, double sizeX, double sizeY){
        this.startX = startX;
        this.startY = startY;
        this.h = h;
        this.w = w;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        iHat = new Vector(1,0);
        jHat = new Vector(0,1);

        //add all the lines
        lineSegments = new LineSegment2D[h + w + 2];
        int i = 0;
        while(i < w + 1){
            lineSegments[i] = new LineSegment2D(startX + i*sizeX, startY, startX + i*sizeX, startY + sizeY*h);
            i++;
        }

        i = 0;
        while(i < h + 1){
            lineSegments[i + w + 1] = new LineSegment2D(startX, startY + i*sizeY, startX + w*sizeX, startY + i*sizeY);
            i++;
        }
    }

    /**
     * Returns the first basis vector of the grid
     */
    public Vector getI(){
        return iHat;
    }

    /**
     * Returns the second basis vector of the grid
     */
    public Vector getJ(){
        return jHat;
    }

    @Override
    public String toString(){
        return "iHat: " + iHat + ", jHat: " + jHat;
    }

    /**
     * Returns null
     */
    @Override
    public Object getMath() {
        return null;
    }

    /**
     * Renders the grid onto the canvas
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setStroke(paint);
        //gc.setLineWidth(1);
        for(LineSegment2D line : lineSegments) {
            line.handleInterpolation();
            line.render(gc);
        }
    }


    @Override
    public void startInterpolation(Matrix matrix, int millis){
        iHat.transform(matrix);
        jHat.transform(matrix);
        for(LineSegment2D line : lineSegments)
            line.startInterpolation(matrix, millis);
    }


    @Override
    public void handleInterpolation() {
        for(LineSegment2D line : lineSegments) {
            line.handleInterpolation();
        }
    }

    @Override
    public String writeString() {
        return "org.canvas2d.Grid2D---"+this.iHat.getVector()[0]+" "+this.iHat.getVector()[1]+" "+this.jHat.getVector()[0]+" "+this.jHat.getVector()[1]
                +" "+this.startX+" "+this.startY+" "+this.h+" "+this.w+" "+this.sizeX+" "+this.sizeY;
    }

    //Loading string from file
    public Grid2D(String fileString){
        String[] nums = fileString.split(" ");
        double[] nums2 = new double[nums.length];
        for(int i = 0; i < nums.length; i++)
            nums2[i] = Double.parseDouble(nums[i]);


        iHat = new Vector(nums2[0],nums2[1]);
        jHat = new Vector(nums2[2],nums2[3]);

        startX = nums2[4];
        startY = nums2[5];
        h =(int) nums2[6];
        w = (int) nums2[7];
        sizeX = nums2[8];
        sizeY = nums2[9];
        //add all the lines
        lineSegments = new LineSegment2D[h + w + 2];
        int i = 0;
        while(i < w + 1){
            lineSegments[i] = new LineSegment2D(startX + i*sizeX, startY, startX + i*sizeX, startY + sizeY*h);
            i++;
        }

        i = 0;
        while(i < h + 1){
            lineSegments[i + w + 1] = new LineSegment2D(startX, startY + i*sizeY, startX + w*sizeX, startY + i*sizeY);
            i++;
        }
    }

}
