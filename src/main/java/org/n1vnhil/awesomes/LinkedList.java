package org.n1vnhil.awesomes;

import javax.management.NotificationEmitter;
import javax.xml.stream.events.NotationDeclaration;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {

    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T item) {
        Node<T> node = new Node<>(item, tail, null);
        if(tail != null) {
            tail.next = node;
        } else {
            head = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T item, int index) {
        if(index < 0 || index > size) throw new IndexOutOfBoundsException();
        if(index == size) add(item);
        Node<T> pos = findNode(index);
        Node<T> node = new Node<>(item, pos.pre, pos);
        if(pos.pre == null) head = node;
        else pos.pre.next = node;
        pos.pre = node;
        size++;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNode(index);
        deleteNode(node);
        return node.val;
    }

    @Override
    public boolean remove(T index) {
        Node<T> t = head;
        while(t != null) {
            if(t.val.equals(index)) {
                deleteNode(t);
                return true;
            }
            t = t.next;
        }
        return false;
    }

    @Override
    public T set(int index, T item) {
        Node<T> node = findNode(index);
        T res = node.val;
        node.val = item;
        return res;
    }

    @Override
    public T get(int index) {
        return findNode(index).val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    class LinkedListIterator implements Iterator<T> {

        Node<T> p = head;

        @Override
        public boolean hasNext() {
            return p != null;
        }

        @Override
        public T next() {
            if(p == null) throw new NoSuchElementException();
            T t = p.val;
            p = p.next;
            return t;
        }
    }

    class Node<T> {
        T val;
        Node<T> pre, next;

        public Node(T val) {
            this.val = val;
        }

        public Node(T val, Node<T> pre, Node<T> next) {
            this.val = val;
            this.pre = pre;
            this.next = next;
        }
    }

    private Node<T> findNode(int index) {
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> p;
        if(index < size / 2) {
            p = head;
            for(int i = 0; i < index; i++) {
                p = p.next;
            }
        } else {
            p = tail;
            for(int i = size - 1; i > index; i--) {
                p = p.pre;
            }
        }

        return p;
    }

    private void deleteNode(Node<T> node) {
        Node<T> pre = node.pre;
        Node<T> next = node.next;
        if(pre == null) head = next;
        else pre.next = next;

        if (next == null) tail = pre;
        else next.pre = pre;

        node.pre = null;
        node.next = null;
        size--;
    }
}
