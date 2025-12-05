package pt.isel

import com.tinyield.limit
import com.tinyield.toList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class CullenTest {
    @Test
    fun testAdvancerCullen() {
        val iter =
            AdvancerCullen
                .cullen()
                .limit(10)
                .toList()
                .iterator()
        listOf(1, 3, 9, 25, 65, 161, 385, 897, 2049, 4609).forEach { expected ->
            assertEquals(expected, iter.next())
        }
        assertFalse(iter.hasNext())
    }

    @Test
    fun testSequenceCullen() {
        val iter = SequenceCullen.cullen().take(10).iterator()
        listOf(1, 3, 9, 25, 65, 161, 385, 897, 2049, 4609).forEach { expected ->
            assertEquals(expected, iter.next())
        }
        assertFalse(iter.hasNext())
    }
}
