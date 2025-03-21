package uno.game

import uno.deck.Card
import uno.deck.CardColor
import uno.deck.CardValue
import uno.deck.Deck

enum class Move {
    PLAY_HAND,
    DRAW,
    DRAW_STASH,
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
    val colorChoice: CardColor?,
    val cardStash: MutableList<Card>,
    val playerCardCount: Int
)

class Turn(
    private val player: Player,
    private val turnNumber: Int,
    private val deck: Deck,
    private val playingCard: Card,
    private var currentColor: CardColor,
    private val cardStash: MutableList<Card>
) {

    private var turnFSM = TurnStateMachine()
    private lateinit var playedCard: Card
    private var colorChoice: CardColor? = null
    private val colors = mapOf(
        0 to CardColor.RED,
        1 to CardColor.BLUE,
        2 to CardColor.GREEN,
        3 to CardColor.YELLOW
    )

    fun playTurn(): TurnSummary {

        println("------ TURN $turnNumber for PLAYER ***[${player.getName()}]*** ------------------------------------")
        println("PLAYING Card : ${playingCard.getSymbol()} ${playingCard.color} ${playingCard.value} ")
        println("PLAYING Color : $currentColor ")
        println()

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
            colorChoice,
            cardStash,
            player.hand.size
        )
    }

    private fun makeMove() {
        when (turnFSM.state) {
            TurnState.TURN_START -> {
                if (player.hasPlayingCard(playingCard, currentColor)) {
                    playHand()
                    turnFSM.updateTurnState(Move.PLAY_HAND)
                } else {
                    if (playingCard.value == CardValue.DRAW) {
                        drawStash()
                        println("You have drawn the stash. Ending the turn.")// if drawing stash pass
                        turnFSM.updateTurnState(Move.DRAW_STASH)
                    } else {
                        drawOneCard()
                        turnFSM.updateTurnState(Move.DRAW)
                    }
                }
            }

            TurnState.CARD_DRAWN -> {
                if (player.hasPlayingCard(playingCard, currentColor)) {
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

        val pickedCard = pickCard(maxChoice)

        if (pickedCard.color == CardColor.WILD) {
            colorChoice = pickColor()
            println("Picked color. Current color is $colorChoice")
        }

        player.showHand()
        println("You have played $pickedCard")
        playedCard = pickedCard
        deck.returnToDeck(mutableListOf(playingCard))
    }


    private fun pickColor(): CardColor {
        try {
            while (true) {
                println("Pick a color to change into. Choose the number associated with the color.")
                showColors()

                // Read input and ensure it's a valid number
                val colorInput = readln().trim()
                if (colorInput.toIntOrNull() != null) {
                    val index = colorInput.toInt()

                    // Check if the index is within the valid range
                    if (index in 0 until colors.size) {
                        return colors.values.toList()[index] // Get the color from the map
                    } else {
                        println("Your choice for color change - $colorInput is not valid. Please choose a number between 0 and ${colors.size - 1}.")
                    }
                } else {
                    println("Invalid input! Please enter a valid number.")
                }
            }
        } catch (e: Exception) {
            println("An error occurred while picking the color: $e")
            throw e // Optionally rethrow the exception if you need to handle it further
        }
    }


    private fun pickCard(maxChoice: Int): Card {
        try {
            while (true) {
                println("Pick a number between 0 and $maxChoice. Counting from left.")
                val input = readln()
                if (input.toInt() in 0..maxChoice) {
                    val pickedCard = player.playCard(input.toInt())
                    return pickedCard
                } else {
                    println("Your choice $input is not valid")
                }
            }
        } catch (e: Exception) {
            println("Play move had an error : $e")
            throw e
        }
    }


    private fun drawOneCard() {
        println("You don't have a valid card to play. You will draw a card.")
        player.draw(deck.dealFromDeck(1))
        println("Your new hand ----")
        player.showHand()
    }

    private fun drawStash() {
        if (playingCard.value == CardValue.DRAW) {
            println("You will have to draw ${cardStash.size}.")
            //print the stash
            player.draw(cardStash)
            cardStash.clear()
        }
        println("Your new hand ----")
        player.showHand()
        playedCard = playingCard
    }

    private fun pass() {
        println("You don't have a valid card to play. Passing to the next player.")
        playedCard = playingCard
    }

    private fun showColors() {
        colors.forEach { (i, color) ->
            println("Pick $i for $color")
        }
    }

    private fun illegalMove() {
        println("This is an illegal move. Turn $turnNumber has ended.")
    }

}


class TurnStateMachine {

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

                    Move.DRAW_STASH -> {
                        state = TurnState.TURN_END
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
                    Move.DRAW_STASH -> illegalStateTransition(state, move)

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
