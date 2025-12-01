package com.tinyield.tree;

import com.tinyield.jayield.INode;

import java.util.function.Supplier;

/**
 * Iterator that traverses the leaves of a binary tree left-to-right,
 * implemented in Baker-style (generator composition via closures).
 * Uses a trampoline approach to avoid stack overflow on deep trees.
 */
public class LeavesIterator<U extends Comparable<U>> extends AbstractIterator<U> {

    public LeavesIterator(BinTree<U> tree) {
        super(tree);
    }

    /**
     * Build generator yielding leaves of `node`, then delegating to `genRest`
     */
    @Override
    protected Gen<U> buildGenerator(INode<U> node, Supplier<Gen<U>> genRestThunk) {
        if (node == null) {
            return genRestThunk.get();
        }

        INode<U> left = node.getLeft();
        INode<U> right = node.getRight();

        // leaf node (both children null) -> yield its value then continue with genRest
        if (left == null && right == null) {
            return () -> new Item<>(node.getValue(), false, genRestThunk.get());
        }

        // internal node -> build right-gen first, then left-gen that delegates to it
        return buildGenerator(left, () -> buildGenerator(right, genRestThunk));
    }
}
