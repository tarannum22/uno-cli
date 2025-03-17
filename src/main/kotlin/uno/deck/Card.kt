package uno.deck

enum class CardColor(val symbol: String) {
    RED("R"),
    BLUE("B"),
    YELLOW("Y"),
    GREEN("G"),
    WILD("W")
}

/*
    For the WILDCARD suite we have a special notation of using
    - ZERO value for normal WILD
    - DRAW value for +4
 */

enum class CardValue(val symbol: String) {
    ZERO("0"),      // is normal wildcard for WILD suite
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    SKIP("S"),
    REVERSE("R"),
    DRAW("+")       // is +4 for WILD suite
}

// card class should encapsulate valid card creation only
data class Card(val color: CardColor, val value: CardValue) {
    fun getSymbol(): String {
        return this.color.symbol + this.value.symbol
    }

    fun isPowerCard(): Boolean {
        return color == CardColor.WILD ||
        value == CardValue.SKIP ||
        value == CardValue.REVERSE ||
        value == CardValue.DRAW
    }

    companion object {
        fun create(color: CardColor, value: CardValue): Card {
            // check for correct wild card creation
            if (color == CardColor.WILD && !(value == CardValue.ZERO || value == CardValue.DRAW)) {
                throw Exception("Invalid card creation. Wild card ")
            }
            return Card(color, value)
        }
    }
}

typealias Collection = MutableList<Card>
