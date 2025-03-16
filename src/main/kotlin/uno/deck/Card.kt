package uno.deck

enum class CardColor(val symbol: String) {
    RED("R"),
    BLUE("B"),
    YELLOW("Y"),
    GREEN("G"),
    WILD("W")
}

enum class CardValue(val symbol: String) {
    ZERO("0"),
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
    DRAW("+")
}

// card class should encapsulate valid card creation only
data class Card(val color: CardColor, val value: CardValue) {

    fun getSymbol() :  String{
        return this.color.symbol + this.value.symbol
    }
}
