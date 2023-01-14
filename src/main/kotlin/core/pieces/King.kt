package core.pieces

import core.*

class King(color: Color): Piece(Type.KING, color) {

    override fun possibleMoves(board: Board): List<Coord> {
        TODO()
    }

    override fun possibleAttacks(board: Board): List<Coord> {
        return possibleMoves(board)
    }
}