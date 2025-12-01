package com.tinyield.tree;

import com.tinyield.jayield.INode;

import java.util.function.Supplier;

/**
 * Iterator that traverses all nodes of a binary tree in pre-order,
 * implemented in Baker-style (generator composition via closures).
 * Uses a trampoline approach to avoid stack overflow on deep trees.
 */
public class NodesIterator<U extends Comparable<U>> extends AbstractIterator<U> {

    public NodesIterator(INode<U> tree) {
        super(tree);
    }

    /**
     * Build generator yielding nodes in pre-order: current → left → right,
     * then delegating to `genRest`
     */
    @Override
    protected Gen<U> buildGenerator(INode<U> node, Supplier<Gen<U>> genRestThunk) {
        if (node == null) {
            return genRestThunk.get();
        }

        INode<U> left = node.getLeft();
        INode<U> right = node.getRight();

        // Pre-order: yield current node first, then left subtree, then right subtree
        return () -> new Item<>(
                node.getValue(),
                false,
                buildGenerator(left, () -> buildGenerator(right, genRestThunk))
        );
    }
}
