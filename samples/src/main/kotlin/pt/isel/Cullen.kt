package pt.isel

import com.tinyield.Advancer

object SequenceCullen {
    fun cullen() =
        sequence {
            var i = 0
            while (true) {
                val nr = (1 shl i) * i + 1
                yield(nr)
                i++
            }
        }
}

object AdvancerCullen {
    fun cullen(): Advancer<Int> {
        var i = 0
        return Advancer { yield ->
            yield((1 shl i) * i + 1)
            i++
            true
        }
    }
}
