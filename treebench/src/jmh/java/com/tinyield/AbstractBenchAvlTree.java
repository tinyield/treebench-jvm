package com.tinyield;

import com.tinyield.tree.AVLTree;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.stream;
import static java.util.stream.Stream.generate;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class AbstractBenchAvlTree {
    /**
     * Tested items are between 0 and this BOUND value.
     */
    public static final int BOUND = 20;
    /**
     * All numbers will be between 0 and this BOUND
     * with exception of a single element that is MAX_SENTINEL.
     */
    public static final Integer MAX_SENTINEL = Integer.valueOf(999);
    static final Random rand = new Random();
    // @Param("100000000") // For Sum
    @Param("10000000")
    public int TREE_SIZE = 128; // 128 for unit tests
    public AVLTree<Integer> mainAvlTree;
    public AVLTree<Integer> otherAvlTree;
    /**
     * Source numbers that will feed the trees.
     */
    private Integer[] nrs;

    private static Integer[] populateArray(int size, int bound) {
        System.out.println("POPULATE Array with " + size);
        return generate(() -> rand.nextInt(bound)).limit(size).toArray(length -> new Integer[length]);
    }

    private static AVLTree<Integer> populateAvlTree(Integer[] source) {
        System.out.println("POPULATE tree... ");
        AVLTree<Integer> tree = new AVLTree<>();    // new AVL tree
        stream(source).forEach(tree::insert); // populate tree
        return tree;
    }

    @Setup
    public void setup() {
        nrs = populateArray(TREE_SIZE, BOUND);
        nrs[0] = MAX_SENTINEL;
        mainAvlTree = populateAvlTree(nrs);
        otherAvlTree = populateAvlTree(nrs);
    }
}
