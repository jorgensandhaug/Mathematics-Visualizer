package org.canvas3d;

import javafx.scene.paint.Color;
/**
 * Material which can be used instead of color, and also keeps track of the reflectivity of a material aka how much
 * it should reflect light
 */
public class Material {
    private Color color;
    private double reflectivity;

    public Material(Color color){
        this.color = color;
        this.reflectivity = 0.75;
    }

    public Material(Color color, double reflectivity){
        this.color = color;
        this.reflectivity = reflectivity;
    }

    /**
     * Returns the color of this material, which takes in to account the brightness and reflectivity
     */
    public Color getColor(double brightness){
        return Color.hsb(color.getHue(), color.getSaturation(), brightness * reflectivity);
    }


    public final static Material MOUNTAIN = new Material(Color.rgb(	50,	50,	50), 0.3);
    public final static Material WATER = new Material(Color.rgb(	0, 119, 190), 1);
    public final static Material SAND = new Material(Color.rgb(227, 201, 175), 0.75);
    public final static Material SNOW = new Material(Color.WHITE, 1);
}
