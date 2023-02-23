package core

import core.pieces.*

class Board {

    private val white = Color.WHITE
    private val black = Color.BLACK

    var turn = white    // 0: joueur 1, 1: joueur 2
    var enPassantTarget: Coord? = null
    var castles = mutableMapOf('Q' to true, 'K' to true, 'q' to true, 'k' to true)
    var halfMoveClock = 0
    var fullMoveClock = 0

    private var board = Array(8) { arrayOfNulls<Piece>(8)}

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
                board[i][j]?.position = Coord(i, j)
            }
        }
    }

    fun init() {
        board[7][0] = King(white)
        board[7][7] = Rook(black)
        board[4][4] = Queen(white)
        board[0][7] = King(black)

        board[7][0]?.position = Coord(7, 0)
        board[7][7]?.position = Coord(7, 7)
        board[4][4]?.position = Coord(4, 4)
        board[0][7]?.position = Coord(0, 7)
    }
    /*
    Game / input
     */

    fun executeInput(input: String) {
        val pos1 = parseInput(input).first
        val pos2 = parseInput(input).second
        board[pos1.x][pos1.y]?.let { move(it, pos2) }
    }

    fun isInputValid(input: String): Boolean {
        if ("abcdefgh".contains(input[0]) &&
            "12345678".contains(input[1]) &&
            "abcdefgh".contains(input[2]) &&
            "12345678".contains(input[3]) &&
            input.length == 4)
            return true
        return false
    }

    fun isInputCoordValid(input: String): Boolean {
        val pos1 = parseInput(input).first
        val pos2 = parseInput(input).second
        return pos1.isValid() && pos2.isValid()
    }

    fun isInputMoveValid(input: String): Boolean {
        val pos1 = parseInput(input).first
        val pos2 = parseInput(input).second
        board[pos1.x][pos1.y]?.let {
            return it.isValidMove(pos2, this)
        }
        return false
    }

    fun move(piece: Piece, destination: Coord) {
        enPassantTarget = null
        // Target en passant
        if (piece is Pawn && piece.moveCounter == 0) {
            val up = if (piece.color == Color.WHITE) 1 else -1
            enPassantTarget = Coord(piece.position.x, piece.position.y+1)
        }
        piece.moveCounter++
        simMove(piece, destination)
    }

    fun simMove(piece: Piece, destination: Coord) {
        board[piece.position.x][piece.position.y] = null
        piece.position = destination
        board[destination.x][destination.y] = piece
    }

    fun nextTurn() {
        turn = turn.opposite()
    }


    /*
    Utilitaire
     */

    fun parseInput(input: String): Pair<Coord, Coord> {
        val pos1 = (input[0].toString() + input[1]).toCoord()
        val pos2 = (input[2].toString() + input[3]).toCoord()
        return Pair(pos1, pos2)
    }

    fun isEmpty(coord: Coord): Boolean {
        return board[coord.x][coord.y] == null
    }

    fun getPiece(coord: Coord): Piece? {
        return board[coord.x][coord.y]
    }

    fun setPiece(piece: Piece?, coord: Coord) {
        board[coord.x][coord.y] = piece
    }

    fun canCapture(coord: Coord, color: Color): Boolean {
        val piece = board[coord.x][coord.y]
        return piece != null && piece.color != color
    }

    fun getPiecesOfColor(color: Color): MutableList<Piece> {
        val pieces = mutableListOf<Piece>()
        for (i in 0..7) {
            for (j in 0..7) {
                if (board[i][j]?.color == color)
                    board[i][j]?.let { pieces.add(it) }
            }
        }
        return pieces
    }

    fun getAttacksOfColor(color: Color): MutableList<Coord> {
        val attacks = mutableListOf<Coord>()
        for (i in 0..7) {
            for (j in 0..7) {
                val p = board[i][j]
                if (p!= null && p.color == color) {
                    attacks.addAll(p.possibleAttacks(this))
                }
            }
        }
        return attacks
    }

    fun isUnderAttack(coord: Coord, color: Color): Boolean {
        for (i in 0..7) {
            for (j in 0..7) {
                val piece = board[i][j]
                if (piece != null && piece.color != color) {
                    if (piece.possibleAttacks(this).contains(coord)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun findKing(color: Color): Piece {
        for (i in 0..7) {
            for (j in 0..7) {
                val piece = board[i][j]
                if (piece is King && piece.color == color)
                    return piece
            }
        }
        throw IllegalArgumentException("King not found for color $color.")
    }

    fun isCheck(color: Color): Boolean {
        return isUnderAttack(findKing(color).position, color)
    }

    /*
    - Import / Export
     */

    fun loadFEN(fen: String) {
        val parts = fen.split(" ")
        val b = Array(8) { arrayOfNulls<Piece>(8)}
        println(parts)

        // Placement des pièces
        var line = 7
        var column = 0
        for (i in parts[0]) {
            val col = if (i.isUpperCase()) white else black
            when (i.lowercase()) {
                "p" -> {
                    b[column][line] = Pawn(col)
                    column++
                }
                "r" -> {
                    b[column][line] = Rook(col)
                    column++
                }
                "n" -> {
                    b[column][line] = Knight(col)
                    column++
                }
                "b" -> {
                    b[column][line] = Bishop(col)
                    column++
                }
                "q" -> {
                    b[column][line] = Queen(col)
                    column++
                }
                "k" -> {
                    b[column][line] = King(col)
                    column++
                }
                "/" -> {
                    line--
                    column = 0
                }
            }
            if ("12345678".indexOf(i) != -1) {
                column += "12345678".indexOf(i)+1
            }
        }
        // Couleur devant jouer
        turn = if (parts[1] == "w") white else black
        // Roques possibles
        castles['Q'] = parts[2].contains('Q')
        castles['K'] = parts[2].contains('K')
        castles['q'] = parts[2].contains('q')
        castles['k'] = parts[2].contains('k')
        // En passant
        enPassantTarget = if (parts[3] == "-") null else parts[3].toCoord()
        // Demi coups
        halfMoveClock = parts[4].toInt()
        // Coups
        fullMoveClock = parts[5].toInt()
        board = b
    }


    /*
    - Debug
     */

    override fun toString(): String {
        var string = ""
        for (i in 0..7) {
            //string += "+---+---+---+---+---+---+---+---+\n"
            for (j in 0..7) {
                val p = board[j][7-i]
                //string += "| "
                string += p?.toString() ?: "."
                string += " "
            }
            //string += "| ${8-i}\n"
            string += "\n"
        }
        //string += "+---+---+---+---+---+---+---+---+\n  a   b   c   d   e   f   g   h"
        return string
    }

    fun debugPossibleMoves(coord: Coord): String {
        var string = ""
        for (i in 0..7) {
            for (j in 0..7) {
                string += if (board[coord.x][coord.y]?.isValidMove(Coord(j, 7-i), this) == true) {
                    "o "
                } else {
                    ". "
                }
            }
            string += "\n"
        }
        return string
    }
}