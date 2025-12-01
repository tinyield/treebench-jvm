package pt.isel;

import com.tinyield.Advancer;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Numbers {

    static Advancer<Integer> NumberDigits(int nr) {
        AtomicInteger count = new AtomicInteger(nr);
        return yield -> {
            if (count.get() > 0) {
                yield.ret(count.get() % 10);
                count.set(count.get() / 10);
                return true;
            }
            return false;
        };
    }

    static public void NumbersDigitTest() {
        Advancer<Integer> adv = Numbers.NumberDigits(762354);
        Arrays
                .asList(4, 5, 3, 2, 6, 7)
                .forEach(expected -> adv.tryAdvance(actual -> {
                    assert expected.equals(actual);
                }));
        assert !adv.tryAdvance(ignore -> { });
    }
}
