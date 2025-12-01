package pt.isel

import pt.isel.Numbers.NumbersDigitTest
import java.util.function.BiFunction
import java.util.stream.Stream
import kotlin.streams.asSequence
import kotlin.streams.asStream

fun main() {
    // checkCullen(10)
    NumbersDigitTest()
    ZipIterable
        .zip(
            cullen()
                .asStream()
                .limit(10),
            Stream.generate { ", " },
        ) { nr, sep -> nr.toString() + sep }
        .forEach(::print)
    println()
    Extensions
        .zip(
            cullen()
                .asStream()
                .limit(10),
            Stream.generate { ", " },
        ) { nr, sep -> nr.toString() + sep }
        .forEach(::print)
    println()
    convolve(
        cullen().take(10),
        Stream.generate { ", " }.asSequence(),
    ) { nr, sep -> nr.toString() + sep }
        .forEach(::print)
}

fun checkCullen(count: Int) {
    val cullen = cullen()
    var limit = count

    for (nr in cullen) {
        if (--limit < 0) break
        println(nr)
    }
}

fun cullen() =
    sequence {
        var i = 1
        while (true) {
            val nr = (1 shl i) * i + 1
            yield(nr)
            i++
        }
    }

fun <T, U, R> convolve(
    self: Sequence<T>,
    other: Sequence<U>,
    zipper: BiFunction<T, U, R>,
) = sequence {
    val rightIter = other.iterator()
    for (left in self) {
        if (rightIter.hasNext()) {
            yield(zipper.apply(left, rightIter.next()))
        }
    }
}
