package com.company;

public class Main {

    public static void main(String[] args) {
        MySet someList = new MyHashSet();

        System.out.println(someList.size());
        System.out.println(someList.isEmpty());

        someList.add("John");
        someList.add("Smith");
        someList.add(33);
        someList.add("hello");
        someList.add(0);
        System.out.println(someList);

        someList.remove(22);
        System.out.println(someList);

        System.out.println(someList.contains("John"));
        System.out.println(someList.contains(33));
        System.out.println(someList.contains("hello"));
        System.out.println(someList.contains(0));

        System.out.println(someList.size());
        System.out.println(someList.isEmpty());

        someList.clear();
        System.out.println(someList);


    }
}
