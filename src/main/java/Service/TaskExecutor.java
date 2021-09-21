package Service;

import DataStructure.Graph;
import DataStructure.MyLinkedList;
import DataStructure.Pair;
import Pojo.Cube;
import Pojo.Edge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static DataStructure.Graph.GraphAlgorithm;

public class TaskExecutor {
    private MyLinkedList<Pair<Integer, Edge>> createVertexList(MyLinkedList<Edge> edgeList, int id) {
        var vertexList = new MyLinkedList<Pair<Integer, Edge>>();
        for (Edge edge : edgeList) {
            var pair = new Pair<>(id, edge);
            vertexList.addLast(pair);
            id++;
        }
        return vertexList;
    }

    private Graph<Edge> buildGraph(MyLinkedList<Cube> cubes) {
        // This is a general vertex which is used as a start point of graph traversal
        int generalVertex = 0;

        var graph = new Graph<Edge>();
        graph.addVertex(generalVertex);

        int first = 0;
        int last = 1;

        for (Cube cube : cubes) {
            var edgeList = cube.getEdgeList();
            var vertexList = createVertexList(edgeList, last);

            for (int i = first; i < last; i++) {
                graph.addAdjacentVertexList(i, vertexList);

            }

            first = last;
            last += vertexList.size();

            for (int i = first; i < last; i++)
                graph.addVertex(i);
        }

        return graph;
    }

    public Map<Integer, Double> calculateCombinations(MyLinkedList<Cube> cubes) {
        var graph = buildGraph(cubes);

        // Map to store partial results
        var memoMap = new HashMap<Integer, List<Pair<Integer, Double>>>();
        var graphTraversal = new GraphTraversal(GraphAlgorithm.uses(graph));

        graphTraversal.dfsAnother(0, new Edge(0, 1), memoMap);

        var resultMap = new TreeMap<Integer, Double>();
        memoMap.get(0)
                .forEach(p -> {
                    Double v = resultMap.get(p.left());
                    if (v != null)
                        resultMap.put(p.left(), v + p.right());
                    else
                        resultMap.put(p.left(), p.right());
                });

        return resultMap;
    }
}
