package com.tinyield

import com.tinyield.jayield.INode
import com.tinyield.tree.BinTree
import java.util.function.BiPredicate

/**
* Implementation of same fringe algorithm that compares the leaves of two binary trees using a provided predicate.
* It uses a zip operation to pair leaves from both trees and applies the predicate to each pair.
* This pipeline processes the leaves in an EAGER manner using the Iterable interface.
*
* * @param q1 The first binary tree.
* * @param q2 The second binary tree.
* * @param predicate A BiPredicate that defines the comparison logic between the leaves of the two trees.
* * @return true if the leaves of both trees are the same according to the predicate; false otherwise.
*/
fun <T : Comparable<T>?, U : Comparable<U>?> sameFringe(
    q1: BinTree<T>,
    q2: BinTree<U>,
    predicate: BiPredicate<T?, U?>,
): Boolean =
    getLeaves(q1)
        .zip(getLeaves(q2)) { t: T, u: U -> predicate.test(t, u) }
        .all(java.lang.Boolean.TRUE::equals)
/**
 * Implementation of equality algorithm that compares the nodes of two AVL trees using a provided predicate.
 * It uses a zip operation to pair nodes from both trees and applies the predicate to each pair.
 * This pipeline processes the nodes in an EAGER manner using the Iterable interface.
 *
 * * @param q1 The first AVL tree.
 * * @param q2 The second AVL tree.
 * * @param predicate A BiPredicate that defines the comparison logic between the nodes of the two trees.
 * * @return true if the nodes of both trees are the same according to the predicate; false otherwise.
 */
fun <T : Comparable<T>?, U : Comparable<U>?> equality(
    q1: INode<T>,
    q2: INode<U>,
    predicate: BiPredicate<T, U>,
): Boolean =
    preOrder(q1)
        .zip(preOrder(q2)) { t: T, u: U -> predicate.test(t, u) }
        .all(java.lang.Boolean.TRUE::equals)

/**
 * Reduces the elements of an AVL tree using the provided binary operation.
 *
 * * @param q1 The AVL tree to be reduced.
 * * @param operation A binary function that takes two parameters: the accumulated value and the current item.
 * * @return The result of reducing the elements of the AVL tree using the specified operation.
 */
fun <T : Comparable<T>?, U : Comparable<U>?> reduce(
    q1: INode<T>,
    operation: (acc: T, T) -> T,
): T = preOrder(q1).reduce(operation)

/**
 * Retrieves a list of distinct elements from the pre-order traversal of an AVL tree.
 *
 * * @param q1 The AVL tree from which to retrieve distinct elements.
 * * @return A list containing the distinct elements from the pre-order traversal of the AVL tree.
 */
fun <T : Comparable<T>?, U : Comparable<U>?> distinctToList(q1: INode<T>): List<T> = preOrder(q1).distinct().toList()

/**
 * Helper function to get the leaves of a binary tree as a sequence.
 *
 * * @param root The root node of the binary tree.
 * * @return A sequence of leaf values from the binary tree.
 */
fun <T : Comparable<T>?> getLeaves(root: INode<T>): Sequence<T> =
    sequence {
        suspend fun SequenceScope<T>.traverseLeaves(node: INode<T>) {
            val left = node.getLeft()
            val right = node.getRight()

            if (left == null && right == null) {
                yield(node.getValue())
            } else {
                traverseLeaves(left)
                traverseLeaves(right)
            }
        }
        traverseLeaves(root)
    }

/**
 * Helper function to perform a pre-order traversal of an AVL tree as a sequence.
 *
 * * @param root The root node of the AVL tree.
 * * @return A sequence of values from the AVL tree in pre-order.
 */
fun <T : Comparable<T>?> preOrder(root: INode<T>?): Sequence<T> =
    sequence {
        suspend fun SequenceScope<T>.traversePreOrder(node: INode<T>?) {
            if (node == null) return

            // Pre-order: yield current, then left, then right
            yield(node.getValue())
            traversePreOrder(node.getLeft())
            traversePreOrder(node.getRight())
        }
        traversePreOrder(root)
    }

