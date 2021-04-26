package org.utils;

import org.math.Expression;
import org.utils.TriFunction;

/**
 * Contains operations that use the TriFunction interface
 */
public class TriFunctions {

    /**
     * Returns the value of a series
     */
    public static TriFunction<Expression, Double, Double, Double> seriesEval = (f, a, b) ->{
        int A = (int)Math.floor(a);
        int B = (int)Math.floor(b);
        double sum = 0;
        if (a >= 0)
            for (int i = A; i <= B; i++)
                sum += f.evaluate(i);
        return sum;
    };

    public static void main(String[] args) {
//        TriFunction<Expression, Integer, Integer, Double> sum = TriFunctions.seriesEval;
//        System.out.println(sum.apply(new Expression("x^2"),0,4));
    }
}
