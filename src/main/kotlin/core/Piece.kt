package core

abstract class Piece(val type: Type, val color: Color) {

    var position: Coord = Coord(-1, -1)
    var moveCounter: Int = 0

    abstract fun possibleMoves(board: Board, ignoreCheck: Boolean): List<Coord>

    abstract fun possibleAttacks(board: Board, ignore: Boolean): List<Coord>

    open fun canPlay(destination: Coord, board: Board, ignoreCheck: Boolean): Boolean {
        if (ignoreCheck)
            return true
        val startPos = position
        position = destination
        val b = !board.isCheck(color)
        position = startPos
        return b
    }

    open fun isValidMove(destination: Coord, board: Board): Boolean {
        return possibleMoves(board, false).contains(destination)
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