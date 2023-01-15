package core.pieces

import core.*

class King(color: Color): Piece(Type.KING, color) {

    override fun possibleMoves(board: Board, ignoreCheck: Boolean): List<Coord> {
        return listOf()
    }

    override fun possibleAttacks(board: Board, ignore: Boolean): List<Coord> {
        return possibleMoves(board, ignore)
    }
}