package DataStructure;

import java.util.List;

public interface Graph<T> {
    void addVertex(int id);
    void addAdjacentVertex(int id, int adjacentToId, T value);

    void addAdjacentVertexList(int id, List<Pair<Integer, T>> list);
    void removeVertex(int id);
    void removeAdjacentVertex(int id, int adjacentToId);
}
