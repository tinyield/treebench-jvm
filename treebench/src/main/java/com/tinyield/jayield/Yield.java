package com.tinyield.jayield;

/**
 * Equivalent to {@code Consumer<T>} with a yield semantics.
 */
@FunctionalInterface
public interface Yield<T> {
    void ret(T item);
}
