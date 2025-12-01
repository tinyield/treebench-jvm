package com.tinyield.tree;

import com.tinyield.jayield.INode;

import java.util.Spliterators;
import java.util.Stack;
import java.util.function.Consumer;

public class BinaryTreeSpliteratorSequential<T> extends Spliterators.AbstractSpliterator<INode<T>> {

    private Stack<INode<T>> nodes = new Stack<>();

    public BinaryTreeSpliteratorSequential(INode<T> root) {
        super(Long.MAX_VALUE, NONNULL | IMMUTABLE);
        this.nodes.add(root);
    }

    @Override
     public boolean tryAdvance(Consumer<? super INode<T>> action) {
        if(nodes.empty()) return false;
        INode<T> current = this.nodes.pop();
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