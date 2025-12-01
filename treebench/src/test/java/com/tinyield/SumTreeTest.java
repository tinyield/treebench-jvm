package com.tinyield;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumTreeTest {

    @Test
    public void checkSumParallel() {
        BenchSumTree bench = new BenchSumTree();
        bench.setup();
        assertEquals(bench.streamsParallel(), bench.adhoc());
    }
    @Test
    public void checkSumAdvancer() {
        BenchSumTree bench = new BenchSumTree();
        bench.setup();
        assertEquals(bench.adhoc(), bench.advancer());
    }
    @Test
    public void checkSumJavaStream() {
        BenchSumTree bench = new BenchSumTree();
        bench.setup();
        assertEquals(bench.adhoc(), bench.streams());
    }
    @Test
    public void checkSumJavaJool() {
        BenchSumTree bench = new BenchSumTree();
        bench.setup();
        assertEquals(bench.adhoc(), bench.jool());
    }
    @Test
    public void checkSumJavaStreamEx() {
        BenchSumTree bench = new BenchSumTree();
        bench.setup();
        assertEquals(bench.adhoc(), bench.streamEx());
    }
    @Test
    public void checkSumJavaVavr() {
        BenchSumTree bench = new BenchSumTree();
        bench.setup();
        assertEquals(bench.adhoc(), bench.vavr());
    }
    @Test
    public void checkSumJavaEclipse() {
        BenchSumTree bench = new BenchSumTree();
        bench.setup();
        assertEquals(bench.adhoc(), bench.eclipse());
    }
    @Test
    public void checkSumJavaKotlin() {
        BenchSumTree bench = new BenchSumTree();
        bench.setup();
        assertEquals(bench.adhoc(), bench.kotlin());
    }
}
