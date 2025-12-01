package pt.isel;

import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Extensions {

    public static <T, U, R> Stream<R> zip(Stream<T> self, Stream<U> other, BiFunction<T, U, R> zipper) {
        Spliterator<T> selfIter = self.spliterator();
        Spliterator<U> otherIter = other.spliterator();
        return StreamSupport.stream(new AbstractSpliterator<>(Long.min(selfIter.estimateSize(), otherIter.estimateSize()), selfIter.characteristics() & otherIter.characteristics()) {
            public boolean tryAdvance(Consumer<? super R> cons) {
                return selfIter.tryAdvance(
                        left -> otherIter.tryAdvance(
                                right -> cons.accept(zipper.apply(left, right))));
            }
        }, false);
    }
}
