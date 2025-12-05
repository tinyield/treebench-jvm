package pt.isel

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class DigitsTest {
    @Test
    fun testAdvancerDigits() {
        val adv = AdvancerDigits.digits(762354)
        listOf(4, 5, 3, 2, 6, 7).forEach { expected ->
            adv.invoke({ actual ->
                assertEquals(expected, actual)
            })
        }
        assertFalse(adv.invoke({ ignore -> }))
    }

    @Test
    fun testSequenceDigits() {
        val iter = SequenceDigits.digits(762354).iterator()
        listOf(4, 5, 3, 2, 6, 7).forEach { expected ->
            assertEquals(expected, iter.next())
        }
        assertFalse(iter.hasNext())
    }
}
