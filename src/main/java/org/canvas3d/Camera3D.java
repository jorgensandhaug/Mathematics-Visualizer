package org.canvas3d;

import org.linalgfx.DefinedVariables;
import org.math.Matrix;
import org.math3d.Vector3;
import org.math3d.Vector4;

/**
 * Movable camera which represents the player
 * Responable for translating points in 3D space and return their projected version on the canvas, including depth-information
 */
public class Camera3D extends Render3D{

    private final double fov = Math.PI/2;
    private final double zFar = 500; // bestemmer rendering distance
    private final double zNear = 0.1; //bestemmer rendering closest distance



    private Matrix projectionMatrix;
    private Matrix lookAtMatrix;
    public Camera3D(){
        super(new Vector3(0, 0, -6), Vector3.FORWARD(), Vector3.UP());
    }

    /**
     * Handles movement of the camera/player
     */
    @Override
    public void beforeRender() {
        double speed = movementSpeed * CanvasRenderer3D.deltaTime / 50;
        if(CONTROL)
            speed *= 2;
        //update movement
        if(FORWARD)
            moveForward(speed);
        if(BACK)
            moveForward(-speed);
        if(RIGHT)
            moveRight(speed);
        if(LEFT)
            moveRight(-speed);
        if(UP)
            moveUp(speed);
        if(DOWN)
            moveUp(-speed);

        if(CanvasRenderer3D.cameraMode == CanvasRenderer3D.CameraMode.STANDARD) {
            position = Vector3.rotateY(position, 0.003);
            pointAt(Vector3.ZERO());
        }
    }

//    //lightsource på camera
//    @Override
//    public void setPosition(Vector3 position){
//        lightSource.setPosition(position);
//        super.setPosition(position);
//    }


    @Override
    public Object getMath() {
        return null;
    }

    /**
     * Updates the matrices lookAtMatrix and projectionMatrix, which are used in the project method to project a poin
     * from 3D space to 2D (the details for how these matrices are constructed is to complex to explain here)
     */
    public void updateMatrix(){
        double f = 1d/(Math.tan(fov / 2));
        double a = CanvasRenderer3D.getCanvasHeight() / CanvasRenderer3D.getCanvasWidth();
        double q = 1/(zFar-zNear);
        double[][] matrix = {
                {a*f, 0, 0, 0},
                {0, -f, 0, 0},
                {0, 0, q, -q*zNear},
                {0, 0, 1, 0}
        };
        projectionMatrix = new Matrix(matrix);


//        Matrix pointAtMatrix = new Matrix(new double[][]{
//                {right.getX(), up.getX(), forward.getX(), position.getX()},
//                {right.getY(), up.getY(), forward.getY(), position.getY()},
//                {right.getZ(), up.getZ(), forward.getZ(), position.getZ()},
//                {0, 0, 0, 1}
//        });
        //lookAtMatrix = pointAtMatrix.getInverted();

        //denne er helt lik pointAt.getIverted();
        Matrix lookAt = new Matrix(new double[][]{
                {right.getX(), right.getY(), right.getZ(), -position.dot(right)},
                {up.getX(), up.getY(), up.getZ(),  -position.dot(up)},
                {forward.getX(), forward.getY(), forward.getZ(),  -position.dot(forward)},
                {0, 0, 0, 1}
        });

        lookAtMatrix = lookAt;
    }

    /**
     * Projects a point from 3D space to 2D Space and returns a 4D vector with extra info about the depth
     * by first transforming based on camera position and angle, and then projecting this point as if the
     * viewer was positioned at (0, 0, 0) facing the z positive direction
     */
    public Vector4 project(Vector3 vector3){
        Vector4 input = new Vector4(vector3.getX(), vector3.getY(), vector3.getZ(), 1);
        double[] cameraView = lookAtMatrix.transform(input.getVector());
        double[] out = projectionMatrix.transform(cameraView);
        //System.out.println("z:"+out[2]);

        if(out[2] < 0 || out[2] > 1)
            return null;

        out[0] /= out[3];
        out[1] /= out[3];

        //Translate to screen
        out[0] += 1d;
        out[1] += 1d;
        out[0] *= CanvasRenderer3D.getCanvasWidth() / 2;
        out[1] *= CanvasRenderer3D.getCanvasHeight() / 2;


        return new Vector4(out);
    }

    public double getRenderingDistance(){
        return zFar;
    }


    @Override
    public String toString(){
        return position.toString();
    }



    //KEYPRESSES and movement
    private double movementSpeed = 1;
    public boolean FORWARD;
    public boolean LEFT;
    public boolean RIGHT;
    public boolean BACK;
    public boolean UP;
    public boolean DOWN;
    public boolean CONTROL;
}
