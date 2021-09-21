package DataStructure;

import java.util.List;

public interface Graph<T> {
    void addVertex(int id);
    void addAdjacentVertex(int id, int adjacentToId, T value);

    // нарушение принципа SOLID: 'L'
    void addAdjacentVertexList(int id, MyLinkedList<Pair<Integer, T>> list);
    void removeVertex(int id);
}
