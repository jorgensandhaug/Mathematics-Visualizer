package org;

import org.junit.jupiter.api.Assertions;

public class TestUtils{
    public static void assertApproximate(double v1, double v2){
        Assertions.assertTrue(Math.abs(v1-v2) <= Math.pow(10, -9));
    }
}