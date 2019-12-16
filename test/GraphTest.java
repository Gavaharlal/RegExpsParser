import generator.ExpressionGenerator;
import org.junit.Assert;
import org.junit.Test;
import parser.parser.Parser;

public class GraphTest {

    private final Parser parser = new Parser();

    @Test
    public void standardTests() {
        String testInput;
        String result;

        testInput = "c"; // F -> c
        result = parser.parse(testInput).toString();
        Assert.assertEquals(testInput, result);

        testInput = "cvcdf"; // E -> E T
        check(testInput);

        testInput = "a|b"; // T -> T | P
        check(testInput);

        testInput = "a*bc"; // P -> F*
        check(testInput);

        testInput = "a(c)"; // F -> (E)
        check(testInput);
    }

    @Test
    public void randomTests() {
        int n = 100;
        for (int i = 0; i < n; i++) {
            String expected = ExpressionGenerator.generate();
            System.out.println("checking: " + expected);
            check(expected);
            System.out.println("done");
        }
    }

    private void check(String input) {
        String result = parser.parse(input).toString();
        Assert.assertEquals(input, result);
    }
}
