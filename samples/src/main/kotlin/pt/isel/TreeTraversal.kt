package pt.isel

fun <T : Any, U : Any> sameFringe(
    tree: Node<T>,
    other: Node<U>,
): Boolean = tree.leaves().zip(other.leaves(), Any::equals).all { item -> item }

/**
 * Helper function to get the leaves of a binary tree as a sequence.
 *
 * * @param root The root node of the binary tree.
 * * @return A sequence of leaf values from the binary tree.
 */
fun <T> Node<T>.leaves(): Sequence<T> =
    sequence {
        if (left == null && right == null) {
            yield(value)
        } else {
            for (n in left?.leaves() ?: emptySequence()) {
                yield(n)
            }
            for (n in right?.leaves() ?: emptySequence()) {
                yield(n)
            }
        }
    }

/**
 * Helper function to perform a pre-order traversal of an AVL tree as a sequence.
 *
 * * @param root The root node of the AVL tree.
 * * @return A sequence of values from the AVL tree in pre-order.
 */
fun <T : Comparable<T>> preOrder(node: Node<T>?): Sequence<T> =
    sequence {
        if (node == null) return@sequence
        // Pre-order: yield current, then left, then right
        yield(node.value)
        for (n in preOrder(node.left)) {
            yield(n)
        }
        for (n in preOrder(node.right)) {
            yield(n)
        }
    }
