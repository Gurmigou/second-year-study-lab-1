package DataStructure;

import java.util.AbstractQueue;
import java.util.Iterator;

/**
 * Implementation of queue using linked lists
 * @param <E> generic parameter of an element type of this collection
 */
public class MyQueue<E> extends AbstractQueue<E> {
    Node<E> head = null;
    Node<E> tail = null;

    private int size;

    private static class Node<E> {
        Node<E> prev;
        E value;

        public Node(Node<E> prev, E value) {
            this.prev = prev;
            this.value = value;
        }
    }

    @Override
    public E poll() {
        final Node<E> prevHead = head;
        if (head == tail)
            head = tail = null;
        else
            head = head.prev;

        size--;
        return prevHead.value;
    }

    @Override
    public boolean offer(E e) {
        final Node<E> queueNode = new Node<>(null, e);
        if (head == null)
            head = queueNode;
        else
            tail.prev = queueNode;

        tail = queueNode;
        size++;
        return true;
    }

    @Override
    public E peek() {
        final Node<E> first = head;
        return (first == null) ? null : first.value;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }
}
