import parser.parser.Parser

fun main(args: Array<String>) {
//    val input = "s(ss)";
//    val input = "((abc*b|a)*ab(aa|b*)b)*";
    val expected = "(b*|c|f)f|j*oa|n|f*|a";
    val parser = Parser()
    val result = parser.parse(expected)

    println("expected: $expected")
    println("actual:   $result")

    val presenter = Presenter()
    presenter.generateImage(result)
}