package org.canvas2d;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

/**
 * Handles moving and zooming on the canvas rendering the 2D graphics
 */
public class CanvasPane2D extends Pane {

    private final Canvas canvas;
    private double startDragX;
    private double startDragY;
    //private double scrollScale = Math.pow(2,(double)1/5);
    private final double scrollScale = 0.1;

    public CanvasPane2D(double width, double height) {
        canvas = new Canvas(width, height);
        getChildren().add(canvas);

        canvas.setOnMousePressed(startDragEvent);
        canvas.setOnMouseDragged(endDragEvent);
        canvas.setOnScroll(scrollEvent);
    }

    /**
     * Returns the canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Handles the canvas binding to the canvasPane when the pane is stretched/squeezed
     */
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        final double x = snappedLeftInset();
        final double y = snappedTopInset();

        final double w = snapSizeX(getWidth()) - x - snappedRightInset();
        final double h = snapSizeY(getHeight()) - y - snappedBottomInset();
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        canvas.setWidth(w);
        canvas.setHeight(h);


        CanvasRenderer2D.accountForChanges();
    }

    /**
     * Registers the start of a drag on the canvas element
     */
    private EventHandler<MouseEvent> startDragEvent = mouse -> {
        startDragX = mouse.getX();
        startDragY = mouse.getY();
    };

    /**
     * Updates the offset of the coordinate system in relation to the progression of the drag event
     */
    private EventHandler<MouseEvent> endDragEvent = mouse -> {
        double endDragX = mouse.getX();
        double endDragY = mouse.getY();

        CanvasRenderer2D.changeOffsetX(endDragX - startDragX);
        CanvasRenderer2D.changeOffsetY(endDragY - startDragY);

        CanvasRenderer2D.accountForChanges();

        startDragX = mouse.getX();
        startDragY = mouse.getY();
    };

    /**
     * Clamps the scroll amount, so that it is not possible to scroll too fast
     */
    private double clampScroll(double val){
        if(Math.abs(val) < 1.05)
            return 1.05;
        if(Math.abs(val) > 1.2)
            return 1.2;
        return Math.abs(val);
    }

    /**
     * Registers and handles scrolling
     */
    private EventHandler<ScrollEvent> scrollEvent = event ->{
        if(event.getDeltaY() > 0)
            CanvasRenderer2D.scaleUnitSize(clampScroll(event.getDeltaY()));
        else
            CanvasRenderer2D.scaleUnitSize(1/clampScroll(event.getDeltaY()));

        CanvasRenderer2D.accountForChanges();
    };
}
