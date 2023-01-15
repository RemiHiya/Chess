package core.pieces

import core.*

class Pawn(color: Color): Piece(Type.PAWN, color) {

    override fun possibleMoves(board: Board, ignoreCheck: Boolean): List<Coord> {

        val possibleMoves = mutableListOf<Coord>()
        val upDirection = if (color == Color.WHITE) 1 else -1

        // Coup quelconque
        var pos = Coord(position.x, position.y + upDirection)
        if (pos.isValid() && board.isEmpty(pos) && canPlay(pos, board, ignoreCheck)) {
            possibleMoves.add(pos)
        }

        // 1er coup
        if (moveCounter == 0) {
            if (board.isEmpty(pos)&&board.isEmpty(Coord(pos.x, pos.y+upDirection)) && canPlay(pos, board, ignoreCheck)) {
                possibleMoves.add(Coord(pos.x, pos.y + upDirection))
            }
        }

        // Capture diagonale droite
        pos = Coord(position.x+1, pos.y)
        if (pos.isValid() && board.canCapture(pos, color) && canPlay(pos, board, ignoreCheck)) {
            possibleMoves.add(pos)
        }

        // Capture diagonale gauche
        pos = Coord(position.x-1, pos.y)
        if (pos.isValid() && board.canCapture(pos, color) && canPlay(pos, board, ignoreCheck)) {
            possibleMoves.add(pos)
        }

        // En passant droite
        pos = Coord(position.x+1, position.y)
        var piece = if (pos.isValid()) board.getPiece(pos) else null
        if (pos.isValid() &&
            piece is Pawn &&
            piece.color != color &&
            piece.moveCounter == 1 &&
            board.isEmpty(Coord(pos.x, pos.y + upDirection)) &&
            canPlay(Coord(pos.x, pos.y + upDirection), board, ignoreCheck)) {
            possibleMoves.add(Coord(pos.x, pos.y + upDirection))
        }


        // En passant gauche
        pos = Coord(position.x-1, position.y)
        piece = if (pos.isValid()) board.getPiece(pos) else null
        if (pos.isValid() &&
            piece is Pawn &&
            piece.color != color &&
            piece.moveCounter == 1 &&
            board.isEmpty(Coord(pos.x, pos.y + upDirection)) &&
            canPlay(Coord(pos.x, pos.y + upDirection), board, ignoreCheck)) {
            possibleMoves.add(Coord(pos.x, pos.y + upDirection))
        }

        return possibleMoves
    }


    override fun possibleAttacks(board: Board, ignore: Boolean): List<Coord> {
        val possibleAttacks = mutableListOf<Coord>()
        val upDirection = if (color == Color.WHITE) 1 else -1

        // Capture diagonale droite
        var pos = Coord(position.x+1, position.y + upDirection)
        if (pos.isValid() && board.canCapture(pos, color) && canPlay(pos, board, ignore)) {
            possibleAttacks.add(pos)
        }

        // Capture diagonale gauche
        pos = Coord(position.x-1, pos.y)
        if (pos.isValid() && board.canCapture(pos, color) && canPlay(pos, board, ignore)) {
            possibleAttacks.add(pos)
        }

        // En passant droite
        pos = Coord(position.x+1, position.y)
        var piece = if (pos.isValid()) board.getPiece(pos) else null
        if (pos.isValid() &&
            piece is Pawn &&
            piece.color != color &&
            piece.moveCounter == 1 &&
            board.isEmpty(Coord(pos.x, pos.y + upDirection)) &&
            canPlay(Coord(pos.x, pos.y + upDirection), board, ignore)) {
            possibleAttacks.add(Coord(pos.x, pos.y + upDirection))
        }

        // En passant gauche
        pos = Coord(position.x-1, position.y)
        piece = if (pos.isValid()) board.getPiece(pos) else null
        if (pos.isValid() &&
            piece is Pawn &&
            piece.color != color &&
            piece.moveCounter == 1 &&
            board.isEmpty(Coord(pos.x, pos.y + upDirection)) &&
            canPlay(Coord(pos.x, pos.y + upDirection), board, ignore)) {
            possibleAttacks.add(Coord(pos.x, pos.y + upDirection))
        }

        return possibleAttacks
    }
}