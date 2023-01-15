import core.*

fun main() {
    val b = Board()
    b.init()
    println(b)
    println(b.debugPossibleMoves(Coord(4, 4)))

}