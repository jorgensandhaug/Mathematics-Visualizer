package org.math;

import org.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexNumbersTest {

    @Test
    void fromVector() {
        Vector v = new Vector(1,2,3);
        assertThrows(IllegalArgumentException.class, ()->
                ComplexNumbers.fromVector(v));
        Vector u = new Vector(1,2);
        Complex z = ComplexNumbers.fromVector(u);
        assertEquals(z.getRe(), 1);
        assertEquals(z.getIm(), 2);
        assertEquals(z.getLength(), Math.sqrt(5));
        assertEquals(z.getAngle(), Math.atan(2));
    }

    @Test
    void fromPolar() {
        assertThrows(IllegalArgumentException.class, ()->
                ComplexNumbers.fromPolar(-1,2));
        Complex z = ComplexNumbers.fromPolar(Math.sqrt(5),Math.atan(2));
        TestUtils.assertApproximate(z.getRe(), 1);
        TestUtils.assertApproximate(z.getIm(), 2);
        TestUtils.assertApproximate(z.getLength(), Math.sqrt(5));
        TestUtils.assertApproximate(z.getAngle(), Math.atan(2));
    }

    @Test
    void multiply() {
        Complex z1 = new Complex(1,2);
        Complex z2 = new Complex(3,4);
        Complex z3 = ComplexNumbers.multiply(z1,z2);
        assertEquals(z3.getRe(), -5);
        assertEquals(z3.getIm(), 10);
    }

    @Test
    void add() {
        Complex z1 = new Complex(1,2);
        Complex z2 = new Complex(3,4);
        Complex z3 = ComplexNumbers.add(z1,z2);
        assertEquals(z3.getRe(), 4);
        assertEquals(z3.getIm(), 6);
    }

    @Test
    void copyOf() {
        Complex z1 = new Complex(1,2);
        Complex z2 = ComplexNumbers.copyOf(z1);
        assertEquals(z1.getRe(), z2.getRe());
        assertEquals(z1.getIm(), z2.getIm());
        assertEquals(z1.getLength(), z2.getLength());
        assertEquals(z1.getAngle(), z2.getAngle());
    }

    @Test
    void pow() {
        Complex z1 = new Complex(1,2);
        Complex z2 = ComplexNumbers.pow(z1, 9);
        TestUtils.assertApproximate(z2.getRe(), -1199);
        TestUtils.assertApproximate(z2.getIm(), -718);
    }
}