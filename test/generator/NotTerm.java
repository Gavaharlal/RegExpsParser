package generator;

public class NotTerm implements Token{
    private final String value;

    public NotTerm(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
