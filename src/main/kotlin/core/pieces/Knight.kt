package core.pieces

import core.*

class Knight(color: Color): Piece(Type.KNIGHT, color) {

    override fun possibleMoves(board: Board): List<Coord> {
        return listOf()
    }

    override fun possibleAttacks(board: Board): List<Coord> {
        return possibleMoves(board)
    }
}