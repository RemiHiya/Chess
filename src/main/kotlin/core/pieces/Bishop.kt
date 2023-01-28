package core.pieces

import core.*

class Bishop(color: Color): Piece(Type.BISHOP, color) {

    override fun possibleMoves(board: Board): List<Coord> {
        return listOf()
    }

    override fun possibleAttacks(board: Board): List<Coord> {
        return possibleMoves(board)
    }
}