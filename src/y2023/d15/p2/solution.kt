package y2023.d15.p2

import println
import readInput
import runMeasure
import y2023.d15.p1.hash

fun main() {
    runMeasure { solve() }
}

data class Lens(val label: String, val focalLength: Long)

fun solve() {
    val boxes = mutableMapOf<Int, MutableList<Lens>>()

    val input = readInput().first()

    input.split(",")
        .forEach { it ->
            val label = it.substringBefore(if (it.contains('-')) '-' else '=')

            val op = if (it.contains('-')) '-' else '='

            val focalLength = if (op == '=') it.split('=').last().toLong() else null

            val hash = hash(label)

            when (op) {
                '=' -> {
                    val l = Lens(label, focalLength!!)

                    if (boxes.containsKey(hash)) {
                        var lensesInBox = boxes[hash]

                        lensesInBox = lensesInBox!!.map { le ->
                            if (le.label == l.label) l else le
                        }.toMutableList()

                        val i = lensesInBox.indexOf(l)

                        if (i == -1) {
                            lensesInBox.add(l)
                        }
                        boxes[hash] = lensesInBox
                    } else {
                        boxes[hash] = mutableListOf(l)
                    }
                }

                '-' -> {
                    if (boxes.containsKey(hash)) {
                        val lensesInBox = boxes[hash]

                        lensesInBox!!.removeIf {
                            it.label == label
                        }
                    }
                }
            }
        }

    var res = 0L
    boxes.forEach {
        it.value.forEachIndexed { index, lens ->
            res += (it.key + 1) * (index + 1) * lens.focalLength
        }
    }

    res.println()
}