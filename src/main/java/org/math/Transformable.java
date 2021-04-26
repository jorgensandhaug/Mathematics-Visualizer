package org.math;

/**
 * Is implemented by all classes that can be transformed by a Matrix
 */
public interface Transformable {
    void transform(Matrix matrix);
}
