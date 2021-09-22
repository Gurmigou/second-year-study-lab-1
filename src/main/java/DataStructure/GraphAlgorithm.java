package DataStructure;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * This class supplies different graphs algorithms
 * @param <T> generic parameter of an element type
 */
public interface GraphAlgorithm<T> {
    List<List<Pair<Integer, T>>> custom();

    boolean[] dfs(int startVertex, T startValue, BiConsumer<Integer, ? super T> biConsumer);

    boolean isConnected(int startVertex, T startValue);

    double distance(int startVertex, int endVertex, boolean isWeighted);
}
