package core

abstract class Piece(val type: Type, val color: Color) {

    var position: Coord = Coord(-1, -1)
    var moveCounter: Int = 0

    abstract fun possibleMoves(board: Board): List<Coord>

    abstract fun possibleAttacks(board: Board): List<Coord>

    open fun isValidMove(destination: Coord, board: Board): Boolean {
        return possibleMoves(board).contains(destination)
    }

    // modifie la liste des mouvements passée en paramètre, pour enlever les situations qui mettent en echec
    open fun dangerRewrite(moves: List<Coord>, board: Board): List<Coord> {
        if (board.turn != color) {
            return moves
        }
        val possibleMoves = mutableListOf<Coord>()
        val backupPos = position
        for (move in moves) {
            val backupPiece = board.getPiece(move) // Enregistre la piece qui est deja dans la destination
            board.simMove(this, move) // Simule le déplacement de la pièce
            if (!board.isCheck(color)) {
                possibleMoves.add(move)
            }
            board.simMove(this, backupPos) // Remet la pièce à sa position initiale
            board.setPiece(backupPiece, move) // Replace la pièce qui était dans la destination

        }
        position = backupPos
        return possibleMoves
    }


    /*
    Debug
     */
    override fun toString(): String {
        val string = when (type) {
            Type.PAWN -> "p"
            Type.KNIGHT -> "n"
            Type.BISHOP -> "b"
            Type.ROOK -> "r"
            Type.QUEEN -> "q"
            Type.KING -> "k"
        }
        return if (color == Color.WHITE) string.uppercase()
        else string
    }

}