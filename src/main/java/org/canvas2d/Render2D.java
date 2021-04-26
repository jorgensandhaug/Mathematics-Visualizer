package org.canvas2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;



/**
 * Is inherited by all classes that can be rendered onto the 2D canvas
 */
public abstract class Render2D  {
    protected String name;
    protected Paint paint;
    private boolean isHidden = false;
    //TODO Add the ability for scalars to be rendered along the real axis
    //TODO Add the name of an object to the object in the graphics window

    /**
     * Represents the Render2D onto the canvas
     * This method gets called on every defined 2D variable every animation loop
     */
    public abstract void render(GraphicsContext gc);

    /**
     * Returns the mathematical object underlying the Render2D, if no such object exists, null is returned
     */
    public abstract Object getMath();

    /**
     * Returns true is the Render2D is hidden, else false
     */
    public boolean isHidden(){
        return isHidden;
    };

    /**
     * Toggles isHidden to false
     */
    public void show(){
        isHidden = false;
    };

    /**
     * Toggles isHidden to true
     */
    public void hide(){
        isHidden = true;
    };

    /**
     * Sets the name of the Render2D
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the color of the Render2D
     */
    public void setPaint(Paint paint){
        this.paint = paint;
    }
}
