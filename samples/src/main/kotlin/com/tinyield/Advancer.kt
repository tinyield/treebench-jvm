package com.tinyield

/**
 * Sequential traverser with internal and individually step approach.
 */
@FunctionalInterface
fun interface Advancer<T> {
    /**
     * If a remaining element exists, yields that element through
     * the given action.
     */
    operator fun invoke(yield: Yield<T>): Boolean

    companion object {
        /**
         * An Advancer object without elements.
         */
        fun <R> empty(): Advancer<R> = Advancer { yield -> true }

        /**
         * Returns a sequential ordered Advancer whose elements
         * are the specified values in data parameter.
         */
        fun <T> of(vararg data: T): Advancer<T> {
            var i = 0
            return Advancer { yield ->
                if (i < data.size) {
                    yield(data[i])
                    i++
                    true
                } else {
                    false
                }
            }
        }

        /**
         * Returns a sequential ordered Advancer whose elements
         * are the specified values in data parameter.
         */
        fun <T> fromList(data: List<T>): Advancer<T> {
            var i = 0
            return Advancer { yield ->
                if (i < data.size) {
                    yield(data[i])
                    i++
                    true
                } else {
                    false
                }
            }
        }
    }
}
