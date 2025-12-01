package com.tinyield.tree;

import java.util.function.Supplier;

/**
 * Generator function type that produces Items in a lazy sequence.
 * Used for Baker-style generator composition.
 */
@FunctionalInterface
public interface Gen<T> extends Supplier<Item<T>> {
}

