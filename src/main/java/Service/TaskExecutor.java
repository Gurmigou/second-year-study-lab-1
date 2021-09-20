package Service;

import DataStructure.Graph;
import DataStructure.MyLinkedList;
import DataStructure.Pair;
import Pojo.Cube;
import Pojo.Edge;

import java.util.Set;

public class TaskExecutor {

    public Set<Pair<Integer, Double>> calculateCombinations(MyLinkedList<Cube> cubes) {
        // This is a general vertex which is used as a start point of graph traversal
        int generalVertex = 0;
        var graph = new Graph<Edge>();
        graph.addVertex(generalVertex);

        int index = generalVertex + 1;

        for (Cube cube : cubes) {
            MyLinkedList<Edge> edgeList = cube.getEdgeList();

        }

        return null;
    }
}
