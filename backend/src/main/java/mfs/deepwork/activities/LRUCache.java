package mfs.deepwork.activities;

import java.util.HashMap;
import java.util.Map;

public class LRUCache <K,E> {

    private class Node<E> {
        public E payload;
        public Node<E> next;
        public Node<E> prev;

        public Node(E elem){
            payload = elem;
        }

        public void addLeft(Node other){
            Node<E> oldPrev = prev;
            prev = other;
            other.next = this;
            other.prev = oldPrev;
        }

        public void addRight(Node other){
            Node<E> oldNext = next;
            next = other;
            other.prev = this;
            other.next = oldNext;
        }

        public void remove(){

            if(this.next != null){
                this.next.prev = this.prev;
            }

            if(this.prev != null){
                this.prev.next = this.next;
            }
        }
    }

    private int capacity;
    private Map<K, Node> store;
    private Node<E> head;
    private Node<E> tail;

    public LRUCache(int capacity){
        this.capacity = capacity;
        store = new HashMap<>(capacity);
    }

    public Node<E> getNode(K key){
        Node<E> node = store.get(key);
        if(node != null){
            node.remove();

            if(node == tail){
                tail = node.prev;
            }

            tail.addRight(node);
            tail = node;
        }
        return node;
    }

    private void evacuate(){
        if(head != null){
            Node<E> next = head.next;
            head.remove();
            head = next;
            if(head == null){
                tail = null;
            }
        }
    }

    public int getSize(){
        return store.size();
    }

    public int getCapacity(){
        return capacity;
    }

    public synchronized E get(K key) {
        Node<E> item = getNode(key);
        if(item != null){
            return item.payload;
        }
        return null;
    }

    public synchronized void put(K key, E elem){
        if(store.containsKey(key)){
            //update existing
            Node<E> item = getNode(key);
            item.payload = elem;
        } else {
            if(store.size() == capacity){
                evacuate();
            }

            Node<E> node = new Node<>(elem);
            if(head == null) {
                head = node;
                tail = node;
            } else {
                tail.addRight(node);
                tail = node;
            }
        }
    }
}
