package org.math;

import org.textInput.Regexes;
import org.linalgfx.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a mathematical function as a binary tree where each node is an Expression-object
 */
public class Expression implements Writable {
    private String originalInput;
    private String expression;
    private Expression leftChild;
    private Expression rightChild;
    private String operator;
    private boolean isPositive = true;
    private boolean isLeaf = true;
    private final Map<Character, Expression> children = new HashMap<>();
    private final Map<Character, Character> oppositeChild = new HashMap<>();

    private final Map<String, BiFunction<Double, Double, Double>> dddOps = new HashMap<>();
    private final Map<String, Function<Double, Double>> ddOps = new HashMap<>();
    private final Map<String, Double> constants = new HashMap<>();


    public Expression(String input) throws IllegalArgumentException{
        originalInput = input;

        if(!checkInput(input))
            throw new IllegalArgumentException("The input does not translate to a legal function");
        this.expression = input;

        dddOps.put("+", Expressions::sum);dddOps.put("-", Expressions::difference); dddOps.put("*", Expressions::product); dddOps.put("/", Expressions::division); dddOps.put("^", Math::pow);
        ddOps.put("cos", Math::cos); ddOps.put("sin", Math::sin); ddOps.put("tan", Math::tan); ddOps.put("abs", Math::abs); ddOps.put("log",Math::log);
        constants.put("pi", Math.PI); constants.put("e", Math.E);

        findChildren();
    }

    public void setOriginalInput(String input){
        originalInput = input;
    }

    /**
     * Returns the operator of the node
     */
    public String getOperator(){
        return operator;
    }

    /**
     * Sets the left child of the Expression
     */
    public void setLeftChild(Expression expression){
        leftChild = expression;
    }

    /**
     * Returns the left child of the Expression
     */
    public Expression getLeftChild(){
        return leftChild;
    }

    /**
     * Returns the right child of the Expression
     */
    public Expression getRightChild(){
        return rightChild;
    }

    /**
     * Sets the right child of the Expression
     */
    public void setRightChild(Expression expression){
        rightChild = expression;
    }

    /**
     * Sets the operator of the expression
     */
    public void setOperator(String operator){
        this.operator = operator;
    }

    /**
     * Validates a function given as a string, returns false if invalid, else true
     */
    private boolean checkInput(String input){
        String f = "[(cos)(sin)(abs)(tan)(log)]";
        if(input.length()==0)
            return false;
        int bracketCount = 0;
        for(int i = 0; i<input.length(); i++){
            if(input.charAt(i)=='(')
                bracketCount++;
            else if(input.charAt(i)==')')
                bracketCount--;
            if(bracketCount<0)
                return false;
        }
        if(bracketCount!=0)
            return false;

        if(Pattern.matches(".*[^0-9\\.e(pi)x\\+\\*\\-/\\^"+f+"].*", input))
            return false;
        if(Pattern.matches(".*[+\\-*/^]{2}.*", input))
            return false;
        if(Pattern.matches(".*\\([\\.+*/^].*", input))
            return false;
        if(Pattern.matches(".*[^0-9e(pi)x]\\..*", input))
            return false;
        if(Pattern.matches(".*\\.[^0-9e(pi)x].*", input))
            return false;
        if(Pattern.matches(".*(e|pi|x)(\\d|e|pi|x)", input))
            return false;
        if(Pattern.matches(".*(\\d|e|pi|x)(e|pi|x)", input))
            return false;
        if(Pattern.matches("[.+*/^].*", input))
            return false;
        if(Pattern.matches(".*[.+\\-*/^]", input))
            return false;
        if(Pattern.matches("\\(\\)", input))
            return false;
        if(Pattern.matches(".*\\)(\\d|e|(pi)|x).*", input))
            return false;
        return true;
    }

    /**
     * Returns the Expression as a string
     */
    public String getExpression() {
        if(isPositive)
            return expression;
        return "-"+expression;
    }

    /**
     * Sets the children of an Expression from its string representation, and continues to do this recursively
     */
    private void findChildren(){
        isLeaf = true;
        removeBrackets();
        if(expression.charAt(0)=='-'){
            isPositive = false;
            expression = expression.substring(1);
            expression = flipSign('-', expression);
        }
        else if(expression.charAt(0)=='+')
            expression = expression.substring(1);
        removeBrackets();
        String[] children = splitExpression();
        if(!children[0].equals("")){
            leftChild = new Expression(children[0]);
            rightChild = new Expression(children[1]);
            isLeaf = false;
        }
        else if(expression.contains("(")){
            leftChild = new Expression(parseComposition());
            isLeaf = false;
        }
    }

    /**
     * Removes excess brackets from the Expression
     */
    private void removeBrackets(){
        boolean run = true;
        while(run && expression.length()>1){
            int depth = 0;
            for (int i = 0; i < expression.length()-1; i++) {
                if(expression.charAt(i) == '(') depth++;
                else if(expression.charAt(i) == ')') depth--;
                if(depth==0){
                    run = false;
                    break;
                }
            }
            if(run)
                expression = expression.substring(1,expression.length()-1);
        }
    }

