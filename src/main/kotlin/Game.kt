import uno.deck.Card
import uno.deck.CardColor
import uno.deck.CardValue

fun main() {
    println("Let's play UNO! \n\n ------------------------")


    // create a valid card
    val myCard = Card.create(CardColor.RED, CardValue.SIX)

    val invalidCard = Card.create(CardColor.WILD, CardValue.ZERO)

    println(myCard.getSymbol())
    println(invalidCard.getSymbol())
}
