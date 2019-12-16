package terms

class Abstraction(private val variable: Variable, private val lambdaTerm: LambdaTerm) : LambdaTerm {

    override fun toString(): String {
        return "(\\$variable.$lambdaTerm)"
    }
}
