package uno.game

import uno.deck.*

typealias Players = Set<Player>

class Game(players: Players, type: DeckType = DeckType.STANDARD) {

    private var activePlayers = mutableListOf<Player>()
    private var deck = Deck(type)
    private var direction = false
    private var currentPlayer = 0
    private var playinCard: Card? = null
    private lateinit var currentColor: CardColor
    private var winnerList = mutableListOf<Player>()
    val turns = mutableListOf<Turn>()

    init {

        println("Creating a new game with a $type Deck")
        players.forEach {
            activePlayers.add(it)
        }

        deck.shuffle()
        deck.showDeck()

        activePlayers.forEach {
            it.draw(deck.dealFromDeck(7))
        }

        pickPlayingCard()
        gameStart()
    }

    fun showInfo() {
        println()
        println("--- UNO game with ${activePlayers.count()} players ---")
        activePlayers.forEach {
            it.showInfo()
            it.showHand()
        }
        deck.showDeck()
        println("Playing in the direction $direction")
        println("Current Player -- ")
        activePlayers[currentPlayer].showInfo()
        println("Playing Card : ${playinCard!!.getSymbol()}")

    }

    private fun pickPlayingCard() {
        println()
        println("Picking a playing card")
        while (true) {
            val topCard = deck.peekFirst()

            if (topCard.color == CardColor.WILD ||
                topCard.value == CardValue.SKIP ||
                topCard.value == CardValue.REVERSE ||
                topCard.value == CardValue.DRAW
            ) {
                println("Invalid First Card ${topCard.getSymbol()}")
                deck.shuffle()
            } else {
                playinCard = deck.dealFromDeck(1).first()
                currentColor = playinCard!!.color
                break
            }
        }
        println("Done Picking a playing card")
        println()
    }

    fun reverse() {
        direction = !direction
    }

    private fun gameStart() {
        Turn(activePlayers[currentPlayer], turns.count(), deck, playinCard!!, currentColor).playTurn()
    }

}


