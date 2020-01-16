import parser.RegExpsGrammarLexer
import parser.RegExpsGrammarParser

fun main(args: Array<String>) {
//    val input = "s(ss)";
//    val input = "((abc*b|a)*ab(aa|b*)b)*";
//    val expected = "(b*|c|f)f|j*oa|n|f";
//    val expected = "(b*|(cde)|f)";

    val expected = "abc|de**fg";
    val parser = RegExpsGrammarParser(RegExpsGrammarLexer(expected))
    val result = parser.mainRule()

    println("expected: $expected")
    println("actual:   $result")

    val presenter = Presenter()
    presenter.generateImage(result)
}