package uno.game

import uno.deck.*

typealias Players = Set<Player>

class Game(players: Players, type: DeckType = DeckType.STANDARD) {

    private var activePlayers = mutableListOf<Player>()
    private var deck = Deck(type)
    private var direction = true
    private var currentPlayer = 0
    private var playinCard: Card? = null
    private lateinit var currentColor: CardColor
    private var winnerList = mutableListOf<Player>()
    private var turns = mutableListOf<TurnSummary>()

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
        playGame()
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

    private fun reverse() {
        direction = !direction
    }

    private fun playGame() {
        while (true) {

            val lastTurn = Turn(
                activePlayers[currentPlayer],
                turns.count(),
                deck,
                playinCard!!,
                currentColor
            ).playTurn()

            playinCard = lastTurn.playedCard
            turns.add(lastTurn)

            // check for reverse
            if (lastTurn.playedCard.value == CardValue.REVERSE) {
                reverse()
            }

            selectNextPlayer(lastTurn.playedCard)

            println(lastTurn)

            // wild card

            // draw


        }
    }

    private fun selectNextPlayer(playedCard: Card) {
        when (direction) {
            // clockwise
            true -> {
                // check for skip
                println(activePlayers.size)
                println(currentPlayer)
                currentPlayer = if (playedCard.value == CardValue.SKIP) {
                    (currentPlayer + 2) % activePlayers.size
                } else {
                    (currentPlayer + 1) % activePlayers.size
                }
                println(currentPlayer)
            }

            // anti-clockwise
            false -> {
                // check for skip
                currentPlayer = if (playedCard.value == CardValue.SKIP) {
                    (((currentPlayer - 2) % activePlayers.size) + activePlayers.size) % activePlayers.size
                } else {
                    // positive modulo
                    (((currentPlayer - 1) % activePlayers.size) + activePlayers.size) % activePlayers.size
                }
            }
        }
    }

}


