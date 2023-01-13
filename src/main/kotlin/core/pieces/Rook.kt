package core.pieces

import core.*

class Rook(color: Color): Piece(Type.ROOK, color) {

    override fun possibleMoves(board: Board): List<Coord> {
        val possibleMoves = mutableListOf<Coord>()
        val directionsPossibles = arrayOf(true, true, true, true)
        val directions = arrayOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

        for (i in 1..7) {
            // 0: haut, 1: droite, 2: bas, 3: gauche
            for (j in 0..3) {
                // Si la direction j est libre
                if (directionsPossibles[j]) {
                    // pos : la position dans la direction j, à la distance i
                    val pos = Coord(
                        getPosition().x + directions[j].first * i,
                        getPosition().y + directions[j].second * i)
                    if (board.isEmpty(pos) || board.canCapture(pos, color)) {
                        possibleMoves.add(pos)
                    } else {
                        directionsPossibles[j] = false
                    }
                }
            }

        }

        return possibleMoves
    }
}