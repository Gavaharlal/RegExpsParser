package parser.lexer

class Token(val tokenType: TokenType, private val value: String) {
    override fun toString(): String {
        return value
    }
}
