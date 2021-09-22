package DataStructure;

import java.util.List;

/**
 * General interface of graphs
 * @param <T> generic parameter of an element type of this collection
 */
public interface Graph<T> {
    void addVertex(int id);
    void addAdjacentVertex(int id, int adjacentToId, T value);

    void addAdjacentVertexList(int id, List<Pair<Integer, T>> list);
    void removeVertex(int id);
    T removeAdjacentVertex(int id, int adjacentToId);
}
