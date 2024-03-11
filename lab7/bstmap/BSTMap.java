package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private Node root;

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private class Node{
        private K key;
        private V value;
        private Node l, r;
        private int size;

        public Node(K key, V value, int size){
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public void clear(){
        root = null;
    }

    public int size(){
        return size(root);
    }

    private int size(Node n){
        if(n == null) return 0;
        else return n.size;
    }

    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(Node node, K key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return containsKey(node.l, key);
        } else if (cmp > 0) {
            return containsKey(node.r, key);
        }
        return true;
    }

    public V get(K key){
        return get(root, key);
    }

    private V get(Node x, K key){
        if(key == null) return null;
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp > 0){
            return get(x.r, key);
        }else if(cmp <0){
            return get(x.l, key);
        }
        return x.value;

    }

    public void put(K key, V value) {
        if (value == null) {
            return;
        }
        root = put(root, key, value);
    }
    private Node put(Node x, K key, V value){
        if(x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if(cmp > 0){
            put(x.r, key, value);
        }else if(cmp <0){
            put(x.l, key, value);
        }else{
            x.size = 1 + size(x.l)+size(x.r);
        }
        return x;
    }

    public Set<K> keySet(){
        throw new UnsupportedOperationException("argument to contains() is null");
    }

    public V remove(K key){
        throw new UnsupportedOperationException("argument to contains() is null");
    }

    public V remove(K key, V value){
        throw new UnsupportedOperationException("argument to contains() is null");
    }
    public void printInOrder(){
        printInOrder(root);
    }
    private void printInOrder(Node node) {
        if (node == null) {
            return;
        }
        printInOrder(node.l);
        System.out.println(node.key.toString() + " -> " + node.value.toString());
        printInOrder(node.r);
    }
}
