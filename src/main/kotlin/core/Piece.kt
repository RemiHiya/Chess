package core

abstract class Piece(val type: Type, val color: Color) {

    private var position: Coord = Coord(-1, -1)
    var moveCounter: Int = 0

    abstract fun possibleMoves(board: Board): List<Coord>

    open fun isValidMove(destination: Coord, board: Board): Boolean {
        return possibleMoves(board).contains(destination)
    }

    fun getPosition(): Coord {
        return position
    }
    fun setPosition(x: Int, y:Int): Coord {
        position = Coord(x, y)
        return position
    }
    fun setPosition(coord: Coord): Coord {
        position = coord
        return position
    }


    /*
    Debug
     */
    override fun toString(): String {
        val string = when (type) {
            Type.PAWN -> "p"
            Type.KNIGHT -> "n"
            Type.BISHOP -> "b"
            Type.ROOK -> "r"
            Type.QUEEN -> "q"
            Type.KING -> "k"
        }
        return if (color == Color.WHITE) string.uppercase()
        else string
    }

}