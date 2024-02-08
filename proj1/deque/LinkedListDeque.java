package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {

    private class Node {
        private Node pre;
        private T item;
        private Node next;

        public Node(T i) {
            pre = null;
            item = i;
            next = null;
        }

        public Node() {
            pre = null;
            item = null;
            next = null;
        }
    }
    private Node sentinel;
    private int size;

    /* Creates an empty linked list deque. */
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(T item){
        size++;
        Node n = new Node(item);
        sentinel.next.pre = n;
        n.next = sentinel.next;
        n.pre = sentinel;
        sentinel.next = n;
    }

    public void addLast(T item){
        size++;
        Node n = new Node(item);
        sentinel.pre.next = n;
        n.next = sentinel;
        n.pre = sentinel.pre;
        sentinel.pre = n;
    }
    public boolean isEmpty() {
        return size == 0;
    }


    public void printDeque(){
        Node n = sentinel;
        while(n.next.next != sentinel){
            n = n.next;
            System.out.print(n.item+" ");
        }
        System.out.print(n.next.item);
    }
    public int size(){
        return size;
    }

    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size--;

        return item;
    }

    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T item = sentinel.pre.item;
        sentinel.pre.pre.next = sentinel;
        sentinel.pre = sentinel.pre.pre;
        size--;

        return item;
    }

    public T get(int pos){
        if(pos > size){
            return null;
        }
        if(size == 0) return null;
        Node n = sentinel.next;
        for(int i = 0; i< pos; i++){
            n = n.next;
        }
        return n.item;
    }

    private T recursiveGet(Node n, int index) {
        if (index == 0) {
            return n.next.item;
        }
        return recursiveGet(n.next, index - 1);
    }

    public Iterator<T> iterator(){
        return new LLDIterator();
    }
    private class LLDIterator implements Iterator<T>{

        private Node n;

        public LLDIterator(){
            n = sentinel.next;
        }

        public boolean hasNext() {
            return n.next != sentinel;
        }

        public T next() {
            T returnItem = n.item;
            n = n.next;
            return returnItem;
        }

        public void remove() {

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
        Deque<T> other = (Deque<T>) o;
        if (size() != other.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            T item1 = get(i);
            T item2 = other.get(i);
            if (!item1.equals(item2)) {
                return false;
            }
        }
        return true;
    }
    private static void main(String[] args) {
        int n = 99;

        LinkedListDeque<Integer> lld1 = new LinkedListDeque();
        for (int i = 0; i <= n; i++) {
            lld1.addLast(i);
        }

        LinkedListDeque<Integer> lld2 = new LinkedListDeque();
        for (int i = n; i >= 0; i--) {
            lld2.addFirst(i);
        }

        lld1.printDeque();

        System.out.println(lld1.equals(lld2));
    }


}
