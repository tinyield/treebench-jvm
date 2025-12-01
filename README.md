# TreeBench

A couple of benchmarks in tree traversal scenarios comparing different stream
libraries (such as Java Stream, Jool, Vavr, StreamEx, and others) in the JVM.

Run with, for example:

```
./gradlew clean jmhJar
java -jar ./treebench/build/libs/treebench-jmh.jar -i 4 -wi 4 -f 1 -r 2 -w 2
```

According to our experiments, the above arguments provide consistent observations. You may vary these parameters accordingly.

* `-i 4` → **`--iterations`** - Number of **measurement iterations** JMH will perform **after warmup**.
* `-wi 4` → **`--warmupIterations`** - Number of **warmup iterations** before actual measurement.
* `-f 1` → **`--forks`** - Number of **forked JVMs** in which the benchmark will run. Each fork is a fresh JVM process.
* `-r 2` → **`--time`** - **Time per iteration**. Default unit is seconds unless a suffix (`ms`, `s`, `m`) is provided. Here: `2` seconds per iteration.
* `-w 2` → **`--warmupTime`** Controls the duration of warmup time per iteration.

