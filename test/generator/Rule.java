package generator;

import java.util.List;
import java.util.Objects;

public class Rule {
    private final TermToken from;

    public TermToken getFrom() {
        return from;
    }

    public List<Token> getDestination() {
        return destination;
    }

    private final List<Token> destination;

    public Rule(TermToken from, List<Token> destination) {
        this.from = from;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return destination.stream().map(Objects::toString).reduce("",String::concat);
    }
}
