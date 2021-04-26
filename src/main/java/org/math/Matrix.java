package org.math;

import org.linalgfx.Writable;

import java.util.List;
import java.util.Arrays;

/**
 * Represents a mathematical matrix
 */
public class Matrix implements Writable {
    //TODO fix matrix-matrix multiplication!
    private double[][] matrix;
    private int width;
    private int height;

    public Matrix(double[][] matrix){
        this.matrix = matrix.clone();
        width = matrix[0].length;
        height = matrix.length;
    }

    public Matrix(int height, int width){
        this.height = height;
        this.width = width;
        matrix = new double[height][width];
    }

    public Matrix(double a, double b, double c, double d){
        this(2, 2);
        double[][] dArr = {
            {a, b},
            {c, d}
        };
        matrix = dArr;
    }

    public Matrix(){
        this(1,1);
        matrix = new double[][]{{0}};
    }

    public boolean equals(Matrix matrix){
        if(getWidth()!=matrix.getWidth() || getHeight()!= matrix.getHeight())
            return false;
        for(int i = 0; i<height; i++){
            if(!new Vector(getRow(i)).equals(new Vector(matrix.getRow(i))))
                return false;
        }
        return true;
    }

    /**
     * Returns the width of the Matrix
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns the height of the Matrix
     */
    public int getHeight(){
        return height;
    }

    /**
     * Inverts a 2x2 Matrix
     */
    public void invert2x2() throws IllegalStateException{
        double determinant = det();
        if(Math.abs(determinant) <= 0.0000001)
            throw new IllegalStateException("Matrix is not invertible");
        double s = 1d / determinant;

        matrix = new double[][]{
                {s*matrix[1][1], -s*matrix[0][1]},
                {-s*matrix[1][0], s*matrix[0][0]}
        };
    }

    /**
     * Returns the product of the Matrix and a Vector
     */
    public Vector transform(Vector vector) throws IllegalArgumentException {
        if(vector.getDimensions() != width)
            throw new IllegalArgumentException("The vectors number of dimensions doesnt match the matrix width");

        return new Vector(transform(vector.getVector()));
    }

