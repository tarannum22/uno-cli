package utlis

import kotlin.random.Random

fun generateUniqueId(): String {
    val nanoTime = System.nanoTime()
    val randomValue = Random.nextInt(100000, 999999)
    val id = (nanoTime.toString() + randomValue).takeLast(10)
    return id
}

fun colorString(string: String, code: String): String {
    val reset = "\u001b[0m"
    return code + string + reset
}
