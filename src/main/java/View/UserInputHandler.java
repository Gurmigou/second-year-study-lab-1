package View;

import DataStructure.MyLinkedList;
import Pojo.Cube;
import Pojo.Edge;

import java.util.Scanner;
import java.io.IOException;

public class UserInputHandler {
    private static final String m1 = "Введіть кількість граней кубика (парна кількість <= 22):";
    private static final String m2 = "Число на грані ( >= 1):";
    private static final String m3 = "Ймовірність грані ( >= 0 <= 1): ";
    private static final String m4 = "Грань номер ";
    private static final String m5 = "Бажаєте додати ще один кубик? (y/n): ";
    private static final String error = "Невірно введені дані";
    private static final String probabilityError = "Сума ймовырностей граней != 1";

    private static int inputNumOfEdges(Scanner scanner) throws IOException {
        System.out.println(m1);
        int numOfEdges = scanner.nextInt();
        if (numOfEdges % 2 == 1 || numOfEdges > 20)
            throw new IOException();
        return numOfEdges;
    }

    private static int inputNumberOnEdge(Scanner scanner) throws IOException {
        System.out.println(m2);
        int numberOnEdge = scanner.nextInt();
        if (numberOnEdge < 1)
            throw new IOException();
        return numberOnEdge;
    }

    private static double inputProbabilityOfEdge(Scanner scanner) throws IOException {
        System.out.println(m3);
        double probability = scanner.nextDouble();
        if (probability < 0 || probability > 1)
            throw new IOException();
        return probability;
    }

    private static boolean cubeDataIsCorrect(MyLinkedList<Edge> list) {
        double probability = 0;
        for (Edge edge : list) {
            probability += edge.getProbability();
            if (Double.compare(probability, 1) > 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        MyLinkedList<Cube> input = input();
    }

    public static MyLinkedList<Cube> input() {
        var scanner = new Scanner(System.in);
        var cubeList = new MyLinkedList<Cube>();

        while (true) {
            int numOfEdges;
            try {
                numOfEdges = inputNumOfEdges(scanner);
            } catch (IOException e) {
                System.out.println(error);
                continue;
            }

            var edgeList = new MyLinkedList<Edge>();

            for (int i = 1; i <= numOfEdges; i++) {
                System.out.println("* " + m4 + i + " *");

                int numberOnEdge;
                double probabilityOfEdge;

                try {
                    numberOnEdge = inputNumberOnEdge(scanner);
                } catch (IOException e) {
                    System.out.println(error);
                    continue;
                }

                try {
                    probabilityOfEdge = inputProbabilityOfEdge(scanner);
                } catch (IOException e) {
                    System.out.println(error);
                    continue;
                }

                var edge = new Edge(numberOnEdge, probabilityOfEdge);
                edgeList.add(edge);
            }

            if (cubeDataIsCorrect(edgeList)) {
                var cube = new Cube(edgeList);
                cubeList.add(cube);
            }
            else {
                System.out.println(probabilityError);
            }

            System.out.println(m5);
            String continueInput = scanner.next();
            if (!continueInput.equalsIgnoreCase("y"))
                break;
        }
        return cubeList;
    }
}
