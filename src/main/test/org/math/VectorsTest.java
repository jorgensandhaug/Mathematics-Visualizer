package org.math;

import org.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorsTest {
    private Vector vector1;
    private Vector vector2;
    @Test
    void sum() {
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);

        assertTrue(Vectors.sum(vector1, vector2).equals(new Vector(-8, 7)));

        Vector vector3 = new Vector(1, 3);
        assertTrue(Vectors.sum(vector1, vector2, vector3).equals(new Vector(-7, 10)));

        vector1 = new Vector(1, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> Vectors.sum(vector1, vector2));
    }

    @Test
    void difference() {
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        assertTrue(Vectors.difference(vector1, vector2).equals(new Vector(10, -3)));


        vector1 = new Vector(1, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> Vectors.difference(vector1, vector2));
    }

    @Test
    void dot() {
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        assertEquals(1, Vectors.dot(vector1, vector2));

        vector1 = new Vector(1, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> Vectors.dot(vector1, vector2));
    }

    @Test
    void angle() {
        vector1 = new Vector(1,0);
        vector2 = new Vector(0,1);
        assertEquals(Vectors.angle(vector1, vector2), Math.PI/2);

        vector2 = new Vector(0, 0);
        assertThrows(IllegalArgumentException.class, ()-> Vectors.angle(vector1, vector2));

        vector2 = new Vector(2, 3, 41);
        assertThrows(IllegalArgumentException.class, ()-> Vectors.angle(vector1, vector2));
    }

    @Test
    void angle2() {
        vector1 = new Vector(1,0);
        vector2 = new Vector(0,1);

        assertEquals(Math.PI/2, Vectors.angle2(vector1.getVector(), vector2.getVector()));

        assertEquals(-Math.PI/2, Vectors.angle2(vector2.getVector(), vector1.getVector()));

        vector1 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> Vectors.angle2(vector1.getVector(), vector2.getVector()));
    }

    @Test
    void scale() {
        vector1 = new Vector(1,3,-2);
        vector2 = Vectors.scale(vector1, 2);
        assertEquals(6, vector2.getElement(1));

        vector1 = new Vector(3, 4, 1, 2, 5, 0, -3, -10.5);
        vector2 = Vectors.scale(vector1, 0);
        for(int i = 0; i < vector2.getDimensions(); i++){
            assertEquals(0, Math.abs(vector2.getElement(i)));
        }
    }

    @Test
    void cross() {
        vector1 = new Vector(-8, 7);
        vector2 = new Vector(-9,5);
        assertThrows(IllegalArgumentException.class, ()-> Vectors.cross(vector1, vector2));

        vector1 = new Vector(1,0);
        vector2 = new Vector(0,1);

        vector1.addDimensions(2);
        vector2.addDimensions(-5);
        assertTrue(Vectors.cross(vector1, vector2).equals(new Vector(-2,5,1)));
    }

    @Test
    void transform() {
    }

    @Test
    void getMagnitude() {
        vector1 = new Vector(1,2,3);
        TestUtils.assertApproximate(Math.sqrt(14), Vectors.getMagnitude(vector1));

        vector1 = new Vector(-3,4);
        TestUtils.assertApproximate(5d, Vectors.getMagnitude(vector1));

        vector1 = new Vector(0, 0);
        assertEquals(0, Vectors.getMagnitude(vector1));
    }

    @Test
    void fromPoint() {
        Point p = new Point(1, 2, 3);
        vector1 = Vectors.fromPoint(p);

        assertTrue(vector1.equals(new Vector(1, 2, 3)));
    }

    @Test
    void fromPoints() {
        Point start = new Point(-1, -1);
        Point end = new Point(3, 3);
        vector1 = Vectors.fromPoints(start, end);


        assertTrue(vector1.equals(new Vector(-4, -4)));
    }

    @Test
    void toPoint() {
        vector1 = new Vector(1, 2, 3);
        Point p = Vectors.toPoint(vector1);

        assertTrue(p.equals(new Point(1, 2, 3)));
    }

    @Test
    void toComplex() {
        vector1 = new Vector(1, 2);
        Complex c = Vectors.toComplex(vector1);

        assertTrue(c.equals(new Complex(1, 2)));

        vector1 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> Vectors.toComplex(vector1));
    }

    @Test
    void fromComplex() {
        Complex c = new Complex(-2, 3);
        vector1 = Vectors.fromComplex(c);

        assertTrue(vector1.equals(new Vector(-2, 3)));
    }

    @Test
    void parseVector() {
        vector1 = new Vector(1, 2, 3.7);
        vector2 = Vectors.parseVector("1", "2", "3.7");

        assertTrue(vector1.equals(vector2));
    }
}