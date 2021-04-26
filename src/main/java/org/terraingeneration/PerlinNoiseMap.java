package org.terraingeneration;

import org.math2d.Vector2;
/**
 * This class contains a 2 dimensional noisemap, where the noise is generated using some code from wikipedia written i C, but
 * translated to Java, source: https://en.wikipedia.org/wiki/Perlin_noise
 * Perlin noise is a techique used for creating pseudo random numbers, where it is important that inputs which are close together
 * get outputs also close together (relatively speaking)
 */
public class PerlinNoiseMap {
    /**
     * Returns the perlin noise value for a point x,y
     */
    public double get(double y, double x){
        //sørger for at man ikke prøver å sample et negativt tall
        return perlin(x+100000, y+100000);
    }

    /** Function to linearly interpolate between a0 and a1
     * Weight w should be in the range [0.0, 1.0]
     */
    private double interpolate(double a0, double a1, double w){
        /* // You may want clamping by inserting:
         * if (0.0 > w) return a0;
         * if (1.0 < w) return a1;
         */

        return (a1 - a0) * ((w * (w * 6.0 - 15.0) + 10.0) * w * w * w) + a0;

        /* // Use this cubic interpolation [[Smoothstep]] instead, for a smooth appearance:
         * return (a1 - a0) * (3.0 - w * 2.0) * w * w + a0;
         *
         * // Use [[Smootherstep]] for an even smoother result with a second derivative equal to zero on boundaries:
         * return (a1 - a0) * ((w * (w * 6.0 - 15.0) + 10.0) * w * w * w) + a0;
         */
    }
    /**
     * Create random direction vector
     */
    private Vector2 randomGradient(int ix, int iy) {
        // Random float. No precomputed gradients mean this works for any number of grid coordinates
        double random = 2920 * Math.sin(ix * 21942 + iy * 171324 + 8912) * Math.cos(ix * 23157 * iy * 217832 + 9758);
        return new Vector2(Math.cos(random), Math.sin(random));
    }
    /**
     * Computes the dot product of the distance and gradient vectors.
     */
     private double dotGridGradient(int ix, int iy, double x, double y) {
        // Get gradient from integer coordinates
        Vector2 gradient = randomGradient(ix, iy);

        // Compute the distance vector
        double dx = x - (double)ix;
        double dy = y - (double)iy;

        // Compute the dot-product
        return (dx*gradient.getX() + dy*gradient.getY());
    }

    /**
     * Compute Perlin noise at coordinates x, y
     */
    private double perlin(double x, double y) {
        // Determine grid cell coordinates
        int x0 = (int)x;
        int x1 = x0 + 1;
        int y0 = (int)y;
        int y1 = y0 + 1;

        // Determine interpolation weights
        // Could also use higher order polynomial/s-curve here
        double sx = x - (double) x0;
        double sy = y - (double) y0;

        // Interpolate between grid point gradients
        double n0, n1, ix0, ix1, value;

        n0 = dotGridGradient(x0, y0, x, y);
        n1 = dotGridGradient(x1, y0, x, y);
        ix0 = interpolate(n0, n1, sx);

        n0 = dotGridGradient(x0, y1, x, y);
        n1 = dotGridGradient(x1, y1, x, y);
        ix1 = interpolate(n0, n1, sx);

        value = interpolate(ix0, ix1, sy);
        return value;
    }
}
