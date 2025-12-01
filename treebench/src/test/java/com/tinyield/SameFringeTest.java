package com.tinyield;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SameFringeTest {

    BenchSameFringe bench = new BenchSameFringe();

    @BeforeEach
    public void setup() {
        bench.init();
    }

    @Test
    public void checkBinTreeFringeIntegrity() {
        Iterator<Integer> leavesA = bench.treeA.leaves().iterator();
        Iterator<Integer> leavesB = bench.treeB.leaves().iterator();
        IntStream.rangeClosed(1, bench.COLLECTION_SIZE).forEach(expected -> {
            assertEquals(expected, leavesA.next().intValue());
            assertEquals(expected, leavesB.next().intValue());
        });
        assertFalse(leavesA.hasNext());
        assertFalse(leavesB.hasNext());
    }

    @Test
    public void checkSameFringeToTrueInJavaStreamsSequential() {
        // TreePrinter.print(bench.treeA);
        // TreePrinter.print(bench.treeB);
        assertTrue(bench.stream());
    }

    @Test
    public void checkSameFringeToFalseInJavaStreamsSequential() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.stream());
    }

    @Test
    public void checkSameFringeToTrueAdhoc() {
        assertTrue(bench.adhoc());
    }

    @Test
    public void checkSameFringeToFalseAdhoc() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.adhoc());
    }

    @Test
    public void checkSameFringeToTrueAdvancer() {
        assertTrue(bench.advancer());
    }

    @Test
    public void checkSameFringeToFalseAdvancer() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.advancer());
    }

    @Test
    public void checkSameFringeToTrueJool() {
        assertTrue(bench.jool());
    }

    @Test
    public void checkSameFringeToFalseJool() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.jool());
    }

    @Test
    public void checkSameFringeToTrueStreamEx() {
        assertTrue(bench.streamEx());
    }

    @Test
    public void checkSameFringeToFalseStreamEx() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.streamEx());
    }

    @Test
    public void checkSameFringeToTrueEclipse() {
        assertTrue(bench.eclipse());
    }

    @Test
    public void checkSameFringeToFalseEclipse() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.eclipse());
    }

    @Test
    public void checkSameFringeToTrueProtonpack() {
        assertTrue(bench.protonpack());
    }

    @Test
    public void checkSameFringeToFalseProtonpack() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.protonpack());
    }

    @Test
    public void checkSameFringeToTrueVavr() {
        assertTrue(bench.vavr());
    }

    @Test
    public void checkSameFringeToFalseVavr() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.vavr());
    }

    @Test
    public void checkSameFringeToTrueGuava() {
        assertTrue(bench.guava());
    }

    @Test
    public void checkSameFringeToFalseGuava() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.guava());
    }

    @Test
    public void checkSameFringeToTrueZipline() {
        assertTrue(bench.zipline());
    }

    @Test
    public void checkSameFringeToFalseZipline() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.zipline());
    }

    @Test
    public void checkSameFringeToTrueKotlin() {
        assertTrue(bench.kotlin());
    }

    @Test
    public void checkSameFringeToFalseKotlin() {
        bench.treeA.Insert(bench.COLLECTION_SIZE + 99);
        bench.treeB.Insert(bench.COLLECTION_SIZE + 11);
        assertFalse(bench.kotlin());
    }

}
