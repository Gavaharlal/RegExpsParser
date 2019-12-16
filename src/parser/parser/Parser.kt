package parser.parser

import parser.lexer.Lexer
import parser.lexer.TokenType.*
import parser.lexer.Tree

class Parser() {

    private lateinit var lexer: Lexer

    fun parse(inputString: String): Tree {
        lexer = Lexer(inputString)
        return expression()
    }

    private fun expression(): Tree {
        val token = lexer.current()
        val result: Tree
        result = when (token.tokenType) {
            OPEN_BRACKET, CHARACTER -> {
                // E -> T E`
                val t: Tree = t()
                val eP: Tree = ePrime()
                Tree("E", true, t, eP)
            }
            else -> throw Error()
        }
        return result
    }

    private fun ePrime(): Tree {
        val token = lexer.current()
        val result: Tree
        result = when (token.tokenType) {
            OPEN_BRACKET, CHARACTER -> {
                // E' -> T E'
                val t: Tree = t()
                val eP: Tree = ePrime()
                Tree("E'", true, t, eP)
            }
            CLOSE_BRACKET, END -> {
                // E' -> eps
                Tree("E'", true)
            }
            else -> throw Error()

        }
        return result
    }

    private fun t(): Tree {
        val token = lexer.current()
        val result: Tree
        result = when (token.tokenType) {
            OPEN_BRACKET, CHARACTER -> {
                // T -> P T'
                val p: Tree = p()
                val tP: Tree = tPrime()
                Tree("T", true, p, tP)
            }
            else -> throw Error()
        }
        return result
    }

    private fun tPrime(): Tree {
        val token = lexer.current()
        val result: Tree
        result = when (token.tokenType) {
            CHOOSE -> {
                // T' -> | P T'
                lexer.next()
                val p: Tree = p()
                val tP: Tree = tPrime()
                Tree("T'", true, Tree("|", false), p, tP)
            }
            CLOSE_BRACKET, OPEN_BRACKET, CHARACTER, END -> {
                // T' -> eps
                Tree("T'", true)
            }
            else -> throw Error()

        }
        return result
    }

    private fun p(): Tree {
        val token = lexer.current()
        val result: Tree
        result = when (token.tokenType) {
            OPEN_BRACKET, CHARACTER -> {
                // P -> F P'
                val f: Tree = f()
                val pP: Tree = pPrime()
                Tree("P", true, f, pP)
            }
            else -> throw Error()
        }
        return result
    }

    private fun pPrime(): Tree {
        val token = lexer.current()
        val result: Tree?
        result = when (token.tokenType) {
            CLOSURE -> {
                // P' -> *
                lexer.next()
                Tree("P'", true, Tree("*", false))
            }
            //$,c,(,|,)
            END, CHARACTER, OPEN_BRACKET, CHOOSE, CLOSE_BRACKET -> {
                // P' -> eps
                Tree("P'", true)
            }

        }
        return result
    }

    private fun f(): Tree {
        val token = lexer.current()
        val result: Tree
        result = when (token.tokenType) {
            CHARACTER -> {
                lexer.next()
                Tree("F", true, Tree(token.toString(), false))
            }
            OPEN_BRACKET -> {
                lexer.next()
                val e = expression()
                if (lexer.current().tokenType != CLOSE_BRACKET) throw Error()
                lexer.next()
                Tree("F", true, Tree("(", false), e, Tree(")", false))
            }
            else -> throw Error()
        }
        return result
    }
}