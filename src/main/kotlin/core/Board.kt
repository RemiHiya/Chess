package core

import core.pieces.*

const val SQUARE_VALUE_WHITE = 10
const val SQUARE_VALUE_BLACK = -10

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

    fun move(piece: Piece, destination: Coord): Piece? {
        enPassantTarget = null
        // Target en passant
        if (piece is Pawn && piece.moveCounter == 0) {
            val up = if (piece.color == Color.WHITE) 1 else -1
            enPassantTarget = Coord(piece.position.x, piece.position.y+1)
        }
        piece.moveCounter++
        nextTurn()
        return simMove(piece, destination)
    }

    fun simMove(piece: Piece, destination: Coord): Piece? {
        board[piece.position.x][piece.position.y] = null
        piece.position = destination
        val captured = board[destination.x][destination.y]
        board[destination.x][destination.y] = piece
        return captured
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

    fun getMovesOfColor(color: Color): MutableMap<Piece, List<Coord>> {
        val map = mutableMapOf<Piece, List<Coord>>()
        for (i in 0..7) {
            for (j in 0..7) {
                val p = board[i][j]
                if (p!= null && p.color == color) {
                    map[p] = p.possibleMoves(this)
                }
            }
        }
        return map
    }

    fun getAllMovesOfColor(color: Color): MutableList<Pair<Piece, Coord>> {
        val moves = mutableListOf<Pair<Piece, Coord>>()
        val mappedMoves = getMovesOfColor(color)
        for (i in mappedMoves.keys) {
            for (j in mappedMoves[i]!!) {
                moves.add(Pair(i, j))
            }

        }
        return moves
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

    fun isMate(color: Color): Boolean {
        return isCheck(color) && getMovesOfColor(color).isEmpty()
    }

    fun isStalemate(color: Color): Boolean {
        return !isCheck(color) && getMovesOfColor(color).isEmpty()
    }

    fun isGameOver(): Boolean {
        return isMate(white) || isMate(black) || isStalemate(white) || isStalemate(black)
    }

    fun updatePositions() {
        for (y in 0..7) {
            for (x in 0..7) {
                board[x][y]?.position = Coord(x, y)
            }
        }
    }

    fun evaluate(): Int {
        var score = 0
        for (row in 0..7) {
            for (col in 0..7) {
                val piece = getPiece(Coord(col, row))
                if (piece != null) {
                    val pieceValue = piece.value
                    val squareValue = getSquareValue(turn, Coord(col, row))
                    score += pieceValue + squareValue
                    if (piece.color == turn.opposite()) {
                        score *= -1
                    }
                }
            }
        }
        return score
    }

    fun getSquareValue(player: Color, position: Coord): Int {
        val row = position.y
        val col = position.x
        val isWhite = (row + col) % 2 == 0
        val value = if (isWhite) SQUARE_VALUE_WHITE else SQUARE_VALUE_BLACK
        return if (player == Color.WHITE) value else -value
    }

    /*
    - Import / Export
     */

    fun loadFEN(fen: String) {
        val parts = fen.split(" ")
        val b = Array(8) { arrayOfNulls<Piece>(8)}

        // Placement des pièces
        var line = 7
        var column = 0
        for (i in parts[0]) {
            val col = if (i.isUpperCase()) white else black
            if (i.lowercase() == "/") {
                line--
                column = 0
                continue
            }
            if ("12345678".indexOf(i) != -1) {
                column += "12345678".indexOf(i)+1
                continue
            }
            b[column][line] = when (i.lowercase()) {
                "p" -> Pawn(col)
                "r" -> Rook(col)
                "n" -> Knight(col)
                "b" -> Bishop(col)
                "q" -> Queen(col)
                "k" -> King(col)
                else -> null
            }
            column++
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
        updatePositions()
    }

    fun getFEN(): String {
        var result = ""
        // Positions des pièces
        for (y in 7 downTo 0) {
            var emptyCounter = 0
            for (x in 0..7) {
                val piece: String = when (board[x][y]) {
                    is Pawn -> "p"
                    is Rook -> "r"
                    is Knight -> "n"
                    is Bishop -> "b"
                    is Queen -> "q"
                    is King -> "k"
                    else -> ""
                }
                if (board[x][y] == null) {
                    emptyCounter++
                } else {
                    result += if (emptyCounter > 0) emptyCounter else ""
                    result += if (board[x][y]?.color == Color.WHITE) piece.uppercase() else piece
                    emptyCounter = 0
                }
            }
            result += if (emptyCounter > 0) emptyCounter else ""
            if (y != 0)
                result += "/"
        }
        // Couleur
        result += " "
        result += if (turn == Color.WHITE) "w" else "b"
        // Roques
        result += " "
        var b = false
        for (i in castles.keys) {
            if (castles[i] == true) {
                result += i
                b = true
            }
        }
        if (!b)
            result += "-"
        result += " "
        result += if (enPassantTarget == null) "-" else enPassantTarget
        result += " "
        result += halfMoveClock
        result += " "
        result += fullMoveClock
        return result
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