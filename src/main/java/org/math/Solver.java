package org.math;

import java.util.*;

/**
 * Handles the solving of linear algebra problems
 */
public class Solver {
    /**
     * Returns the Matrix reduced to row echelon form
     */
    public static Matrix toReducedRowEchelon(Matrix m){
        Map<Integer, List<Integer>> pivotIndexes;
        while(!m.isRowEchelon()){
            //find pivot indexes
            pivotIndexes = findPivotIndexes(m);
            //scale rows
            for(int i = 0; i<m.getHeight(); i++){
                for(int j = 0; j<m.getHeight(); j++){
                    if(m.get(i,j)!=0){
                        m.scaleRow(i, 1/m.get(i,j));
                        break;
                    }
                }
            }
            //sort rows according to pivot element
            int count = 0;
            double[][] sortedM = new double[m.getHeight()][m.getWidth()];
            for(int i = 0; i<m.getHeight(); i++){
                for(int n:pivotIndexes.get(i)){
                    sortedM[count] = m.getRow(n);
                    count++;
                }
            }
            m = new Matrix(sortedM);
            pivotIndexes = findPivotIndexes(m);
            //subtract rows with same pivot index
            count = 0;
            for(int i = 0; i<m.getHeight(); i++){
                for(int j = 0; j<pivotIndexes.get(i).size(); j++){
                    count++;
                    if(j==0)
                        continue;
                    m.addRowToRow(pivotIndexes.get(i).get(j), m.getScaledRow(pivotIndexes.get(i).get(0),-1));
                }
            }
        }
        for(int i = 0; i<m.getHeight(); i++){
            for(int j = i+1; j<m.getHeight(); j++){
                m.addRowToRow(i, m.getScaledRow(j, -m.get(i,j)));
            }
        }
        return m;
    }

    /**
     * Returns a mapping from an index to a list of indexes representing the pivot indexes of a Matrix
     */
    private static Map<Integer, List<Integer>> findPivotIndexes(Matrix m){
        Map<Integer, List<Integer>> pivotIndexes = new HashMap<>();
        for(int i = 0; i<m.getHeight(); i++)
            pivotIndexes.put(i, new ArrayList<>());
        for(int i = 0; i<m.getHeight(); i++){
            for(int j = 0; j<m.getHeight(); j++){
                if(m.get(i,j)!=0){
                    pivotIndexes.get(j).add(i);
                    break;
                }
            }
        }
        return pivotIndexes;
    }

    /**
     * Returns the inverse of the Matrix
     */
    public static Matrix invertedMatrix(Matrix matrix) throws IllegalArgumentException{
        if(matrix.getWidth()!=matrix.getHeight() || matrix.det()==0)
            throw new IllegalArgumentException();
        Matrix m = new Matrix(matrix.getMatrix());
        m.append(Matrices.getIdentityMatrix(m.getHeight()));
        int a = 0;
        m = toReducedRowEchelon(m);
        double[][] ansM = new double[m.getHeight()][m.getHeight()];
        for(int i = 0; i< m.getHeight(); i++)
            ansM[i] = Arrays.copyOfRange(m.getRow(i),m.getHeight(),m.getHeight()*2);
        return new Matrix(ansM);
    }

    /**
     * Returns the solution to a system of linear equations as a Vector
     */
    public static Vector solveLinSys(Matrix matrix, Vector v) throws IllegalArgumentException{
        if(matrix.getHeight()!=matrix.getWidth() || matrix.getWidth()!=v.getDimensions() || matrix.det()==0)
            throw new IllegalArgumentException();
        Matrix m = new Matrix(matrix.getMatrix().clone());
        m.append(v);
        m = toReducedRowEchelon(m);
        return new Vector(m.getColumn(m.getHeight()));
    }

    public static void main(String[] args) {
        double[][] arr = {
                {0, 1},
                {1, 0}
        };
        Matrix m = new Matrix(arr);
        Vector v = new Vector(1,2);
        System.out.println(solveLinSys(m,v));
    }
}
