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


class Turn(
    private val player: Player,
    private val turnNumber: Int,
    private val deck: Deck,
    private val playingCard: Card,
    private val currentColor: CardColor
) {

    private var turnFSM = TurnStateMachine()

    fun playTurn() {

        println("Playing Card : ${playingCard.getSymbol()} ${playingCard.color} ${playingCard.value} ")
        println()
        println("--- TURN $turnNumber for ---")
        println("PLAYER ***[${player.getName()}]***")
//        println("---- CHOOSE MOVE ----")
        player.showHand()
        // calculate possible move
        getMove()

    }

    private fun getMove() {
        when (turnFSM.state) {
            TurnState.TURN_START -> {
                if (player.hasPlayingCard(playingCard)) {
                    turnFSM.makeMove(Move.PLAY_HAND)
                    playHand()
                } else {
                    turnFSM.makeMove(Move.DRAW)
                    drawCard()
                }
            }

            TurnState.CARD_DRAWN -> TODO()
            TurnState.TURN_END -> TODO()
        }
    }

    private fun drawCard() {
        println("You don't have a valid card to play. You will draw a card.")
        //draw a card
        print("Your new hand ----")
    }

    private fun playHand() {

        val maxChoice = player.getHandCount()
        println("You have the option to play your hand.")
        println("Please pick a card. Enter the position of the card you want to play")

        val playerChoice : Int
        while (true) {
            println("Pick a number between 1 and $maxChoice. Counting from right")
            val input = readln()
            if (input.toInt() in 1..maxChoice) {
                playerChoice = input.toInt()
                break
            } else {
                println("Your choice $input is not valid")
            }
        }

        val pickedCard = player.playCard(playerChoice)
        println("You have played $pickedCard")

    }
}


class TurnStateMachine() {

    var state: TurnState = TurnState.TURN_START

    fun getAllowedMoves(): List<Move> {
        return when (state) {
            TurnState.TURN_START -> listOf(Move.PLAY_HAND, Move.DRAW)
            TurnState.CARD_DRAWN -> listOf(Move.PLAY_HAND, Move.PASS)
            TurnState.TURN_END -> listOf()
        }
    }

    fun makeMove(move: Move) {
        when (state) {
            TurnState.TURN_START -> {
                when (move) {
                    Move.PLAY_HAND -> {
                        state = TurnState.TURN_END
                    }

                    Move.DRAW -> {
                        state = TurnState.CARD_DRAWN
                    }

                    Move.PASS -> illegalMove(state, move)
                }
            }

            TurnState.CARD_DRAWN -> {
                when (move) {
                    Move.PLAY_HAND -> {
                        state = TurnState.TURN_END
                    }

                    Move.DRAW -> illegalMove(state, move)
                    Move.PASS -> {
                        state = TurnState.TURN_END
                    }
                }
            }

            TurnState.TURN_END -> {
                illegalMove(state, move)
            }
        }
    }

    private fun pass() {
        TODO("Not yet implemented")
    }

    private fun illegalMove(state: TurnState, move: Move) {
        TODO("Not yet implemented")
    }
}
