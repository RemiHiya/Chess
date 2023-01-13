package core

import core.pieces.*

class Board {

    private val white = Color.WHITE
    private val black = Color.BLACK

    private var turn = 0    // 0: joueur 1, 1: joueur 2

    private val board = Array(8) { arrayOfNulls<Piece>(8)}

    fun initialize() {
        board[0][0] = Rook(white)
        board[1][0] = Knight(white)
        board[2][0] = Bishop(white)
        board[3][0] = Queen(white)
        board[4][0] = King(white)
        board[5][0] = Bishop(white)
        board[6][0] = Knight(white)
        board[7][0] = Rook(white)
        for (i in 0..7) {
            board[i][1] = Pawn(white)
        }
        board[0][7] = Rook(black)
        board[1][7] = Knight(black)
        board[2][7] = Bishop(black)
        board[3][7] = Queen(black)
        board[4][7] = King(black)
        board[5][7] = Bishop(black)
        board[6][7] = Knight(black)
        board[7][7] = Rook(black)
        for (i in 0..7) {
            board[i][6] = Pawn(black)
        }
        for (i in 0..7) {
            for (j in 0..7) {
                board[i][j]?.setPosition(i, j)
            }
        }
    }

    /*
    Game
     */

    fun input(input: String) {
        TODO()
    }

    fun move(piece: Piece, dest: Coord) {
        TODO()
    }

    fun nextTurn() {
        turn = if (turn == 0) 1 else 0
    }


    /*
    Utilitaire
     */
    fun isEmpty(coord: Coord): Boolean {
        return board[coord.x][coord.y] == null
    }

    fun getPiece(coord: Coord): Piece? {
        return board[coord.x][coord.y]
    }

    fun canCapture(coord: Coord, color: Color): Boolean {
        val piece = board[coord.x][coord.y]
        return piece != null && piece.color != color
    }


    /*
    - Debug
     */

    override fun toString(): String {
        var string = ""
        for (i in 0..7) {
            for (j in 0..7) {
                val p = board[j][7-i]
                string += p?.toString() ?: "."
                string += " "
            }
            string += "\n"
        }
        return string
    }
}