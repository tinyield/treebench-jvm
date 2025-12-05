package pt.isel

import com.tinyield.Advancer
import java.util.stream.Stream
import kotlin.streams.asSequence
import kotlin.streams.asStream

fun main() {
    checkCullenSequence(10)
    checkCullenAdvancer(10)
    ZipIterable
        .zip(
            SequenceCullen
                .cullen()
                .asStream()
                .limit(10),
            Stream.generate { ", " },
        ) { nr, sep -> nr.toString() + sep }
        .forEach(::print)
    println()
    Extensions
        .zip(
            SequenceCullen
                .cullen()
                .asStream()
                .limit(10),
            Stream.generate { ", " },
        ) { nr, sep -> nr.toString() + sep }
        .forEach(::print)
    println()
    convolve(
        SequenceCullen.cullen().take(10),
        Stream.generate { ", " }.asSequence(),
    ) { nr, sep -> nr.toString() + sep }
        .forEach(::print)
}

fun checkCullenSequence(count: Int) {
    val cullen: Sequence<Int> = SequenceCullen.cullen()
    var limit = count

    for (nr in cullen) {
        if (--limit < 0) break
        println(nr)
    }
}

fun checkCullenAdvancer(count: Int) {
    val cullen: Advancer<Int> = AdvancerCullen.cullen()
    var limit = 10
    while (cullen(::println)) {
        if (--limit < 1) break
    }
}

fun <T, U, R> convolve(
    self: Sequence<T>,
    other: Sequence<U>,
    zipper: (T, U) -> R,
) = sequence {
    val rightIter = other.iterator()
    for (left in self) {
        if (rightIter.hasNext()) {
            yield(zipper(left, rightIter.next()))
        }
    }
}
