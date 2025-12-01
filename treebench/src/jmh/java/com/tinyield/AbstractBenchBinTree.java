package com.tinyield;

import com.tinyield.tree.BinTree;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public abstract class AbstractBenchBinTree {

    /**
     * The size of the Sequence for benchmarking
     */
    // @Param("100000000") // 100_000_000 For Sum
    // @Param("10000000") // 10_000_000 For checking AVL trees equality
    @Param({"1000000"}) // 1_000_000 For same-fringe. 100_000 is the number of nodes on the Fringe of the leaf-tree.
    public int COLLECTION_SIZE = 16; // For Unit Tests

    /**
     * treeA and treeB are two binary leaf-trees with the same Integer objects.
     * but shuffled in different way.
     * Same fringe but different topology.
     * All values are between 1 and the COLLECTION_SIZE.
     */
    public BinTree<Integer> treeA;
    public BinTree<Integer> treeB;

    static <T> void shuffle(T[] values, int seed) {
        var rnd = new Random(seed);
        for (var i = 0; i < values.length - 2; i++) {
            var iSwap = rnd.nextInt(values.length - i) + i;
            var tmp = values[iSwap];
            values[iSwap] = values[i];
            values[i] = tmp;
        }
    }

    /**
     * Sets up the data sources to be used in this benchmark
     */
    @Setup
    public void init() {
        Integer[] nrs = IntStream
                .rangeClosed(1, COLLECTION_SIZE)
                .boxed()
                .toArray(Integer[]::new);
        shuffle(nrs, 428);
        treeA = new BinTree<>(Arrays.asList(nrs).iterator());
        // Shuffling will create a tree with the same fringe but different topology
        shuffle(nrs, 428);
        treeB = new BinTree<>(Arrays.asList(nrs).iterator());
    }
}
