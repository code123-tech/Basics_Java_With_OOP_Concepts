package org.example.SequencedCollections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.SequencedCollection;
import java.util.SequencedSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SequencedCollectionExercise {

    // ── Exercise 8: Generic method — works for ANY SequencedCollection ──────
    private static <E> void printEnds(SequencedCollection<E> col) {
        System.out.println("  First    : " + col.getFirst());
        System.out.println("  Last     : " + col.getLast());
        System.out.println("  Reversed : " + col.reversed());
    }

    public static void main(String[] args) {

        // Exercise 1: SequencedCollection via ArrayList
        sequencedCollectionViaArrayList();

        // Exercise 2: SequencedCollection via ArrayDeque
        sequencedCollectionViaDeque();

        // Exercise 3: SequencedSet via LinkedHashSet
        sequencedSetViaLinkedHashSet();

        // Exercise 4: SequencedSet via TreeSet
        sequencedSetViaTreeSet();

        // Exercise 5: SequencedMap via LinkedHashMap
        sequencedMapViaLinkedHashMap();

        // Exercise 6: SequencedMap via TreeMap
        sequencedMapViaTreeMap();

        // Exercise 7: reversed() is a live view, not a copy
        reversedLiveView();

        // Exercise 8: Generic method using SequencedCollection
        genericMethodUsingSequencedCollection();

        // Exercise 9: Collections.unmodifiableSequencedSet
        unmodifiableSequencedCollection();
    }

    private static void sequencedCollectionViaArrayList() {

        System.out.println("=== Exercise 1: ArrayList ===");
        List<String> fruits = new ArrayList<>(Arrays.asList("Banana", "Cherry", "Date"));

        fruits.addFirst("Apple");
        fruits.addLast("Elderberry");
        System.out.println("After addFirst/addLast : " + fruits);

        System.out.println("getFirst               : " + fruits.getFirst()); // Apple
        System.out.println("getLast                : " + fruits.getLast());  // Elderberry

        fruits.removeFirst();
        fruits.removeLast();
        System.out.println("After removeFirst/Last : " + fruits);

        System.out.print("Reversed iteration     : " + fruits.reversed());
    }

    private static void sequencedCollectionViaDeque() {

        System.out.println("\n=== Exercise 2: ArrayDeque ===");
        Deque<Integer> deque = new ArrayDeque<>(List.of(2, 3, 4));

        deque.addFirst(1);
        deque.addLast(5);
        System.out.println("After addFirst/addLast : " + deque); // [1, 2, 3, 4, 5]
        System.out.println("getFirst               : " + deque.getFirst()); // 1
        System.out.println("getLast                : " + deque.getLast());  // 5

        deque.removeFirst();
        deque.removeLast();
        System.out.println("After removeFirst/Last : " + deque); // [2, 3, 4]
        System.out.println("reversed()             : " + deque.reversed()); // [4, 3, 2]
    }

    private static void sequencedSetViaLinkedHashSet() {

        System.out.println("\n=== Exercise 3: LinkedHashSet ===");
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("B"); set.add("C"); set.add("D");
        System.out.println("Initial                : " + set);

        set.addFirst("A");
        System.out.println("After addFirst(A)      : " + set);

        set.addLast("E");
        System.out.println("After addLast(E)       : " + set);

        // Key behaviour: repositions duplicate instead of ignoring it
        set.addLast("A"); // A already exists — moved to end
        System.out.println("addLast(A) repositions : " + set);

        set.addFirst("D");  // D already exists — moved to front
        System.out.println("addFirst(D) repositions: " + set);

        System.out.println("getFirst               : " + set.getFirst());
        System.out.println("getLast                : " + set.getLast());

        SequencedSet<String> revSet = set.reversed();
        System.out.println("reversed()             : " + revSet);
    }

    private static void sequencedSetViaTreeSet() {

        System.out.println("\n=== Exercise 4: TreeSet (SortedSet) ===");
        TreeSet<Integer> ts = new TreeSet<>(Set.of(10, 50, 30, 70, 20));

        System.out.println("TreeSet                : " + ts);
        System.out.println("getFirst (min)         : " + ts.getFirst());
        System.out.println("getLast  (max)         : " + ts.getLast());

        ts.removeFirst();
        ts.removeLast();
        System.out.println("After removeFirst/Last : " + ts);
        System.out.println("reversed()             : " + ts.reversed());

        try {
            ts.addFirst(5); // not allowed — TreeSet controls its own order
        } catch (UnsupportedOperationException e) {
            System.out.println("addFirst on TreeSet    : UnsupportedOperationException (expected)");
        }

    }

    private static void sequencedMapViaLinkedHashMap() {

        System.out.println("\n=== Exercise 5: LinkedHashMap ===");
        LinkedHashMap<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Alice", 85);
        scores.put("Bob",   90);
        scores.put("Carol", 78);

        System.out.println("firstEntry             : " + scores.firstEntry());
        System.out.println("lastEntry              : " + scores.lastEntry());

        scores.putFirst("Zara", 99);
        System.out.println("After putFirst(Zara)   : " + scores);

        scores.putLast("Dan", 70);
        System.out.println("After putLast(Dan)     : " + scores);

        // Repositioning: Carol already exists — moved to front with new value
        scores.putFirst("Carol", 100);
        System.out.println("putFirst(Carol) repos  : " + scores);

        Map.Entry<String, Integer> polled = scores.pollFirstEntry();
        System.out.println("pollFirstEntry         : " + polled.getKey() + "=" + polled.getValue());
        System.out.println("After poll             : " + scores);

        SequencedSet<String> keys = scores.sequencedKeySet();
        SequencedCollection<Integer> vals = scores.sequencedValues();
        System.out.println("sequencedKeySet        : " + keys);
        System.out.println("sequencedValues rev    : " + vals.reversed());

        Map.Entry<String, Integer> snapshot = scores.firstEntry();
        try {
            snapshot.setValue(0);
        } catch (UnsupportedOperationException e) {
            System.out.println("firstEntry.setValue    : UnsupportedOperationException (snapshot)");
        }

    }

    private static void sequencedMapViaTreeMap() {

        System.out.println("\n=== Exercise 6: TreeMap (SortedMap) ===");
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("banana", 2);
        treeMap.put("apple",  1);
        treeMap.put("cherry", 3);
        System.out.println("TreeMap                : " + treeMap);
        System.out.println("firstEntry (alpha min) : " + treeMap.firstEntry());
        System.out.println("lastEntry  (alpha max) : " + treeMap.lastEntry());
        System.out.println("reversed()             : " + treeMap.reversed());

        try {
            treeMap.putFirst("zz", 99);
        } catch (UnsupportedOperationException e) {
            System.out.println("putFirst on TreeMap    : UnsupportedOperationException (expected)");
        }
    }

    private static void reversedLiveView() {

        System.out.println("\n=== Exercise 7: reversed() is a live view ===");
        List<String> original = new ArrayList<>(List.of("A", "B", "C"));
        System.out.println("original               : " + original);

        List<String> view = original.reversed();
        System.out.println("view                   : " + view);

        original.add("D");
        System.out.println("After original.add(D)  : " + view);

        view.addFirst("Z");  // addFirst on reversed = addLast on original
        System.out.println("After view.addFirst(Z) : " + original);

        // double reversed ≈ original
        System.out.println("view.reversed()        : " + view.reversed());
    }

    private static void genericMethodUsingSequencedCollection() {

        System.out.println("\n=== Exercise 8: Generic printEnds() ===");
        System.out.println("ArrayList:");
        printEnds(new ArrayList<>(List.of(1, 2, 3)));

        System.out.println("ArrayDeque:");
        printEnds(new ArrayDeque<>(List.of("x", "y", "z")));

        System.out.println("LinkedHashSet:");
        printEnds(new LinkedHashSet<>(List.of("P", "Q", "R")));

        System.out.println("TreeSet:");
        printEnds(new TreeSet<>(Set.of(40, 10, 30, 20)));
    }

    private static void unmodifiableSequencedCollection() {

        System.out.println("\n=== Exercise 9: Unmodifiable Sequenced Collections ===");
        LinkedHashSet<String> lhs = new LinkedHashSet<>(List.of("X", "Y", "Z"));

        // Old way — returns plain Set, loses sequencing info
        Set<String> oldUnmod = Collections.unmodifiableSet(lhs);
        // oldUnmod.getFirst() — compile error! It's just a Set

        // New way — preserves SequencedSet type
        SequencedSet<String> newUnmod = Collections.unmodifiableSequencedSet(lhs);
        printEnds(newUnmod);

        try {
            newUnmod.addFirst("W");
        } catch (UnsupportedOperationException e) {
            System.out.println("addFirst on unmod      : UnsupportedOperationException (expected)");
        }
    }

}
