package org.math;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ComplexTest {
    Complex complex;

    @Test
    public void constructorTest(){
        complex = new Complex(1d,-1);
        assertEquals(complex.getRe(), 1);
        assertEquals(complex.getIm(), -1);
        assertEquals(complex.getLength(), Math.sqrt(2));
        assertEquals(complex.getAngle(), -Math.PI/4);

        complex = new Complex();
        assertEquals(complex.getRe(), 0);
        assertEquals(complex.getIm(), 0);
        assertEquals(complex.getLength(), 0);
        assertEquals(complex.getAngle(), 0);
    }

    @Test
    public void add(){
        complex = new Complex(1,2);
        Complex complex2 = new Complex(-1,9);
        complex.add(complex2);
        assertEquals(complex.getRe(),0);
        assertEquals(complex.getIm(),11);
        assertEquals(complex.getLength(), 11);
        assertEquals(complex.getAngle(), 0);
    }

    @Test
    public void multiply(){
        complex = new Complex(3,-2);
        Complex complex2 = new Complex(-6,2);
        complex.multiply(complex2);
        assertEquals(complex.getRe(), -14);
        assertEquals(complex.getIm(), 18);
    }

    @Test
    public void pow(){
        complex = new Complex(2,3);
        complex.pow(4);
        assertEquals(Math.round(complex.getRe()), -119);
        assertEquals(Math.round(complex.getIm()), -120);
        assertEquals(Math.round(complex.getLength()), 169);
        assertEquals(complex.getAngle(), Math.atan(120d/119)+Math.PI);
    }

    @Test
    public void asPolar(){
        complex = new Complex(3,4);
        assertEquals(complex.asPolar(),5.0+"e^i*"+Math.atan(4d/3));
    }

    @Test
    public void ToString(){
        complex = new Complex(1,2);
        assertEquals(complex.toString(), "1.0 + 2.0i");
        complex = new Complex(1,-2);
        assertEquals(complex.toString(), "1.0 - 2.0i");
    }
    @Test
    public void setLength(){
        complex = new Complex(3,4);
        complex.setLength(10);
        assertEquals(complex.getLength(), 10);
        assertEquals(Math.round(complex.getRe()), 6);
        assertEquals(Math.round(complex.getIm()), 8);
        assertThrows(IllegalArgumentException.class, ()->
                complex.setLength(-1));
    }

    @Test
    public void writeString(){
        complex = new Complex(1,3);
        assertEquals(complex.writeString(), "org.math.Complex---1.0 3.0");
    }

    @Test
    public void stringConstructorTest(){
        complex = new Complex("1.0 3.0");
        assertEquals(complex.getRe(), 1.0);
        assertEquals(complex.getIm(), 3.0);
    }
}
