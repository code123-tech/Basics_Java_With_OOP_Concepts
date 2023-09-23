package Serialization;

import java.util.*;

/**
 * Collections Basically Provides an architecture to store some group of objects, and perform some manipulation on them.
 * Java Collection Provides
 * Various Interfaces: List, Set, Queue, Deque.
 * Various Classes: ArrayList, LinkedList, Vector, PriorityQueue, HashSet, LinkedHashSet, TreeSet.
 * JVM Basically Stores reference of other objects into Collection Object, doesn't create physical copy of those stored object as they are already present in the Memory.
 * Interview: https://www.scientecheasy.com/2021/11/java-collections-interview-questions.html/
 * Read It: https://www.javatpoint.com/java-collections-interview-questions
 */
public class CollectionsTut {
    public static void main(String[] args) {
        // A List of Cities,
        // As City is of Type String only.
        List<String> cities = new ArrayList<>();

        /**
         * Why we needed collections
         * Suppose there is no collections, then we will go with following approaches:
         * 1. Variables based Approach:
         *         int x = 10;
         *         Suppose we want 5k values, then need to create 5k variables.
         * 2. Class Based:
         *      class Employee{ int eNo; String eName;}
         *      We can store only fixed number of variables inside classes also.
         * 3. Using array object:
         *      int[] arr = new int[5000];
         *      Here, For above problem we can use array, but still why we need collections?
         *      Some Problem with array:
         *      **> We can store only fixed number of values like: we need to know the number of items going to be stored in Array at static time.
         *      **> We can store Homogenous type of objects only. (Belong to same class Only)
         *      **> There are some readymade methods available in case of collections like insert, search etc.
         *      **> Arrays supports both Primitive and Object type, but Collections support only objects.
         *  Collection supports two type of containers:
         *      a. One for storing collection of elements.
         *      b. Other, for storing key/value pairs, Ex: Map
         */

        /**
         * Difference between Collections and Static Array
         */
        // 1. We can store only fixed number of values like: we need to know the number of items going to be stored in Array at static time.
        int[] arr = new int[5];
        List<Integer> list = new ArrayList<>();
        list.add(1);

        // 2. We can store Homogenous type of objects only in Static Array. (Belong to same class Only)
        long[] arr1 = new long[5];
        // arr1 stores only long type of data,
        // Collections can store homogenous and heterogenous type of objects
        List collections = new ArrayList();
        collections.add(1);
        collections.add("ram");

        // 3. There are some readymade methods available in case of collections like insert, search etc.
        // arr1.index(3);
        list.isEmpty(); // to check if empty or not.

        // 4. Arrays supports both Primitive and Object type, but Collections support only objects.
        Long[] arr2 = new Long[5];

        // List<int> list4 = new ArrayList<>();


        // create list from array
        Integer[] integers = new Integer[]{1, 2, 3, 4, 5};
        List<Integer> integers1 = Arrays.asList(integers);
        for(Integer el: integers1) System.out.print(el + " ");

//        integers1.stream().forEach(el -> System.out.println(el));
//        integers1.addAll(integers1);
//        integers1.removeIf(el -> el%2 == 0);
//        for(Integer el: integers1) System.out.print(el + " ");
        Map<Integer, Integer> counter = new HashMap<>();
        /**
         * Imp: How HashMap Internally Works in Java?
         * Check: https://www.youtube.com/watch?v=2E54GqF0H4s&list=PLDV1Zeh2NRsB6SWUrDFW2RmDotAfPbeHu&index=29&ab_channel=WilliamFiset
         */
    }
}
