# Sequenced Collections (JEP 431) — Finalized in Java 21

## The Problem: What Was Missing Before Java 21

Java had many ordered collections but **no unified API** to access their first/last elements. Every type had its own inconsistent idiom:

| Collection | Get First | Get Last |
|---|---|---|
| `List` | `list.get(0)` | `list.get(list.size() - 1)` |
| `Deque` | `deque.getFirst()` | `deque.getLast()` |
| `SortedSet` | `sortedSet.first()` | `sortedSet.last()` |
| `LinkedHashSet` | `set.iterator().next()` | No direct way — iterate entire set! |
| `LinkedHashMap` | `map.entrySet().iterator().next()` | No direct way — iterate entire map! |

Also: no common `reversed()` method across all ordered types, and no shared supertype to write generic "ordered collection" methods against.

---

## 1. The Three New Interfaces

JEP 431 introduces three interfaces and retrofits them into the existing hierarchy via **default methods** — zero breaking changes.

### Interface Hierarchy

```
Collection
    └── SequencedCollection<E>       (NEW)
            ├── List<E>              (retrofitted)
            ├── Deque<E>             (retrofitted)
            └── SequencedSet<E>      (NEW, also extends Set<E>)
                    ├── SortedSet<E> (retrofitted)
                    └── LinkedHashSet (implements directly)

Map
    └── SequencedMap<K,V>            (NEW)
            ├── SortedMap<K,V>       (retrofitted)
            └── LinkedHashMap        (implements directly)
```

---

## 2. `SequencedCollection<E>` — New Methods

```java
SequencedCollection<E> reversed()     // abstract — returns a live reverse-order VIEW
void addFirst(E e)                     // add at beginning
void addLast(E e)                      // add at end
E    getFirst()                        // read first element (NoSuchElementException if empty)
E    getLast()                         // read last element  (NoSuchElementException if empty)
E    removeFirst()                     // remove & return first
E    removeLast()                      // remove & return last
```

### Example

```java
List<String> fruits = new ArrayList<>(Arrays.asList("Banana", "Cherry", "Date"));

fruits.addFirst("Apple");
fruits.addLast("Elderberry");
System.out.println(fruits);  // [Apple, Banana, Cherry, Date, Elderberry]

System.out.println(fruits.getFirst()); // Apple
System.out.println(fruits.getLast());  // Elderberry

fruits.removeFirst(); // removes Apple
fruits.removeLast();  // removes Elderberry
System.out.println(fruits); // [Banana, Cherry, Date]

// Iterate in reverse — no copy needed!
for (String f : fruits.reversed()) {
    System.out.print(f + " "); // Date Cherry Banana
}
```

### Before vs After

```java
// BEFORE Java 21
String first = list.get(0);
String last  = list.get(list.size() - 1);

// AFTER Java 21
String first = list.getFirst();
String last  = list.getLast();
```

---

## 3. `SequencedSet<E>` — Key Behavioral Difference

Inherits all `SequencedCollection` methods. The critical difference: **`addFirst()`/`addLast()` reposition duplicate elements** (instead of ignoring them like regular `Set.add()`).

```java
LinkedHashSet<String> set = new LinkedHashSet<>();
set.add("A"); set.add("B"); set.add("C");
System.out.println(set); // [A, B, C]

// Regular add — duplicate ignored, order unchanged
set.add("A");
System.out.println(set); // [A, B, C]

// addLast with duplicate — REPOSITIONS to end
set.addLast("A");
System.out.println(set); // [B, C, A]  ← A moved to end

// addFirst with duplicate — REPOSITIONS to front
set.addFirst("C");
System.out.println(set); // [C, B, A]  ← C moved to front

SequencedSet<String> rev = set.reversed();
System.out.println(rev); // [A, B, C]
```

**SortedSet (TreeSet):** `addFirst`/`addLast` throw `UnsupportedOperationException` — sort order controls position. But `getFirst`, `getLast`, `removeFirst`, `removeLast` all work fine.

```java
TreeSet<Integer> ts = new TreeSet<>(Set.of(10, 30, 20, 50));
System.out.println(ts.getFirst()); // 10 (minimum)
System.out.println(ts.getLast());  // 50 (maximum)
ts.removeFirst();                   // removes 10
ts.addFirst(5);                     // UnsupportedOperationException!
```

---

## 4. `SequencedMap<K,V>` — New Methods

```java
SequencedMap<K,V>               reversed()            // live reverse-order view
Map.Entry<K,V>                  firstEntry()          // null if empty (no exception!)
Map.Entry<K,V>                  lastEntry()           // null if empty
Map.Entry<K,V>                  pollFirstEntry()      // remove & return first (null if empty)
Map.Entry<K,V>                  pollLastEntry()       // remove & return last  (null if empty)
V                               putFirst(K k, V v)    // insert at beginning (repositions if exists)
V                               putLast(K k, V v)     // insert at end       (repositions if exists)
SequencedSet<K>                 sequencedKeySet()     // keys as SequencedSet
SequencedCollection<V>          sequencedValues()     // values as SequencedCollection
SequencedSet<Map.Entry<K,V>>    sequencedEntrySet()   // entries as SequencedSet
```

### Example

