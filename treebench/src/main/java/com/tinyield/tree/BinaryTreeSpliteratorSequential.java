package com.tinyield.tree;

import java.util.Spliterators;
import java.util.Stack;
import java.util.function.Consumer;

public class BinaryTreeSpliteratorSequential<T> extends Spliterators.AbstractSpliterator<Node<T>> {

    private Stack<Node<T>> nodes = new Stack<>();

    public BinaryTreeSpliteratorSequential(Node<T> root) {
        super(Long.MAX_VALUE, NONNULL | IMMUTABLE);
        this.nodes.add(root);
    }

    @Override
     public boolean tryAdvance(Consumer<? super Node<T>> action) {
        if(nodes.empty()) return false;
        Node<T> current = this.nodes.pop();
        if(current != null) {
            action.accept(current);
            if(current.getLeft() != null)
                this.nodes.push(current.getLeft());
            if(current.getRight() != null)
                this.nodes.push(current.getRight());
            return true;
        }
        return false;
    }

}