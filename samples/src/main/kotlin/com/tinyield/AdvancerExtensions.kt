package com.tinyield

import pt.isel.Node
import java.util.function.BinaryOperator
import java.util.function.Predicate

fun <T> Advancer<T>.filter(pred: (T) -> Boolean) =
    Advancer { yield ->
        var found = false
        while (!found &&
            invoke { e ->
                if (pred(e)) {
                    yield(e)
                    found = true
                }
            }
        ) { // no op
        }
        found
    }

fun <T, R> Advancer<T>.map(mapper: (T) -> R) =
    Advancer { yield ->
        invoke { item -> yield(mapper(item)) }
    }

fun <T> Advancer<T>.limit(size: Int): Advancer<T> {
    var count = size
    return Advancer { yield ->
        if (--count < 0) {
            false
        } else {
            invoke(yield)
        }
    }
}

fun <T, R> Advancer<T>.flatMap(mapper: (T) -> Advancer<R>): Advancer<R> {
    var src = Advancer<R> { yield -> false } // Empty sequence
    return Advancer { yield ->
        while (!src(yield)) {
            if (!invoke { item -> src = mapper(item) }) {
                return@Advancer false
            }
        }
        true
    }
}

/**
 * Returns a sequence  consisting of the distinct elements (according to
 * <see><T>.Equals</T></see>).
 */
fun <T> Advancer<T>.distinct(): Advancer<T> {
    val selected: HashSet<T> = HashSet()
    return Advancer { yield ->
        var found = false
        while (!found &&
            invoke { item: T ->
                if (selected.add(item)) {
                    yield(item)
                    found = true
                }
            }
        ) {
        }
        found
    }
}

/**
 * Applies a specified function to the corresponding elements of two
 * sequences, producing a sequence of the results.
 */
fun <T, U, R> Advancer<T>.zip(
    other: Advancer<U>,
    zipper: (T, U) -> R,
): Advancer<R> =
    Advancer { yield ->
        var yielded = false
        invoke { left ->
            yielded = other { right ->
                yield(zipper(left, right))
            }
        }
        yielded
    }

/**
 * Returns whether all elements of this query match the provided
 * predicate. May not evaluate the predicate on all elements if not
 * necessary for determining the result. If the query is empty then
 * `true` is returned and the predicate is not evaluated.
 */
fun <T> Advancer<T>.allMatch(p: Predicate<T>): Boolean {
    var succeed = true
    while (succeed &&
        invoke { item ->
            if (!p.test(item)) {
                succeed = false
            }
        }
    ) {
    }
    return succeed
}

fun <T> Advancer<T>.reduce(
    seed: T,
    accumulator: BinaryOperator<T>,
): T {
    var prev: T = seed
    while (invoke { curr -> prev = accumulator.apply(prev, curr) }) { // no op
    }
    return prev
}

/**
 * Returns a list containing the elements of this sequence.
 */
fun <T> Advancer<T>.toList(): MutableList<T> {
    val data: MutableList<T> = ArrayList()
    while (invoke { e: T -> data.add(e) }) {
        // no op
    }
    return data
}

/**
 * Build an Advancer that traverses the leaves of `root` left-to-right,
 * implemented in Baker-style (generator composition via closures).
 *
 * @param root the root node of the tree
 * @param <U> the type of elements in the tree
 * @return an Advancer that yields leaves left-to-right
</U> */
fun <U> getLeaves(root: Node<U>): Advancer<U> {
    return gen(root) { Advancer.empty() }
}

/**
 * Helper method to build generator yielding leaf nodes, then delegating to genRest.
 */
private fun <U> gen(
    node: Node<U>?,
    genRest: () -> Advancer<U>,
): Advancer<U> {
    if (node == null) {
        return genRest()
    }

    // If it's an internal node, recursively process left then right subtrees
    if (node.left != null || node.right != null) {
        return gen(node.left) { gen(node.right, genRest) }
    }

    // Leaf node: yield its value, then delegate to genRest for subsequent calls
    val rest = genRest()
    var yielded = false
    return Advancer<U> { yield ->
        if (!yielded) {
            yielded = true
            yield(node.value)
            true
        } else {
            rest(yield)
        }
    }
}
