package uno.deck

import uno.deck.Collection

enum class DeckType {
    MINI,
    STANDARD
}

class Deck(type: DeckType) {
    private var deckType: DeckType = type
    private var deck: Collection = mutableListOf()

    init {
        when (type) {
            DeckType.MINI -> createMiniDeck()
            DeckType.STANDARD -> createStandardDeck()
        }
    }

    private fun createStandardDeck() {
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
    }

    private fun createMiniDeck() {
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
    }

    fun showDeck() {
        println("-----DECK (has ${deck.count()} cards)-----")
        this.deck.forEach {
            print(it.getSymbol().padEnd(3))
        }
        println()
    }

    fun shuffle() {
        println("Shuffling Deck ...")
        this.deck.shuffle()
    }

    fun dealFromDeck(n: Int): Collection {
        val dealingCards = mutableListOf<Card>()
        for (i in 1..n) {
            dealingCards.add(this.deck.removeFirst())
        }
        return dealingCards
    }

    fun peekFirst(): Card {
        return deck[0]
    }

    fun returnToDeck(cardStash : Collection){
        deck += cardStash
    }
}
