package com.tinyield;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * Sequential traverser with internal and individually step approach.
 */
public interface Advancer<T> {
    /**
     * If a remaining element exists, yields that element through
     * the given action.
     */
    boolean tryAdvance(Yield<? super T> yield);

    /**
     * An Advancer object without elements.
     */
    static <R> Advancer<R> empty() {
        return action -> false;
    }

    /**
     * Returns a sequence  consisting of the distinct elements (according to
     * <see><T>.Equals</see>).
     */
    default Advancer<T> distinct() {
        HashSet<T> selected = new HashSet<T>();
        return yield -> {
            BoolBox found = new BoolBox(false);
            while (found.isFalse() && tryAdvance(item ->
            {
                if (selected.add(item))
                {
                    yield.ret(item);
                    found.set(true);
                }
            })) { }
            return found.isTrue();
        };
    }

    /**
     * Applies a specified function to the corresponding elements of two
     * sequences, producing a sequence of the results.
     */
    default <U, R> Advancer<R> zip(Advancer<U> other, BiFunction<? super T, ? super U, ? extends R> zipper) {
        // Simplified version assuming this and other have the same size!
        return yield -> tryAdvance(e1 -> other.tryAdvance(e2 -> {
            yield.ret(zipper.apply(e1, e2));
        }));
    }
    /**
     * Returns whether all elements of this query match the provided
     * predicate. May not evaluate the predicate on all elements if not
     * necessary for determining the result. If the query is empty then
     * {@code true} is returned and the predicate is not evaluated.
     */
    default boolean allMatch(Predicate<? super T> p) {
        BoolBox succeed = new BoolBox(true);
        while(succeed.isTrue() && tryAdvance(item -> {
            if(!p.test(item)) {
                succeed.set(false);
            }
        })) { }
        return succeed.isTrue();
    }

    default T reduce(T seed, BinaryOperator<T> accumulator) {
        class Box<T> {
            T val;
            public Box(T val) { this.val = val; }
        }
        Box<T> prev = new Box<>(seed);
        while(tryAdvance(curr -> prev.val = accumulator.apply(prev.val, curr))) {
        }
        return prev.val;
    }

    /**
     * Returns a list containing the elements of this sequence.
     */
    default List<T> toList()
    {
        List<T> data = new ArrayList<>();
        while(tryAdvance(data::add));
        return data;
    }

    class BoolBox {
        private boolean value;

        public BoolBox(boolean value) {
            this.value = value;
        }
        public boolean isTrue() {
            return value;
        }

        public boolean isFalse() {
            return !value;
        }

        public void set(boolean val) {
            value = val;
        }
    }
}
