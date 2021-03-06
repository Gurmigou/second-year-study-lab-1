package DataStructure;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Graph implementation using adjacent matrix
 * @param <T> generic parameter of an element type of this collection
 */
public class GraphMatrix<T> extends AbstractGraph<T> {
    private static final int BUNG = 0;

    public GraphMatrix(List<List<Pair<Integer, T>>> graph) {
        super(graph);
    }

    private void enlargeRow(List<Pair<Integer, T>> row, int byElements) {
        for (int i = 0; i < byElements; i++)
            row.add(null);
    }

    private void addAdjacentVertexUtil(int id, int adjacentToId, T value) {
        var row = graph().get(id);
        var pair = new Pair<>(BUNG, value);

        if (row.size() <= adjacentToId) {
            enlargeRow(row, row.size() - adjacentToId);
            row.add(row.size() - 1, pair);
        }
        else
            row.set(adjacentToId, pair);
    }

    private T removeAdjacentVertexUtil(int id, int adjacentToId) {
        var row = graph().get(id);
        var e = row.get(id);
        row.set(id, null);
        return e.right();
    }

    @Override
    public void addAdjacentVertex(int id, int adjacentToId, T value) {
        ensureVertexExist(id);
        ensureVertexExist(adjacentToId);

        addAdjacentVertexUtil(id, adjacentToId, value);
        addAdjacentVertexUtil(adjacentToId, id, value);
    }

    @Override
    public void addAdjacentVertexList(int id, List<Pair<Integer, T>> list) {
        throw new UnsupportedOperationException("This operation isn't supported");
    }

    @Override
    public void removeVertex(int id) {
        for (var row : graph())
            row.remove(id);
        graph().remove(id);
    }

    @Override
    public T removeAdjacentVertex(int id, int adjacentToId) {
        ensureVertexExist(id);
        T e = removeAdjacentVertexUtil(id, adjacentToId);
        removeAdjacentVertexUtil(adjacentToId, id);
        return e;
    }

    void dfsUtil(int vertexIndex, T vertexValue, BiConsumer<Integer, ? super T> biConsumer, boolean[] marks) {
        biConsumer.accept(vertexIndex, vertexValue);
        marks[vertexIndex] = true;

        var row = graph().get(vertexIndex);

        for (int i = 0; i < row.size(); ++i) {
            if (row.get(i) != null && !marks[i])
                dfsUtil(i, row.get(i).right(), biConsumer, marks);
        }
    }

    @Override
    public boolean[] dfs(int startVertex, T startValue, BiConsumer<Integer, ? super T> biConsumer) {
        boolean[] marks = new boolean[getNumberOfVertexes()];

        dfsUtil(startVertex, startValue, biConsumer, marks);
        return marks;
    }

    @Override
    public boolean isConnected(int startVertex, T startValue) {
        boolean[] marks = dfs(startVertex, startValue, (i, v) -> {});

        int marked = 0;
        for (boolean mark : marks) {
            if (mark) marked++;
        }
        return marked == getNumberOfVertexes();
    }

    private double distanceUtil(int curVertex, int endVertex, boolean[] marks,
                                boolean isWeighted, double curWeight)
    {
        if (curVertex == endVertex)
            return curWeight;

        marks[curVertex] = true;
        var row = graph().get(curVertex);

        for (int i = 0; i < row.size(); ++i) {
            if (row.get(i) != null && !marks[i]) {
                double weight = (isWeighted) ? getWeight(row.get(i).right()) : 1;
                return distanceUtil(i, endVertex, marks, isWeighted, weight + curVertex);
            }
        }

        return -1;
    }

    @Override
    public double distance(int startVertex, int endVertex, boolean isWeighted) {
        ensureVertexExist(startVertex);
        ensureVertexExist(endVertex);

        boolean[] marks = new boolean[getNumberOfVertexes()];
        return distanceUtil(startVertex, endVertex, marks, isWeighted, 0);
    }
}
