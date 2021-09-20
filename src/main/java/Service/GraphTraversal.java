package Service;

import DataStructure.MyLinkedList;
import DataStructure.Pair;
import Pojo.Edge;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static DataStructure.Graph.GraphAlgorithm;

public class GraphTraversal {
    private final List<MyLinkedList<Pair<Integer, Edge>>> adjStructure;

    public GraphTraversal(GraphAlgorithm<Edge> graphAlgorithm) {
        this.adjStructure = graphAlgorithm.adjStructure();
    }

    public void dfs(int vertexIndex, Map<Integer, List<Pair<Integer, Double>>> memo) {
        var vertexList = adjStructure.get(vertexIndex);

        for (var vertex : vertexList) {
            int adjIndex = vertex.left();
            Edge adjEdge = vertex.right();

            if (memo.containsKey(adjIndex)) {
                List<Pair<Integer, Double>> values = memo.get(adjIndex);

                List<Pair<Integer, Double>> memoValue = values
                        .stream()
                        .peek(pair -> {
                                pair.setLeft(pair.left() + adjEdge.getNumber());
                                pair.setRight(pair.right() * adjEdge.getProbability());
                        })
                        .collect(Collectors.toList());
                memo.put(vertexIndex, memoValue);
            } else
                dfs(adjIndex, memo);
        }
    }
}
