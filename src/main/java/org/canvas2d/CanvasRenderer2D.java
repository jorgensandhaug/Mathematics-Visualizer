package org.canvas2d;

import org.linalgfx.DefinedVariables;
import org.graphics.VariableContainer;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import org.math.*;
import org.utils.Utils;

import java.text.DecimalFormat;

/**
 * Handles rendering of 2D objects onto the canvas
 */
public abstract class CanvasRenderer2D{
    private static Canvas canvas;
    private static GraphicsContext graphicsContext;
    private static double offsetX;
    private static double offsetY;
    public static double unitSize;
    private static double baseSpacing;
    public static long deltaTime;
    private static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Initializes the coordinate system and starts an animation timer
     */
    public static void start(){
        DefinedVariables.add(new Grid2D(0, 0, 5, 5, 1, 1), "grid");



        accountForChanges();

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime;
            @Override
            public void handle(long now) {
                deltaTime = (now - lastFrameTime) / 1000000;
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                drawLines();
                DefinedVariables.get2DRenderables().forEach(r -> {
                    r.getVariable().render(graphicsContext);
                });

                lastFrameTime = now;

                DefinedVariables.updateText();

                graphicsContext.setFill(Paint.valueOf("black"));
                if(deltaTime > 0)
                    graphicsContext.fillText("FPS: " + 1000/deltaTime, 10, 10);

            }
        };
        animationTimer.start();
    }


    /**
     * Sets the graphics context
     */
    public static void setGraphicsContext(GraphicsContext graphicsContext) {
        CanvasRenderer2D.graphicsContext = graphicsContext;
    }

    /**
     * Sets the canvas
     */
    public static void setCanvas(Canvas canvas) {
        CanvasRenderer2D.canvas = canvas;
    }

    /**
     * Returns the unit size of the coordinate system
     */
    public static double getUnitSize(){
        return unitSize;
    }

    /**
     * Sets the unit size of the coordinate system
     */
    public static void setUnitSize(double i){
        unitSize = i;
    }

    /**
     * Returns the width of the canvas
     */
    public static double getCanvasWidth() {
        return canvas.getWidth();
    }

    /**
     * Returns the height of the canvas
     */
    public static double getCanvasHeight() {
        return canvas.getHeight();
    }

    /**
     * Returns the position on the canvas correlating to a given mathematical point
     */
    public static Point toCanvasPoint(Point point) throws IllegalArgumentException {
        return new Point(toCanvasX(point.getElement(0)), toCanvasY(point.getElement(1)));
    }

    //public static Point fromCanvasPoint(Point point) throws IllegalNumberOfDimensionsException{ }

    /**
     * Returns the x value on the canvas correlating to a given number
     */
    public static double toCanvasX(double x){
        return getCanvasWidth()/2 + offsetX + x * unitSize;
    }

    /**
     * Returns the y value on the canvas correlating to a given number
     */
    public static double toCanvasY(double y){
        return getCanvasHeight() / 2 + offsetY - y * unitSize;
    }

    /**
     * Returns the the number correlating to an x value on the canvas
     */
    public static double fromCanvasX(double x){
        return (x - offsetX - getCanvasWidth()/2) / unitSize;
    }

    /**
     * Returns the the number correlating to an y value on the canvas
     */
    public static double fromCanvasY(double y){
        return - (y - offsetY - getCanvasHeight()/2) / unitSize;
    }

    /**
     * Renders the coordinate system and variables according to changes in zoom and offset
     */
    public static void accountForChanges(){
        //oppdaterer alle linjer
        for(VariableContainer<Render2D> variableContainer : DefinedVariables.get2DRenderables())
            if(variableContainer.getVariable() instanceof Line2D)
                ((Line2D) variableContainer.getVariable()).updateCanvasPoints();
    }

    /**
     * Adds to the offset in the x direction
     */
    public static void changeOffsetX(double x){
        offsetX += x;
    }

    /**
     * Adds to the offset in the y direction
     */
    public static void changeOffsetY(double y){
        offsetY += y;
    }

    /**
     * Scales the unit size
     */
    public static void scaleUnitSize(double s) {
        unitSize *= s;

        if (unitSize == 0)
            unitSize = Double.MIN_VALUE;
    }

    /**
     * Returns the offset in the x direction
     */
    public static double getOffsetX(){
        return offsetX;
    }

    /**
     * Returns the offset in the y direction
     */
    public static double getOffsetY(){
        return offsetY;
    }

    /**
     * Draws the lines of the coordinate system
     */
    public static void drawLines(){
        baseSpacing = 40;

        double spacing =  baseSpacing * Math.pow(2, Utils.log2(unitSize/baseSpacing) - Math.floor(Utils.log2(unitSize/baseSpacing)));

        long n = -Math.round(Math.floor(Utils.log2(unitSize/baseSpacing)));

        double originX = toCanvasX(0);
        double originY = toCanvasY(0);

        double dX = originX % spacing;
        double dY = originY % spacing;

        graphicsContext.setStroke(Paint.valueOf("black"));
        graphicsContext.setLineWidth(1);

        graphicsContext.strokeLine(originX, 0, originX, getCanvasHeight());
        graphicsContext.strokeLine(0, originY, getCanvasWidth(), originY);

        double x = dX;
        double y = dY;

        graphicsContext.setStroke(Paint.valueOf("grey"));
        graphicsContext.setFill(Paint.valueOf("black"));
        graphicsContext.setLineWidth(0.5);
        while(x <= getCanvasWidth()){
            graphicsContext.fillText(stringifyPowerOf2(fromCanvasX(x), n), x, originY-4);
            graphicsContext.strokeLine(x, 0, x, getCanvasHeight());
            x+=spacing;
        }


        while(y <= getCanvasHeight()){
            graphicsContext.fillText(stringifyPowerOf2(fromCanvasY(y), n), originX+6, y);
            graphicsContext.strokeLine(0, y, getCanvasWidth(), y);
            y+=spacing;
        }
    }

    /**
     * Returns a multiple of an integer power of two as a String
     */
    private static String stringifyPowerOf2(double d, long n){
        int k = (int) Math.round(d / Math.pow(2, n));

        if(k == 0)
            return "";
        //return k + ", "+n + ", d:" + df.format(d);

        if(n >= 0)
            return "" + (int) (k*Math.pow(2, n));

        int denominator = (int) Math.pow(2, -n);
        int gcd = Math.abs(Utils.gcd(k, denominator));
        denominator = denominator/gcd;
        if(denominator == 1)
            return ""+k/gcd;

        return "" + k/gcd + "/" + (int) Math.pow(2, -n) / gcd;
    }

    /**
     * Returns true if a mathematical point is contained in the set of points rendered by the canvas
     */
    public static boolean insideCanvas(Point point){
        Point actual = CanvasRenderer2D.toCanvasPoint(point);
        if(actual.getElement(0) < 0 || actual.getElement(0) > CanvasRenderer2D.getCanvasWidth() || actual.getElement(1) < 0 || actual.getElement(1) > CanvasRenderer2D.getCanvasHeight())
            return false;
        return true;
    }

    public static void main(String[] args) {
        Expression f = new Expression("x^5+x^4+3");
        Expression g = Differentiator.derivative(f);
        System.out.println(f);
        System.out.println(g);
        System.out.println(g.evaluate(1));
    }
}
