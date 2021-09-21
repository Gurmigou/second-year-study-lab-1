package DataStructure;

import java.util.List;
import java.util.function.BiConsumer;

public interface GraphAlgorithm<T> {
    List<List<Pair<Integer, T>>> custom();
    boolean[] dfs(int startVertex, T startValue, BiConsumer<Integer, ? super T> biConsumer);
    boolean isConnected(int startVertex, T startValue);
}
