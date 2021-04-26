package org.utils;

import org.math.Matrix;

/**
 * Implemented by classes that are have interpolation animations
 */
public interface Interpolatable {
    /**
     * Initializes the interpolation process, time set to 0
     */
    void startInterpolation(Matrix m, int millis);

    /**
     * Handles the animation for each step in the interpolation process until finished
     * How the interpolation should be handled has to be specified by each class
     */
    void handleInterpolation();
}
