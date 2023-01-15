package core.pieces

import core.*

class Queen(color: Color): Piece(Type.QUEEN, color) {

    override fun possibleMoves(board: Board, ignoreCheck: Boolean): List<Coord> {
        return listOf()
    }

    override fun possibleAttacks(board: Board, ignore: Boolean): List<Coord> {
        return possibleMoves(board, ignore)
    }
}