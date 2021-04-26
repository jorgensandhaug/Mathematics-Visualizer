package org.canvas3d;

import org.math.Editable;
import org.utils.Interpolatable;
import org.utils.Interpolator;
import javafx.scene.paint.Color;
import org.math.Matrix;
import org.math3d.Vector3;
import org.terraingeneration.TerrainChunk;


/**
 * Is inherited by all classes that can be rendered onto the 3D canvas
 * This class contains a name, position, vectors that describe how the object is rotated i space
 * and all the triangles and vertices that the rendered object contains
 */
public abstract class Render3D implements Interpolatable, Editable {
    protected String name;
    protected Vector3 position;
    protected Vector3 forward;
    protected Vector3 up;
    protected Vector3 right;
    protected Triangle[] triangles;
    protected Vector3[] vertices;
    protected Interpolator interpolator;


    public Render3D(Vector3[] vertices, Triangle[] triangles, Vector3 position, Vector3 forward, Vector3 up){
        this(position, forward, up);
        this.triangles = triangles;
        this.vertices = vertices;
    }

    public Render3D(Vector3 position, Vector3 forward, Vector3 up) {
        this.position = position;
        this.forward = forward.normalized();
        this.up = up.normalized();
        this.right = Vector3.cross(up, forward);
        this.right = this.right.normalized();
    }

    public Render3D(Vector3 position){
        this(position, Vector3.FORWARD(), Vector3.UP());
    }

    public Render3D(){
        this(Vector3.ZERO());
    }

    public Render3D(Vector3[] vertices, Triangle[] triangles, Vector3 position){
        this(position);
        this.triangles = triangles;
        this.vertices = vertices;
    }

    /**
     * Sets the forward direction of the object, which then tries to set the other directions naturally according to the new forward
     */
    public void setForward(Vector3 forward) {
        if(forward.equals(Vector3.ZERO()))
            throw new IllegalArgumentException("Forward cant be null vector");

        Vector3 f = forward.normalized();
        double tol = Math.pow(10, -8);

        if(f.dot(this.up) < -(1-tol))
            this.up = Vector3.scale(this.forward, 1);
        else if(f.dot(this.up) > 1-tol)
            this.up = Vector3.scale(this.forward, -1);

        else{
            this.up = Vector3.difference(up, Vector3.scale(f, f.dot(up))).normalized();
        }

        this.forward = Vector3.copy(f);
        this.right = Vector3.cross(up, f);

        if(Math.abs(this.forward.dot(this.up)) > tol){
            System.out.println("Det er en error i setforward funksjonen på Render3D, dot = " + this.forward.dot(this.up));
        }
    }

    public void setPosition(Vector3 position){
        this.position = position;
    }

    /**
     * Sets the forward vectors such that the object points at the given input vector
     */
    public void pointAt(Vector3 target){
        setForward(Vector3.difference(target, position));
    }

    /**
     * Rotates the object around the x axis
     */
    public void rotateX(double angle){
        forward = Vector3.rotateX(forward, angle);
        up = Vector3.rotateX(up, angle);
        right = Vector3.rotateX(right, angle);
    }
    /**
     * Rotates the object around the y axis
     */
    public void rotateY(double angle){
        forward = Vector3.rotateY(forward, angle);
        up = Vector3.rotateY(up, angle);
        right = Vector3.rotateY(right, angle);
    }
    /**
     * Rotates the object around the z axis
     */
    public void rotateZ(double angle){
        forward = Vector3.rotateZ(forward, angle);
        up = Vector3.rotateZ(up, angle);
        right = Vector3.rotateZ(right, angle);
    }

    /**
     * Rotates the object around its own right axis, aka tilt
     */
    public void rotateOwnRight(double angle){
        forward = Vector3.rotate(right, forward, angle);
        up = Vector3.rotate(right, up, angle);
    }

    /**
     * Rotates the object around its own up axis, aka spin
     */
    public void rotateOwnUp(double angle){
        forward = Vector3.rotate(up, forward, angle);
        right = Vector3.rotate(up, right, angle);
    }

