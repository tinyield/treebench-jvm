package pt.isel

import com.tinyield.Advancer
import com.tinyield.allMatch
import com.tinyield.distinct
import com.tinyield.filter
import com.tinyield.flatMap
import com.tinyield.limit
import com.tinyield.map
import com.tinyield.reduce
import com.tinyield.zip
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AdvancerTest {
    @Test
    fun testZip() {
        val expected = listOf("a1", "b2", "c3", "d4", "e5", "f6", "g7")
        val nrs: Advancer<Int> = Advancer.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val pipe =
            Advancer
                .of('a', 'b', 'c', 'd', 'e', 'f', 'g')
                .zip(nrs, { c, n -> "" + c + n })
        val actual: MutableList<String> = mutableListOf()
        while (pipe({ item ->
                assertTrue(actual.size < expected.size)
                actual.add(item)
            })
        ) {
        }
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun testDistinctCount() {
        val arrange =
            listOf("a", "x", "v", "d", "g", "x", "j", "x", "y", "r", "y", "w", "y", "a", "e")
        val pipe =
            Advancer
                .fromList(arrange)
                .distinct()
        var count = 0
        while (pipe { ignore -> count++ }) {
        }
        assertEquals(count, 10)
    }

    @Test
    fun testAllMatchForAllElements() {
        val arrange: List<Int> = listOf(2, 4, 6, 8, 10, 12)
        val actual: Boolean = Advancer.fromList(arrange).allMatch { nr -> nr % 2 == 0 }
        assertEquals(actual, true)
    }

    @Test
    fun testAllMatchFailOnIntruder() {
        val arrange: List<Int> = listOf(2, 4, 6, 7, 10, 12)
        val actual: Boolean = Advancer.fromList(arrange).allMatch { nr -> nr % 2 == 0 }
        assertEquals(actual, false)
    }

    @Test
    fun testReduce() {
        val input = listOf("a", "b", "c")
        val expected = "abc"
        val actual = Advancer.fromList(input).reduce("") { p, c -> p + c }
        assertEquals(actual, expected)
    }

    @Test
    fun testFilter() {
        val arrange = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val expected = listOf(2, 4, 6, 8, 10)
        val pipe =
            Advancer
                .fromList(arrange)
                .filter { nr -> nr % 2 == 0 }
        val actual: MutableList<Int> = mutableListOf()
        while (pipe { item -> actual.add(item) }) {
        }
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun testFilterEmptyResult() {
        val arrange = listOf(1, 3, 5, 7, 9)
        val pipe =
            Advancer
                .fromList(arrange)
                .filter { nr -> nr % 2 == 0 }
        var count = 0
        while (pipe { ignore -> count++ }) {
        }
        assertEquals(0, count)
    }

    @Test
    fun testMap() {
        val arrange = listOf(1, 2, 3, 4, 5)
        val expected = listOf(2, 4, 6, 8, 10)
        val pipe =
            Advancer
                .fromList(arrange)
                .map { nr -> nr * 2 }
        val actual: MutableList<Int> = mutableListOf()
        while (pipe { item -> actual.add(item) }) {
        }
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun testMapToString() {
        val arrange = listOf(1, 2, 3)
        val expected = listOf("1", "2", "3")
        val pipe =
            Advancer
                .fromList(arrange)
                .map { nr -> nr.toString() }
        val actual: MutableList<String> = mutableListOf()
        while (pipe { item -> actual.add(item) }) {
        }
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun testFlatMap() {
        val expected = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9).iterator()
        val arrange = listOf(2, 5, 8)
        val pipe: Advancer<Int> =
            Advancer
                .fromList(arrange)
                .flatMap { nr -> Advancer.of(nr - 1, nr, nr + 1) }
        while (pipe { item ->
                assertEquals(expected.next(), item)
            }
        ) {
            // no op
        }
        assertFalse { expected.hasNext() }
    }

    @Test
    fun testLimit() {
        val arrange = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val expected = listOf(1, 2, 3, 4, 5)
        val pipe =
            Advancer
                .fromList(arrange)
                .limit(5)
        val actual: MutableList<Int> = mutableListOf()
        while (pipe { item -> actual.add(item) }) {
        }
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun testLimitZero() {
        val arrange = listOf(1, 2, 3, 4, 5)
        val pipe =
            Advancer
                .fromList(arrange)
                .limit(0)
        var count = 0
        while (pipe { ignore -> count++ }) {
        }
        assertEquals(0, count)
    }

    @Test
    fun testLimitMoreThanAvailable() {
        val arrange = listOf(1, 2, 3)
        val expected = listOf(1, 2, 3)
        val pipe =
            Advancer
                .fromList(arrange)
                .limit(10)
        val actual: MutableList<Int> = mutableListOf()
        while (pipe { item -> actual.add(item) }) {
        }
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun testFilterMapLimit() {
        val arrange = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val expected = listOf(4, 8, 12)
        val pipe =
            Advancer
                .fromList(arrange)
                .filter { nr -> nr % 2 == 0 }
                .map { nr -> nr * 2 }
                .limit(3)
        val actual: MutableList<Int> = mutableListOf()
        while (pipe { item -> actual.add(item) }) {
        }
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }
}
