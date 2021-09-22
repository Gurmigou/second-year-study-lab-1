package DataStructure;

import java.util.Stack;

/**
 * Implementation of stack using linked lists
 * @param <E> generic parameter of an element type of this collection
 */
public class MyStack<E> extends Stack<E> {
    Node<E> top = null;

    private int size;

    private static class Node<E> {
        Node<E> prev;
        E value;

        public Node(Node<E> prev, E value) {
            this.prev = prev;
            this.value = value;
        }
    }
    public E push(E value) {
        top = new Node<>(top, value);
        size++;
        return value;
    }

    public E pop() {
        if (isEmpty())
            return null;

        E toReturn = top.value;
        top = top.prev;
        size--;
        return toReturn;
    }

    public E peek() {
        return (top == null) ? null : top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
