package uno.game

import uno.deck.Card
import uno.deck.CardColor
import uno.deck.CardValue
import uno.deck.Collection
import utlis.generateUniqueId

class Player(private val name: String) {
    private val id = generateUniqueId()
    var hand: Collection = mutableListOf()
    val isUNO: Boolean = false

    fun showInfo() {
        println("Player ID : ${this.id} Name : $name")
    }

    fun showHand() {
        println("-----HAND of ${name}-----")
        this.hand.forEachIndexed { i, _ ->
            print(i.toString().padEnd(3))
        }
        println()
        this.hand.forEach {
            print(it.getSymbol().padEnd(3))
        }
        println()
    }

    fun getName(): String {
        return name
    }

    fun draw(cards: Collection) {
        hand += cards
    }

    private fun hasColor(color: CardColor): Boolean {
        val check = hand.any { it.color == color }
        return check
    }

    private fun hasValue(value: CardValue): Boolean {
        val check = hand.any { it.value == value }
        return check
    }

    private fun hasWild(): Boolean {
        val check = hand.any { it.color == CardColor.WILD }
        return check
    }

    fun hasPlayingCard(playingCard: Card, currentColor: CardColor): Boolean {
        val check = if (playingCard.value == CardValue.DRAW) {
            if (playingCard.color == CardColor.WILD) {
                checkHandForWildDraw()
            } else {
                checkHandForDraw()
            }
        } else {
            (hasColor(currentColor) || hasValue(playingCard.value) || hasWild())
        }
        return check
    }

    private fun checkHandForDraw(): Boolean {
        return hand.any { it.value == CardValue.DRAW }
    }

    private fun checkHandForWildDraw(): Boolean {
        return hand.any { it.value == CardValue.DRAW && it.color == CardColor.WILD }
    }

    fun getHandCount(): Int {
        return hand.count()
    }

    fun playCard(index: Int): Card {
        return hand.removeAt(index)
    }

}
