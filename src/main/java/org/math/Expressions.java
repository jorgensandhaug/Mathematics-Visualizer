package org.math;

/**
 * Support class for arithmetic, algebra and calculus
 */
public class Expressions {
    /**
     * Returns the sum of two doubles
     */
    public static double sum(double a, double b){
        return a+b;
    }
    /**
     * Returns the difference of two doubles
     */
    public static double difference(double a, double b){
        return a-b;
    }
    /**
     * Returns the product of two doubles
     */
    public static double product(double a, double b){
        return a*b;
    }
    /**
     * Returns the quotient of two doubles
     */
    public static double division(double a, double b){
        if(b==0)
            throw new ArithmeticException("Can not divide by zero");
        return a/b;
    }
    /**
     * Returns the derivative of a function
     */
    public static Expression differentiate(Expression expression){
        return Differentiator.derivative(expression);
    }
}

