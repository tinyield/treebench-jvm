package com.tinyield;

import com.tinyield.tree.Node;

import java.util.function.Consumer;

public interface Traverser<T> {

    /**
     * Yields elements sequentially in the current thread,
     * until all elements have been processed or an
     * exception is thrown.
     */
    void traverse(Consumer<? super T> yield);

    static <U> Traverser<U> fromTree(Node<U> node){
        return yield -> next(node, yield);
    }

    static <U> void next(Node<U> root, Consumer<? super U> yield) {
        yield.accept(root.getValue());
        if(root.getLeft() != null) next(root.getLeft(), yield);
        if(root.getRight() != null) next(root.getRight(), yield);
    }
}
