package sample;

import java.util.*;

public class MyMap<K, V> implements Map<K, V> {

    private final int TABLE_SIZE = 16;

    private class Node<K, V> implements Map.Entry<K,V>{
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    private Node<K, V>[] table;

    public MyMap(){
        table = (Node<K,V>[])new Node[TABLE_SIZE];
    }

    private int size = 0;

    private int hash(Object key) {
        return key == null ? 0 : key.hashCode() & (TABLE_SIZE-1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            Node<K, V> node = table[i];
            while (node != null) {
                if (node.key.equals(key)) return true;
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            Node<K, V> node = table[i];
            while (node != null) {
                if (node.value == value) return true;
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public V get(Object key){

        int hash = hash(key);
        Node<K, V> node = table[hash];

       while (node != null){
           if(node.key.equals(key)){
               return node.getValue();
           }
           node = node.next;
       }
       return null;
    }

    @Override
    public V put(K key, V value) {
        Node<K, V> p; int i;
        if ((p = table[i = hash(key)])!=null) {
            Node<K, V> c = null; K k;
            if ((k = p.key) == key || (key!=null && key.equals(k))) {
                V oldVal = p.value;
                p.value = value;
                return oldVal;
            } else if (p.next!=null) {
                c = p;
                do {
                    c = c.next;
                    if ((k = c.key) == key || (key!=null && key.equals(k))) {
                        V oldVal = c.value;
                        c.value = value;
                        return oldVal;
                    }
                } while (c.next!=null);
            }
            if (c!=null) {
                c.next = new Node<>(key, value);
                size++;
            } else {
                p.next = new Node<>(key, value);
                size++;
            }

        } else {
            table[i] = new Node<>(key, value);
            size++;
        }
        return null;
    }

    @Override
    public V remove(Object key){
        int hash = hash(key);
        Node<K, V> node = table[hash];

        if(node == null){
            return null;
        }

        if(node.next == null && node.key.equals(key)){

            table[hash] = null;
            size--;
            return node.value;
        }

        Node<K, V> prevNode = node;
        node = node.next;

        while(node != null){
            if(node.key == key){
                prevNode.next = node.next;
                node.next = null;
                size--;
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Entry<? extends K, ? extends V>entry : m.entrySet()){
            K key = entry.getKey();
            V value = entry.getValue();
            put(key,value);
        }
    }

    @Override
    public void clear() {
        for(int i = 0; i < TABLE_SIZE; i++){
            table[i] = null;
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set  = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            while(node != null){
                set.add(node.key);
                node = node.next;
            }
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            while(node != null){
                values.add(node.value);
                node = node.next;
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> entries = new HashSet<>();

        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            while(node != null){
                entries.add(node);
                node = node.next;
            }
        }
        return entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyMap<?, ?> myMap = (MyMap<?, ?>) o;
        return Arrays.equals(table, myMap.table);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(table);
        return result;
    }
}
