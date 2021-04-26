package org.math;

/**
 * Support class for Matrix
 */
public class Matrices {
    /**
     * Returns the an n-dimensional identity Matrix
     */
    public static Matrix getIdentityMatrix(int n) throws IllegalArgumentException{
        if(n<=0)
            throw new IllegalArgumentException("n must be a positive integer");
        double[][] m = new double[n][n];
        for(int i = 0; i<n; i++){
            m[i][i] = 1;
        }
        return new Matrix(m);
    }

    /**
     * Returns the product of two Matrices
     */
    public static Matrix product(Matrix m1, Matrix m2){
        Matrix matrix1 = new Matrix(m1.getMatrix());
        matrix1.multiply(m2);
        return matrix1;
    }

    /**
     * Returns the Matrix scaled by s
     */
    public static Matrix scale(Matrix matrix, double s){
        double[][] newMatrix = new double[matrix.getHeight()][matrix.getWidth()];
        for(int y = 0; y < newMatrix.length; y++){
            for(int x = 0; x < newMatrix.length; x++){
                newMatrix[y][x] = matrix.get(y, x)*s;
            }
        }
        return new Matrix(newMatrix);
    }

    /**
     *  Returns a sum of Matrices
     */
    public static Matrix add(Matrix... matrices){
        int width = matrices[0].getWidth();
        int height = matrices[0].getHeight();
        double[][] newMatrix = new double[height][width];

        for(int i = 0; i < matrices.length; i++){
            if(matrices[i].getWidth() != width || matrices[i].getHeight() != height)
                throw new IllegalArgumentException("Matrices must be of same dimension!");
            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    newMatrix[y][x] += matrices[i].get(y, x);
                }
            }
        }
        return new Matrix(newMatrix);
    }

    public static double det(Matrix m){
        try{
            return m.det();
        }
        catch (Exception e){
            throw new IllegalArgumentException("Matrix must be quadratic");
        }
    }
}
