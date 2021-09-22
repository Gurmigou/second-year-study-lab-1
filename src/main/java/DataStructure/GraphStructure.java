package DataStructure;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Graph implementation using adjacent structure
 * @param <T> generic parameter of an element type of this collection
 */

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
    public T removeAdjacentVertex(int id, int adjacentToId) {
        ensureVertexExist(id);
        var row = graph().get(id);
        var iterator = row.iterator();
        while (iterator.hasNext()) {
            var next = iterator.next();
            if (next.left() == adjacentToId) {
                iterator.remove();
                return next.right();
            }
        }
        return null;
    }

    private void dfsHelper(int vertex, T value, BiConsumer<Integer, ? super T> biConsumer, boolean[] marks) {
        biConsumer.accept(vertex, value);
        marks[vertex] = true;

        var row = graph().get(vertex);
        for (var pair : row) {
            if (!marks[pair.left()])
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



    private double distanceUtil(int curVertex, int endVertex, boolean[] marks,
                             boolean isWeighted, double curWeight)
    {
        if (curVertex == endVertex)
            return curWeight;

        marks[curVertex] = true;
        var row = graph().get(curVertex);

        for (var pair : row) {
            if (!marks[pair.left()]) {
                double weight = (isWeighted) ? getWeight(pair.right()) : 1;
                return distanceUtil(pair.left(), endVertex, marks, isWeighted, weight + curWeight);
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
