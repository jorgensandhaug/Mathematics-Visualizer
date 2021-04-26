package org.math;

import org.TestUtils;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {
    private Vector vector1;
    private Vector vector2;

    @Test
    public void constructorTest(){
        vector1 = new Vector(1,2,3,4,5);
        assertEquals(vector1.getDimensions(), 5);
        assertEquals(vector1.getElement(0), 1);
        assertEquals(vector1.getElement(3), 4);

        vector1 = new Vector();
        assertEquals(0, vector1.getDimensions());
    }

    @Test
    public void getMagnitude(){
        vector1 = new Vector(1,2,3);
        TestUtils.assertApproximate(Math.sqrt(14), vector1.getMagnitude());

        vector1 = new Vector(-3,4);
        TestUtils.assertApproximate(5d, vector1.getMagnitude());

        vector1 = new Vector(0, 0);
        assertEquals(0, vector1.getMagnitude());
    }

    @Test
    public void addDimensions(){
        vector1 = new Vector(1,3,-2);
        vector1.addDimensions(5,7);
        assertEquals(5, vector1.getDimensions());
        assertEquals(7, vector1.getElement(4));
    }

    @Test
    public void setMagnitude(){
        vector1 = new Vector(3,4);
        vector1.setMagnitude(10);
        assertEquals(6, vector1.getElement(0));

        assertThrows(IllegalArgumentException.class, () -> vector1.setMagnitude(-1));
    }

    @Test
    public void scale(){
        vector1 = new Vector(1,3,-2);
        vector1.scale(2);
        assertEquals(6, vector1.getElement(1));

        vector1 = new Vector(3, 4, 1);
        vector1.scale(0);
        for(int i = 0; i < vector1.getDimensions(); i++){
            assertEquals(0, vector1.getElement(i));
        }
    }

    @Test
    public void setElement(){
        vector1 = new Vector(3,4);
        vector1.setElement(0, 4);
        assertEquals(4, vector1.getElement(0));
    }

    @Test
    public void getElement(){
        vector1 = new Vector(1d);
        assertEquals(1, vector1.getElement(0));

        vector2 = new Vector();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> vector2.getElement(0));
    }


    @Test
    public void add(){
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        vector1.add(vector2);
        assertTrue(vector1.equals(new Vector(-8, 7)));
        assertFalse(vector1.equals(new Vector(1,2)));

        vector1 = new Vector(1, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> vector1.add(vector2));
    }

    @Test
    public void sub(){
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        vector1.sub(vector2);
        assertTrue(vector1.equals(new Vector(10, -3)));
        assertFalse(vector1.equals(new Vector(1,2)));

        vector1 = new Vector(1, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> vector1.sub(vector2));
    }

    @Test
    public void dot(){
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        assertEquals(1, vector1.dot(vector2));

        vector1 = new Vector(1, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> vector1.dot(vector2));
    }

    @Test
    public void hasSameDimensions(){
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        assertTrue(vector1.hasSameDimensions(vector2));
        assertFalse(vector1.hasSameDimensions(new Vector(1, 2, 3)));
    }

    @Test
    public void angle(){
        vector1 = new Vector(1,0);
        vector2 = new Vector(0,1);
        assertEquals(vector1.angle(vector2), Math.PI/2);

        vector2 = new Vector(0, 0);
        assertThrows(IllegalArgumentException.class, ()-> vector1.angle(vector2));

        vector2 = new Vector(2, 3, 41);
        assertThrows(IllegalArgumentException.class, ()-> vector1.angle(vector2));
    }

    @Test
    public void isParallel(){
        vector1 = new Vector(1,0);
        vector2 = new Vector(3,0);
        assertTrue(vector1.isParallel(vector2));
        assertFalse(vector1.isParallel(new Vector(1, 2)));

        vector1 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> vector1.isParallel(vector2));
    }

    @Test
    public void cross(){
        vector1 = new Vector(-8, 7);
        vector2 = new Vector(-9,5);
        assertThrows(IllegalArgumentException.class, ()-> vector1.cross(vector2));

        vector1 = new Vector(1,0);
        vector2 = new Vector(0,1);

        vector1.addDimensions(2);
        vector2.addDimensions(-5);
        assertTrue(vector1.cross(vector2).equals(new Vector(-2,5,1)));
    }

    @Test
    public void factorize(){
        vector2 = new Vector(2,4);
        assertEquals(2, vector2.factorize());
        vector1 = new Vector(1.2, 3.4);
        assertThrows(IllegalArgumentException.class, () -> vector1.factorize());
    }

    @Test
    public void equals(){
        vector1 = new Vector(40);//length 40, all entries are 0
        vector2 = new Vector(40);

        for(int i = 0; i < 40; i++){
            double number = Math.random()*100;
            double randomDelta = (Math.random()-0.5)*0.0000000001;
            vector1.setElement(i, number);
            vector2.setElement(i, number + randomDelta);
        }
        assertTrue(vector1.equals(vector2));

        vector2.setElement(0, -18);
        assertFalse(vector1.equals(vector2));
    }
}
