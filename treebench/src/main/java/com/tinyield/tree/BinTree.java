package com.tinyield.tree;

import java.util.Iterator;

/**
 * A binary leaf-free.
 * All inserted nodes go to leafs.
 * We may have trees with the same fringe and different topology,
 * when we insert the same values indifferent order.
 *
 * @param <T>
 */
public class BinTree<T extends Comparable<T>> implements Node<T> {
    private BinTree<T> _left;
    private BinTree<T> _right;
    private T _value;

    public BinTree<T> getLeft() {
        return _left;
    }

    public BinTree<T> getRight() {
        return _right;
    }

    // On interior nodes, any value greater than or equal to Value goes in the
    // right subtree, everything else in the left.
    public T getValue() {
        return _value;
    }

    public boolean isLeaf() {
        return _left == null;
    }

    private BinTree(BinTree<T> left, BinTree<T> right, T value) {
        _left = left;
        _right = right;
        _value = value;
    }

    public BinTree(T value) {
        this(null, null, value);
    }

    public BinTree(Iterator<T> values) {
        _value = values.next();
        while (values.hasNext()) {
            Insert(values.next());
        }

    }

    public void Insert(T value) {
        if (isLeaf()) {
            if (value.compareTo(_value) < 0) {
                _left = new BinTree<T>(value);
                _right = new BinTree<T>(_value);
            } else {
                _left = new BinTree<T>(_value);
                _right = new BinTree<T>(value);
                _value = value;
            }
        } else {
            if (value.compareTo(_value) < 0) {
                _left.Insert(value);
            } else {
                _right.Insert(value);
            }
        }
    }

    public Iterable<T> leaves() {
        return () -> new LeavesIterator<>(this);
    }
    public Iterable<T> preOrder() {
        return () -> new NodesIterator<>(this);
    }
}
