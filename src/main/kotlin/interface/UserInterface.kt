package `interface`

import utlis.colorString

fun welcomeMessage() {

    val boldBLue = "\u001b[1;34m"

//    Bold Red : \u001b[1;31 m
//    Bold Green : \u001b[1;32 m
//    Bold Yellow : \u001b[1;33 m
//    Bold Blue : \u001b[1;34 m
//    Bold Magenta : \u001b[1;35 m
//    Bold Cyan : \u001b[1;36 m
//    Bold White : \u001b[1;37 m
//
//    Red: \u001b[31m
//    Green: \u001b[32m
//    Yellow: \u001b[33m
//    Blue: \u001b[34m
//    Magenta: \u001b[35m
//    Cyan: \u001b[36m
//    White: \u001b[37m


    println(colorString("Let's play UNO!", boldBLue))
    println("------------------------")
    println()

}


