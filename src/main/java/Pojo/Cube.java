package Pojo;

import DataStructure.MyLinkedList;

public record Cube(MyLinkedList<Edge> edgeList) {
    public MyLinkedList<Edge> getEdgeList() {
        return edgeList;
    }
}
