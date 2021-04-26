package org.canvas3d;

import org.math3d.Vector3;
/**
 * A simple cube, which loads a mesh from an obj file
 */
public class Cube extends Mesh{
    public Cube(Vector3 position, double scale){
        super("cube.obj", position, scale);
    }

    @Override
    public void beforeRender() {

    }

    @Override
    public String writeString(){
        return "org.canvas3d.Cube---"+position+"::"+currentScale;
    }

    public Cube(String fileString){
        this(Vector3.valueOf(fileString.split("::")[0]), Double.parseDouble(fileString.split("::")[1]));
    }
}
