import parser.parser.Parser

fun main(args: Array<String>) {
//    val input = "s(ss)";
//    val input = "((abc*b|a)*ab(aa|b*)b)*";
//    val expected = "(b*|c|f)f|j*oa|n|f";
//    val expected = "(b*|(cde)|f)";

    val expected = "abc|de**fg";
    val parser = Parser()
    val result = parser.parse(expected)

    println("expected: $expected")
    println("actual:   $result")

    val presenter = Presenter()
    presenter.generateImage(result)
}