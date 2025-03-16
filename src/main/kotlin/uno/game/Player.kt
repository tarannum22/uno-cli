package uno.game

import uno.deck.Collection
import utlis.generateUniqueId

class Player(private val name: String) {
    private val id = generateUniqueId()
    private var hand : Collection  = mutableListOf()

    fun showInfo() {
        println("Player ID : ${this.id} Name : ${this.name}")
    }

    fun showHand() {
        println("-----HAND of ${this.name}-----")
        this.hand.forEach {
            print(it.getSymbol().padEnd(3))
        }
        println()
    }

    fun give(cards: Collection) {
        hand+= cards
    }

}
