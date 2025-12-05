package pt.isel

import com.tinyield.Advancer

object AdvancerDigits {
    fun digits(nr: Int): Advancer<Int> {
        var count = nr
        return Advancer { yield ->
            count > 0 &&
                run {
                    yield(count % 10)
                    count /= 10
                    true
                }
        }
    }
}

object SequenceDigits {
    fun digits(nr: Int) =
        sequence {
            var count = nr
            while (count > 0) {
                yield(count % 10)
                count = count / 10
            }
        }

//    fun digits(numbers: Array<Int>) =
//        sequence {
//            for (n in numbers) {
//                for (d in digits(n)) {
//                    yield(d)
//                }
//            }
//        }

    fun digits(numbers: Array<Int>) = numbers.flatMap { nr -> digits(nr) }
}
