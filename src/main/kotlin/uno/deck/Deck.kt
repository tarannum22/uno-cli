package uno.deck

enum class DeckType {
    MINI,
    STANDARD
}

class Deck(type: DeckType = DeckType.STANDARD) {
    private var deckType: DeckType = type
    private var deck: Collection = mutableListOf()

    init {
        when (type) {
            DeckType.MINI -> createMiniDeck()
            DeckType.STANDARD -> createStandardDeck()
        }
    }

    private fun createStandardDeck() {
        println("Creating a Standard Deck")
        this.deckType = DeckType.STANDARD

        val cards = mutableListOf<Card>()
        CardColor.entries.forEach {
            if (it == CardColor.WILD) {
                for (i in 1..4) {
                    cards.add(Card.create(CardColor.WILD, CardValue.ZERO))
                    cards.add(Card.create(CardColor.WILD, CardValue.DRAW))
                }
            } else {
                for (i in 1..2) {
                    cards.add(Card.create(it, CardValue.ONE))
                    cards.add(Card.create(it, CardValue.TWO))
                    cards.add(Card.create(it, CardValue.THREE))
                    cards.add(Card.create(it, CardValue.FOUR))
                    cards.add(Card.create(it, CardValue.FIVE))
                    cards.add(Card.create(it, CardValue.SIX))
                    cards.add(Card.create(it, CardValue.SEVEN))
                    cards.add(Card.create(it, CardValue.EIGHT))
                    cards.add(Card.create(it, CardValue.NINE))
                    cards.add(Card.create(it, CardValue.SKIP))
                    cards.add(Card.create(it, CardValue.REVERSE))
                    cards.add(Card.create(it, CardValue.DRAW))
                }
                cards.add(Card.create(it, CardValue.ZERO))
            }
        }

        this.deck = cards
        println("Created a Standard deck with ${this.deck.count()} cards")
    }

    private fun createMiniDeck() {
        println("Creating a Mini Deck")
        this.deckType = DeckType.MINI

        val cards = mutableListOf<Card>()
        CardColor.entries.forEach {
            if (it == CardColor.WILD) {
                for (i in 1..2) {
                    cards.add(Card.create(CardColor.WILD, CardValue.ZERO))
                    cards.add(Card.create(CardColor.WILD, CardValue.DRAW))
                }
            } else {
                cards.add(Card.create(it, CardValue.ZERO))
                cards.add(Card.create(it, CardValue.ONE))
                cards.add(Card.create(it, CardValue.TWO))
                cards.add(Card.create(it, CardValue.THREE))
                cards.add(Card.create(it, CardValue.FOUR))
                cards.add(Card.create(it, CardValue.FIVE))
                cards.add(Card.create(it, CardValue.SIX))
                cards.add(Card.create(it, CardValue.SEVEN))
                cards.add(Card.create(it, CardValue.EIGHT))
                cards.add(Card.create(it, CardValue.NINE))
                cards.add(Card.create(it, CardValue.SKIP))
                cards.add(Card.create(it, CardValue.REVERSE))
                cards.add(Card.create(it, CardValue.DRAW))
            }
        }

        this.deck = cards
        println("Created a Mini deck with ${this.deck.count()} cards")
    }

    fun shuffle() {
        println("Shuffling Deck ...")
        this.deck.shuffle()
    }

    fun showDeck() {
        println("-----DECK-----")
        this.deck.forEach {
            print(it.getSymbol().padEnd(3))
        }
        println()
    }
}
