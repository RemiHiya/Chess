package core

class AI(private val board: Board) {

    fun predict(depth: Int): Pair<Piece, Coord>? {
        return minimax(depth, true).second
    }

    private fun minimax(depth: Int, maximizingPlayer: Boolean): Pair<Int, Pair<Piece, Coord>?> {
        if (depth == 0 || board.isGameOver()) {
            return Pair(board.evaluate(), null)
        }

        var bestValue: Int? = null
        var bestMove: Pair<Piece, Coord>? = null

        val moves = board.getAllMovesOfColor(board.turn)
        for (move in moves) {

            val previousBoard = board.getFEN()
            val capturedPiece = board.move(move.first, move.second)
            val value = minimax(depth - 1, !maximizingPlayer)?.first
            board.loadFEN(previousBoard)

            if (value == null) {
                continue
            }

            if (bestValue == null || (maximizingPlayer && value > bestValue) || (!maximizingPlayer && value < bestValue)) {
                bestValue = value
                bestMove = move
            }
        }

        return Pair(bestValue ?: 0, bestMove)
    }

}