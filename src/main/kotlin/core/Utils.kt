package core

enum class Type {
    PAWN,
    KNIGHT,
    BISHOP,
    ROOK,
    QUEEN,
    KING
}

enum class Color {
    WHITE,
    BLACK
}

class Coord(var x: Int, var y: Int) {
    override fun toString(): String {
        return "abcdefgh"[x] + (y+1).toString()
    }
    fun isValid(): Boolean{
        return x in 0..7 && y in 0..7
    }
}

fun String.toCoord(): Coord {
    if(this.length != 2) return Coord(-1, -1)
    val x = "abcdefgh".indexOf(this[0])
    val y = "123456789".indexOf(this[1])
    return Coord(x, y)
}