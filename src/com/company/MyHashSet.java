package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyHashSet<E> implements MySet<E> {
    private static int DEFAULT_INITIAL_CAPACITY = 18;
    private static int MAX_CAPACITY = 1 << 40;
    private int capacity;
    private static float DEFAULT_MAX_LOAD_FACTOR = 0.85f;
    private float loadFactorThreshold;
    private int size = 0;
    private LinkedList[] table;

    MyHashSet() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    private MyHashSet(int initialCapacity, float loadFactor) {
        if (initialCapacity > MAX_CAPACITY)
            this.capacity = MAX_CAPACITY;
        else
            this.capacity = trimToPower(initialCapacity);
        this.loadFactorThreshold = loadFactor;
        table = new LinkedList[capacity];
    }

    private int trimToPower(int initialCapacity) {
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;
        return capacity;
    }

    @Override
    public void clear() {
        size = 0;
        removeElements();
    }

    private void removeElements() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null)
                table[i].clear();
        }
    }

    @Override
    public boolean contains(E e) {
        int bucketIndex = hash(e.hashCode());
        if (table[bucketIndex] != null) {
            LinkedList bucket = table[bucketIndex];
            for (Object element : bucket)
                if (element.equals(e))
                    return true;
        }
        return false;
    }

    private int hash(int hashCode) {
        return supplementalHash(hashCode) & (capacity - 1);
    }

    private int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    @Override
    public boolean add(E e) {
        if (contains(e))
            return false;
        if (size + 1 > capacity * loadFactorThreshold) {
            if (capacity == MAX_CAPACITY)
                throw new RuntimeException("Exceeding maximum capacity");
            rehash();
        }
        int bucketIndex = hash(e.hashCode());
        if (table[bucketIndex] == null)
            table[bucketIndex] = new LinkedList<E>();
        table[bucketIndex].add(e);
        size++;
        return true;
    }

    private void rehash() {
        ArrayList<E> list = setToList();
        capacity <<= 1;
        table = new LinkedList[capacity];
        size = 0;

        for (E element : list) {
            add(element);
        }
    }

    private ArrayList<E> setToList() {
        ArrayList<E> list = new ArrayList<E>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null)
                for (Object e : table[i])
                    list.add((E) e);
        }
        return list;
    }

    @Override
    public boolean remove(E e) {
        if (!contains(e))
            return false;
        int bucketIndex = hash(e.hashCode());

        if (table[bucketIndex] != null) {
            LinkedList bucket = table[bucketIndex];
            for (Object element : bucket)
                if (e.equals(element)) {
                    bucket.remove(element);
                    break;
                }
        }
        size--;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        ArrayList<E> list = setToList();

        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    @Override
    public String toString() {
        ArrayList<E> list = setToList();
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < list.size() - 1; i++)
            builder.append(list.get(i)).append(", ");
        if (list.size() == 0)
            builder.append("]");
        else
            builder.append(list.get(list.size() - 1)).append("]");
        return builder.toString();
    }
}
