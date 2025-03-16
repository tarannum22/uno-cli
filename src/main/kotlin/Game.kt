import uno.deck.Card
import uno.deck.CardColor
import uno.deck.CardValue

fun main() {
    println("Let's play UNO! \n\n ------------------------")


    // create a valid card
    val myCard = Card(CardColor.RED, CardValue.SIX)

    val invalidCard = Card(CardColor.WILD, CardValue.SIX)

    println(myCard.getSymbol())
    println(invalidCard.getSymbol())
}
