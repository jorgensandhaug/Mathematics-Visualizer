package org.canvas2d;

import org.utils.Interpolatable;
import javafx.scene.canvas.GraphicsContext;
import org.math.Line;
import org.math.Matrix;
import org.math.Point;
import org.linalgfx.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

/**
 * Represents a graphical two dimensional line
 */
public class Line2D extends Render2D implements Interpolatable, Writable {
    private Vector2D direction;
    private Point2D start;
    private Line line;
    private boolean isInsideCanvas;

    private double canvasStartX = 0;
    private double canvasStartY = 0;
    private double canvasEndX = 0;
    private double canvasEndY = 0;

    public Line2D(Point2D start, Vector2D direction){
        this.start = start;
        this.direction = direction;
        this.line = new Line(start.getPoint(), direction.getVector());
    }

    public Line2D(double sx, double sy, double ex, double ey){
        this(new Point2D(sx, sy), new Vector2D(ex, ey));
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {
        start.startInterpolation(m, millis);
        direction.startInterpolation(m, millis);
    }


    @Override
    public void handleInterpolation() {
        direction.handleInterpolation();
        start.handleInterpolation();
        updateCanvasPoints();
    }

    /**
     * Returns a mathematical line object
     */
    @Override
    public Object getMath() {
        return line;
    }

    /**
     * Renders the line onto the canvas
     */
    @Override
    public void render(GraphicsContext gc){
        //linear interpolation of transformation
        handleInterpolation();


        //System.out.println(isInsideCanvas);
        if(!isInsideCanvas)
            return;

        gc.setStroke(paint);
        gc.setLineWidth(1);
        gc.strokeLine(canvasStartX, canvasStartY, canvasEndX, canvasEndY);
    }

    /**
     * Updates the points of intersection with the canvas, so that the line only has to be drawn between
     * the two intersection points when rendering to canvas
     */
    public void updateCanvasPoints(){
        if(Math.abs(direction.getVector().getX()) <= 0.001 / CanvasRenderer2D.unitSize){
            //vertical linje
            isInsideCanvas = true;
            if(start.getPoint().getX() <= CanvasRenderer2D.fromCanvasX(CanvasRenderer2D.getCanvasWidth()) && start.getPoint().getX() >= CanvasRenderer2D.fromCanvasX(0))
                isInsideCanvas = true;
            canvasStartX = CanvasRenderer2D.toCanvasX(start.getPoint().getX());
            canvasEndX = CanvasRenderer2D.toCanvasX(start.getPoint().getX());
            canvasStartY = 0;
            canvasEndY = CanvasRenderer2D.getCanvasHeight();
            return;
        }
        //calculate intersection with canvas
        List<Point> startEndPoints = new ArrayList<>();


        //x coords from origin for the vertical canvas borders
        double l1 = CanvasRenderer2D.fromCanvasX(0);
        double l3 = CanvasRenderer2D.fromCanvasX(CanvasRenderer2D.getCanvasWidth());

        //y coords from origin for the horizontal canvas borders
        double l2 = CanvasRenderer2D.fromCanvasY(0);
        double l4 = CanvasRenderer2D.fromCanvasY(CanvasRenderer2D.getCanvasHeight());

        //intersect left vertical
        double y1 = getYFromX(l1);
        if(y1 >= l4 && y1 <= l2) //intersection!
            startEndPoints.add(new Point(l1, y1));


        //intersect right vertical
        double y3 = getYFromX(l3);
        if(y3 >= l4 && y3 <= l2)//intersection!
            startEndPoints.add(new Point(l3, y3));

        //intersect top horizontal
        double x2 = getXFromY(l2);
        if(x2 <= l3 && x2 >= l1) //intersection!
            startEndPoints.add(new Point(x2, l2));

        //intersect bottom horizontal
        double x4 = getXFromY(l4);
        if(x4 <= l3 && x4 >= l1) //intersection!
            startEndPoints.add(new Point(x4, l4));

        if(startEndPoints.size() != 2){
            isInsideCanvas = false;
            return;
        }
        isInsideCanvas = true;



        canvasStartX = CanvasRenderer2D.toCanvasX(startEndPoints.get(0).getElement(0));
        canvasStartY = CanvasRenderer2D.toCanvasY(startEndPoints.get(0).getElement(1));
        canvasEndX = CanvasRenderer2D.toCanvasX(startEndPoints.get(1).getElement(0));
        canvasEndY = CanvasRenderer2D.toCanvasY(startEndPoints.get(1).getElement(1));

        //System.out.println("x1: " + canvasStartX + ", y1: " + canvasStartY + ", x2: " + canvasEndX + ", y2: " + canvasEndY);
    }

    /**
     * Returns the slope of the line
     */
    private double getA(){
        return direction.getVector().getY() / direction.getVector().getX();
    }

    /**
     * Returns the intersection between the line and the y-axis
     */
    private double getB(){
        return start.getPoint().getY() - getA()*start.getPoint().getX();
    }

    /**
     * Returns the y value of the line given an x value
     */
    private double getYFromX(double x){
        return getA() * x + getB();
    }

    /**
     * Returns the y value of the line given an x value
     */
    private double getXFromY(double y){
        return (y - getB()) / getA();
    }

    @Override
    public String toString(){
        return "Start: " + start + ", Direction: " + direction;
    }

    @Override
    public String writeString() {
        return "org.canvas2d.Line2D---"+start.getPoint().getX()+" "+start.getPoint().getY()+" "+direction.getVector().getX()+" "+direction.getVector().getY();
    }

    //load from file string
    public Line2D(String fileString){
        String[] nums = fileString.split(" ");
        this.start = new Point2D(Double.parseDouble(nums[0]), Double.parseDouble(nums[1]));
        this.direction= new Vector2D(Double.parseDouble(nums[2]), Double.parseDouble(nums[3]));
        this.line = new Line(start.getPoint(), direction.getVector());
    }
}
