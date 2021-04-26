package org.canvas2d;

import javafx.scene.canvas.GraphicsContext;
import org.math.Expression;
import org.linalgfx.Writable;

/**
 * Represents a graphical function from R to R
 */
public class Mapping extends Render2D implements Writable {
    //TODO fix problems related to rendering
    private final double start;
    private final double end;
    private final double step;
    private String name;
    private Expression expression;


    public Mapping(String expression){
        this.expression = new Expression(expression);
        start = -10;
        end = 10;
        step = 1;
    }

    public Mapping(Expression expression){
        this.expression = expression;

        start = -10;
        end = 10;
        step = 1;
    }

    /**
     * Returns the function evaluated at a given x
     */
    public double evaluate(double x){
        return expression.evaluate(x);
    }


    @Override
    public String toString(){
        return expression.toString();
    }

    /**
     * Returns the Expression underlying the mapping
     */
    @Override
    public Object getMath() {
        return expression;
    }

    /**
     * Renders the function onto the 2D canvas
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setLineWidth(1.5);
        gc.setStroke(paint);
        //non-transformable
        double prev = start;
        for(int x = 0; x < CanvasRenderer2D.getCanvasWidth(); x+= step){

            gc.strokeLine(prev, CanvasRenderer2D.toCanvasY(evaluate(CanvasRenderer2D.fromCanvasX(prev))), x, CanvasRenderer2D.toCanvasY(evaluate(CanvasRenderer2D.fromCanvasX(x))));

            prev = x;
        }
    }

    @Override
    public String writeString() {
        return "org.canvas2d.Mapping---"+expression.writeString();
    }

}
