package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T>{
    private T[] items;
    private int size;

    private int nextFirst;

    private int nextLast;

    private int capacity;

    private static final int INIT_CAPACITY = 8;

    private static final int RFACTOR = 2;

    private static final double minuse = 0.25;

    public ArrayDeque(){
        items = (T[]) new Object[INIT_CAPACITY];
        nextFirst = 5;
        nextLast = 4;
        capacity = INIT_CAPACITY;

    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    public int pOne(int a){
        return (a + 1 + items.length)% items.length;
    }

    public int mOne(int a){
        return (a - 1 + items.length)% items.length;
    }
    @Override
    public void addFirst(T item) {
        if(size == items.length){
            resize(size * RFACTOR);
        }
        items[nextFirst] = item;
        nextFirst = mOne(nextFirst);
        size++;

    }

    public void resize(int capacity){
        T[] newItems = (T[]) new Object[capacity];

        int curr = pOne(nextFirst);
        for(int i = 0; i< size; i++){
            newItems[i] = items[curr];
            curr = pOne(curr);
        }
        items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    @Override
    public void addLast(T item) {
        if(size == items.length){
            resize(size * RFACTOR);
        }
        items[nextLast] = item;
        nextLast = pOne(nextLast);
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        int first = pOne(nextFirst);
        T firstItem = items[first];
        items[first] = null;
        nextFirst = first;
        size -= 1;

        if (items.length >= 16 && size < items.length * minuse) {
            resize(items.length / 2);
        }

        return firstItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        int last = mOne(nextFirst);
        T lastItem = items[last];
        items[last] = null;
        nextLast = last;
        size -= 1;

        if (items.length >= 16 && size < items.length * minuse) {
            resize(items.length / 2);
        }

        return lastItem;
    }

    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }

        index = (pOne(nextFirst) + index) % items.length;
        return items[index];
    }

    @Override

    public void printDeque() {
        for (T i : this) {
            System.out.print(i + " ");
        }
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        private ArrayDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T item = get(wizPos);
            wizPos += 1;
            return item;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> oa = (Deque<T>) o;
        if (oa.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i += 1) {
            if (!(oa.get(i).equals(this.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public int size(){
        return size;
    }
}
