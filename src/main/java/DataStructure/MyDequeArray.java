package DataStructure;

import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of Double-ended-queue using an array
 * @param <E> generic parameter of an element type of this collection
 */
public class MyDequeArray<E> extends MyQueueArray<E> implements Deque<E> {
    @Override
    public void addFirst(E e) {
        offerFirst(e);
    }

    @Override
    public void addLast(E e) {
        offerLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        rightShiftAndPutAtBegin(1, e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        return super.offer(e);
    }

    @Override
    public E removeFirst() {
        E e = pollFirst();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E removeLast() {
        E e = pollLast();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E pollFirst() {
        return super.poll();
    }

    @Override
    public E pollLast() {
        E e = queue()[end() - 1];
        setEnd(end() - 1);
        return e;
    }

    @Override
    public E getFirst() {
        return peekFirst();
    }

    @Override
    public E getLast() {
        return peekLast();
    }

    @Override
    public E peekFirst() {
        return super.peek();
    }

    @Override
    public E peekLast() {
        return super.queue()[end() - 1];
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void push(E e) {
        addLast(e);
    }

    @Override
    public E pop() {
        return pollFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
    }
}
