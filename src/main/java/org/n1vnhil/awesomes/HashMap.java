package org.n1vnhil.awesomes;


import javax.swing.*;
import java.util.Objects;

public class HashMap<K, V> {

    private static final int initSize = 16;

    int size = 0;

    Node<K, V>[] table = new Node[initSize];

    private int indexOf(Object key) {
        return key.hashCode() & (table.length - 1);
    }


    public V put(K key, V value) {
        int index = indexOf(key);
        Node<K, V> node = table[index];
        if(Objects.isNull(node)) {
            table[index] = new Node<>(key,  value);
            size++;
            resizeIfNecessary();
            return null;
        }

        while(true) {
            if(node.key.equals(key)) {
                V t = node.value;
                node.value = value;
                return t;
            }

            if(node.next == null) {
                node.next = new Node<>(key, value);
                size++;
                resizeIfNecessary();
                return null;
            }

            node = node.next;
        }

    }

    public V get(K key) {
        int index = indexOf(key);
        Node<K, V> node = table[index];

        while(node != null) {
            if(node.key.equals(key)) return node.value;
            node = node.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = indexOf(key);
        Node<K, V> node = table[index];
        V res = null;
        if(node == null) return null;

        if(node.key.equals(key)) {
            res = node.value;
            table[index] = null;
            size--;
            return res;
        }

        while(node.next != null) {
            if(node.next.key.equals(key)) {
                res = node.next.value;
                node.next = node.next.next;
                size--;
                return res;
            }
            node = node.next;
        }

        return res;
    }

    public int size() {
        return size;
    }

    private void resizeIfNecessary() {
        if(size < 0.75 * table.length) {
            return;
        }

        Node<K, V>[] table = new Node[this.table.length * 2];
        for(Node<K, V> node: this.table) {
            if(node == null) continue;
            Node<K, V> current = node;
            while(current != null) {
                int newIndex = current.key.hashCode() & (table.length - 1);
                Node<K, V> next = current.next;

                if(table[newIndex] == null) {
                    table[newIndex] = current;
                    current.next = null;
                    current = next;
                } else {
                    current.next = table[newIndex];
                    current = next;
                }
            }
        }

        this.table = table;
        System.out.println("扩容完成，当前大小：" + table.length);
    }

    class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        public Node (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
