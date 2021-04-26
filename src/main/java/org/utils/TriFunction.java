package org.utils;



/**
 * Implemented by operations that require three inputs
 */
@FunctionalInterface
public interface TriFunction<A,B,C,D>{
    D apply(A a, B b, C c);
}
