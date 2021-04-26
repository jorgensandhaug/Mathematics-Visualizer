package org.terraingeneration;

import org.canvas3d.Material;
import org.canvas3d.Mesh;
import org.canvas3d.Triangle;
import org.math3d.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

/**
 * TerrainChunk which is created procedurally when the player/camera is moving
 */
public class TerrainChunk extends Mesh {
    private double[] heightMap;
    private Material[] materialMap;
    private PerlinNoiseMap perlinNoise;
    public TerrainChunk(Vector3 position, int size, PerlinNoiseMap perlinNoise){
        super(position);
        int nVertices = size +1;
        this.perlinNoise = perlinNoise;
        heightMap = new double[nVertices*nVertices];
        materialMap = new Material[nVertices*nVertices];
        vertices = new Vector3[nVertices*nVertices];

        for(int z = 0; z < nVertices; z++){
            for(int x = 0; x < nVertices; x++) {
                double height1 = perlinNoise.get((z+position.getZ())/20, (x+position.getX())/20);
                double height2 = 0.5 * perlinNoise.get((z+position.getZ())/10, (x+position.getX())/10);
                double height3 = 0.25 * perlinNoise.get((z+position.getZ())/5, (x+position.getX())/5);
                double height = height1 + height2 + height3;
                if(height < -0.2) //f0r å få vannet til å bli flatt
                    height = -0.2;
                heightMap[z*nVertices + x] = height;
                materialMap[z*nVertices + x] = materialFromHeight(height);
                vertices[z*nVertices + x] = new Vector3((x-(double)nVertices/2), (1 + heightMap[z*nVertices + x]) * 10 - 10, (z-(double)nVertices/2));
            }
        }
//        this.triangles = new Triangle[(size-1)*(size-1) * 2];
        List<Triangle> triangles = new ArrayList<>();
        for(int y = 0; y < nVertices-1; y++){
            for(int x = 0; x < nVertices-1; x++){
                triangles.add(new Triangle(
                        vertices[y*nVertices+x], vertices[(y+1)*nVertices +x], vertices[(y+1)*nVertices + x + 1],
                        materialMap[y*nVertices+x], materialMap[(y+1)*nVertices +x], materialMap[(y+1)*nVertices + x + 1]));


                triangles.add(new Triangle(
                        vertices[y*nVertices+x], vertices[(y+1)*nVertices +x + 1], vertices[y*nVertices + x + 1],
                        materialMap[y*nVertices+x], materialMap[(y+1)*nVertices +x + 1], materialMap[y*nVertices + x + 1]));
            }
        }
        //faktiske triangle array som renderes av superklassen
        this.triangles = triangles.toArray(new Triangle[0]);
    }

    /**
     * Returns the type of material to be used for a vertex depending on the height
     */
    private Material materialFromHeight(double height){
        if(height <= -0.2) //water
            return Material.WATER;
        if(height < -0.1) //sand
            return Material.SAND;
//        if(height < 0.3) //dirt
//            return Color.rgb(131,101,57);

        if(height < 0.3) //fjell
            return Material.MOUNTAIN;

        //snø
        return Material.SNOW;
    }
}
