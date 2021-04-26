package org.textInput;

/**
 * Handles parsing of some inputs
 */
public class InputParser {

    /**
     * Parses a string representation of a vector and returns it as an array of doubles
     */
    public static double[] vectorN(String input){
        String[] s = input.split(",");
        double[] d = new double[s.length];
        for(int i = 0; i<s.length; i++){
            d[i] = Double.parseDouble(s[i]);
        }
        return d;
    }

    /**
     * Parses a string representation of a matrix and returns it as an array of arrays of doubles
     */
    public static double[][] matrixMN(String input) throws IllegalArgumentException{
        String[] s = input.split(";");
        int l  = s[0].split(",").length;
        double[][] t = new double[s.length][l];
        for(int i = 0; i<s.length; i++){
            String[] u = s[i].split(",");
            if(u.length!=l)
                throw new IllegalArgumentException("Matrix not valid");
            else
                t[i] = vectorN(s[i]);
        }
        return t;
    }

    public static void main(String[] args) {
        double[][] d = matrixMN("1,2;3,4");
    }
}
