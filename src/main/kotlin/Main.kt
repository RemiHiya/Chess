import core.*

fun main() {
    val b = Board()
    b.initialize()
    b.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
    println(b)
    //println(AI(b).predict(3))

    // TODO: vérif couleur, vérif echec

    while (true) {
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
                var t = AI(b).predict(3)
                println(t!!.first.position)
                println(t.second)
            }
        }

    }


}