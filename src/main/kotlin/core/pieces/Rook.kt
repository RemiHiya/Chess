package core.pieces

import core.*

class Rook(color: Color): Piece(Type.ROOK, color) {

    override val value: Int = 5

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
                        position.x + directions[j].first * i,
                        position.y + directions[j].second * i)
                    if (pos.isValid()) {
                        if (board.isEmpty(pos)) {
                            possibleMoves.add(pos)
                        }
                        else if (board.canCapture(pos, color)) {
                            possibleMoves.add(pos)
                            directionsPossibles[j] = false
                        } else {
                            directionsPossibles[j] = false
                        }
                    } else {
                        directionsPossibles[j] = false
                    }
                }
            }

        }
        return dangerRewrite(possibleMoves, board)
    }

    override fun possibleAttacks(board: Board): List<Coord> {
        return possibleMoves(board)
    }
}