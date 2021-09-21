import DataStructure.MyLinkedList;
import Pojo.Cube;
import Service.TaskExecutor;
import View.UserView;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Cube> cubes = UserView.input();

        var task = new TaskExecutor();
        Map<Integer, Double> resultMap = task.calculateCombinations(cubes);

        UserView.outputResult(resultMap);
    }
}
