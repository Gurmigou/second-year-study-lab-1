package DataStructure;

import java.util.*;

/**
 * Implementation of linked list and double ended queue (using linked lists)
 * @param <T> generic parameter of an element type of this collection
 */
public class MyLinkedList<T> extends AbstractList<T> implements Deque<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    private int modCount;

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
        modCount = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index must be >= 0 and < " + size);

        var iterator = this.iterator();
        int pos = 0;
        while (pos != index) {
            iterator.next();
            pos++;
        }
        return iterator.next();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean add(T value) {
        addLast(value);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(Collection<? extends T> c) {
        merge((MyLinkedList<T>) c);
        return true;
    }

    public T getFirst() {
        return head.value;
    }

    public T getLast() {
        return tail.value;
    }

    public void addFirst(T value) {
        if (isEmpty())
            addWhenListIsEmpty(value);
        else {
            Node<T> node = new Node<>(value, null, this.head);
            head.prev = node;
            this.head = node;
        }
        size++;
        modCount++;
    }

    public void addLast(T value) {
        if (isEmpty())
            addWhenListIsEmpty(value);
        else {
            Node<T> node = new Node<>(value, this.tail, null);
            tail.next = node;
            this.tail = node;
        }
        size++;
        modCount++;
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node<T> cur = head;
        int pos = 0;
        while (pos < index) {
            cur = cur.next;
            pos++;
        }
        T prevValue = cur.value;
        cur.value = element;
        return prevValue;
    }

    private void addWhenListIsEmpty(T value) {
        Node<T> node = new Node<>(value, null, null);
        this.head = node;
        this.tail = node;
    }

    public T removeFirst() {
        if (head == null)
            throw new NoSuchElementException();
        if (head == tail)
            return removeWhenHeadAndTailAreEqual();

        Node<T> oldHead = head;
        this.head = head.next;
        head.prev = null;
        size--;
        modCount++;

        return oldHead.value;
    }

    public T removeLast() {
        if (tail == null)
            throw new NoSuchElementException();
        if (head == tail)
            return removeWhenHeadAndTailAreEqual();

        Node<T> oldTail = tail;
        this.tail = tail.prev;
        tail.next = null;
        size--;
        modCount++;

        return oldTail.value;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        else if (index == 0)
            addFirst(element);
        else if (index == size - 1)
            addLast(element);
        else {
            int pos = 0;
            Node<T> prev = head;
            while (pos < index - 1) {
                prev = prev.next;
                pos++;
            }
            addNodeBetween(element, prev, prev.next);
        }
    }

    public void merge(MyLinkedList<T> another) {
        if (!another.isEmpty()) {
            if (this.isEmpty())
                this.head = another.head;
            else {
                this.tail.next = another.head;
                another.head.prev = this.tail;
            }
            this.tail = another.tail;
            this.size += another.size();
        }
    }

    private T removeWhenHeadAndTailAreEqual() {
        T value = head.value;
        head = tail = null;
        size = 0;
        modCount++;
        return value;
    }

    private void removeNode(Node<T> node) {
        if (node == head)
            removeFirst();
        else if (node == tail)
            removeLast();
        else {
            Node<T> prev = node.prev;
            prev.next = node.next;
            node.next.prev = node.prev;
            size--;
            modCount++;
        }
    }

    private void addNodeBetween(T value, Node<T> left, Node<T> right) {
        if (right == head)
            addFirst(value);
        else if (left == tail)
            addLast(value);
        else {
            if (left == null || right == null)
                throw new NullPointerException("One of the params (left or right) is null");

            Node<T> newNode = new Node<>(value, left, right);
            left.next = newNode;
            right.prev = newNode;

            size++;
            modCount++;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIteratorImpl();
    }

    public ListIterator<T> listIterator() {
        return new ListIteratorImpl();
    }

    private final class ListIteratorImpl implements ListIterator<T> {
        private Node<T> next = head;
        private Node<T> lastReturned;
        private int nextIndex;
        private int expectedModCount = modCount;

        private void checkConcurrencyModification() {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public T next() {
            checkConcurrencyModification();
            if (!hasNext())
                throw new IllegalStateException();

            T value = next.value;
            lastReturned = next;
            next = next.next;
            nextIndex++;

            return value;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public T previous() {
            checkConcurrencyModification();

            if (!hasPrevious())
                throw new IllegalStateException();

            if (next == null)
                next = lastReturned = tail;
            else {
                lastReturned = next.prev;
                next = lastReturned;
            }

            nextIndex--;

            return lastReturned.value;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            checkConcurrencyModification();

            if (lastReturned == null)
                throw new IllegalStateException("No elements have been read yet");

            Node<T> lastReturnedNext = lastReturned.next;

            removeNode(lastReturned);

            // means that a removal is made after "previous" operation
            if (lastReturned == next)
                next = lastReturnedNext;
                // means that a removal is made after "next" operation
            else
                nextIndex--;

            lastReturned = null;
            expectedModCount++;
        }

        @Override
        public void set(T e) {
            checkConcurrencyModification();

            if (lastReturned == null)
                throw new IllegalStateException("No elements have been read yet");

            lastReturned.value = e;
        }

        @Override
        public void add(T e) {
            checkConcurrencyModification();

            // add the first element
            if (!hasPrevious())
                addNodeBetween(e, null, head);
                // add the last element
            else if (!hasNext())
                addNodeBetween(e, tail, null);
            else
                addNodeBetween(e, next.prev, next);

            lastReturned = null;
            expectedModCount++;
            nextIndex++;
        }
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "[]";

        var sb = new StringBuilder("[");
        for (T t : this) {
            sb.append(t.toString())
                    .append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(']');

        return sb.toString();
    }


    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T pollFirst() {
        return removeFirst();
    }

    @Override
    public T pollLast() {
        return removeLast();
    }

    @Override
    public T peekFirst() {
        return getFirst();
    }

    @Override
    public T peekLast() {
        return getLast();
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
    public boolean offer(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return removeFirst();
    }

    @Override
    public T element() {
        T first = getFirst();
        if (first == null)
            throw new NoSuchElementException();
        return first;
    }

    @Override
    public T peek() {
        return getFirst();
    }

    @Override
    public void push(T t) {
        addLast(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public Iterator<T> descendingIterator() {
        throw new UnsupportedOperationException();
    }
}