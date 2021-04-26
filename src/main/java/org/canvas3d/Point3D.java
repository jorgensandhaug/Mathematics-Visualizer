package org.canvas3d;

import org.math.*;
import org.math3d.Vector3;
import org.utils.Interpolator;

/**
 * Simple point which renders a low poly sphere mesh
 */
public class Point3D extends Mesh implements Editable {
    private final Vector3 point3;
    private static final double scale = 0.1;
    public Point3D(double x, double y, double z){
        super("lowpoly_sphere.obj", Vector3.ZERO(), scale);
        this.point3 = new Vector3(x, y, z);
        setPosition(this.point3);
    }
    public Point3D(Vector3 p){
        super("lowpoly_sphere.obj", Vector3.ZERO(), scale);
        this.point3 = p;
        setPosition(this.point3);
    }

    @Override
    public Object getMath() {
        return Vectors.toPoint(point3).toPoint3();
    }

    @Override
    public String toString(){
        return Points.fromVector(point3).toString();
    }

    @Override
    public String writeString(){
        return "org.canvas3d.Point3D---"+ point3;
    }
    //from file
    public Point3D(String fileString){
        this(Vector3.valueOf(fileString));
    }

    @Override
    public double[] getCopy() {
        return point3.getCopy();
    }

    @Override
    public void set(double[] doubles) {
        point3.set(doubles);
        setPosition(point3);
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {
        double[] start = point3.getVector();
        double[] end = m.transform(point3.getVector());
        interpolator = new Interpolator(millis, start, end);
    }

    @Override
    public void handleInterpolation() {
        if(interpolator != null){
            interpolator.handle();
            point3.setX(interpolator.get(0));
            point3.setY(interpolator.get(1));
            point3.setZ(interpolator.get(2));


            if(interpolator.isFinished())
                interpolator = null;
        }
    }
}
