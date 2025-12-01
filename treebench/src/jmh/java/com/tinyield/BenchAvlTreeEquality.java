/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.tinyield;

import com.codepoetics.protonpack.StreamUtils;
import com.google.common.collect.Streams;
import com.tinyield.jayield.Advancer;
import com.tinyield.jayield.INode;
import com.tinyield.jayield.Traverser;
import com.tinyield.tree.BinaryTreeSpliteratorParallel;
import com.tinyield.tree.BinaryTreeSpliteratorSequential;
import com.tinyield.tree.Gen;
import com.tinyield.tree.Item;
import com.tinyield.tree.ZipSpliterator;
import org.eclipse.collections.api.factory.Lists;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchAvlTreeEquality extends AbstractBenchAvlTree {

    @Benchmark
    public boolean streamsParallel() {
        Spliterator<INode<Integer>> iter1 = new BinaryTreeSpliteratorParallel<>(mainAvlTree.root);
        Spliterator<INode<Integer>> iter2 = new BinaryTreeSpliteratorParallel<>(otherAvlTree.root);
        ZipSpliterator<INode<Integer>, INode<Integer>, Boolean> zip = new ZipSpliterator<>(
                iter1,
                iter2,
                (n1, n2) -> n1.getValue().equals(n2.getValue()));
        return StreamSupport
                .stream(zip, true)
                .allMatch(n -> n);
    }

    @Benchmark
    public boolean streams() {
        Spliterator<INode<Integer>> iter1 = new BinaryTreeSpliteratorSequential<>(mainAvlTree.root);
        Spliterator<INode<Integer>> iter2 = new BinaryTreeSpliteratorSequential<>(otherAvlTree.root);
        ZipSpliterator<INode<Integer>, INode<Integer>, Boolean> zip = new ZipSpliterator<>(
                iter1,
                iter2,
                (n1, n2) -> n1.getValue().equals(n2.getValue()));
        return StreamSupport
                .stream(zip, false)
                .allMatch(n -> n);
    }

    @Benchmark
    public boolean tinyieldTraverser() {
        boolean[] res = {true};
        Advancer<Integer> adv = Advancer.preOrder(mainAvlTree.root);
        Traverser
                .fromTree(otherAvlTree.root)
                .traverse(e1 -> {
                    if (!adv.tryAdvance(e2 -> {
                        if (!e1.equals(e2))
                            res[0] = false;
                    }))
                        res[0] = false;
                });
        return res[0];
    }

    @Benchmark
    public boolean advancer() {
        Advancer<Integer> adv = Advancer.preOrder(mainAvlTree.root);
        Advancer<Integer> other = Advancer.preOrder(otherAvlTree.root);
        return adv
                .zip(other, Object::equals)
                .allMatch(e -> e);
    }


    @Benchmark
    public boolean adhoc() {
        // Empty (EOF) generator
        Gen<Integer> emptyGen = () -> new Item<>(null, true, null);

        Gen<Integer> genMain = genPreOrder(mainAvlTree.root, emptyGen);
        Gen<Integer> genOther = genPreOrder(otherAvlTree.root, emptyGen);

        while (true) {
            Item<Integer> im = genMain.get();
            Item<Integer> io = genOther.get();

            // both at EOF => trees are equal
            if (im.eof && io.eof) return true;
            // one EOF and other not => different
            if (im.eof != io.eof) return false;
            // both not EOF: compare values
            if (!im.value.equals(io.value)) return false;

            // advance generators (use next or emptyGen if null)
            genMain = im.next != null ? im.next : emptyGen;
            genOther = io.next != null ? io.next : emptyGen;
        }
    }

    private Gen<Integer> genPreOrder(INode<Integer> node, Gen<Integer> genRest) {
        if (node == null) return genRest;

        // Pre-order: yield current node, then left, then right
        return () -> new Item<>(
                node.getValue(),
                false,
                genPreOrder(node.getLeft(), genPreOrder(node.getRight(), genRest))
        );
    }

    @Benchmark
    public boolean streamEx() {
        return Extensions.streamEx(mainAvlTree.preOrder())
                .zipWith(Extensions.streamEx(otherAvlTree.preOrder()), Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean jool() {
        return Extensions.jool(mainAvlTree.preOrder())
                .zip(Extensions.jool(otherAvlTree.preOrder()), Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean vavr() {
        return Extensions.vavr(mainAvlTree.preOrder())
                .zipWith(Extensions.vavr(otherAvlTree.preOrder()), Object::equals)
                .forAll(f -> f);
    }

    @Benchmark
    public boolean protonpack() {
        return StreamUtils
                .zip(
                        Extensions.protonpack(mainAvlTree.preOrder()),
                        Extensions.protonpack(otherAvlTree.preOrder()),
                        Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean guava() {
        return Streams
                .zip(
                        Extensions.guava(mainAvlTree.preOrder()),
                        Extensions.guava(otherAvlTree.preOrder()),
                        Object::equals)
                .allMatch(f -> f);
    }

    @Benchmark
    public boolean zipline() {
        Iterator<Integer> it = otherAvlTree.preOrder().iterator();
        return Extensions.stream(mainAvlTree.preOrder())
                .map(t -> t.equals(it.next()))
                .allMatch(Boolean.TRUE::equals);
    }

    @Benchmark
    public boolean eclipse() {
        return Lists.immutable.ofAll(mainAvlTree.preOrder()).asLazy()
                .zip(Lists.immutable.ofAll(otherAvlTree.preOrder()).asLazy())
                .collect(p -> p.getOne().equals(p.getTwo()))
                .allSatisfy(Boolean.TRUE::equals);
    }

    @Benchmark
    public boolean kotlin() {
        return BinTreeLeavesKt.equality(mainAvlTree.root, otherAvlTree.root, Object::equals);
    }
}
