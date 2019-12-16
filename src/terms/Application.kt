package terms

class Application(private val left: LambdaTerm, private val right: LambdaTerm) : LambdaTerm {

    override fun toString(): String {
        return "($left $right)"
    }
}
