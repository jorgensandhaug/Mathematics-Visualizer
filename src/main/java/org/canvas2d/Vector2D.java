package org.canvas2d;

import org.math.Editable;
import org.utils.Interpolatable;
import org.utils.Interpolator;
import javafx.scene.canvas.GraphicsContext;
import org.math.Matrix;
import org.math.Vector;
import org.math.Vectors;
import org.math2d.Vector2;
import org.linalgfx.Writable;

/**
 * Represents a graphical two dimensional vector
 */
public class Vector2D extends Render2D implements Interpolatable, Writable, Editable {
    private Interpolator interpolator;
    private final Vector2 vector2;

    public Vector2D(double x, double y){
        this.vector2 = new Vector2(x, y);
    }

    public Vector2D(Vector2 v){
        this.vector2 = v;
    }

    /**
     * Renders the vector onto the 2D canvas
     */
    @Override
    public void render(GraphicsContext gc){
        //linear interpolation
        handleInterpolation();

        gc.setFill(paint);
        gc.setStroke(paint);

        Vector distance = Vectors.scale(vector2, 1/ vector2.getMagnitude()/3);
        gc.fillText(name, CanvasRenderer2D.toCanvasX(vector2.getX()+distance.getElement(0)), CanvasRenderer2D.toCanvasY(vector2.getY()+distance.getElement(1)));


        gc.setLineWidth(1.5);
        gc.strokeLine(CanvasRenderer2D.toCanvasX(0), CanvasRenderer2D.toCanvasY(0), CanvasRenderer2D.toCanvasX(vector2.getX()), CanvasRenderer2D.toCanvasY(vector2.getY()));


        //fill arrow
        double angle = Math.atan2(vector2.getY(), vector2.getX());

        double arrowTipLength = 12;
        double startX = CanvasRenderer2D.toCanvasX(vector2.getX()) - arrowTipLength * Math.cos(angle); //move back so tip can be at exact location
        double startY = CanvasRenderer2D.toCanvasY(vector2.getY()) + arrowTipLength * Math.sin(angle); //move back so tip can be at exact location

        double arrowSideLength = 7;
        double[] xCoords = {
                CanvasRenderer2D.toCanvasX(vector2.getX()), //tipX
                startX + arrowSideLength * Math.sin(angle), //rightX
                startX - arrowSideLength * Math.sin(angle) //leftX
        };

        double[] yCoords = {
                CanvasRenderer2D.toCanvasY(vector2.getY()), //tipY
                startY + arrowSideLength * Math.cos(angle), //rightY
                startY - arrowSideLength * Math.cos(angle), //leftY
        };

        gc.fillPolygon(xCoords, yCoords, 3);
    }


    @Override
    public void handleInterpolation() {
        if(interpolator != null){
            interpolator.handle();
            //0 is the length, and 1 is the angle
            vector2.setX(interpolator.get(0) * Math.cos(interpolator.get(1)));
            vector2.setY(interpolator.get(0) * Math.sin(interpolator.get(1)));
            if(interpolator.isFinished())
                interpolator = null;
        }
    }

    @Override
    public String toString(){
        return vector2.toString();
    }


    @Override
    public void startInterpolation(Matrix m, int millis){
        double[] vec = vector2.getVector();
        double[] endPos = m.transform(vec);
        double startAngle = Math.atan2(vec[1], vec[0]);
        double endAngle = startAngle + Vectors.angle2(vec, endPos);
        double startLength = Math.sqrt(Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
        double endLength = Math.sqrt(Math.pow(endPos[0], 2) + Math.pow(endPos[1], 2));
        interpolator = new Interpolator(millis, new double[]{startLength, startAngle}, new double[]{endLength, endAngle});
    }

    /**
     * Represents the mathematical vector
     */
    @Override
    public Object getMath() {
        return vector2;
    }

    /**
     * Returns vector2
     */
    public Vector2 getVector(){
        return vector2;
    }

    @Override
    public String writeString() {
        return "org.canvas2d.Vector2D---"+this.vector2.getX()+" "+this.vector2.getY();
    }
    //load from file string
    public Vector2D(String fileString){
        String[] xy = fileString.split(" ");
        this.vector2 = new Vector2(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
    }

    @Override
    public double[] getCopy() {
        return vector2.getCopy();
    }

    @Override
    public void set(double[] doubles) {
        vector2.set(doubles);
    }
}
