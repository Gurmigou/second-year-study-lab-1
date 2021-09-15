package Pojo;

import DataStructure.MyLinkedList;

public class Cube {
    private final MyLinkedList<Edge> edgeList;
    private final int numberOfEdges;
    private final int id;

    private static int nextId = 1;

    {
        this.id = nextId;
        nextId++;
    }

    public Cube(MyLinkedList<Edge> edgeList) {
        this.edgeList = edgeList;
        this.numberOfEdges = edgeList.size();
    }

    public MyLinkedList<Edge> getEdgeList() {
        return edgeList;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public int getId() {
        return id;
    }
}
