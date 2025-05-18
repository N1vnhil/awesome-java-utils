package org.n1vnhil.awesomes.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<T> implements List<T> {

    private static final int initSize = 16;

    Object[] list = new Object[initSize];

    private int size = 0;

    @Override
    public void add(T item) {
        resizeIfNecessary();
        list[size++] = item;
    }

    @Override
    public void add(T item, int index) {
        resizeIfNecessary();
        if(index < 0 || index > size) throw new IndexOutOfBoundsException();
        System.arraycopy(list, index, list, index + 1, size - index);
        list[index] = item;
        size++;
    }

    @Override
    public T remove(int index) {
        checkBoundary(index);
        T res = (T) list[index];
        System.arraycopy(list, index - 1, list, index, size - index - 1);
        list[--size] = null;
        return res;
    }

    @Override
    public boolean remove(T item) {
        for(int i = 0; i < size; i++) {
            if(Objects.equals(item, list[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public T set(int index, T item) {
        T t = get(index);
        list[index] = item;
        return t;
    }

    @Override
    public T get(int index) {
        checkBoundary(index);
        return (T) list[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    class ArrayListIterator implements Iterator<T> {

        int p;

        @Override
        public boolean hasNext() {
            return p != size;
        }

        @Override
        public T next() {
            if(p >= size) throw new NoSuchElementException();
            return (T) list[p++];
        }
    }

    private void resizeIfNecessary() {
        if(size < list.length) return;
        Object[] newList = new Object[list.length * 2];
        System.arraycopy(list, 0, newList, 0, list.length);
        list = newList;
    }

    private void checkBoundary(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
