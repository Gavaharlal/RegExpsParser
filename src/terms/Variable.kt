package terms

class Variable(private val variableName: String) : LambdaTerm {

    override fun toString(): String {
        return variableName
    }
}
