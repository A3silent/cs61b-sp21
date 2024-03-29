package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private static final int DEFAULT_SIZE = 16;
    private int initilizeSize;
    private Set<K> keys;
    private static final double MAX_REFACTOR_SIZE = 0.75;
    private double localrefactor;
    private int size;
    private int bucketSize;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_SIZE, MAX_REFACTOR_SIZE);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, MAX_REFACTOR_SIZE);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initilizeSize = initialSize;
        this.localrefactor = maxLoad;
        this.size = 0;
        this.bucketSize = initialSize;
        buckets = createTable(bucketSize);
        keys = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for(int i = 0; i< tableSize; i++){
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        for(int i = 0; i< bucketSize; i++){
            buckets[i].clear();
        }
        keys.clear();
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    private Node getNode(K key){
        if(key == null){
            throw new IllegalArgumentException("Key can't be null");
        }
        int h = hash(key);
        for(Node node : buckets[h]){
            if(node.key.equals(key)){
                return node;
            }
        }
        return null;
    }

    @Override
    public V get(K key) {
        Node n = getNode(key);
        if(n == null) return null;
        else return n.value;
    }

    @Override
    public int size() {
        return size;
    }

    private int hash(K key) {
        int h = key.hashCode();
        return Math.floorMod(h, bucketSize);
    }

    private void resize(int newBucketSize){
        MyHashMap<K, V> newMyHashMap = new MyHashMap<>(newBucketSize, this.localrefactor);
        for (K key : keySet()) {
            newMyHashMap.put(key, get(key));
        }
        this.bucketSize = newMyHashMap.bucketSize;
        this.buckets = newMyHashMap.buckets;
    }
    @Override
    public void put(K key, V value) {
            if(key == null){
                throw new IllegalArgumentException("Key can't be null");
            }
            Node n = getNode(key);
            if( n == null){
                int h = hash(key);
                Node newNode = createNode(key, value);
                buckets[h].add(newNode);
                size++;
                keys.add(key);
                if (size * 1.0 / bucketSize >= localrefactor) {
                    resize(bucketSize * 2);
                }
            }else{
                n.value = value;
            }
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        return remove(key, get(key));
    }

    @Override
    public V remove(K key, V value) {
        if(key == null){
            throw new IllegalArgumentException("the key can't be null");
        }
        int h = hash(key);
        Node n = getNode(key);
        if(n == null || !n.value.equals(value)){
            return null;
        }
        buckets[h].remove(n);
        size --;
        keys.remove(key);
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
