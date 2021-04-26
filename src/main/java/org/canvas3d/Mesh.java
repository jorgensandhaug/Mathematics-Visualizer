package org.canvas3d;

import org.linalgfx.Writable;
import org.math3d.Vector3;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Render3D which holds a mesh (set of vertices and triangles) which can be rendered to the canvas
 */
public class Mesh extends Render3D implements Writable {
    protected double currentScale;
    private String relativePath;
    protected double scaleX = 1;
    protected double scaleY = 1;
    protected double scaleZ = 1;

    public Mesh(Vector3 position){
        super(position);
    }


    /**
     * This constructor takes in a path to an obj or text file, which is then read and translated into vertices and triangles
     */
    public Mesh(String relativePath, Vector3 position, double scale){
        super(position);
        this.relativePath = relativePath;

        currentScale = scale;

        try(Scanner sc = new Scanner(new File(getClass().getResource(relativePath).toExternalForm().replace("file:", "")))){

            List<Vector3> vertices = new ArrayList<>();
            List<Triangle> triangles = new ArrayList<>();
            while(sc.hasNextLine()){
                String[] line = sc.nextLine().split(" ");

                if(line[0].equals("v"))
                    vertices.add(Vector3.scale(new Vector3(Double.parseDouble(line[1]), Double.parseDouble(line[2]), Double.parseDouble(line[3])), scale));

                else if(line[0].equals("f")){
                    try {
                        Triangle triangle = new Triangle(vertices.get(Integer.parseInt(line[1])-1), vertices.get(Integer.parseInt(line[2])-1), vertices.get(Integer.parseInt(line[3])-1));
                        triangles.add(triangle);
                    }
                    catch (Exception e){
                        System.out.println(Arrays.toString(line));
                        System.out.println(vertices.size());
                        //e.printStackTrace();
                    }
                }
            }
            this.vertices = vertices.toArray(new Vector3[0]);
            this.triangles = triangles.toArray(new Triangle[0]);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Sets the scale of the mesh
     */
    public void setScale(double scale){
        for (Vector3 vertex : vertices) {
            vertex.scale(scale / currentScale);
        }
        currentScale = scale;
    }

    public void setScaleX(double scale){
        for (Vector3 vertex : vertices) {
            vertex.setX(vertex.getX() * scale / scaleX);
        }
        scaleX = scale;
    }

    public void setScaleY(double scale){
        for (Vector3 vertex : vertices) {
            vertex.setY(vertex.getY() * scale / scaleY);
        }
        scaleY = scale;
    }

    public void setScaleZ(double scale){
        for (Vector3 vertex : vertices) {
            vertex.setZ(vertex.getZ() * scale / scaleZ);
        }
        scaleZ = scale;
    }



    @Override
    public void beforeRender() {

    }

    @Override
    public Object getMath() {
        return null;
    }

    @Override
    public String writeString(){
        return "org.canvas3d.Mesh---"+relativePath+"::"+position+"::"+currentScale;
    }
    //from file
    public Mesh(String fileString){
        this(fileString.split("::")[0], Vector3.valueOf(fileString.split("::")[1]), Double.parseDouble(fileString.split("::")[2]));
    }
}
