package Service;

import DataStructure.MyLinkedList;
import DataStructure.Pair;
import Pojo.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static DataStructure.Graph.GraphAlgorithm;

public class GraphTraversal {
    private final List<MyLinkedList<Pair<Integer, Edge>>> adjStructure;

    public GraphTraversal(GraphAlgorithm<Edge> graphAlgorithm) {
        this.adjStructure = graphAlgorithm.adjStructure();
    }

    private void updateVertexMemo(int vertexNumber, double vertexProbability,
            List<Pair<Integer, Double>> updateMemo, List<Pair<Integer, Double>> anotherMemo)
    {
        anotherMemo.stream()
            .map(pair -> new Pair<>(pair.left() + vertexNumber, pair.right() * vertexProbability))
            .forEach(updateMemo::add);
    }

    public void dfsAnother(int vertexIndex, Edge edge,
                           Map<Integer, List<Pair<Integer, Double>>> memo)
    {
        var vertexList = adjStructure.get(vertexIndex);

        // if the current vertex is the last vertex in the chain
        // then put a value of this vertex into memo map
        if (vertexList.isEmpty()) {
            if (edge == null) return;
            var list = new ArrayList<Pair<Integer, Double>>();
            list.add(new Pair<>(edge.getNumber(), edge.getProbability()));
            memo.put(vertexIndex, list);
        } else {
            // if current edge doesn't contain a list then add an array list to the memo map
            memo.computeIfAbsent(vertexIndex, k -> new ArrayList<>());
        }

        for (var vertex : vertexList) {
            int adjIndex = vertex.left();
            Edge adjEdge = vertex.right();

            // traverse a vertex if it wasn't traversed
            if (!memo.containsKey(adjIndex))
                dfsAnother(adjIndex, adjEdge, memo);

            var currentEdgeMemo = memo.get(vertexIndex);

            // memo values of vertex with id = "adjIndex"
            var memoValues = memo.get(adjIndex);

            updateVertexMemo(edge.getNumber(), edge.getProbability(),
                             currentEdgeMemo, memoValues);
        }
    }
}
