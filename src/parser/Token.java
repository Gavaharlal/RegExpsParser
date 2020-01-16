package parser;

public class Token {
    public final RegExpsGrammarTokens type;
    private final String text;

    public Token(final RegExpsGrammarTokens type, final String text) {
        this.type = type;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
