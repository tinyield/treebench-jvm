package com.tinyield;


import com.tinyield.jayield.Advancer;
import com.tinyield.jayield.INode;
import com.tinyield.tree.BinaryTreeSpliteratorParallel;
import org.eclipse.collections.api.factory.Lists;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

import java.util.stream.StreamSupport;

public class BenchSumTree extends AbstractBenchAvlTree {

    /**
     * The size of the Sequence for this benchmark
     */
    @Param({"1000000"})
    public int COLLECTION_SIZE = 16; // For Unit Tests

    @Benchmark
    public int adhoc() {
        return adhoc(mainAvlTree.root);
    }
    private static int adhoc(INode<Integer> root) {
        int sum = root.getValue();
        if(root.getLeft() != null) sum += adhoc(root.getLeft());
        if(root.getRight() != null) sum += adhoc(root.getRight());
        return sum;
    }

    @Benchmark
    public int advancer() {
        return Advancer
            .preOrder(mainAvlTree.root)
            .reduce(0, Integer::sum);
    }

    @Benchmark
    public int streamsParallel() {
        return StreamSupport
            .stream(new BinaryTreeSpliteratorParallel<>(mainAvlTree.root), true)
            .reduce(0, (prev, n) -> prev + n.getValue(), Integer::sum);
    }

    @Benchmark
    public int streams() {
        return StreamSupport
            .stream(mainAvlTree.preOrder().spliterator(), false)
            .reduce(0, Integer::sum);
    }
    @Benchmark
    public int jool() {
        return Extensions
            .jool(mainAvlTree.preOrder())
            .foldRight(0, Integer::sum);
    }
    @Benchmark
    public int vavr() {
        return Extensions
            .vavr(mainAvlTree.preOrder())
            .reduce(Integer::sum);
    }
    @Benchmark
    public int streamEx() {
        return Extensions
            .streamEx(mainAvlTree.preOrder())
            .reduce(0, Integer::sum);
    }
    @Benchmark
    public int eclipse() {
        return Lists.immutable.ofAll(mainAvlTree.preOrder()).asLazy()
            .reduce(Integer::sum)
            .get();
    }
    @Benchmark
    public int kotlin() {
        return BinTreeLeavesKt.reduce(mainAvlTree.root, (prev, n) -> prev + n);
    }
}
