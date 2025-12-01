package com.tinyield.tree;

import com.tinyield.jayield.INode;

import java.util.ArrayDeque;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class BinaryTreeSpliteratorParallel<T> extends Spliterators.AbstractSpliterator<INode<T>> {
    /**
     * a node that has not been traversed, but its children are only
     * traversed if contained in this.pending
     * (otherwise a different spliterator might be responsible)
     */
    private INode<T> pendingNode;
    /** pending nodes needing full traversal */
    private ArrayDeque<INode<T>> pending = new ArrayDeque<>();

    public BinaryTreeSpliteratorParallel(INode<T> root) {
        super(Long.MAX_VALUE, NONNULL | IMMUTABLE);
        push(root);
    }

    private BinaryTreeSpliteratorParallel(INode<T> pending, INode<T> next) {
        super(Long.MAX_VALUE, NONNULL | IMMUTABLE);
        pendingNode = pending;
        if(next!=null) this.pending.offer(next);
    }
    private void push(INode<T> n) {
        if(pendingNode == null) {
            pendingNode = n;
            if(n != null) {
                if(n.getRight()!=null) pending.offerFirst(n.getRight());
                if(n.getLeft() !=null) pending.offerFirst(n.getLeft());
            }
        }
        else pending.offerFirst(n);
    }

    @Override
     public boolean tryAdvance(Consumer<? super INode<T>> action) {
        INode<T> current = pendingNode;
        if(current == null) {
            current = pending.poll();
            if(current == null) return false;
            if(current.getRight() != null) push(current.getRight());
            if(current.getLeft() != null) push(current.getLeft());
        }
        else pendingNode = null;
        action.accept(current);
        return true;
    }

    @Override
    public void forEachRemaining(Consumer<? super INode<T>> action) {
        INode<T> current = pendingNode;
        if(current != null) {
            pendingNode = null;
            action.accept(current);
        }
        for(;;) {
            current = pending.poll();
            if(current == null) break;
            traverseLocal(action, current);
        }
    }
    private void traverseLocal(Consumer<? super INode<T>> action, INode<T> current) {
        do {
            action.accept(current);
            INode<T> child = current.getLeft();
            if(child!=null) traverseLocal(action, child);
            current = current.getRight();
        } while(current != null);
    }

    @Override
    public Spliterator<INode<T>> trySplit() {
        INode<T> next = pending.poll();
        if(next == null) return null;
        if(pending.isEmpty()) {
            pending.offer(next);
            next = null;
        }
        if(pendingNode==null) return next==null? null: new BinaryTreeSpliteratorParallel(next);
        Spliterator<INode<T>> s = new BinaryTreeSpliteratorParallel(pendingNode, next);
        pendingNode = null;
        return s;
    }
}