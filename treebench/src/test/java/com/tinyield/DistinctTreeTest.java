package com.tinyield;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.sort;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class DistinctTreeTest {

    @Test
    public void checkDistinctParallel() {
        BenchDistinctTree bench = new BenchDistinctTree();
        bench.setup();
        Object[] expecteds = bench.streamsParallel().toArray();
        Object[] actuals = bench.adhoc().toArray();
        sort(actuals);
        sort(expecteds);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void checkDistinctAdvancer() {
        BenchDistinctTree bench = new BenchDistinctTree();
        bench.setup();
        Object[] expecteds = bench.adhoc().toArray();
        Object[] actuals = bench.advancer().toArray();
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void checkDistinctJavaStream() {
        BenchDistinctTree bench = new BenchDistinctTree();
        bench.setup();
        Object[] adhoc = bench.adhoc().toArray();
        Object[] streams = bench.streams().toArray();
        assertArrayEquals(adhoc, streams);
    }

    @Test
    public void checkDistinctJavaJool() {
        BenchDistinctTree bench = new BenchDistinctTree();
        bench.setup();
        Object[] expecteds = bench.adhoc().toArray();
        Object[] actuals = bench.jool().toArray();
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void checkDistinctJavaStreamEx() {
        BenchDistinctTree bench = new BenchDistinctTree();
        bench.setup();
        Object[] expecteds = bench.adhoc().toArray();
        Object[] actuals = bench.streamEx().toArray();
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void checkDistinctJavaVavr() {
        BenchDistinctTree bench = new BenchDistinctTree();
        bench.setup();
        Object[] expecteds = bench.adhoc().toArray();
        Object[] actuals = bench.vavr().toJavaArray();
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void checkDistinctJavaEclipse() {
        BenchDistinctTree bench = new BenchDistinctTree();
        bench.setup();
        Object[] expecteds = bench.adhoc().toArray();
        Object[] actuals = bench.eclipse().toArray();
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void checkDistinctJavaKotlin() {
        BenchDistinctTree bench = new BenchDistinctTree();
        bench.setup();
        Object[] expecteds = bench.adhoc().toArray();
        Object[] actuals = bench.kotlin().toArray();
        assertArrayEquals(expecteds, actuals);
    }
}
