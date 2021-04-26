package org.math;

import org.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    Matrix m1;
    Matrix m2;

    @Test
    void testEquals() {
        m1 = new Matrix(1,2,3,4);
        m2 = new Matrix(1,2,3,4);
        assertTrue(m1.equals(m2));
        m2 = new Matrix(1,2,3,5);
        assertFalse(m1.equals(m2));
        m2 = new Matrix(new double[][]{{1, 2, 5}, {3, 4, 6}});
        assertFalse(m1.equals(m2));
    }

    @Test
    void invert2x2() {
        m1 = new Matrix(1,2,2,3);
        m1.invert2x2();
        assertTrue(m1.equals(new Matrix(-3,2,2,-1)));
        m1 = new Matrix(1,1,1,1);
        assertThrows(IllegalStateException.class, ()->
                m1.invert2x2());
    }

    @Test
    void transform() {
        Vector v = new Vector(1,2);
        m1 = new Matrix(1,3,5,2);
        assertTrue(m1.transform(v).equals(new Vector(7,9)));
        m1 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Vector u = new Vector(1,2);
        assertThrows(IllegalArgumentException.class, ()->
                m1.transform(u));
        m1 = new Matrix(new double[][]{{1,2},{3,4},{5,6}});
        assertTrue(m1.transform(v).equals(new Vector(5,11,17)));
    }

    @Test
    void testTransform() {
        double[] d = new double[]{1d,2d};
        m1 = new Matrix(1,3,5,2);
        assertArrayEquals(m1.transform(d), new double[]{7,9});
        m1 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        double[] d2 = new double[]{1,2};
        assertThrows(IllegalArgumentException.class, ()->
                m1.transform(d2));
        m1 = new Matrix(new double[][]{{1,2},{3,4},{5,6}});
        assertArrayEquals(m1.transform(d), new double[]{5, 11, 17});
    }

    @Test
    void multiply() {
        m1 = new Matrix(1,2,3,4);
        m2 = new Matrix(new double[][]{{1, 2, 3},{4,5,6}});
        assertThrows(IllegalArgumentException.class, ()->
                m2.multiply(m1));
        m1.multiply(m2);
        assertTrue(m1.equals(new Matrix(new double[][]{{9,12,15},{19,26,33}})));
        assertEquals(m1.getHeight(), 2);
        assertEquals(m1.getWidth(), 3);
    }

    @Test
    void getColumn() {
        m1 = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        assertArrayEquals(m1.getColumn(1), new double[]{2,5,8});
        assertThrows(IllegalArgumentException.class, ()->
                m1.getColumn(3));

    }

    @Test
    void getAllColumns() {
        m1 = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        assertArrayEquals(m1.getAllColumns(), new double[][]{{1,4,7},{2,5,8},{3,6,9}});
    }

    @Test
    void det() {
        m1 = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,11}});
        TestUtils.assertApproximate(m1.det(),-6);
        m2 = new Matrix(new double[][] {{1,2,3,1},{4,5,6,1},{7,8,11,1}});
        assertThrows(IllegalStateException.class, ()->
                m2.det());
    }

    @Test
    void scaleRow() {
        m1 = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        m1.scaleRow(1, 2);
        assertArrayEquals(m1.getRow(1), new double[]{8,10,12});
        assertThrows(IllegalArgumentException.class, ()->
                m1.scaleRow(3, 5));
    }

    @Test
    void getScaledRow() {
        m1 = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        assertArrayEquals(m1.getScaledRow(1,2), new double[]{8,10,12});
        assertThrows(IllegalArgumentException.class, ()->
                m1.getScaledRow(3,1));
    }

    @Test
    void addRowToRow() {
        m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        m1.addRowToRow(0, new double[]{1,1,1});
        assertArrayEquals(m1.getRow(0), new double[]{2,3,4});
        assertThrows(IllegalArgumentException.class, ()->
                m1.addRowToRow(3, new double[]{1,1,1}));
        assertThrows(IllegalArgumentException.class, ()->
                m1.addRowToRow(2, new double[]{1,1,1,1}));
    }

    @Test
    void swapRows() {
        m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        m1.swapRows(0,1);
        assertArrayEquals(m1.getRow(0), new double[]{4,5,6});
        assertArrayEquals(m1.getRow(1), new double[]{1,2,3});
        assertThrows(IllegalArgumentException.class, ()->
                m1.swapRows(1,3));
    }

    @Test
    void append() {
        m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        m1.append(new Vector(1,2,3));
        assertTrue(m1.equals(new Matrix(new double[][]{{1, 2, 3, 1}, {4, 5, 6, 2}, {7, 8, 9, 3}})));
        assertThrows(IllegalArgumentException.class, ()->
                m1.append(new Vector(1,2)));
        assertThrows(IllegalArgumentException.class, ()->
                m1.append(new Vector(1,2,3,4)));
    }

    @Test
    void testAppend() {
        m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        m2 = new Matrix(new double[][]{{1, 2},{2, 3},{3, 4}});
        m1.append(m2);
        assertTrue(m1.equals(new Matrix(new double[][]{{1, 2, 3, 1, 2}, {4, 5, 6, 2, 3}, {7, 8, 9, 3, 4}})));
        assertThrows(IllegalArgumentException.class, ()->
                m1.append(new Matrix(new double[][]{{1},{2}})));
    }

    @Test
    void isIdentityMatrix() {
        m1 = new Matrix(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
        assertTrue(m1.isIdentityMatrix());
        m1.set(0,1,1);
        assertFalse(m1.isIdentityMatrix());
        m2 = new Matrix(new double[][]{{1, 0, 0}, {0, 2, 0}, {0, 0, 1}});
        assertFalse(m2.isIdentityMatrix());
        m2.set(0,0,0);
        assertFalse(m2.isIdentityMatrix());
        m1 = new Matrix(new double[][]{{1, 0}, {0, 1}, {0, 0}});
        assertFalse(m1.isIdentityMatrix());
    }

    @Test
    void getInverted() {
        m1 = new Matrix(new double[][]{{1,0,0},{0,2,3},{0,3,4}});
        assertTrue(m1.getInverted().equals(new Matrix(new double[][]{{1,0,0},{0,-4,3},{0,3,-2}})));
        m1.append(new Vector(1,2,3));
        assertThrows(IllegalArgumentException.class, ()->
                m1.getInverted());
        m2 = new Matrix(new double[][]{{1,1,1},{1,1,1},{1,1,1}});
        assertThrows(IllegalArgumentException.class, ()->
                m2.getInverted());
    }

    @Test
    void invert() {
        m1 = new Matrix(new double[][]{{1,0,0},{0,2,3},{0,3,4}});
        m1.invert();
        assertTrue(m1.equals(new Matrix(new double[][]{{1,0,0},{0,-4,3},{0,3,-2}})));
        m1.append(new Vector(1,2,3));
        assertThrows(IllegalArgumentException.class, ()->
                m1.invert());
        m2 = new Matrix(new double[][]{{1,1,1},{1,1,1},{1,1,1}});
        assertThrows(IllegalArgumentException.class, ()->
                m2.invert());
    }

    @Test
    void isRowEchelon() {
        m1 = new Matrix(new double[][]{{1,1,1},{0,1,1},{0,0,1}});
        assertTrue(m1.isRowEchelon());
        m1.set(0,0, 2);
        assertFalse(m1.isRowEchelon());
        m1.set(0,0,1);
        m1.set(1,0,1);
        assertFalse(m1.isRowEchelon());
    }

    @Test
    void testToString() {
        m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        assertEquals(m1.toString(), "\n1.0\t2.0\t3.0\n4.0\t5.0\t6.0\n7.0\t8.0\t9.0\n");
    }

    @Test
    void writeString() {
        m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        assertEquals(m1.writeString(), "org.math.Matrix---3 1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0 ");
    }
}