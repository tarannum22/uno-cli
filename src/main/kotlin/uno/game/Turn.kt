package uno.game

import uno.deck.Card
import uno.deck.CardColor
import uno.deck.Deck

enum class Move {
    PLAY_HAND,
    DRAW,
    PASS
}


enum class TurnState {
    TURN_START,
    CARD_DRAWN,
    TURN_END
}

data class TurnSummary(
    val player: Player,
    val turnNumber: Int,
    val playingCard: Card,
    val playedCard: Card,
    val moves: MutableList<String>
)

class Turn(
    private val player: Player,
    private val turnNumber: Int,
    private val deck: Deck,
    private val playingCard: Card,
    private val currentColor: CardColor?
) {

    private var turnFSM = TurnStateMachine()
    private lateinit var playedCard: Card

    fun playTurn(): TurnSummary {

        println("Playing Card : ${playingCard.getSymbol()} ${playingCard.color} ${playingCard.value} ")
        println()
        println("--- TURN $turnNumber for ---")
        println("PLAYER ***[${player.getName()}]***")

        player.showHand()

        while (turnFSM.state != TurnState.TURN_END) {
            makeMove()
        }

        println("Turn has ended.")

        return TurnSummary(
            player,
            turnNumber,
            playingCard,
            playedCard,
            mutableListOf("move1, move2")
        )
    }

    private fun makeMove() {
        when (turnFSM.state) {
            TurnState.TURN_START -> {
                if (player.hasPlayingCard(playingCard)) {
                    playHand()
                    turnFSM.updateTurnState(Move.PLAY_HAND)
                } else {
                    drawCard()
                    turnFSM.updateTurnState(Move.DRAW)
                }
            }

            TurnState.CARD_DRAWN -> {
                if (player.hasPlayingCard(playingCard)) {
                    playHand()
                    turnFSM.updateTurnState(Move.PLAY_HAND)
                } else {
                    pass()
                    turnFSM.updateTurnState(Move.PASS)
                }
            }

            TurnState.TURN_END -> {
                illegalMove()
            }
        }
    }


    private fun playHand() {

        val maxChoice = player.getHandCount() - 1
        println("You have the option to play your hand.")
        println("Please pick a card. Enter the position of the card you want to play.")

        val playerChoice: Int
        while (true) {
            println("Pick a number between 0 and $maxChoice. Counting from left.")
            val input = readln()
            if (input.toInt() in 0..maxChoice) {
                playerChoice = input.toInt()
                break
            } else {
                println("Your choice $input is not valid")
            }
        }

        val pickedCard = player.playCard(playerChoice)
        player.showHand()
        println("You have played $pickedCard")
        playedCard = pickedCard
    }

    private fun drawCard() {
        println("You don't have a valid card to play. You will draw a card.")
        player.draw(deck.dealFromDeck(1))
        print("Your new hand ----")
        player.showHand()
    }

    private fun pass() {
        println("You don't have a valid card to play. Passing to the next player. Turn has ended.")
        playedCard = playingCard
    }

    private fun illegalMove() {
        println("This is an illegal move. Turn $turnNumber has ended.")
    }

}


class TurnStateMachine() {

    var state: TurnState = TurnState.TURN_START

    fun updateTurnState(move: Move) {
        when (state) {
            TurnState.TURN_START -> {
                when (move) {
                    Move.PLAY_HAND -> {
                        state = TurnState.TURN_END
                    }

                    Move.DRAW -> {
                        state = TurnState.CARD_DRAWN
                    }

                    Move.PASS -> illegalStateTransition(state, move)
                }
            }

            TurnState.CARD_DRAWN -> {
                when (move) {
                    Move.PLAY_HAND -> {
                        state = TurnState.TURN_END
                    }

                    Move.DRAW -> illegalStateTransition(state, move)
                    Move.PASS -> {
                        state = TurnState.TURN_END
                    }
                }
            }

            TurnState.TURN_END -> {
                illegalStateTransition(state, move)
            }
        }
    }

    private fun illegalStateTransition(state: TurnState, move: Move) {
        println("Illegal move $move in state $state")
    }
}
