package com.tinyield.jayield;

import java.util.function.Consumer;

public interface Traverser<T> {

    /**
     * Yields elements sequentially in the current thread,
     * until all elements have been processed or an
     * exception is thrown.
     */
    void traverse(Consumer<? super T> yield);

    static <U> Traverser<U> fromTree(INode<U> node){
        return yield -> next(node, yield);
    }

    static <U> void next(INode<U> root, Consumer<? super U> yield) {
        yield.accept(root.getValue());
        if(root.getLeft() != null) next(root.getLeft(), yield);
        if(root.getRight() != null) next(root.getRight(), yield);
    }
}
