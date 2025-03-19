package `interface`

import utlis.prettyPrintln
import utlis.prettyString

enum class StringEffect(val code: String) {
    RED("\\u001b[31m"),
    BLUE("\u001b[34m"),
    YELLOW("\\u001B[33m"),
    GREEN("\\u001B[32m"),
    UNDERLINE("\\u001B[4m"),
    CYAN("\\u001b[36m"),
    MAGENTA("\\u001b[1;35 m")
}

fun welcomeMessage() {

    val boldBLue = "\u001b[1;34m"

//    Bold Red : \u001b[1;31 m
//    Bold Green : \u001b[1;32 m
//    Bold Yellow : \u001b[1;33 m
//    Bold Blue : \u001b[1;34 m
//    Bold Magenta : \u001b[1;35 m
//    Bold Cyan : \u001b[1;36 m
//    Bold White : \u001b[1;37 m

//    println(prettyPrintln("Let's play UNO!", StringEffect.BLUE))

    println("Let's play UNO!")
    println("------------------------")
    println()

}

