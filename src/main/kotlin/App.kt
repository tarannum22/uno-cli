import `interface`.StringEffect
import `interface`.welcomeMessage
import uno.deck.*
import uno.game.Game
import uno.game.Player
import utlis.prettyPrintln

fun main() {

    welcomeMessage()

    val alice = Player("Alice")
    val bob = Player("Bob")
    val charlie = Player("Charlie")

    println("Adding players to the game ...")

    val players = mutableSetOf(alice, bob, charlie)

    val game = Game(players, DeckType.MINI)

    game.showInfo()
}

