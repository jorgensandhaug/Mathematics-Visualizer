package org.canvas3d;

import org.canvas2d.CanvasRenderer2D;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import org.math3d.Vector3;

/**
 * Handles moving and zooming on the canvas rendering the 3D graphics
 */
public class CanvasPane3D extends Pane {

    private final Canvas canvas;
    private boolean mouseIsPressed;
    private final double mouseSensitivity = 1;
    private double previousX;
    private double previousY;

    public CanvasPane3D(double width, double height) {
        canvas = new Canvas(width, height);
        //canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());

        getChildren().add(canvas);
        canvas.setOnScroll(scrollHandler);
        canvas.setOnKeyPressed(keyDownHandler);
        canvas.setOnKeyReleased(keyUpHandler);
        canvas.setOnMouseDragged(mouseHandler);
        canvas.setOnMousePressed(event -> {
            previousX = event.getX();
            previousY = event.getY();
            this.mouseIsPressed = true;
        });
        canvas.setOnMouseReleased(event -> {
            this.mouseIsPressed = false;
        });
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
     * Handles mouse movement, and acts differently based on the camera movement mode selected
     */
    private EventHandler<MouseEvent> mouseHandler = mouseEvent -> {
        //TODO fix camera pitching

        Camera3D camera = CanvasRenderer3D.getCamera();
        double movementX = (mouseEvent.getX() - previousX) * mouseSensitivity * CanvasRenderer3D.deltaTime / CanvasRenderer3D.getCanvasWidth();
        double movementY = (mouseEvent.getY() - previousY) * mouseSensitivity * CanvasRenderer3D.deltaTime / CanvasRenderer3D.getCanvasHeight();

        //fps camera
        if(CanvasRenderer3D.cameraMode == CanvasRenderer3D.CameraMode.FPS){
            camera.rotateY(-movementX / 3);
        }

        //standard camera
        else{
            camera.setPosition(Vector3.rotateY(camera.position, -movementX));
            //camera.setPosition(Vector3.add(camera.position, Vector3.scale(Vector3.UP(), movementY)));
            camera.pointAt(Vector3.ZERO());
        }


        previousX = mouseEvent.getX();
        previousY = mouseEvent.getY();
    };

    /**
     * Handles key inputs, and moves the player/camera accordingly
     */
    private EventHandler<KeyEvent> keyDownHandler = keyEvent ->{
        if(CanvasRenderer3D.cameraMode == CanvasRenderer3D.CameraMode.STANDARD)
            return;

        if(keyEvent.getCode().equals(KeyCode.SPACE))
            CanvasRenderer3D.getCamera().UP = true;

        else if(keyEvent.getCode().equals(KeyCode.SHIFT))
            CanvasRenderer3D.getCamera().DOWN = true;

        else if(keyEvent.getCode().equals(KeyCode.D))
            CanvasRenderer3D.getCamera().RIGHT = true;

        else if(keyEvent.getCode().equals(KeyCode.A))
            CanvasRenderer3D.getCamera().LEFT = true;

        else if(keyEvent.getCode().equals(KeyCode.W))
            CanvasRenderer3D.getCamera().FORWARD = true;

        else if(keyEvent.getCode().equals(KeyCode.S))
            CanvasRenderer3D.getCamera().BACK = true;

        else if(keyEvent.getCode().equals(KeyCode.CONTROL))
            CanvasRenderer3D.getCamera().CONTROL = true;
    };

    private EventHandler<KeyEvent> keyUpHandler = keyEvent ->{
        //System.out.println("Character: " + keyEvent.getCode());

        if(keyEvent.getCode().equals(KeyCode.SPACE))
            CanvasRenderer3D.getCamera().UP = false;

        else if(keyEvent.getCode().equals(KeyCode.SHIFT))
            CanvasRenderer3D.getCamera().DOWN = false;

        else if(keyEvent.getCode().equals(KeyCode.D))
            CanvasRenderer3D.getCamera().RIGHT = false;

        else if(keyEvent.getCode().equals(KeyCode.A))
            CanvasRenderer3D.getCamera().LEFT = false;

        else if(keyEvent.getCode().equals(KeyCode.W))
            CanvasRenderer3D.getCamera().FORWARD = false;

        else if(keyEvent.getCode().equals(KeyCode.S))
            CanvasRenderer3D.getCamera().BACK = false;

        else if(keyEvent.getCode().equals(KeyCode.CONTROL))
            CanvasRenderer3D.getCamera().CONTROL = false;
    };

    /**
     * Handles mouse scroll, zooms out (aka moves the camera further away or closer to the origin) only if cameramode
     * is set to STANDARD, else returns without doing anything
     */
    private EventHandler<ScrollEvent> scrollHandler = scrollEvent ->{
        if(CanvasRenderer3D.cameraMode == CanvasRenderer3D.CameraMode.FPS)
            return;

        //skal kun funke med vanlig mode
        CanvasRenderer3D.getCamera().position.scale(1 - scrollEvent.getDeltaY() / 300);
    };
}
