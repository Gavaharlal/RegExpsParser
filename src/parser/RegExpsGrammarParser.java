package parser;

public class RegExpsGrammarParser {
    private final RegExpsGrammarLexer lexer;

    public RegExpsGrammarParser(final RegExpsGrammarLexer lexer) {
        this.lexer = lexer;
    }

    public Tree mainRule() throws ParsingException, LexingException {
        lexer.nextToken();
        Tree tree = parseE();
        if (lexer.getCurrentToken().type != RegExpsGrammarTokens._END) {
            throw new ParsingException("Expected end of input but found " + lexer.getCurrentToken().getText());
        }
        return tree;
    }

    private Tree parseE() throws ParsingException, LexingException {
        Tree tree;
        switch (lexer.getCurrentToken().type) {
            case LB:
            case CHARACTER: {
                
                Tree p = parseP();
                
                Tree t_ = parseT_();
                tree = new Tree("E", true, p, t_);
                break;
            }
            default: {
                throw new ParsingException("Expected LB, CHARACTER but found " + lexer.getCurrentToken().type.name());
            }
        }
        return tree;
    }

    private Tree parseT_() throws ParsingException, LexingException {
        Tree tree;
        switch (lexer.getCurrentToken().type) {
            case OR: {
                
                
                lexer.nextToken();
                Tree p = parseP();
                
                Tree t_ = parseT_();
                tree = new Tree("T'", true, new Tree("|", false), p, t_);
                break;
            }
            case _END:
            case RB: {
                tree = new Tree("T'", true);
                break;
            }
            default: {
                throw new ParsingException("Expected OR, _END, RB but found " + lexer.getCurrentToken().type.name());
            }
        }
        return tree;
    }

    private Tree parseP() throws ParsingException, LexingException {
        Tree tree;
        switch (lexer.getCurrentToken().type) {
            case LB:
            case CHARACTER: {
                
                Tree t = parseT();
                
                Tree e_ = parseE_();
                tree = new Tree("P", true, t, e_);
                break;
            }
            default: {
                throw new ParsingException("Expected LB, CHARACTER but found " + lexer.getCurrentToken().type.name());
            }
        }
        return tree;
    }

    private Tree parseE_() throws ParsingException, LexingException {
        Tree tree;
        switch (lexer.getCurrentToken().type) {
            case LB:
            case CHARACTER: {
                
                Tree t = parseT();
                
                Tree e_ = parseE_();
                tree = new Tree("E'", true, t, e_);
                break;
            }
            case _END:
            case RB:
            case OR: {
                tree = new Tree("E'", true);
                break;
            }
            default: {
                throw new ParsingException("Expected LB, CHARACTER, _END, RB, OR but found " + lexer.getCurrentToken().type.name());
            }
        }
        return tree;
    }

    private Tree parseT() throws ParsingException, LexingException {
        Tree tree;
        switch (lexer.getCurrentToken().type) {
            case LB:
            case CHARACTER: {
                
                Tree f = parseF();
                
                Tree p_ = parseP_();
                tree = new Tree("T", true, f, p_);
                break;
            }
            default: {
                throw new ParsingException("Expected LB, CHARACTER but found " + lexer.getCurrentToken().type.name());
            }
        }
        return tree;
    }

    private Tree parseP_() throws ParsingException, LexingException {
        Tree tree;
        switch (lexer.getCurrentToken().type) {
            case CLOSURE: {
                
                
                lexer.nextToken();
                Tree p_ = parseP_();
                tree = new Tree("P'", true, new Tree("*", false), p_);
                break;
            }
            case _END:
            case RB:
            case OR:
            case LB:
            case CHARACTER: {
                tree = new Tree("P'", true);
                break;
            }
            default: {
                throw new ParsingException("Expected CLOSURE, _END, RB, OR, LB, CHARACTER but found " + lexer.getCurrentToken().type.name());
            }
        }
        return tree;
    }

    private Tree parseF() throws ParsingException, LexingException {
        Tree tree;
        switch (lexer.getCurrentToken().type) {
            case CHARACTER: {
                
                Token c = lexer.getCurrentToken();
                tree = new Tree("F", true, new Tree(c.getText(), false));
                lexer.nextToken();
                break;
            }
            case LB: {
                
                
                lexer.nextToken();
                Tree e = parseE();
                
                tree = new Tree("F", true, new Tree("(", false), e, new Tree(")", false));
                lexer.nextToken();
                break;
            }
            default: {
                throw new ParsingException("Expected CHARACTER, LB but found " + lexer.getCurrentToken().type.name());
            }
        }
        return tree;
    }

}
