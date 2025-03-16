import `interface`.welcomeMessage
import uno.deck.*
import uno.game.Game
import uno.game.Player

fun main() {

    welcomeMessage()

    val alice = Player("Alice")
    val bob = Player("Bob")
    val charlie = Player("Charlie")

    println("Adding players to the game ...")

    val players = mutableSetOf(alice, bob, charlie)

    val ourGame = Game(players, DeckType.MINI)

    ourGame.showInfo()
}

