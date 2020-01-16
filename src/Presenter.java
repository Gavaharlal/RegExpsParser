import parser.Tree;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Presenter {

    private BufferedWriter bufferedWriter;
    private Map<String, Integer> idCounter = new HashMap<>();

    public void generateImage(Tree tree) throws IOException {
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("graph.gv")));
        idCounter.put("E", 0);
        idCounter.put("E'", 0);
        idCounter.put("T", 0);
        idCounter.put("T'", 0);
        idCounter.put("P", 0);
        idCounter.put("P'", 0);
        idCounter.put("F", 0);
        bufferedWriter.write("digraph PresenterGraph {\n");
        processNode(tree);
        bufferedWriter.write("}");
        bufferedWriter.close();

        Runtime.getRuntime().exec("dot -Tpng graph.gv -o result.png");
    }

    private String processNode(Tree curNode) throws IOException {
        idCounter.putIfAbsent(curNode.getNode(), 0);
        int curNodeId = idCounter.get(curNode.getNode());
        idCounter.put(curNode.getNode(), curNodeId + 1);

        String nodeNameTag = curNode.getNode() + curNodeId;

        bufferedWriter.write("\t");
        if (curNode.isNonTerminal()){
            bufferedWriter.write("\"" + nodeNameTag + "\" [label=\"" + curNode.getNode() + "\"]\n");
        } else {
            bufferedWriter.write("\"" + nodeNameTag + "\" [label=\"" + curNode.getNode() + "\", style=\"filled\", fillcolor=\"blue\", fontcolor=\"#FFFFFF\"]\n");
        }
        for (Tree child : curNode.getChildren()) {
            String childName = processNode(child);
            bufferedWriter.write("\t");
            bufferedWriter.write("\"" + nodeNameTag + "\"" + " -> " + "\"" + childName + "\"\n");
        }
        bufferedWriter.write("\n");
        return nodeNameTag;
    }
}
