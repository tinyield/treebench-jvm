package com.tinyield;

import com.codepoetics.protonpack.StreamUtils;
import com.google.common.collect.Streams;
import com.tinyield.jayield.Advancer;
import com.tinyield.jayield.INode;
import com.tinyield.tree.Gen;
import com.tinyield.tree.Item;
import org.eclipse.collections.api.factory.Lists;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.Iterator;

import static com.tinyield.tree.StreamZipOperation.zip;

public class BenchSameFringe extends AbstractBenchBinTree {

    @Benchmark
    public boolean adhoc() {
        // Empty (EOF) generator
        Gen<Integer> emptyGen = () -> new Item<>(null, true, null);

        Gen<Integer> genA = genFringe(treeA, emptyGen);
        Gen<Integer> genB = genFringe(treeB, emptyGen);

        while (true) {
            Item<Integer> ia = genA.get();
            Item<Integer> ib = genB.get();

            // both at EOF => same fringe
            if (ia.eof && ib.eof) return true;
            // one EOF and other not => different
            if (ia.eof != ib.eof) return false;
            // both not EOF: compare values
            if (!ia.value.equals(ib.value)) return false;

            // advance generators (use next or emptyGen if null)
            genA = ia.next != null ? ia.next : emptyGen;
            genB = ib.next != null ? ib.next : emptyGen;
        }
    }

    private Gen<Integer> genFringe(INode<Integer> node, Gen<Integer> genRest) {
        if (node == null) return genRest;

        INode<Integer> left = node.getLeft();
        INode<Integer> right = node.getRight();

        // leaf node (both children null) -> yield its value then continue with genRest
        if (left == null && right == null) {
            return () -> new Item<>(node.getValue(), false, genRest);
        }

        // internal node -> build right-gen first, then left-gen that delegates to it
        Gen<Integer> rightGen = genFringe(right, genRest);
        return genFringe(left, rightGen);
    }

    @Benchmark
    public boolean stream() {
        return zip(Extensions.stream(treeA.leaves()), Extensions.stream(treeB.leaves()), Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean streamEx() {
        return Extensions.streamEx(treeA.leaves())
                .zipWith(Extensions.streamEx(treeB.leaves()), Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark()
    public boolean advancer() {
        return Advancer.getLeaves(treeA)
                .zip(Advancer.getLeaves(treeB), Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean jool() {
        return Extensions.jool(treeA.leaves())
                .zip(Extensions.jool(treeB.leaves()), Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean vavr() {
        return Extensions.vavr(treeA.leaves())
                .zipWith(Extensions.vavr(treeB.leaves()), Object::equals)
                .forAll(f -> f);
    }

    @Benchmark
    public boolean protonpack() {
        return StreamUtils
                .zip(
                        Extensions.protonpack(treeA.leaves()),
                        Extensions.protonpack(treeB.leaves()),
                        Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean guava() {
        return Streams
                .zip(
                        Extensions.guava(treeA.leaves()),
                        Extensions.guava(treeB.leaves()),
                        Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean zipline() {
        Iterator<Integer> it = treeB.leaves().iterator();
        return Extensions.stream(treeA.leaves())
                .map(t -> t.equals(it.next()))
                .allMatch(Boolean.TRUE::equals);
    }

    @Benchmark
    public boolean eclipse() {
        return Lists.immutable.ofAll(treeA.leaves()).asLazy()
                .zip(Lists.immutable.ofAll(treeB.leaves()).asLazy())
                .collect(p -> p.getOne().equals(p.getTwo()))
                .allSatisfy(Boolean.TRUE::equals);
    }

    @Benchmark
    public boolean kotlin() {
        return BinTreeLeavesKt.sameFringe(treeA, treeB, Object::equals);
    }

}
