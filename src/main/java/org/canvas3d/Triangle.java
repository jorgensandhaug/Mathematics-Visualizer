package org.canvas3d;

import javafx.scene.paint.Color;
import org.math3d.Line3;
import org.math3d.Vector3;

/**
 * Triangle, used in every mesh drawn to the 3D canvas
 * Keeps track of corners and color/material info
 */
public class Triangle {
    private Vector3[] vertices;
    private Color[] adjustedColors = new Color[3];
    private Material material;
    private Material[] materials;
    private boolean shouldInterpolateColors;

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3, Color color){
        this.vertices = new Vector3[]{p1, p2, p3};
        this.material = new Material(color);
    }

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3){
        this.vertices = new Vector3[]{p1, p2, p3};
    }

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3, Color c1, Color c2, Color c3){
        this.vertices = new Vector3[]{p1, p2, p3};
        setColors(new Color[]{c1, c2, c3});
    }

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3, Material m1, Material m2, Material m3){
        this.vertices = new Vector3[]{p1, p2, p3};
        setMaterials(new Material[]{m1, m2, m3});
    }



    /**
     * Renders the triangle with no respect to the Render3D object's direction and position
     * (renders it at the origin facing the positive z direction)
     */
    public void renderAbsolute(GraphicsContext3D gc){
        Vector3 normal = getAbsoluteNormal();

        if(!facingCamera(normal, vertices[0]))
            return;

        //color interpolation between vertices
        if(shouldInterpolateColors && materials != null){
            for (int i = 0; i < 3; i++) {
                adjustedColors[i] = materials[i].getColor(brightness(vertices[i], normal));
            }
            gc.fillTriangle(vertices[0], vertices[1], vertices[2], adjustedColors);
        }

        //simple fill color
        else if(material != null){
            gc.setFill(material.getColor(brightness(getAverage(), normal)));
            gc.fillTriangle(vertices[0], vertices[1], vertices[2]);
        }
        //simple grayscale fill based on brightness from lightSource
        else
            gc.setFill(Color.grayRgb((int) brightness(getAverage(), normal)));
    }

    /**
     * Renders the triangle relative to the Render3D objects position and direction
     */
    public void render(GraphicsContext3D gc, Vector3 origin, Vector3 forward, Vector3 up, Vector3 right){

        Vector3 pos1 = Vector3.sum(origin, Vector3.scale(right, vertices[0].getX()), Vector3.scale(up, vertices[0].getY()), Vector3.scale(forward, vertices[0].getZ()));
        Vector3 pos2 = Vector3.sum(origin, Vector3.scale(right, vertices[1].getX()), Vector3.scale(up, vertices[1].getY()), Vector3.scale(forward, vertices[1].getZ()));
        Vector3 pos3 = Vector3.sum(origin, Vector3.scale(right, vertices[2].getX()), Vector3.scale(up, vertices[2].getY()), Vector3.scale(forward, vertices[2].getZ()));

        Vector3 normal = Vector3.cross(Vector3.difference(pos2, pos1), Vector3.difference(pos3, pos1));

        if(!facingCamera(normal, pos1))
            return;

        //color interpolation between vertices
        if(shouldInterpolateColors && materials != null){
            adjustedColors[0] = materials[0].getColor(brightness(pos1, normal));
            adjustedColors[1] = materials[1].getColor(brightness(pos2, normal));
            adjustedColors[2] = materials[2].getColor(brightness(pos3, normal));


            gc.fillTriangle(pos1, pos2, pos3, adjustedColors);
        }

        //simple fill color
        else if(material != null){

            gc.setFill(material.getColor(brightness(Vector3.scale(Vector3.sum(pos1, pos2, pos3), 0.33333333), normal)));
            gc.fillTriangle(pos1, pos2, pos3);
        }
        //simple grayscale fill based on brightness from lightSource
        else {
            Color fill = Color.grayRgb((int) (255 * brightness(Vector3.scale(Vector3.sum(pos1, pos2, pos3), 0.33333333), normal)));
            gc.setFill(fill);
            gc.fillTriangle(pos1, pos2, pos3);
        }

    }

    /**
     * Returns true if this triangle's normal vector is facing the camera, else false
     */
    public boolean facingCamera(Vector3 normal, Vector3 arbitraryPointOnTriangle){
        try {
            return normal.dot(Vector3.difference(arbitraryPointOnTriangle, CanvasRenderer3D.getCamera().position)) < 0;
        }
        catch (Exception e){
            System.out.println("FEIL I FACING CAMERA FUNKSJONEN!");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns the normal, as if the Render3D object is postitioned at the origin facing the positive z direction
     */
    public Vector3 getAbsoluteNormal(){
        try {
            return Vector3.cross(Vector3.difference(vertices[1], vertices[0]), Vector3.difference(vertices[2], vertices[0]));
        }
        catch (Exception e){
            System.out.println("Error i getNormal funksjonen");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the centroid of the triangle
     */
    public Vector3 getCentroid(){
        Vector3 normal = getAbsoluteNormal();
        Vector3 r1 = new Vector3(vertices[0].getX()-vertices[1].getX(), vertices[0].getY()-vertices[1].getY(), vertices[0].getZ()-vertices[1].getZ());
        Vector3 r2 = new Vector3(vertices[0].getX()-vertices[2].getX(), vertices[0].getY()-vertices[2].getY(), vertices[0].getZ()-vertices[2].getZ());
        Vector3 v1 = Vector3.cross(normal, r1);
        Vector3 v2 = Vector3.cross(normal, r2);

        Line3 line1 = new Line3(new Vector3((vertices[0].getX() + vertices[1].getX())/2, (vertices[0].getY() + vertices[1].getY())/2, (vertices[0].getZ() + vertices[1].getZ())/2), v1);
        Line3 line2 = new Line3(new Vector3((vertices[0].getX() + vertices[2].getX())/2, (vertices[0].getY() + vertices[2].getY())/2, (vertices[0].getZ() + vertices[2].getZ())/2), v2);

        Vector3 intersection = line1.intersection(line2);
//        if(intersection == null)
//            return null;
        if(intersection == null)
            throw new Error("FEIL I getCentroid()-funksjonen");

        return intersection;
    }

    /**
     * Returns the midpoint/average point of the corners
     */
    private Vector3 getAverage(){
        return Vector3.scale(Vector3.sum(vertices[0], vertices[1], vertices[2]), 0.3333333333);
    }

    public Vector3[] getVertices(){
        return vertices;
    }

    /**
     * Returns the brightness of a point and a normal vector by using the LightSource brightness method
     */
    private double brightness(Vector3 point, Vector3 normal){
        double bright = 0;
        for(LightSource lightSource : CanvasRenderer3D.getLightSources()){
            bright += lightSource.getBrightness(point, normal) * 0.8 + 0.1;
        }
        return Math.min(bright, 1);
    }

    /**
     * Sets the color, and also sets the shouldInterpolateColors to false to indicate that only a single color should be used
     * when rasterizing the triangle
     */
    public void setColor(Color color){
        this.material = new Material(color);
        this.shouldInterpolateColors = false;
    }

    /**
     * Sets the color array, and also sets the shouldInterpolateColors to true to indicate that color interpolation should be used
     * when rasterizing the triangle
     */
    public void setColors(Color[] colors){
        if(colors.length != 3)
            throw new IllegalArgumentException("Length of colorarray must be 3");

        Material[] mats = new Material[colors.length];
        for(int i = 0; i < colors.length; i++)
            mats[i] = new Material(colors[i]);

        this.materials = mats;
        this.shouldInterpolateColors = true;
    }

    /**
     * Sets the material, and also sets the shouldInterpolateColors to false to indicate that only a single color should be used
     * when rasterizing the triangle
     */
    public void setMaterial(Material material){
        this.material = material;
        this.shouldInterpolateColors = false;
    }

    /**
     * Sets the color array, and also sets the shouldInterpolateColors to true to indicate that color interpolation should be used
     * when rasterizing the triangle
     */
    public void setMaterials(Material[] materials){
        if(materials.length != 3)
            throw new IllegalArgumentException("Length of material array must be 3");

        this.materials = materials;
        this.shouldInterpolateColors = true;
    }

    public void setShouldInterpolateColors(boolean bool){
        this.shouldInterpolateColors = bool;
    }
}
