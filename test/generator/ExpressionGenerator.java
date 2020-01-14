package generator;

import java.util.*;
import java.util.stream.Collectors;

public class ExpressionGenerator {

    // E -> E T
    // E -> T
    // T -> T | P
    // T -> P
    // P -> F *
    // P -> F
    // F -> C
    // F -> (E)

    public static String generate() {
        Rule[] rules = new Rule[8];
        rules[0] = new Rule(TermToken.E, new ArrayList<>(Arrays.asList(TermToken.E, TermToken.T)));
        rules[1] = new Rule(TermToken.E, new ArrayList<>(Collections.singletonList(TermToken.T)));
        rules[2] = new Rule(TermToken.T, new ArrayList<>(Arrays.asList(TermToken.T, new NotTerm("|"), TermToken.P)));
        rules[3] = new Rule(TermToken.T, new ArrayList<>(Collections.singletonList(TermToken.P)));
        rules[4] = new Rule(TermToken.P, new ArrayList<>(Arrays.asList(TermToken.F, new NotTerm("*"))));
        rules[5] = new Rule(TermToken.P, new ArrayList<>(Collections.singletonList(TermToken.F)));
        rules[6] = new Rule(TermToken.F, new ArrayList<>(Collections.singletonList(TermToken.C)));
        rules[7] = new Rule(TermToken.F, new ArrayList<>(Arrays.asList(new NotTerm("("), TermToken.E, new NotTerm(")"))));

        Map<TermToken, List<Rule>> rulesMap = new HashMap<>();
        for (Rule rule : rules) {
            rulesMap.computeIfAbsent(rule.getFrom(), (k) -> new ArrayList<>());
            rulesMap.get(rule.getFrom()).add(rule);
        }

        Random random = new Random();

        Queue<Token> expressionState = new ArrayDeque<>(rules[0].getDestination());
        int toProcessCounter;
        int terminalsCounter = 2;
        while (terminalsCounter > 0) {
            toProcessCounter = expressionState.size();
            while (toProcessCounter > 0) {
                Token curToken = expressionState.remove();
                toProcessCounter--;
                if (curToken instanceof NotTerm) {
                    expressionState.add(curToken);
                } else {
                    terminalsCounter--;
                    TermToken curTerm = (TermToken) curToken;
                    Rule chosenRule;
                    if (curTerm == TermToken.F){
                        double prob = Math.random();
                        if (prob < 0.18) {
                            chosenRule = rulesMap.get(curTerm).get(1);
                        } else {
                            chosenRule = rulesMap.get(curTerm).get(0);
                        }
//                        chosenRule = rulesMap.get(curTerm).get(0);
                    } else {
                        chosenRule = rulesMap.get(curTerm).get(random.nextInt(rulesMap.get(curTerm).size()));
                    }

                    for (Token token : chosenRule.getDestination()) {
                        if (token instanceof TermToken) {
                            if (token == TermToken.C) {
                                int ranNum = random.nextInt(15);
                                token = new NotTerm(Character.toString((char) (ranNum + 'a')));
                            } else {
                                terminalsCounter++;
                            }
                        }
                        expressionState.add(token);
                    }
                }
            }

        }

        String expression = expressionState.stream().map(Object::toString).collect(Collectors.joining());
        return expression;
    }
}
