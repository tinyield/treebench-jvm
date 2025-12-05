package com.tinyield

/**
 * Equivalent to `Consumer<T>` with a yield semantics.
 */
fun interface Yield<T> {
    operator fun invoke(item: T)
}
