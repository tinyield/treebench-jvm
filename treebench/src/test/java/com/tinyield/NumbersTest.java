package com.tinyield;

import com.tinyield.jayield.Advancer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class NumbersTest {

    @Test
    public void NumbersDigitTest() {
        Advancer<Integer> adv = Numbers.NumberDigits(762354);
        Arrays
            .asList(4, 5, 3, 2, 6, 7)
            .forEach(expected -> adv.tryAdvance(actual -> {
                assertEquals(expected, actual);
            }));
        assertFalse(adv.tryAdvance(ignore -> {}));
    }
}
