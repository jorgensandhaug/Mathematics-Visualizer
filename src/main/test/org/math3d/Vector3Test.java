package org.math3d;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class Vector3Test {
    Vector3 vector3;

    @BeforeEach
    public void random(){
        vector3 = new Vector3(Math.random()*10 - 5, Math.random()*10 - 5, Math.random()*10 - 5);
    }

    @Test
    public void constructorTest(){
        vector3 = new Vector3(1, 3.2, 3);
        assertEquals(3.2, vector3.getY());

        assertThrows(IllegalArgumentException.class, () -> vector3 = new Vector3(1, 2, 3, 4));
    }

    @Test
    public void normalized() {
        vector3 = new Vector3(0, 0, 0);
        assertThrows(IllegalStateException.class, () -> vector3.normalized());

        for(int i = 0; i < 10; i++) {
            random();
            if(vector3.equals(Vector3.ZERO()))
                assertThrows(IllegalStateException.class, () -> vector3.normalized());
            else
                assertTrue(Math.abs(vector3.normalized().getMagnitude() - 1) <= 0.0001);
        }
    }

    @Test
    public void scale() {
        vector3 = new Vector3(5, 2, 3);
        Vector3 expected = new Vector3(15, 6, 9);
        vector3.scale(3);
        assertTrue(expected.equals(vector3));


        vector3 = new Vector3(-1, 2, -1);
        expected = new Vector3(2, -4, 2);
        vector3.scale(-2);
        assertTrue(expected.equals(vector3));

        vector3 = new Vector3(-1, 2, -1);
        expected = new Vector3(0,0,0);
        vector3.scale(0);
        assertTrue(expected.equals(vector3));
    }

    @Test
    public void rotateZ() {
        vector3 = new Vector3(1, 1, 1);
        Vector3 expected = new Vector3(-1, 1, 1);
        assertTrue(expected.equals(Vector3.rotateZ(vector3, Math.PI/2)));

        vector3 = new Vector3(0,0,0);
        expected = new Vector3(0,0,0);
        for(int i = 0; i < 10; i++){
            assertTrue(expected.equals(Vector3.rotateZ(vector3, Math.random()*10)));
        }

        vector3 = new Vector3(0, 0, 1);
        assertTrue(vector3.equals(Vector3.rotateZ(vector3, Math.random()*10)));
    }

    @Test
    public void rotateX() {
        vector3 = new Vector3(1, 1, 1);
        Vector3 expected = new Vector3(1, 1, -1);
        assertTrue(expected.equals(Vector3.rotateX(vector3, -Math.PI/2)));

        vector3 = new Vector3(0,0,0);
        expected = new Vector3(0,0,0);
        for(int i = 0; i < 10; i++){
            assertTrue(expected.equals(Vector3.rotateX(vector3, Math.random()*10)));
        }

        vector3 = new Vector3(1, 0, 0);
        assertTrue(vector3.equals(Vector3.rotateX(vector3, Math.random()*10)));
    }

    @Test
    public void rotateY() {
        vector3 = new Vector3(1, 2, 0);
        Vector3 expected = new Vector3(0, 2, 1);
        assertTrue( expected.equals(Vector3.rotateY(vector3, Math.PI/2)));

        vector3 = new Vector3(0,0,0);
        expected = new Vector3(0,0,0);
        for(int i = 0; i < 10; i++){
            assertTrue(expected.equals(Vector3.rotateY(vector3, Math.random()*10)));
        }

        vector3 = new Vector3(0, 1, 0);
        assertTrue(vector3.equals(Vector3.rotateY(vector3, Math.random()*10)));
    }

    @Test
    public void cross() {
        Vector3 cross = Vector3.cross(Vector3.UP(), Vector3.FORWARD());

        assertTrue(cross.equals(Vector3.RIGHT()));
    }
    @Test
    public void difference() {
        vector3 = new Vector3(4, -2, 3);
        Vector3 other = new Vector3(4, -3, 4.5);
        Vector3 expected = new Vector3(0, 1, -1.5);
        assertTrue(Vector3.difference(vector3, other).equals(expected));
    }

    @Test
    public void sum() {
        vector3 = new Vector3(4, -2, 3);
        Vector3 other = new Vector3(4, -3, 4.5);
        Vector3 expected = new Vector3(0, 1, -1.5);
        assertTrue(Vector3.difference(vector3, other).equals(expected));
    }
}