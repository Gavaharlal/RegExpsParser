import generator.ExpressionGenerator;
import org.junit.Assert;
import org.junit.Test;
import parser.LexingException;
import parser.ParsingException;
import parser.RegExpsGrammarLexer;
import parser.RegExpsGrammarParser;

public class GraphTest {

    private RegExpsGrammarParser parser;

    @Test
    public void standardTests() throws ParsingException, LexingException {
        String testInput;
        String result;

        testInput = "c"; // F -> c
        parser = new RegExpsGrammarParser(new RegExpsGrammarLexer(testInput));
        result = parser.mainRule().toString();
        Assert.assertEquals(testInput, result);

        testInput = "cvcdf"; // E -> E T
        check(testInput);

        testInput = "a|b"; // T -> T | P
        check(testInput);

        testInput = "a*bc"; // P -> F*
        check(testInput);

        testInput = "a(cb)"; // F -> (E)
        check(testInput);

        testInput = "a***"; // P -> P *
        check(testInput);
    }

    @Test
    public void randomTests() throws ParsingException, LexingException {
        int n = 50;
        for (int i = 0; i < n; i++) {
            System.out.println("===============================");
            System.out.println("generating test");
            String expected = ExpressionGenerator.generate();
            System.out.println("checking: " + expected);
            check(expected);
            System.out.println("done");
        }
    }

    private void check(String input) throws ParsingException, LexingException {
        parser = new RegExpsGrammarParser(new RegExpsGrammarLexer(input));
        String result = parser.mainRule().toString();
        Assert.assertEquals(input, result);
    }
}
