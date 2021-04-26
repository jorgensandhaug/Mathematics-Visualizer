package org.terraingeneration;

import org.canvas3d.CanvasRenderer3D;
import org.canvas3d.GraphicsContext3D;
import org.canvas3d.Render3D;
import org.linalgfx.Writable;
import org.math3d.Vector3;

import java.util.HashMap;
import java.util.Map;
/**
 * Infinite 3D terrain that automatically generates new chunks or loads previously generated ones near the position of the player/camera
 */
public class InfiniteTerrain extends Render3D {
    private Map<String, TerrainChunk> terrainChunkMap;
    public final int chunkSize = 16;
    private final double divRoot2 = 1/Math.sqrt(2);
    private final PerlinNoiseMap perlinNoiseMap = new PerlinNoiseMap();
    public InfiniteTerrain(){
        this.terrainChunkMap = new HashMap<>();
    }

    @Override
    public void render(GraphicsContext3D gc){
        //double viewDistance = CanvasRenderer3D.getCamera().getRenderingDistance();
        double viewDistance = 50;
        int currentChunkX = (int) Math.round(CanvasRenderer3D.getCamera().getPosition().getX() / chunkSize);
        int currentChunkZ = (int) Math.round(CanvasRenderer3D.getCamera().getPosition().getZ() / chunkSize);

        int numberOfChunksXZ = (int) Math.round(viewDistance * divRoot2 / chunkSize);

        for(int x = currentChunkX - numberOfChunksXZ; x <= currentChunkX + numberOfChunksXZ; x++){
            for(int z = currentChunkZ - numberOfChunksXZ; z <= currentChunkZ + numberOfChunksXZ; z++){
                String stringKey = ""+x+z;
                if(terrainChunkMap.containsKey(stringKey))
                    terrainChunkMap.get(stringKey).render(gc);
                else{
                    //System.out.println(terrainChunkMap.size() + ", " + stringKey);
                    TerrainChunk newChunk = new TerrainChunk(new Vector3(x*chunkSize, 0, z*chunkSize), chunkSize, perlinNoiseMap);
                    newChunk.render(gc);
                    terrainChunkMap.put(stringKey, newChunk);
                    CanvasRenderer3D.chunksSpawnedCount++;
                }
            }
        }
    }

    @Override
    public void beforeRender() {

    }

    @Override
    public Object getMath() {
        return null;
    }


}
