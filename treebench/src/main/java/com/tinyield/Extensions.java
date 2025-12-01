package com.tinyield;

import com.tinyield.tree.BinTree;
import one.util.streamex.StreamEx;
import org.jooq.lambda.Seq;

import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Extensions {

    public static <T> Stream<T> stream(Iterable<T> src) {
        return StreamSupport
                .stream(Spliterators.spliterator(src.iterator(), -1, 0), false);
    }

    public static <T> StreamEx<T> streamEx(Iterable<T> src) {
        return StreamEx.of(src.iterator());
    }

    public static <T> Seq<T> jool(Iterable<T> src) {
        return Seq.seq(src.iterator());
    }

    public static <T> io.vavr.collection.Stream<T> vavr(Iterable<T> src) {
        return io.vavr.collection.Stream.ofAll(src);
    }

    public static <T> Stream<T> protonpack(Iterable<T> src) {
        return stream(src);
    }

    public static <T> Stream<T> guava(Iterable<T> src) {
        return stream(src);
    }
}
