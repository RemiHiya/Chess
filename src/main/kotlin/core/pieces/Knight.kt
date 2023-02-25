package core.pieces

import core.*

class Knight(color: Color): Piece(Type.KNIGHT, color) {

    override val value: Int = 3

    override fun possibleMoves(board: Board): List<Coord> {
        val moves = listOf(
            Coord(-2, -1),
            Coord(-2, 1),
            Coord(-1, -2),
            Coord(-1, 2),
            Coord(1, -2),
            Coord(1, 2),
            Coord(2, -1),
            Coord(2, 1)
        )
        val possibleMoves = moves.map { it+position }.filter {
            it.isValid() && (board.isEmpty(it) || board.canCapture(it, color))
        }
        return dangerRewrite(possibleMoves, board)
    }

    override fun possibleAttacks(board: Board): List<Coord> {
        return possibleMoves(board)
    }
}