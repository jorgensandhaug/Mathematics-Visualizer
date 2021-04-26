package org.math;

import org.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatricesTest {

    @Test
    void getIdentityMatrix() {
        Matrix m1 = Matrices.getIdentityMatrix(3);
        assertTrue(m1.equals(new Matrix(new double[][]{{1,0,0},{0,1,0},{0,0,1}})));
        assertThrows(IllegalArgumentException.class, ()->
                Matrices.getIdentityMatrix(-1));
    }

    @Test
    void product() {
        Matrix m1 = new Matrix(new double[][]{{1,5,1},{-3,1,4},{7,0,1}});
        Matrix m2 = new Matrix(new double[][]{{7,3,1},{-3,2,4},{-7,5,1}});
        assertTrue(Matrices.product(m1, m2).equals(new Matrix(new double[][]{{-15, 18, 22}, {-52, 13, 5}, {42, 26, 8}})));
        assertTrue(m1.equals(new Matrix(new double[][]{{1,5,1},{-3,1,4},{7,0,1}})));
        assertTrue(m2.equals(new Matrix(new double[][]{{7,3,1},{-3,2,4},{-7,5,1}})));
        Matrix m3 = new Matrix(1,2,3,4);
        assertThrows(IllegalArgumentException.class, ()->
                Matrices.product(m1, m3));
    }

    @Test
    void scale() {
        Matrix m2 = new Matrix(1,2,3,4);
        Matrix m1 = Matrices.scale(m2, 2);
        System.out.println(m1);
        assertTrue(m1.equals(new Matrix(new double[][]{{2,4},{6,8}})));
        assertTrue(m2.equals(new Matrix(new double[][]{{1,2},{3,4}})));

    }

    @Test
    void add() {
        Matrix m1 = new Matrix(new double[][]{{1,5,1},{-3,1,4},{7,0,1}});
        Matrix m2 = new Matrix(new double[][]{{7,3,1},{-3,2,4},{-7,5,1}});
        Matrix m3 = Matrices.add(m1,m2);
        assertTrue(m3.equals(new Matrix(new double[][]{{8,8,2},{-6,3,8},{0,5,2}})));
        Matrix m4 = new Matrix(1,2,3,4);
        assertThrows(IllegalArgumentException.class, ()->
                Matrices.add(m1, m4));
    }

    @Test
    void det() {
        Matrix m1 = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        TestUtils.assertApproximate(m1.det(), 0);
        Matrix m2 = new Matrix(new double[][]{{7,3,1},{-3,2,4},{-7,5,1}});
        TestUtils.assertApproximate(m2.det(), -202);
        Matrix m3 = new Matrix(new double[2][3]);
        assertThrows(IllegalArgumentException.class, ()->
                Matrices.det(m3));
    }
}