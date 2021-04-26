package org.canvas2d;

import org.utils.Interpolatable;
import org.utils.Interpolator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import org.math.*;
import org.math2d.Point2;
import org.linalgfx.Writable;

import java.util.Arrays;

/**
 * Represents a graphical two dimensional point
 */
public class Point2D extends Render2D implements Interpolatable, Writable, Editable {
    private Interpolator interpolator;
    private final Point2 point2;

    public Point2D(double x, double y){
        this.point2 = new Point2(x, y);
    }

    public Point2D(Point2 p){
        this.point2 = p;
    }


    @Override
    public void startInterpolation(Matrix m, int millis) {
        double[] p = point2.getPoint();
        double[] endPos = m.transform(p);
        double startAngle = Math.atan2(p[1], p[0]);
        double endAngle = startAngle + Vectors.angle2(p, endPos);
        double startLength = Math.sqrt(Math.pow(p[0], 2) + Math.pow(p[1], 2));
        double endLength = Math.sqrt(Math.pow(endPos[0], 2) + Math.pow(endPos[1], 2));
        interpolator = new Interpolator(millis, new double[]{startLength, startAngle}, new double[]{endLength, endAngle});
    }

    @Override
    public void handleInterpolation() {
        if(interpolator != null){
            interpolator.handle();
            //0 is the length, and 1 is the angle
            point2.setElement(0, interpolator.get(0) * Math.cos(interpolator.get(1)));
            point2.setElement(1, interpolator.get(0) * Math.sin(interpolator.get(1)));
            if(interpolator.isFinished())
                interpolator = null;
        }
    }

    /**
     * Returns the mathematical point
     */
    @Override
    public Object getMath() {
        return point2;
    }

    /**
     * Renders the point onto the 2D canvas
     */
    @Override
    public void render(GraphicsContext gc){
        double[] p = point2.getPoint();
        //linear interpolation
        handleInterpolation();

        gc.setFill(paint);

        gc.fillOval(CanvasRenderer2D.toCanvasX(p[0]) - 5, CanvasRenderer2D.toCanvasY(p[1])- 5, 10,10);
        gc.fillOval(CanvasRenderer2D.toCanvasX(p[0]) - 5, CanvasRenderer2D.toCanvasY(p[1])- 5, 10,10);
        if(name!=null){
            gc.setFill(Paint.valueOf("purple"));
            Vector d = Vectors.scale(Vectors.fromPoint(point2), 1/ point2.toVector().getMagnitude()/3);
            gc.fillText(name, CanvasRenderer2D.toCanvasX(p[0]+d.getElement(0)), CanvasRenderer2D.toCanvasY(p[1]+d.getElement(1)));
        }
    }

    /**
     * Returns point2
     */
    public Point2 getPoint(){
        return point2;
    }

    @Override
    public String toString(){
        return point2.toString();
    }
    @Override
    public String writeString() {
        return "org.canvas2d.Point2D---"+this.point2.getX()+" "+this.point2.getY();
    }
    //load from file string
    public Point2D(String fileString){
        String[] xy = fileString.split(" ");
        this.point2 = new Point2(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
    }


    @Override
    public double[] getCopy() {
        return point2.getCopy();
    }

    @Override
    public void set(double[] doubles) {
        point2.set(doubles);
    }
}

