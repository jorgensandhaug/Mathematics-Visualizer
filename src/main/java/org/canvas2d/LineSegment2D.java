package org.canvas2d;

import javafx.scene.canvas.GraphicsContext;
import org.math.*;
import org.utils.Interpolatable;
import org.utils.Interpolator;
import org.linalgfx.Writable;

/**
 * Represents a graphical two dimensional line segment
 */
public class LineSegment2D extends Render2D implements Interpolatable, Writable {
    private final Point2D start;
    private final Point2D end;
    private Interpolator interpolator;

    public LineSegment2D(double x1, double y1, double x2, double y2){
        start = new Point2D(x1, y1);
        end = new Point2D(x2, y2);
    }

    /**
     * Returns null
     */
    @Override
    public Object getMath() {
        return null;
    }

    /**
     * Renders the line segment onto the 2D canvas
     */
    @Override
    public void render(GraphicsContext gc){
        gc.setLineWidth(1);
        //linear interpolation for the points
        handleInterpolation();

        gc.setStroke(paint);

        gc.strokeLine(CanvasRenderer2D.toCanvasX(start.getPoint().getX()), CanvasRenderer2D.toCanvasY(start.getPoint().getY()), CanvasRenderer2D.toCanvasX(end.getPoint().getX()), CanvasRenderer2D.toCanvasY(end.getPoint().getY()));
    }


    @Override
    public void startInterpolation(Matrix m, int millis) {
        start.startInterpolation(m , millis);
        end.startInterpolation(m, millis);
    }


    @Override
    public void handleInterpolation() {
        start.handleInterpolation();
        end.handleInterpolation();
    }

    @Override
    public String toString(){
        return "Start: " + start.toString() + "\tEnd: " + end.toString();
    }

    @Override
    public String writeString() {
        return "org.canvas2d.LineSegment2D---"+start.getPoint().getX()+" "+start.getPoint().getY()+end.getPoint().getX()+" "+end.getPoint().getY();
    }

    //Loading string from file
    public LineSegment2D(String fileString){
        String[] nums = fileString.split(" ");
        this.start = new Point2D(Double.parseDouble(nums[0]), Double.parseDouble(nums[1]));
        this.end = new Point2D(Double.parseDouble(nums[2]), Double.parseDouble(nums[3]));
    }
}
