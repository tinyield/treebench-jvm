package com.tinyield;

import com.tinyield.jayield.Advancer;

import java.util.concurrent.atomic.AtomicInteger;

public class Numbers {

    static Advancer<Integer> NumberDigits (int nr) {
        AtomicInteger count = new AtomicInteger(nr);
        return yield -> {
            if(count.get() > 0) {
                yield.ret(count.get() % 10);
                count.set(count.get() / 10);
                return true;
            }
            return false;
        };
    }
}