```java
LinkedHashMap<String, Integer> scores = new LinkedHashMap<>();
scores.put("Alice", 85);
scores.put("Bob",   90);
scores.put("Carol", 78);

System.out.println(scores.firstEntry()); // Alice=85
System.out.println(scores.lastEntry());  // Carol=78

scores.putFirst("Zara", 99);
System.out.println(scores); // {Zara=99, Alice=85, Bob=90, Carol=78}

scores.putLast("Dan", 70);
System.out.println(scores); // {Zara=99, Alice=85, Bob=90, Carol=78, Dan=70}

// pollFirstEntry — removes and returns
Map.Entry<String, Integer> first = scores.pollFirstEntry();
System.out.println(first.getKey()); // Zara
System.out.println(scores);         // {Alice=85, Bob=90, Carol=78, Dan=70}

// Sequenced views — all support reversed()
SequencedSet<String>       keys = scores.sequencedKeySet();
SequencedCollection<Integer> vals = scores.sequencedValues();
System.out.println(keys);            // [Alice, Bob, Carol, Dan]
System.out.println(vals.reversed()); // [70, 78, 90, 85]
```

**Important:** `firstEntry()` / `lastEntry()` return **snapshot** entries — `setValue()` throws `UnsupportedOperationException`. Use a live `entrySet().iterator()` entry if you need to mutate.

**SortedMap (TreeMap):** `putFirst`/`putLast` throw `UnsupportedOperationException`. Read/poll methods work fine.

---

## 5. `reversed()` — Live View, Not a Copy

`reversed()` returns a **live, write-through view**:
- No data copied in memory (lazy)
- Changes to original reflected immediately in view, and vice versa
- Type-preserving: `List.reversed()` → `List`, `SequencedSet.reversed()` → `SequencedSet`, etc.
- `reversed().reversed()` ≈ original

```java
List<String> original = new ArrayList<>(List.of("A", "B", "C"));
List<String> view = original.reversed();

original.add("D");
System.out.println(view); // [D, C, B, A] — live!

view.addFirst("Z");       // addFirst on reversed = addLast on original
System.out.println(original); // [A, B, C, D, Z]

// Before Java 21 — had to COPY and MUTATE
List<String> copy = new ArrayList<>(original);
Collections.reverse(copy); // destructive, original untouched but now you have a copy
```

---

## 6. Which Classes Implement These Interfaces

### `SequencedCollection`
| Class | Notes |
|---|---|
| `ArrayList` | `addFirst`/`addLast` shift elements |
| `LinkedList` | Natural O(1) end operations |
| `ArrayDeque` | O(1) at both ends |

### `SequencedSet`
| Class | Notes |
|---|---|
| `LinkedHashSet` | `addFirst`/`addLast` reposition duplicates |
| `TreeSet` | `addFirst`/`addLast` → `UnsupportedOperationException` |

### `SequencedMap`
| Class | Notes |
|---|---|
| `LinkedHashMap` | `putFirst`/`putLast` reposition duplicates |
| `TreeMap` | `putFirst`/`putLast` → `UnsupportedOperationException` |

---

## 7. Generic Code — The Real Win

Write one method that works for `ArrayList`, `ArrayDeque`, `LinkedHashSet`, `TreeSet`:

```java
// BEFORE — impossible to unify, needed overloads
void printEnds(List<E> list) { ... }
void printEnds(Deque<E> deque) { ... }
// LinkedHashSet had no common interface for this at all

// AFTER — one method for all
public static <E> void printEnds(SequencedCollection<E> col) {
    System.out.println("First: " + col.getFirst());
    System.out.println("Last:  " + col.getLast());
    System.out.println("Reversed: " + col.reversed());
}
```

---

## 8. `Collections` Utility Updates

Three new unmodifiable factory methods — preserve sequenced type (old `Collections.unmodifiableSet()` returned a plain `Set`, losing ordering info):

```java
Collections.unmodifiableSequencedCollection(SequencedCollection<T> c)
Collections.unmodifiableSequencedSet(SequencedSet<T> s)
Collections.unmodifiableSequencedMap(SequencedMap<K,V> m)
```

---

## 9. Edge Cases Summary

| Scenario | `SequencedCollection` / `SequencedSet` | `SequencedMap` |
|---|---|---|
| Empty — read | `NoSuchElementException` | Returns `null` (no exception) |
| Empty — poll | `NoSuchElementException` | Returns `null` (no exception) |
| Unmodifiable — mutate | `UnsupportedOperationException` | `UnsupportedOperationException` |
| SortedSet/SortedMap — `addFirst`/`putFirst` | `UnsupportedOperationException` | `UnsupportedOperationException` |
| `firstEntry().setValue()` | N/A | `UnsupportedOperationException` (snapshot) |

---

## Exercise

See `SequencedCollectionsExercise.java` in this folder.

The exercise covers:
1. `SequencedCollection` via `ArrayList` — all new methods
2. `SequencedCollection` via `ArrayDeque`
3. `SequencedSet` via `LinkedHashSet` — repositioning behaviour
4. `SequencedSet` via `TreeSet` — sorted constraints
5. `SequencedMap` via `LinkedHashMap` — all new methods
6. `SequencedMap` via `TreeMap` — sorted constraints
7. `reversed()` as a live view
8. Generic method using `SequencedCollection`
9. `Collections.unmodifiableSequencedSet`