    /**
     * Returns the product of the Matrix and a Vector represented by an array
     */
    public double[] transform(double[] coords) throws IllegalArgumentException{
        if(coords.length!=width)
            throw new IllegalArgumentException("Illegal dimension");
        double[][] allColumns = getAllColumns();

        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                allColumns[i][j] *= coords[i];

        double[] sum = new double[height];

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sum[j] += allColumns[i][j];
            }
        }
        return sum;
    }

    /**
     * Returns the product of the Matrix and another Matrix
     */
    public void multiply(Matrix other) throws IllegalArgumentException{
        if(width != other.height)
            throw new IllegalArgumentException("Illegal size of matrices");
        double[][] m = new double[height][other.width];

        for(int y = 0; y < m.length; y++){
            for(int x = 0; x < m[y].length; x++){
                m[y][x] = new Vector(getRow(y)).dot(new Vector(other.getColumn(x)));
            }
        }
        matrix = m;
        height = m.length;
        width = m[0].length;
    }

    /**
     * Returns the Matrix as a two-dimensional array
     */
    public double[][] getMatrix(){
        return matrix.clone();
    }

    /**
     * Returns the specified column as an array
     */
    public double[] getColumn(int columnNumber) throws IndexOutOfBoundsException{
        double[] col = new double[height];
        for(int i = 0; i < height; i++){
            col[i] = get(i, columnNumber);
        }
        return col;
    }

    /**
     * Returns a list of all columns
     */
    public double[][] getAllColumns(){
        var vecs = new double[width][height];
        for(int column = 0; column < width; column++){
            vecs[column] = getColumn(column);
        }
        return vecs;
    }

    /**
     * Returns the determinant of the Matrix
     */
    public double det(){
        if(getWidth()!=getHeight())
            throw new IllegalStateException("Matrix must be quadratic");
        if(width==1){
            return matrix[0][0];
        }
        double sum = 0;
        int j = 0;
        for(int i = 0; i<getWidth(); i++){
            sum+=matrix[0][i]*getDetMatrix(i,j).det()*Math.pow(-1, i);
        }
        return sum;
    }

    /**
     * Support function for calculating the determinant
     */
    private Matrix getDetMatrix(int x, int y){
        double[][] newMatrix = new double[height-1][width-1];
        int counter = 0;
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                if(i!=y && j!=x){
                    newMatrix[counter/(width-1)][counter%(width-1)] = matrix[i][j];
                    counter++;
                }
            }
        }
        return new Matrix(newMatrix);
    }

    /**
     * Scales a given row by a double
     */
    public void scaleRow(int row, double scale) throws IndexOutOfBoundsException{
        for(int i = 0; i<width; i++){
            matrix[row][i]*=scale;
        }
    }

    /**
     * Returns a given row as an array
     */
    public double[] getRow(int row)throws IndexOutOfBoundsException{
        return matrix[row].clone();
    }

    /**
     * Returns a given row scaled by a double as an array
     */
    public double[] getScaledRow(int row, double scale) throws IndexOutOfBoundsException{
        double[] scaledRow = new double[getWidth()];
        for(int i = 0; i<width; i++){
            scaledRow[i] = Math.round(Math.pow(10,10)*matrix[row][i]*scale)/Math.pow(10,10);
        }
        return scaledRow;
    }

    /**
     * Adds a row onto another row
     */
    public void addRowToRow(int row1, double[] row2) throws IndexOutOfBoundsException{
        for(int i = 0; i<row2.length; i++){
            matrix[row1][i]+=row2[i];
        }
    }

    /**
     * Swaps the position of two rows
     */
    public void swapRows(int row1, int row2) throws IndexOutOfBoundsException{
        double[] r1 = matrix[row1]; double[] r2 = matrix[row2];
        matrix[row1] = r2; matrix[row2] = r1;
    }

    /**
     * Appends a Vector to the right side of the Matrix
     */
    public void append(Vector v) throws IllegalArgumentException{
        if(v.getDimensions()!=height)
            throw new IllegalArgumentException("Illegal dimension");
        for(int i = 0; i<height; i++){
            double[] d = new double[width+1];
            for(int j = 0; j<width; j++)
                d[j] = matrix[i][j];
            d[width] = v.getElement(i);
            matrix[i] = d;
        }
        width+=1;
    }

    /**
     * Appends a Matrix to the right side of the Matrix
     */
    public void append(Matrix m) throws IllegalArgumentException{
        if(height!=m.getHeight())
            throw new IllegalArgumentException("Illegal dimension");
        double[][] newM = new double[height][width+m.getWidth()];
        for(int i = 0; i<height; i++){
            System.arraycopy(this.getRow(i),0,newM[i], 0, width);
            System.arraycopy(m.getRow(i),0,newM[i], width, m.getWidth());
        }
        width+=m.getWidth();
        matrix = newM;
    }

    /**
     * Returns true if the Matrix is an identity Matrix, else false
     */
    public boolean isIdentityMatrix(){
        if(width!=height)
            return false;
        for(int i = 0; i<height; i++){
            for(int j = 0; j<height; j++){
                if(i==j){
                    if(matrix[i][j]!=1)
                        return false;
                }
                else
                    if(matrix[i][j]!=0)
                        return false;
            }
        }
        return true;
    }

    /**
     * Returns the inverse of the Matrix
     */
    public Matrix getInverted(){
        return Solver.invertedMatrix(this);
    }

    /**
     * Inverts the Matrix
     */
    public void invert(){
        matrix = Solver.invertedMatrix(this).getMatrix();
    }

    /**
     * Returns true if the Matrix is on row echelon form, else false
     */
    public boolean isRowEchelon(){
        for(int i = 0; i<height; i++){
            for(int j = 0; j<i; j++){
                if(matrix[i][j]!=0)
                    return false;
            }
            if(matrix[i][i]!=1)
                return false;
        }
        return true;
    }

    /**
     * Returns the element in a given position
     */
    public double get(int y, int x) throws IndexOutOfBoundsException{
        return matrix[y][x];
    }

    /**
     * Sets the element in a given position
     */
    public void set(int y, int x, double value) throws IndexOutOfBoundsException{
        matrix[y][x] = value;
    }

    @Override
    public String toString(){
        int[] paddings = new int[width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(Double.toString(matrix[i][j]).length() > paddings[j])
                    paddings[j] = Double.toString(matrix[i][j]).length();
            }
        }
        String str = "\n";
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                String inner = Double.toString(matrix[i][j]);
                while(inner.length() < paddings[j])
                    inner += " ";

                str += inner;
                if(j < width - 1)
                    str += "\t";
            }
            str += "\n";
        }
        return str;
    }

    @Override
    public String writeString() {
        StringBuilder string = new StringBuilder("org.math.Matrix---" + width+" ");
        for(double[] r:matrix){
            for(double d: r){
                string.append(d).append(" ");
            }
        }
        return string.toString();
    }

    //From file
    public Matrix(String fileString){
        String[] nums = fileString.split(" ");
        width = (int)Double.parseDouble(nums[0]);
        height = (nums.length-1)/width;
        matrix = new double[height][width];
        for(int i = 1; i < nums.length; i++){
            set((i-1)/width, (i-1)%width, Double.parseDouble(nums[i]));
        }
    }

    public static void main(String[] args) {
        double[][] arr = {
                {1, 2, 4, 7},
                {2, 1, -3, 5},
                {8, 1, -6, 8},
                {2, -1, 3, -5}
        };

        Matrix m = new Matrix(arr);

        System.out.println(m.det());
        System.out.println(m);

    }
}

