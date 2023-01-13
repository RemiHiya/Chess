package core.pieces

import core.*

class Pawn(color: Color): Piece(Type.PAWN, color) {

    override fun possibleMoves(board: Board): List<Coord> {

        val possibleMoves = mutableListOf<Coord>()
        val upDirection = if (color == Color.WHITE) 1 else -1

        // Coup quelconque
        var pos = Coord(getPosition().x, getPosition().y + upDirection)
        if (pos.isValid() && board.isEmpty(pos))
            possibleMoves.add(pos)

        // 1er coup
        if (moveCounter == 0) {
            if (board.isEmpty(pos) && board.isEmpty(Coord(pos.x, pos.y + upDirection)))
                possibleMoves.add(Coord(pos.x, pos.y + upDirection))
        }

        // Capture diagonale droite
        pos = Coord(getPosition().x+1, pos.y)
        if (pos.isValid() && board.canCapture(pos, color))
            possibleMoves.add(pos)
        // Capture diagonale gauche
        pos = Coord(getPosition().x-1, pos.y)
        if (pos.isValid() && board.canCapture(pos, color))
            possibleMoves.add(pos)

        // En passant droite
        pos = Coord(getPosition().x+1, getPosition().y)
        var piece = if (pos.isValid()) board.getPiece(pos) else null
        if (pos.isValid() &&
            piece is Pawn &&
            piece.color != color &&
            piece.moveCounter == 1 &&
            board.isEmpty(Coord(pos.x, pos.y + upDirection)))
            possibleMoves.add(Coord(pos.x, pos.y + upDirection))

        // En passant gauche
        pos = Coord(getPosition().x-1, getPosition().y)
        piece = if (pos.isValid()) board.getPiece(pos) else null
        if (pos.isValid() &&
            piece is Pawn &&
            piece.color != color &&
            piece.moveCounter == 1 &&
            board.isEmpty(Coord(pos.x, pos.y + upDirection)))
            possibleMoves.add(Coord(pos.x, pos.y + upDirection))

        return possibleMoves
    }
}