package pt.isel;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ZipIterable<T, U, R> implements Iterable<R> {

    private final Iterator<T> selfIter;
    private final Iterator<U> otherIter;
    private final BiFunction<T, U, R> zipper;
    public ZipIterable(Stream<T> self, Stream<U> other, BiFunction<T, U, R> zipper) {
        this.selfIter = self.iterator();
        this.otherIter = other.iterator();
        this.zipper = zipper;
    }

    // Static utility method to create a new zipped sequence
    static <T, U, R> Stream<R> zip(Stream<T> a, Stream<U> b, BiFunction<T, U, R> zipper) {
        var iterable = new ZipIterable<>(a, b, zipper);
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public Iterator<R> iterator() {
        return new Iterator<>() {
            public boolean hasNext() {
                return selfIter.hasNext() && otherIter.hasNext();
            }

            public R next() {
                return zipper.apply(selfIter.next(), otherIter.next());
            }
        };
    }
}
