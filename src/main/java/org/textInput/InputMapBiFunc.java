package org.textInput;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Represents families of functions that share similar types of input and output with two inputs
 */
public class InputMapBiFunc<A, B, C>{
    private Map<String, BiFunction<A, B, C>> map;
    private A input1;
    private B input2;
    private C output;
    public InputMapBiFunc(A a, B b, C c){
        map = new HashMap<>();
        input1 = a;
        input2 = b;
        output = c;
    }

    /**
     * Adds a new function to the map
     */
    public void put(String name, BiFunction<A,B,C> function){
        map.put(name, function);
    }

    /**
     * Returns the map of functions
     */
    public Map<String,BiFunction<A,B,C>> getMap(){
        return map;
    }

    /**
     * Returns input1 so that the class of the first input can later be extracted
     */
    public A getInput1(){
        return input1;
    }

    /**
     * Returns input2 so that the class of the second input can later be extracted
     */
    public B getInput2(){
        return input2;
    }

    /**
     * Returns output so that the class of the output can later be extracted
     */
    public C getOutput(){
        return output;
    }

    /**
     * Returns value of the function evaluated at the given input
     */
    public C apply(String s, A inp1, B inp2){
        return map.get(s).apply(inp1, inp2);
    }

}
