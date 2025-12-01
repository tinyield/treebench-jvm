package com.tinyield.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * Abstract base class for tree iterators using Baker-style generator composition.
 * Uses a trampoline approach to avoid stack overflow on deep trees.
 */
public abstract class AbstractIterator<U extends Comparable<U>> implements Iterator<U> {

    /**
     * Empty (EOF) generator singleton
     */
    private final Gen<U> emptyGen = () -> new Item<>(null, true, null);

    // Trampoline: shared state holding the current active generator
    private Gen<U> current;
    private U nextValue;
    private boolean hasNext;

    protected AbstractIterator(Node<U> tree) {
        this.current = buildGenerator(tree, () -> emptyGen);
        advance();
    }

    /**
     * Hook method: subclasses implement their specific traversal logic
     * @param node current node to process
     * @param genRestThunk supplier for the continuation generator
     * @return generator for this subtree
     */
    protected abstract Gen<U> buildGenerator(Node<U> node, Supplier<Gen<U>> genRestThunk);

    /**
     * Advance to the next item, updating hasNext and nextValue
     */
    private void advance() {
        Item<U> item = current.get();
        this.hasNext = !item.eof;
        this.nextValue = item.value;
        // Trampoline: update current to next generator
        this.current = item.next != null ? item.next : emptyGen;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public U next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        U result = nextValue;
        advance();
        return result;
    }
}
