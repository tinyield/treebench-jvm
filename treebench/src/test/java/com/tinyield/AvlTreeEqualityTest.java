package com.tinyield;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvlTreeEqualityTest {

    @Test
    public void checkEqualityToTrueInJavaStreamsParallel() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        // TreePrinter.print(bench.mainTree.root);
        // TreePrinter.print(bench.otherTree.root);
        assertTrue(bench.streamsParallel());
    }

    @Test
    public void checkEqualityToFalseInStreamsParallel() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        // TreePrinter.print(bench.mainTree.root);
        // TreePrinter.print(bench.otherTree.root);
        assertFalse(bench.streamsParallel());
    }

    @Test
    public void checkEqualityToTrueInJavaStreamsSequential() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.streams());
    }

    @Test
    public void checkEqualityToFalseInStreamsSequential() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.streams());
    }

    @Test
    public void checkEqualityToTrueTinyieldTraverser() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.tinyieldTraverser());
    }

    @Test
    public void checkEqualityToFalseTinyieldTraverser() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.tinyieldTraverser());
    }
    @Test
    public void checkEqualityToTrueTinyieldAdvancer() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.advancer());
    }

    @Test
    public void checkEqualityToFalseTinyieldAdvancer() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        assertTrue(bench.otherAvlTree.delete(bench.MAX_SENTINEL));
        bench.otherAvlTree.insert(1111);
        // TreePrinter.print(bench.mainAvlTree.root);
        // TreePrinter.print(bench.otherAvlTree.root);
        assertFalse(bench.advancer());
    }
    @Test
    public void checkEqualityToTrueSequentiallyAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.adhoc());
    }

    @Test
    public void checkEqualityToFalseSequentiallyAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.adhoc());
    }

    @Test
    public void checkEqualityToTrueJoolAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.jool());
    }

    @Test
    public void checkEqualityToFalseJoolAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.jool());
    }
    @Test
    public void checkEqualityToTrueStreamExAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.streamEx());
    }

    @Test
    public void checkEqualityToFalseStreamExAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.streamEx());
    }
    @Test
    public void checkEqualityToTrueVavrAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.vavr());
    }

    @Test
    public void checkEqualityToFalseVavrAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.vavr());
    }
    @Test
    public void checkEqualityToTrueProtonpackAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.protonpack());
    }

    @Test
    public void checkEqualityToFalseProtonpackAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.protonpack());
    }
    @Test
    public void checkEqualityToTrueGuavaAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.guava());
    }

    @Test
    public void checkEqualityToFalseGuavaAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.guava());
    }
    @Test
    public void checkEqualityToTrueZiplineAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.zipline());
    }

    @Test
    public void checkEqualityToFalseZiplineAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.zipline());
    }
    @Test
    public void checkEqualityToTrueEclipseAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.eclipse());
    }

    @Test
    public void checkEqualityToFalseEclipseAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.eclipse());
    }
    @Test
    public void checkEqualityToTrueKotlinAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        assertTrue(bench.kotlin());
    }

    @Test
    public void checkEqualityToFalseKotlinAdhoc() {
        BenchAvlTreeEquality bench = new BenchAvlTreeEquality();
        bench.setup();
        /*
         * Replace the only distinct and max value
         */
        bench.otherAvlTree.delete(bench.MAX_SENTINEL);
        bench.otherAvlTree.insert(1111);
        assertFalse(bench.kotlin());
    }
}
