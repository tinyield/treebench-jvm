package com.tinyield.tree;

/**
 * Item returned by a generator function.
 * Contains a value, EOF flag, and the next generator in the chain.
 */
public class Item<T> {
    public final T value;
    public final boolean eof;
    public final Gen<T> next;

    public Item(T value, boolean eof, Gen<T> next) {
        this.value = value;
        this.eof = eof;
        this.next = next;
    }
}

