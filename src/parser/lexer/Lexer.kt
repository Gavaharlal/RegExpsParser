package parser.lexer

class Lexer(private val input: String) {

    private val tokens = ArrayList<Token>()
    private var curPos = 0

    init {
        tokenize()
        tokens.add(Token(TokenType.END, "END"))
    }

    fun hasNext() = curPos < tokens.size - 1

    fun current() = tokens[curPos]

    fun next() = ++curPos

    private fun tokenize() {
        var i = -1
        while (++i < input.length) {
            if (Character.isWhitespace(input[i])) continue
            when (input[i]) {
                '(' -> tokens.add(Token(TokenType.OPEN_BRACKET, "("))
                ')' -> tokens.add(Token(TokenType.CLOSE_BRACKET, ")"))
                '|' -> tokens.add(Token(TokenType.CHOOSE, "|"))
                '*' -> tokens.add(Token(TokenType.CLOSURE, "*"))
                else -> {
                    tokens.add(Token(TokenType.CHARACTER, input[i].toString()))
                }
            }
        }
    }
}
