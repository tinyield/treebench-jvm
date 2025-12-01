package com.tinyield.tree;

public interface Node<T> {
    Node<T> getLeft();
    Node<T> getRight();
    T getValue();
}
