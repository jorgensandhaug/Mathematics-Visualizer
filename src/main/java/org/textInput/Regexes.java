package org.textInput;

/**
 * Stores some regexes used in parsing
 */
public class Regexes {
    public static final String FL_NUM = "(-?[0-9]+(\\.?[0-9]+)?)";
    public static final String POS_FL_NUM = "([0-9]+(\\.?[0-9]+)?)";
    public static final String VAR_NAME = "(\\w[0-9a-zA-Z_]*)";
    public static final String VAR_DEC = "(\\w[0-9a-zA-Z_]*)=";
    public static final String FUN_DEC = "(\\w[0-9a-zA-Z_]*)\\(x\\)=";
    public static final String FUNC_NAME = "(abs|cos|sin|tan|log)";

    private static String list(int n, String s){
        String r = s;
        for(int i = 0; i<n-1; i++)
            r+=","+s;
        return r;
    }

    public static String vector(int n){
        return "\\["+list(n, FL_NUM)+"\\]";
    }

    public static String point(int n){
        return "\\("+list(n, FL_NUM)+"\\)";
    }

    public static String vectorN(){
        return "\\[("+ FL_NUM +"(,("+ FL_NUM +"))*)\\]";
    }

    public static String pointN(){
        return "\\(("+ FL_NUM +"(,("+ FL_NUM +"))*)\\)";
    }

    public static String matrix(){
        return "\\[("+ FL_NUM +"(,("+ FL_NUM +"))*(;"+ FL_NUM +"(,("+ FL_NUM +"))*)+)\\]";
    }

    public static String scalar(){
        return FL_NUM;
    }

    public static String complex(){
        return "(("+ FL_NUM +"([+-]"+ POS_FL_NUM +"?)i)|((-?"+ POS_FL_NUM +"?)i([+-]"+ POS_FL_NUM +"))|((-?"+ POS_FL_NUM +"?)i))";
    }

    public static void main(String[] args) {

    }

}
