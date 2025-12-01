package com.tinyield;


import com.tinyield.tree.Node;
import com.tinyield.tree.BinaryTreeSpliteratorParallel;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ListIterable;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class BenchDistinctTree extends AbstractBenchAvlTree {

    /**
     * The size of the Sequence for this benchmark
     */
    @Param({"1000000"})
    public int COLLECTION_SIZE = 16; // For Unit Tests

    private static void adhoc(Node<Integer> root, List<Integer> selected) {
        if (!selected.contains(root.getValue())) {
            selected.add(root.getValue());
        }
        if (root.getLeft() != null) adhoc(root.getLeft(), selected);
        if (root.getRight() != null) adhoc(root.getRight(), selected);
    }

    @Benchmark
    public List<Integer> adhoc() {
        List<Integer> selected = new ArrayList<>();
        adhoc(mainAvlTree.root, selected);
        return selected;
    }

    @Benchmark
    public List<Integer> advancer() {
        return Advancer
                .preOrder(mainAvlTree.root)
                .distinct()
                .toList();
    }

    @Benchmark
    public List<Integer> streamsParallel() {
        return StreamSupport
                .stream(new BinaryTreeSpliteratorParallel<>(mainAvlTree.root), true)
                .map(Node::getValue)
                .distinct()
                .collect(toList());
    }

    @Benchmark
    public List<Integer> streams() {
        return StreamSupport
                .stream(mainAvlTree.preOrder().spliterator(), false)
                .distinct()
                .collect(toList());
    }

    @Benchmark
    public List<Integer> jool() {
        return Extensions
                .jool(mainAvlTree.preOrder())
                .distinct()
                .toList();
    }

    @Benchmark
    public io.vavr.collection.List<Integer> vavr() {
        return Extensions
                .vavr(mainAvlTree.preOrder())
                .distinct()
                .toList();
    }

    @Benchmark
    public List<Integer> streamEx() {
        return Extensions
                .streamEx(mainAvlTree.preOrder())
                .distinct()
                .toList();
    }

    @Benchmark
    public ListIterable<Integer> eclipse() {
        return Lists.immutable.ofAll(mainAvlTree.preOrder()).asLazy()
                .distinct()
                .toList();
    }

    @Benchmark
    public List<Integer> kotlin() {
        return BinTreeLeavesKt.distinctToList(mainAvlTree.root);
    }
}