    /**
     * Returns a string where all operations of a given type are inverted
     */
    private String flipSign(Character sign, String expression){
        int bracketDepth = 0;
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i)=='(')
                bracketDepth++;
            if(expression.charAt(i)==')')
                bracketDepth--;
            if(bracketDepth==0){
                if(sign=='-'){
                    if(expression.charAt(i)=='+')
                        expression = expression.substring(0,i)+"-"+expression.substring(i+1);
                    else if(expression.charAt(i)=='-')
                        expression = expression.substring(0,i)+"+"+expression.substring(i+1);
                }
                if(sign=='/'){
                    if(expression.charAt(i)=='*')
                        expression = expression.substring(0,i)+"/"+expression.substring(i+1);
                    else if(expression.charAt(i)=='/')
                        expression = expression.substring(0,i)+"*"+expression.substring(i+1);
                }
            }
        }
        return expression;
    }

    /**
     * Splits the expression into two new Expressions and an operator
     */
    private String[] splitExpression(){
        List<Integer> plus = new ArrayList<>();
        List<Integer> minus = new ArrayList<>();
        List<Integer> times = new ArrayList<>();
        List<Integer> divide = new ArrayList<>();
        List<Integer> exponent = new ArrayList<>();
        String lc = "";
        String rc = "";
        int bracketDepth = 0;
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i)=='(')
                bracketDepth++;
            else if(expression.charAt(i)==')')
                bracketDepth--;
            if(bracketDepth==0){
                if(expression.charAt(i)=='+'){
                    lc = expression.substring(0,i);
                    rc = expression.substring(i+1);
                    operator = "+";
                }
                else if(expression.charAt(i)=='-'){
                    lc = expression.substring(0,i);
                    rc = flipSign('-',expression.substring(i+1));
                    operator = "-";
                }
                else if(expression.charAt(i)=='*')
                    times.add(i);
                else if(expression.charAt(i)=='/')
                    divide.add(i);
                else if(expression.charAt(i)=='^')
                    exponent.add(i);
            }
        }
        if(lc.equals("")){
            if(!times.isEmpty()){
                int i = times.get(0);
                lc = expression.substring(0,i);
                rc = expression.substring(i+1);
                operator = "*";
            }
            else if(!divide.isEmpty()){
                int i = divide.get(0);
                lc = expression.substring(0,i);
                rc = flipSign('/',expression.substring(i+1));
                operator = "/";
            }
            else if(!exponent.isEmpty()){
                int i = exponent.get(0);
                lc = expression.substring(0,i);
                rc = expression.substring(i+1);
                operator = "^";
            }
        }
        return new String[]{lc,rc};
    }

    /**
     * Parses a predefined function and returns it as a string
     */
    private String parseComposition(){
        //Set<String> functions = Set.of("cos", "sin", "abs", "log", "tan");
        String f = "(cos|sin|abs|log|tan|)";
        Matcher m = Pattern.compile(f+"\\((.+)\\)").matcher(expression);
        if(m.find()){
            operator = m.group(1);
            return m.group(2);
        }
        throw new IllegalArgumentException("The input does not translate to a legal function");
    }

    /**
     * Returns the tree who's root is the current Expression as a debug-friendly string
     */
    public String debugToString(){
        return toString(0);
    }

    /**
     * Returns the Expression as a string
     */
    public String toString(){
        String sign = "";
        if(!isPositive)
            sign = "-";
        if(rightChild==null){
            if(leftChild==null)
                return sign+expression;
            return "("+sign+operator + "("+leftChild+"))";
        }
        return sign+"("+leftChild+operator+rightChild+")";
    }

    /**
     * Evaluates the function in a given point
     */
    public double evaluate(double x){
        int c = 1;
        if(!isPositive)
            c = -1;
        if(leftChild!=null && rightChild!=null){
            return dddOps.get(operator).apply(leftChild.evaluate(x), rightChild.evaluate(x))*c;
        }
        else if(leftChild!=null){
            return ddOps.get(operator).apply(leftChild.evaluate(x))*c;
        }
        else{
            if(Pattern.matches("e|pi", expression))
                return constants.get(expression)*c;
            else if(Pattern.matches("x", expression))
                return x*c;
            else
                return Double.parseDouble(expression)*c;
        }
    }

    /**
     * Recursive support function for debugToString
     */
    private String toString(int depth){
        String indent = new String(new char[depth]).replace("\0", "\t");
        String sign = "";
        if(!isPositive)
            sign = "-";
        String result = indent + sign + getExpression() + "\top:"+operator+"\n";
        if(!(leftChild==null))
            result += leftChild.toString(depth+1);
        if(!(rightChild==null)){
            result += rightChild.toString(depth+1);
        }
        return result;
    }

    /**
     * Returns true if the Expression is positive, else false
     */
    public boolean isPositive() {
        return isPositive;
    }

    /**
     * Makes the Expression negative
     */
    public void setToNegative(){
        isPositive = false;
    }

    /**
     * Returns true if the Expression is a leaf node, else false
     */
    public boolean isLeaf(){
        return isLeaf;
    }

    /**
     * Simplifies the expression
     */
    public void simplify(){
       while(cleanup());
    }

    /**
     * Resets the Expression
     */
    private void resetNode(String expression){
        leftChild = null;
        rightChild = null;
        operator = null;
        isLeaf = true;
        this.expression = expression;
        findChildren();
    }

    /**
     * Simplifies the expression once
     */
    private boolean cleanup(){
        if(leftChild!=null && rightChild!=null){
            if(operator.equals("*")){
                if(leftChild.getExpression().equals("0") || rightChild.expression.equals("0")){
                    resetNode("0");
                    return true;
                }
                if(leftChild.getExpression().equals("1")){
                    resetNode(rightChild.getExpression());
                    return true;
                }
                if(rightChild.getExpression().equals("1")){
                    resetNode(leftChild.getExpression());
                    return true;
                }
                if(leftChild.isLeaf() && rightChild.isLeaf()){
                    System.out.println(expression);
                    if(Pattern.matches(Regexes.POS_FL_NUM, leftChild.getExpression()) && Pattern.matches(Regexes.POS_FL_NUM, rightChild.getExpression())){
                        resetNode(Double.toString(Double.parseDouble(leftChild.getExpression())*Double.parseDouble(rightChild.getExpression())));
                        return true;
                    }
                }
            }
            if(operator.equals("/")){
                if(leftChild.getExpression().equals("0")){
                    resetNode("0");
                    return true;
                }
                if(rightChild.getExpression().equals("1")){
                    resetNode(leftChild.getExpression());
                    return true;
                }
                if(leftChild.isLeaf() && rightChild.isLeaf()){
                    if(Pattern.matches(Regexes.POS_FL_NUM, leftChild.getExpression()) && Pattern.matches(Regexes.POS_FL_NUM, rightChild.getExpression())){
                        resetNode(Double.toString(Double.parseDouble(leftChild.getExpression())/Double.parseDouble(rightChild.getExpression())));
                        return true;
                    }
                }
            }
            if(operator.equals("+")){
                if(leftChild.getExpression().equals("0")){
                    resetNode(rightChild.getExpression());
                    return true;
                }
                if(rightChild.getExpression().equals("0")){
                    resetNode(leftChild.getExpression());
                    return true;
                }
                if(leftChild.isLeaf() && rightChild.isLeaf()){
                    if(Pattern.matches(Regexes.POS_FL_NUM, leftChild.getExpression()) && Pattern.matches(Regexes.POS_FL_NUM, rightChild.getExpression())){
                       resetNode(Double.toString(Double.parseDouble(leftChild.getExpression())+Double.parseDouble(rightChild.getExpression())));
                        return true;
                    }
                }
            }
            if(operator.equals("-")){
                if(leftChild.getExpression().equals("0")){
                   resetNode(rightChild.getExpression());
                   isPositive = !isPositive;
                   return true;
                }
                if(rightChild.getExpression().equals("0")){
                    resetNode(leftChild.getExpression());
                    return true;
                }
                if(leftChild.isLeaf() && rightChild.isLeaf()){
                    if(Pattern.matches(Regexes.POS_FL_NUM, leftChild.getExpression()) && Pattern.matches(Regexes.POS_FL_NUM, rightChild.getExpression())){
                        resetNode(Double.toString(Double.parseDouble(leftChild.getExpression())-Double.parseDouble(rightChild.getExpression())));
                        return true;
                    }
                }
            }
            if(operator.equals("^")){
                if(leftChild.getExpression().equals("0")){
                    resetNode("0");
                    return true;
                }
                if(rightChild.getExpression().equals("0")){
                    resetNode("1");
                    return true;
                }
                if(leftChild.getExpression().equals("1")){
                    resetNode("1");
                    return true;
                }
                if(rightChild.getExpression().equals("1")){
                    resetNode(leftChild.getExpression());
                    return true;
                }
                if(leftChild.isLeaf() && rightChild.isLeaf()){
                    if(Pattern.matches(Regexes.POS_FL_NUM, leftChild.getExpression()) && Pattern.matches(Regexes.POS_FL_NUM, rightChild.getExpression())){
                       resetNode(Double.toString(Math.pow(Double.parseDouble(leftChild.getExpression()),Double.parseDouble(rightChild.getExpression()))));
                        return true;
                    }
                }
            }
            return leftChild.cleanup() || rightChild.cleanup();
        }
        return false;
    }

    /**
     * Fixes contradictory fields
     */
    public void fixExpression(){
        if(rightChild==null){
            if(leftChild!=null){
                expression = "("+operator + "("+leftChild+"))";
                isLeaf = false;
                return;
            }
            isLeaf = true;
            return;
        }
        isLeaf = false;
        leftChild.fixExpression();
        rightChild.fixExpression();
        expression = "("+leftChild+operator+rightChild+")";
    }

    @Override
    public String writeString() {
        return originalInput;
    }



    public static void main(String[] args) {
        Expression root = new Expression("-cos(x)+7*x");
        System.out.println(root.toString());
        System.out.println(root.evaluate(1));
    }

}

