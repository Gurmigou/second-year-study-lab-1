package DataStructure;

import java.util.AbstractQueue;
import java.util.Iterator;

/**
 * Implementation of queue using an array
 * @param <E> generic parameter of an element type of this collection
 */
public class MyQueueArray<E> extends AbstractQueue<E> {
    private E[] queue;
    private static final int MIN_QUEUE_LENGTH = 16;

    private int front;
    private int end;

    @SuppressWarnings("unchecked")
    public MyQueueArray() {
        this.queue = (E[]) new Object[MIN_QUEUE_LENGTH];
    }

    @SuppressWarnings("unchecked")
    public MyQueueArray(int queueLength) {
        if (queueLength <= 0)
            throw new IllegalArgumentException("stackLength must be a positive number");

        this.queue = (E[]) new Object[queueLength];
    }

    private void leftShiftIntoArray(E[] arr) {
        int left = 0;
        int right = front;

        while (right < queue.length)
            arr[left++] = queue[right++];

        int size = size();
        front = 0;
        end = size;
    }

    @SafeVarargs
    protected final void rightShiftAndPutAtBegin(int byElements, E... arr) {
        if (size() + byElements > queue.length)
            grow((int)(size() + byElements * 1.5));
        int right = end + byElements - 1;
        int left = end - 1;

        int size = size();
        for (int i = 0; i < size; i++)
            queue[right--] = queue[left--];

        if (byElements >= 0) System.arraycopy(arr, 0, queue, 0, byElements);
    }

    private void grow(int newLength) {
        if (newLength <= MIN_QUEUE_LENGTH)
            throw new IllegalArgumentException("Illegal \"newLength\" value");

        @SuppressWarnings("unchecked")
        E[] newQueue = (E[]) new Object[newLength];

        leftShiftIntoArray(newQueue);
        this.queue = newQueue;
    }

    protected void ensureCapacity() {
        if (end == queue.length) {
            // if the queue is half empty
            if (front > queue.length / 2)
                leftShiftIntoArray(queue);
            else  {
                // grow depending on queue size
                if (queue.length <= MIN_QUEUE_LENGTH)
                    grow(queue.length * (2 + (int) Math.ceil((double)MIN_QUEUE_LENGTH / queue.length)));
                else
                    grow(queue.length * 2);
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(E e) {
        ensureCapacity();
        queue[end++] = e;
        return true;
    }

    @Override
    public E poll() {
        return (front < queue.length) ? queue[front++] : null;
    }

    @Override
    public E peek() {
        return queue[front];
    }

    @Override
    public int size() {
        return end - front;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    protected E[] queue() {
        return queue;
    }

    protected int front() {
        return front;
    }

    protected int end() {
        return end;
    }

    protected void setEnd(int end) {
        this.end = end;
    }

    protected void setFront(int front) {
        this.front = front;
    }
}
