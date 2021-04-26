package org.canvas3d;

import org.linalgfx.DefinedVariables;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles rendering of 3D objects onto the canvas
 */
public abstract class CanvasRenderer3D {
    private static Canvas canvas;
    private static GraphicsContext graphicsContext2D;
    private static GraphicsContext3D graphicsContext3D;
    public static long deltaTime;
    public static Camera3D camera3D;

    public static int chunksRenderedCount;
    public static int chunksSpawnedCount;
    public enum CameraMode{STANDARD, FPS};
    public static CameraMode cameraMode = CameraMode.STANDARD;

    //private static final List<Render3D> gameObjects = new ArrayList<>();

    /**
     * Starts an animation timer
     */
    public static void start(){
        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime;
            @Override
            public void handle(long now) {
                chunksRenderedCount = 0;
                deltaTime = (now - lastFrameTime) / 1000000;
                graphicsContext3D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                camera3D.updateMatrix();
                graphicsContext3D.clearDepthBuffer();

                camera3D.render(graphicsContext3D);
                DefinedVariables.get3DRenderables().forEach(r -> {
                    r.getVariable().render(graphicsContext3D);
                });
                DefinedVariables.updateText();



                graphicsContext2D.setFont(new Font(13));
                graphicsContext2D.setFill(Paint.valueOf("black"));
                if(deltaTime > 0)
                    graphicsContext2D.fillText("FPS: " + 1000/deltaTime, 10, 10);

                lastFrameTime = now;
            }
        };
        animationTimer.start();
    }

    /**
     * Initializes the Camera3D and the 3D graphic context (which internally is using the canvas 2D graphics context)
     */
    public static void setGraphicsContext(GraphicsContext graphicsContext2D) {
        CanvasRenderer3D.graphicsContext2D = graphicsContext2D;
        camera3D = new Camera3D();
        CanvasRenderer3D.graphicsContext3D = new GraphicsContext3D(graphicsContext2D, camera3D);
    }

    public static void setCanvas(Canvas canvas) {
        CanvasRenderer3D.canvas = canvas;
    }

    public static double getCanvasWidth() {
        return canvas.getWidth();
    }

    public static double getCanvasHeight() {
        return canvas.getHeight();
    }

    public static Camera3D getCamera(){
        return camera3D;
    }


    public static Collection<LightSource> getLightSources(){return DefinedVariables.get3DRenderables().stream().filter(var -> var.getVariable() instanceof LightSource).map(var -> (LightSource)var.getVariable()).collect(Collectors.toList());}

}
