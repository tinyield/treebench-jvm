package pt.isel

/**
 * Helper function to get the leaves of a binary tree as a sequence.
 *
 * * @param root The root node of the binary tree.
 * * @return A sequence of leaf values from the binary tree.
 */
fun <T : Comparable<T>> getLeaves(node: Node<T>): Sequence<T> =
    sequence {
        if (node.left == null && node.right == null) {
            yield(node.value)
        } else {
            if (node.left != null) {
                for (n in getLeaves(node.left)) {
                    yield(n)
                }
            }
            if (node.right != null) {
                for (n in getLeaves(node.right)) {
                    yield(n)
                }
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
