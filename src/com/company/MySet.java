package com.company;

public interface MySet<E> {

    boolean add(E e);

    void clear();

    boolean contains(E e);

    boolean isEmpty();

    boolean remove(E e);

    int size();

    Object[] toArray();
}
