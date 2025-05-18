package org.n1vnhil.awesomes.utils;

public interface List<T> extends Iterable<T> {

    void add(T item);

    void add(T item, int index);

    T remove(int index);

    boolean remove(T index);

    T set(int index, T item);

    T get(int index);

    int size();

}
