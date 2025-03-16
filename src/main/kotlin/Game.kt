import uno.deck.*

fun main() {

    welcomeMessage()

    val miniDeck = Deck(DeckType.MINI)
    miniDeck.showDeck()
    miniDeck.shuffle()
    miniDeck.showDeck()

    println()

    val standardDeck = Deck()
    standardDeck.showDeck()
    standardDeck.shuffle()
    standardDeck.showDeck()

}

