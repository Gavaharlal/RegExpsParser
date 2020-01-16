package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpsGrammarLexer {
    private final Pattern ignorePattern;
    private CharSequence inputCharSequence;
    private final Map<RegExpsGrammarTokens, Pattern> tokens;
    private Token currentToken;

    public RegExpsGrammarLexer(CharSequence input) {
        ignorePattern = Pattern.compile(" ");
        inputCharSequence = input;
        this.tokens = new HashMap<>();
        tokens.put(RegExpsGrammarTokens.OR, Pattern.compile("\\|"));
        tokens.put(RegExpsGrammarTokens.CLOSURE, Pattern.compile("\\*"));
        tokens.put(RegExpsGrammarTokens.CHARACTER, Pattern.compile("[a-zA-Z0-9]"));
        tokens.put(RegExpsGrammarTokens.LB, Pattern.compile("\\("));
        tokens.put(RegExpsGrammarTokens.RB, Pattern.compile("\\)"));
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void nextToken() throws LexingException {
        Matcher matcher = ignorePattern.matcher(inputCharSequence);
        while (matcher.lookingAt()) {
            inputCharSequence = inputCharSequence.subSequence(matcher.end(), inputCharSequence.length());
            matcher.reset(inputCharSequence);
        }

        for (RegExpsGrammarTokens token : RegExpsGrammarTokens.values()) {
            if (token == RegExpsGrammarTokens._END) continue;
            Pattern tokenPattern = tokens.get(token);
            matcher = tokenPattern.matcher(inputCharSequence);
            if (matcher.lookingAt()) {
                String curMatch = inputCharSequence.subSequence(0, matcher.end()).toString();
                inputCharSequence = inputCharSequence.subSequence(curMatch.length(), inputCharSequence.length());
                currentToken = new Token(token, curMatch);
                return;
            }
        }

        if (inputCharSequence.length() == 0) {
            currentToken = new Token(RegExpsGrammarTokens._END, "END");
        } else {
            throw new LexingException("Unmatched data in input: \"" + inputCharSequence + "\"");
        }
    }
}
