package core.pieces

import core.*

class Queen(color: Color): Piece(Type.QUEEN, color) {

    override fun possibleMoves(board: Board): List<Coord> {
        TODO()
    }

    override fun possibleAttacks(board: Board): List<Coord> {
        return possibleMoves(board)
    }
}