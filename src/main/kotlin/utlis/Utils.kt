package utlis

import `interface`.StringEffect
import kotlin.random.Random

fun generateUniqueId(): String {
    val nanoTime = System.nanoTime()
    val randomValue = Random.nextInt(100000, 999999)
    val id = (nanoTime.toString() + randomValue).takeLast(10)
    return id
}

fun prettyString(string: String, effect: StringEffect): String {
    val reset = "\u001b[0m"
    return effect.code + string + reset
}

fun prettyPrintln(string: String, effect: StringEffect) {
    println(prettyString(string, effect))
}
