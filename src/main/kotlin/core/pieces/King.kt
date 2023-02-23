package core.pieces

import core.*

class King(color: Color): Piece(Type.KING, color) {

    override fun possibleMoves(board: Board): List<Coord> {
        val directions = arrayOf(Coord(0, 1), Coord(1, 0), Coord(0, -1), Coord(-1, 0),
            Coord(1, 1), Coord(1, -1), Coord(-1, -1), Coord(-1, 1))
        val possiblesMoves = directions.map { position + it }.filter {
            it.isValid() && (board.isEmpty(it) || board.canCapture(it, color))
        }

        return dangerRewrite(possiblesMoves, board)
    }

    override fun possibleAttacks(board: Board): List<Coord> {
        return possibleMoves(board)
    }
}