import core.*

fun main() {
    val b = Board()
    b.initialize()
    //b.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq e3 0 1")
    println(b)

    // TODO: vérif couleur, vérif echec

    /*while (true) {
        val input = readlnOrNull()
        if (input != null) {
            if (!b.isInputValid(input))
                println("Entrée incorrecte")
            else if (!b.isInputCoordValid(input))
                println("Coordonées incorrectes")
            else if (!b.isInputMoveValid(input))
                println("Mouvement impossible")
            else {
                b.executeInput(input)
                println(b)
            }
        }

    }*/


}