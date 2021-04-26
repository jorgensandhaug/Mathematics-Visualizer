package org.math3d;

import org.math.Matrices;
import org.math.Matrix;
import org.math.Vector;
import org.math.Vectors;



/**
 * Represents a three dimensional vector
 */
public class Vector3 extends Vector {
    public Vector3(double... args){
        super(args);
        if(args.length != 3)
            throw new IllegalArgumentException("Illegal dimension");
    }


    /**
     * Returns the x coordinate of the vector
     */
    public double getX(){
        return getElement(0);
    }
    /**
     * Returns the y coordinate of the vector
     */
    public double getY(){
        return getElement(1);
    }
    /**
     * Returns the z coordinate of the vector
     */
    public double getZ(){
        return getElement(2);
    }
    /**
     * Sets the x coordinate of the vector
     */
    public void setX(double x){
        setElement(0, x);
    }
    /**
     * Sets the y coordinate of the vector
     */
    public void setY(double y){
        setElement(1, y);
    }
    /**
     * Sets the z coordinate of the vector
     */
    public void setZ(double z){
        setElement(2, z);
    }

    /**
     * Returns the vector scaled such that its magnitude is 1
     */
    public Vector3 normalized(){
        if(getMagnitude() == 0)
            throw new IllegalStateException("Vector cant be the zero vector");
        Vector vec = Vectors.scale(this, 1d/getMagnitude());
        return new Vector3(vec.getVector());
    }



    //STATIC UTILITY FUNCTIONS
    /**
     * Returns the vector scaled by a double
     */
    public static Vector3 scale(Vector3 vec, double s){
        double[] doubles = new double[3];
        for(int i = 0; i < 3; i++){
            doubles[i] = vec.getElement(i) * s;
        }
        return new Vector3(doubles);
    }

    /**
     * Returns the vector rotated angle radians around the z axis
     */
    public static Vector3 rotateZ(Vector3 vec, double angle){
        Matrix rotation = new Matrix(new double[][]{
                {Math.cos(angle), -Math.sin(angle), 0},
                {Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 1}
        });
        return new Vector3(rotation.transform(vec.getVector()));
    }

    /**
     * Returns the vector rotated angle radians around the x axis
     */
    public static Vector3 rotateX(Vector3 vec, double angle){
        Matrix rotation = new Matrix(new double[][]{
                {1, 0, 0},
                {0, Math.cos(angle), -Math.sin(angle)},
                {0, Math.sin(angle), Math.cos(angle)},
        });
        return new Vector3(rotation.transform(vec.getVector()));
    }

    /**
     * Returns the vector rotated angle radians around the y axis
     */
    public static Vector3 rotateY(Vector3 vec, double angle){
        Matrix rotation = new Matrix(new double[][]{
                {Math.cos(angle), 0, -Math.sin(angle)},
                {0, 1, 0},
                {Math.sin(angle), 0, Math.cos(angle)},
        });
        return new Vector3(rotation.transform(vec.getVector()));
    }


    /**
     * Returns the vector rotated angle radians around an arbitrary axis given as a direction vector
     */
    public static Vector3 rotate(Vector3 axis, Vector3 input, double angle){
        //TODO fikse denne funksjonen
        Matrix c = new Matrix(new double[][]{
                {0, -axis.getZ(), axis.getY()},
                {axis.getZ(), 0, -axis.getX()},
                {-axis.getY(), axis.getX(), 0}
        });
        Matrix rotationMatrix = Matrices.add(Matrices.scale(Matrices.getIdentityMatrix(3),
                Math.cos(angle)), Matrices.scale(c, Math.sin(angle)),
                Matrices.scale(Matrices.product(new Matrix(new double[][]{{axis.getX()}, {axis.getY()}, {axis.getZ()}}), (new Matrix(new double[][]{{axis.getX(), axis.getY(), axis.getZ()}}))), 1-Math.cos(angle)));

        return new Vector3(rotationMatrix.transform(input.getVector()));
    }

    /**
     * Returns the cross product of two vectors
     */
    public static Vector3 cross(Vector3 v1, Vector3 v2){
        return new Vector3(v1.getY()*v2.getZ()-v2.getY()*v1.getZ(), -(v1.getX()*v2.getZ()-v2.getX()*v1.getZ()), v1.getX()*v2.getY()-v2.getX()*v1.getY());
    }

    /**
     * Returns the difference of two vectors
     */
    public static Vector3 difference(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
    }

    /**
     * Returns the sum of two vectors
     */
    public static Vector3 sum(Vector3 v1, Vector3 v2){
        return new Vector3(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
    }

    /**
     * Returns the sum of vectors
     */
    public static Vector3 sum(Vector3... vecs){
        double[] u = vecs[0].getVector().clone();
        for(int i = 1; i < vecs.length; i++){
            for(int j = 0; j < vecs[0].getDimensions(); j++)
                u[j] += vecs[i].getElement(j);
        }
        return new Vector3(u);
    }

    /**
     * Returns a copy of a vector
     */
    public static Vector3 copy(Vector3 vector3){
        return new Vector3(vector3.getVector().clone());
    }

    /**
     * Creates a new vector3 from a string format
     */
    public static Vector3 valueOf(String string){
        String[] nums = string.substring(1, string.length()-1).split(", ");
        return new Vector3(Double.parseDouble(nums[0]), Double.parseDouble(nums[1]), Double.parseDouble(nums[2]));
    }
    /**
     * Returns a Vector3 with coordinates [0,1,0]
     */
    public static Vector3 UP() {
        return new Vector3(0, 1, 0);
    }

    /**
     * Returns a Vector3 with coordinates [0,0,1]
     */
    public static Vector3 FORWARD() {
        return new Vector3(0, 0, 1);
    }

    /**
     * Returns a Vector3 with coordinates [1,0,0]
     */
    public static Vector3 RIGHT(){
        return new Vector3(1, 0, 0);
    }

    /**
     * Returns a Vector3 with coordinates [0,0,0]
     */
    public static Vector3 ZERO(){
        return new Vector3(0, 0, 0);
    }



}
