package DataStructure;

import java.util.List;
import java.util.function.BiConsumer;

public class GraphStructure<T> extends AbstractGraph<T> {
    public GraphStructure(List<List<Pair<Integer, T>>> graph) {
        super(graph);
    }

    public void addAdjacentVertex(int id, int adjacentToId, T value) {
        ensureVertexExist(id);

        var vertexList = graph().get(id);
        vertexList.add(vertexList.size() - 1, new Pair<>(adjacentToId, value));
    }

    public void addAdjacentVertexList(int id, List<Pair<Integer, T>> list) {
        ensureVertexExist(id);

        var vertexList = graph().get(id);
        vertexList.addAll(list);
    }

    public void removeVertex(int id) {
        ensureVertexExist(id);
        setNumberOfVertexes(getNumberOfVertexes() - 1);
        graph().remove(id);
    }

    @Override
    public void removeAdjacentVertex(int id, int adjacentToId) {

    }

    private void dfsHelper(int vertex, T value, BiConsumer<Integer, ? super T> biConsumer, boolean[] marks) {
        biConsumer.accept(vertex, value);
        marks[vertex] = true;

        var vertexList = graph().get(vertex);
        for (var pair : vertexList) {
            if (!marks[vertex])
                dfsHelper(pair.left(), pair.right(), biConsumer, marks);
        }
    }

    public boolean[] dfs(int startVertex, T startValue, BiConsumer<Integer, ? super T> biConsumer) {
        ensureVertexExist(startVertex);
        boolean[] marks = new boolean[getNumberOfVertexes()];

        dfsHelper(startVertex, startValue, biConsumer, marks);
        return marks;
    }

    public boolean isConnected(int startVertex, T startValue) {
        boolean[] marks = dfs(startVertex, startValue, (i, v) -> {});

        int marked = 0;
        for (boolean mark : marks) {
            if (mark) marked++;
        }
        return marked == getNumberOfVertexes();
    }
}