    /**
     * Rotates the object around its own forward axis, aka roll
     */
    public void rotateOwnForward(double angle){
        up = Vector3.rotate(forward, up, angle);
        right = Vector3.rotate(forward, right, angle);
    }

    /**
     * Moves the object along its forward vector based on the scalar
     */
    public void moveForward(double scalar){
        position = Vector3.sum(position, Vector3.scale(forward, scalar));
    }
    /**
     * Moves the object along its right vector based on the scalar
     */
    public void moveRight(double scalar){
        position = Vector3.sum(position, Vector3.scale(right, scalar));
    }
    /**
     * Moves the object along its up vector based on the scalar
     */
    public void moveUp(double scalar){
        position = Vector3.sum(position, Vector3.scale(up, scalar));
    }

    /**
     * Renders the object to the 3D canvas, if this method is not overridden, it defaults to just rendering all the triangles
     */
    public void render(GraphicsContext3D gc){
        beforeRender();
        handleInterpolation();

        if(triangles == null) //ingenting som skal renderes
            return;

        if(this instanceof TerrainChunk)
            CanvasRenderer3D.chunksRenderedCount++;


        for(Triangle tri : triangles)
            tri.render(gc, position, forward, up, right);
    }


    /**
     * This method is called before each object is rendered, and it must be implemented so that the object for example can
     * set the color before render
     */
    public abstract void beforeRender();

    public void setName(String name){
        this.name = name;
    }

    public void setColor(Color color){
        if(triangles == null) //ingenting som skal endres
            return;

        for(Triangle triangle : triangles){
            triangle.setColor(color);
            triangle.setShouldInterpolateColors(false);
        }
    }

    public void setColors(Color[] colors){
        if(triangles == null) //ingenting som skal endres
            return;

        for(Triangle triangle : triangles){
            triangle.setColors(colors);
            triangle.setShouldInterpolateColors(true);
        }
    }

    public void setColors(Color c1, Color c2, Color c3){
        if(triangles == null) //ingenting som skal endres
            return;

        for(Triangle triangle : triangles){
            triangle.setColors(new Color[]{c1, c2, c3});
            triangle.setShouldInterpolateColors(true);
        }
    }


    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getForward() {
        return forward;
    }

    public Vector3 getUp() {
        return up;
    }

    public Vector3 getRight() {
        return right;
    }





    private boolean isHidden = false;

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
     * Default implementation of the startInterpolation function which is
     * necessary for the default implementation of handleInterpolation to work
     */
    @Override
    public void startInterpolation(Matrix m, int millis) {
        double[] startForward = forward.getVector();
        double[] startRight = right.getVector();
        double[] startUp = up.getVector();

        double[] endForward = m.transform(startForward);
        double[] endRight = m.transform(startRight);
        double[] endUp = m.transform(startUp);

        double[] starts = new double[9];
        double[] ends = new double[9];

        for(int i = 0; i < 3; i++){
            starts[i] = startForward[i];
            starts[3 + i] = startRight[i];
            starts[6 + i] = startUp[i];

            ends[i] = endForward[i];
            ends[3 + i] = endRight[i];
            ends[6 + i] = endUp[i];
        }
        interpolator = new Interpolator(millis, starts, ends);
    }

    /**
     * Default implementation of the handleInterpolation, which just interpolates all direction vectors and the position vector
     * between their start and end vector
     */
    @Override
    public void handleInterpolation() {
        if(interpolator != null){
            interpolator.handle();

            forward.setX(interpolator.get(0));
            forward.setY(interpolator.get(1));
            forward.setZ(interpolator.get(2));
            right.setX(interpolator.get(3));
            right.setY(interpolator.get(4));
            right.setZ(interpolator.get(5));
            up.setX(interpolator.get(6));
            up.setY(interpolator.get(7));
            up.setZ(interpolator.get(8));

            if(interpolator.isFinished())
                interpolator = null;
        }
    }

    @Override
    public double[] getCopy() {
        return position.getCopy();
    }

    @Override
    public void set(double[] doubles) {
        position.set(doubles);
    }
}
