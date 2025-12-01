package com.tinyield.tree;

import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ZipSpliterator<T, U, R> implements Spliterator<R> {

    private final Spliterator<T> src1;
    private final Spliterator<U> src2;
    private final BiFunction<T, U, R> zipper;

    public ZipSpliterator(Spliterator<T> s1, Spliterator<U> s2, BiFunction<T, U, R> zipper) {
        this.src1 = s1;
        this.src2 = s2;
        this.zipper = zipper;
    }

    @Override
    public boolean tryAdvance(Consumer<? super R> action) {
        return src1.tryAdvance(e1 ->
                    src2.tryAdvance(e2 ->
                            action.accept(zipper.apply(e1, e2))));
    }

    @Override
    public void forEachRemaining(Consumer<? super R> action) {
        src1.forEachRemaining(e1 -> src2.tryAdvance(e2 -> action.accept(zipper.apply(e1, e2))));
    }

    @Override
    public Spliterator<R> trySplit() {
        Spliterator<T> new1 = src1.trySplit();
        Spliterator<U> new2 = src2.trySplit();
        return new1 == null && new2 == null
            ? null
            : new ZipSpliterator<>(new1, new2, zipper);
    }

    @Override
    public long estimateSize() {
        return Math.min(src1.estimateSize(), src2.estimateSize());
    }

    @Override
    public int characteristics() {
        return src1.characteristics() & src2.characteristics() & ~(DISTINCT | SORTED);
    }
}
