package org.canvas3d;

import org.math3d.Vector3;
/**
 * A sphere which renders a more detailed sphere as a mesh
 */
public class Sphere extends Mesh{
    public Sphere(Vector3 position, double radius){
        super("sphere.obj", position, radius);
    }

    @Override
    public void beforeRender() {

    }

    @Override
    public Object getMath() {
        return null;
    }

    @Override
    public String toString(){
        return "Center: " + position + ", Radius: " + currentScale;
    }


    @Override
    public String writeString(){
        return "org.canvas3d.Sphere---"+position+"::"+currentScale;
    }

    public Sphere(String fileString){
        this(Vector3.valueOf(fileString.split("::")[0]), Double.parseDouble(fileString.split("::")[1]));
    }
}
