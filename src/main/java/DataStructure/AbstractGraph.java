package DataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractGraph<T> implements Graph<T>, GraphAlgorithm<T> {
    private final List<List<Pair<Integer, T>>> graph;
    private int numberOfVertexes;

    public AbstractGraph(List<List<Pair<Integer, T>>> graph) {
        this.graph = graph;
    }

    @Override
    public void addVertex(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Vertex id must be a non-negative number");

        if (id > graph.size()) {
            for (int i = 0; i <= id; i++)
                graph.add(null);
        }

        numberOfVertexes++;
        graph.add(id, new MyLinkedList<>());
    }

    @Override
    public List<List<Pair<Integer, T>>> custom() {
        return Collections.unmodifiableList(graph());
    }

    protected List<List<Pair<Integer, T>>> graph() {
        return graph;
    }

    protected void ensureVertexExist(int id) {
        if (id < 0 || id >= graph.size() || graph.get(id) == null)
            throw new IllegalArgumentException("Vertex with id =" + id + " doesn't exist");
    }

    public int getNumberOfVertexes() {
        return numberOfVertexes;
    }

    public void setNumberOfVertexes(int numberOfVertexes) {
        this.numberOfVertexes = numberOfVertexes;
    }
}
