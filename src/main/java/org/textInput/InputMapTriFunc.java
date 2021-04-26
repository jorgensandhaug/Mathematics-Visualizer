package org.textInput;

import org.utils.TriFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents families of functions that share similar types of input and output with three inputs
 */
public class InputMapTriFunc<A,B,C,D>{
    private final Map<String, TriFunction<A, B, C, D>> map;
    private final A input1;
    private final B input2;
    private final C input3;
    private final D output;

    public InputMapTriFunc(A a, B b, C c, D d){
        map = new HashMap<>();
        input1 = a;
        input2 = b;
        input3 = c;
        output = d;
    }

    /**
     * Adds a new function to the map
     */
    public void put(String name, TriFunction<A,B,C,D> function){
        map.put(name, function);
    }

    /**
     * Returns the map of functions
     */
    public Map<String,TriFunction<A,B,C,D>> getMap(){
        return map;
    }

    /**
     * Returns input1 so that the class of the first input can later be extracted
     */
    public A getInput1(){
        return input1;
    }

    /**
     * Returns input2 so that the class of the first input can later be extracted
     */
    public B getInput2(){
        return input2;
    }

    /**
     * Returns input3 so that the class of the first input can later be extracted
     */
    public C getInput3(){
        return input3;
    }

    /**
     * Returns output so that the class of the output can later be extracted
     */
    public D getOutput(){
        return output;
    }

    /**
     * Returns value of the function evaluated at the given input
     */
    public D apply(String s, A inp1, B inp2, C inp3){
        return map.get(s).apply(inp1, inp2, inp3);
    }
}
