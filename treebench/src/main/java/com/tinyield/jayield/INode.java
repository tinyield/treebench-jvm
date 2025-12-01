package com.tinyield.jayield;

public interface INode<T> {
    INode<T> getLeft();
    INode<T> getRight();
    T getValue();
}
