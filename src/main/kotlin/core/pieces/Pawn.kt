package core.pieces

import core.*

class Pawn(color: Color): Piece(Type.PAWN, color) {

    override fun possibleMoves(board: Board): List<Coord> {
        TODO()
    }
}