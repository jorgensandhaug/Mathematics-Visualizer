package org.textInput;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents families of functions that share similar types of input and output with one input
 */
public class InputMapFunc<A,B> {
    private Map<String, Function<A, B>> map;
    private A input;
    private B output;
    public InputMapFunc(A a, B b){
        map = new HashMap<>();
        input = a;
        output = b;
    }

    /**
     * Adds a new function to the map
     */
    public void put(String name, Function<A,B> function){
        map.put(name, function);
    }

    /**
     * Returns the map of functions
     */
    public Map<String,Function<A,B>> getMap(){
        return map;
    }

    /**
     * Returns the input so that the class of the input can later be extracted
     */
    public A getInput(){
        return input;
    }

    /**
     * Returns the output so that the class of the output can later be extracted
     */
    public B getOutput(){
        return output;
    }

    /**
     * Returns value of the function evaluated at the given input
     */
    public B apply(String s, A inp1){
        return map.get(s).apply(inp1);
    }
}
