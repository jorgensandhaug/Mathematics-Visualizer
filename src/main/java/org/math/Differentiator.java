package org.math;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles differentiation of an Expression-object
 */
public class Differentiator {
    private static final Map<String, String> derivatives = new HashMap<>();

    /**
     * Fills known derivatives into a Map
     */
    public static void fillDerivatives(){
        derivatives.put("sin", "cos($)");
        derivatives.put("cos", "-sin($)");
        derivatives.put("log", "1/$");
        derivatives.put("tan", "1/cos($)^2");
        derivatives.put("exp", "exp($)");
    }

    /**
     * Returns the derivative of an Expression
     */
    public static Expression derivative(Expression expression){
        Expression d = getDerivative(expression);
        d.fixExpression();
        d.simplify();
        d.fixExpression();
        d.setOriginalInput(d.getExpression());
        return d;
    }
    /**
     * Returns an unsimplified derivative of an Expression
     */
    private static Expression getDerivative(Expression expression){
        Expression derivative = new Expression("0");
        Expression derivativeL = new Expression("0");
        Expression derivativeR = new Expression("0");
        Expression derivativeT = new Expression("0");
        Expression derivativeB = new Expression("0");
        Expression derivativeX = new Expression("0");
        Expression derivativeY = new Expression("0");
        if(!expression.isPositive())
            derivative.setToNegative();
        if(expression.getOperator()!=null){
            if(expression.getOperator().equals("+")){
                derivative.setLeftChild(getDerivative(expression.getLeftChild()));
                derivative.setRightChild(getDerivative(expression.getRightChild()));
                derivative.setOperator("+");
            }
            else if(expression.getOperator().equals("-")){
                derivative.setLeftChild(getDerivative(expression.getLeftChild()));
                derivative.setRightChild(getDerivative(expression.getRightChild()));
                derivative.setOperator("+");
            }
            else if(expression.getOperator().equals("*")){
                derivativeL.setLeftChild(getDerivative(expression.getRightChild()));
                derivativeL.setRightChild(expression.getLeftChild());
                derivativeL.setOperator("*");
                derivativeR.setLeftChild(getDerivative(expression.getLeftChild()));
                derivativeR.setRightChild(expression.getRightChild());
                derivativeR.setOperator("*");
                derivative.setLeftChild(derivativeL);
                derivative.setRightChild(derivativeR);
                derivative.setOperator("+");
            }
            else if(expression.getOperator().equals("/")){
                derivativeB.setLeftChild(expression.getRightChild());
                derivativeB.setRightChild(new Expression("2"));
                derivativeB.setOperator("^");
                derivativeL.setLeftChild(getDerivative(expression.getLeftChild()));
                derivativeL.setRightChild(expression.getRightChild());
                derivativeL.setOperator("*");
                derivativeR.setLeftChild(getDerivative(expression.getRightChild()));
                derivativeR.setRightChild(expression.getLeftChild());
                derivativeR.setOperator("*");
                derivativeT.setLeftChild(derivativeL);
                derivativeT.setRightChild(derivativeR);
                derivativeT.setOperator("-");
                derivative.setLeftChild(derivativeT);
                derivative.setRightChild(derivativeB);
                derivative.setOperator("/");
            }
            else if(expression.getOperator().equals("^")){
                derivativeT.setLeftChild(expression.getRightChild());
                derivativeT.setRightChild(getDerivative(expression.getLeftChild()));
                derivativeT.setOperator("*");
                derivativeR.setLeftChild(derivativeT);
                derivativeR.setRightChild(expression.getLeftChild());
                derivativeR.setOperator("/");
                derivativeX.setLeftChild(expression.getLeftChild());
                derivativeX.setOperator("log");
                derivativeL.setLeftChild(getDerivative(expression.getRightChild()));
                derivativeL.setRightChild(derivativeX);
                derivativeL.setOperator("*");
                derivativeB.setLeftChild(derivativeL);
                derivativeB.setRightChild(derivativeR);
                derivativeB.setOperator("+");
                derivativeY.setLeftChild(expression.getLeftChild());
                derivativeY.setRightChild(expression.getRightChild());
                derivativeY.setOperator("^");
                derivative.setLeftChild(derivativeY);
                derivative.setRightChild(derivativeB);
                derivative.setOperator("*");
            }
            else if(expression.getOperator()!=null){
                derivative.setLeftChild(getDerivative(expression.getLeftChild()));
                System.out.println(expression.getOperator());
                System.out.println(derivatives.get(expression.getOperator()));
                derivative.setRightChild(new Expression(derivatives.get(expression.getOperator()).replace("$",expression.getLeftChild().getExpression())));
                derivative.setOperator("*");
            }
        }
        else
            if(expression.getExpression().equals("x"))
                return new Expression("1");
            else if(expression.getExpression().equals("-x"))
                return new Expression("-1");
            else
                return new Expression("0");
        return derivative;

    }
}
