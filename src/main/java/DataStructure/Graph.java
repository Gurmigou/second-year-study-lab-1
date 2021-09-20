package DataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;

public class Graph<T> {
    private final List<MyLinkedList<Pair<Integer, T>>> adjStructure = new ArrayList<>();
    private int numberOfVertexes;

    public static void main(String[] args) {
        var graph = new Graph<Integer>();
        graph.addVertex(8);
    }

    public void addVertex(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Vertex id must be a non-negative number");

        if (id > adjStructure.size()) {
            for (int i = 0; i <= id; i++)
                adjStructure.add(null);
        }
        numberOfVertexes++;
        adjStructure.add(id, new MyLinkedList<>());
    }

    public void addAdjacentVertex(int id, int adjacentToId, T value) {
        ensureVertexExist(id);

        var vertexList = adjStructure.get(id);
        vertexList.addLast(new Pair<>(adjacentToId, value));
    }

    public void addAdjacentVertexList(int id, MyLinkedList<Pair<Integer, T>> list) {
        ensureVertexExist(id);

        var vertexList = adjStructure.get(id);
        vertexList.merge(list);
    }

    public void removeVertex(int id) {
        ensureVertexExist(id);
        numberOfVertexes--;
        adjStructure.remove(id);
    }

    private void ensureVertexExist(int id) {
        if (id < 0 || id >= adjStructure.size() || adjStructure.get(id) == null)
            throw new IllegalArgumentException("Vertex with id =" + id + " doesn't exist");
    }

    private void dfsHelper(int vertex, T value, BiConsumer<Integer, ? super T> biConsumer, boolean[] marks) {
        biConsumer.accept(vertex, value);
        marks[vertex] = true;

        var vertexList = adjStructure.get(vertex);
        for (var pair : vertexList) {
            if (!marks[vertex])
                dfsHelper(pair.left(), pair.right(), biConsumer, marks);
        }
    }

    public boolean[] dfs(int startVertex, T startValue, BiConsumer<Integer, ? super T> biConsumer) {
        ensureVertexExist(startVertex);
        boolean[] marks = new boolean[numberOfVertexes];

        dfsHelper(startVertex, startValue, biConsumer, marks);
        return marks;
    }

    public boolean graphIsConnected(int startVertex, T startValue) {
        boolean[] marks = dfs(startVertex, startValue, (i, v) -> {});

        int marked = 0;
        for (boolean mark : marks) {
            if (mark) marked++;
        }
        return marked == numberOfVertexes;
    }

    public static class GraphAlgorithm<T> {
        private final List<MyLinkedList<Pair<Integer, T>>> adjStructure;

        public GraphAlgorithm(List<MyLinkedList<Pair<Integer, T>>> adjStructure) {
            this.adjStructure = adjStructure;
        }

        public static <T> GraphAlgorithm<T> implement(Graph<T> graph) {
            var graphAlgorithm = new GraphAlgorithm<T>(new ArrayList<>());
            Collections.copy(graphAlgorithm.adjStructure, graph.adjStructure);
            return graphAlgorithm;
        }

        public List<MyLinkedList<Pair<Integer, T>>> adjStructure() {
            return adjStructure;
        }
    }
}
