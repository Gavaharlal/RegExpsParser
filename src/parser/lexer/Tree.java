package parser.lexer;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Tree {
    private final String node;
    private final List<Tree> children;
    private final boolean isNonTerminal;

    public Tree(@NotNull String node, boolean isNonTerminal, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
        this.isNonTerminal = isNonTerminal;
    }

    public String getNode() {
        return node;
    }

    public List<Tree> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        if (!isNonTerminal) {
            return node;
        } else {
            return children.stream().map(Objects::toString).collect(Collectors.joining());
        }
    }

    public boolean isNonTerminal() {
        return isNonTerminal;
    }
}
