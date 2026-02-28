package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Records are Java’s shortcut for making small “data-only” classes.
 * <p>
 * Instead of writing fields + constructor + getters + equals/hashCode + toString,
 * you write one line: {@code record Task(int id, String name, double cost) {}}
 * <p>
 * Syntax structure:
 * <pre>
 * record RecordName(Type1 field1, Type2 field2, ...) {
 *     // optional body: validation, derived methods, static members
 * }
 * </pre>
 * The part inside parentheses is the record header (also called "record components").
 * <p>
 * In this file you’ll practice:
 * - record basics (components + accessors)
 * - equality by values (equals/hashCode)
 * - validation using a compact constructor
 * - the shallow immutability pitfall (mutable fields like List)
 *
 * All variables of record are private, and final (means can't be modified)
 * But as seen due shall immutability, it is modified
 *
 * record class are final (can't be inherited)
 *
 * record can  implement interfaces
 *
 * we can define static variables inside record
 * 
 * Records can be generic, like class are generic
 * <pre>
 * record RecordName<T>(T field1, T field2, ...) {
 *     // optional body: validation, derived methods, static members
 * }
 * </pre>
 * 
 * Records can be nested, like class are nested
 * <pre>
 * record RecordName {
 *     record NestedRecordName(Type1 field1, Type2 field2, ...) {
 *         // optional body: validation, derived methods, static members
 *     }
 * }
 * </pre>
 * 
 */


public class UnderstandingRecords {

    public static void doExercise() {
        System.out.println("Records");

        System.out.println("1. Creating a basic record");
        Task task1 = new Task(1, "test", 12.90);
        System.out.println("Task 1 record information: " + task1);


        System.out.println();
        System.out.println("2. Compare with another record with same content");

        Task task2 = new Task(1, "test", 12.90);
        System.out.println("Task 2 record information: " + task2);

        System.out.println("Compare using equals, result is: " + task1.equals(task2));
        System.out.println("Compare using == operator, result is: " + (task1 == task2));

        System.out.println();
        System.out.println("3. Validation inside Record");
        try{
            new Task(3, "Task3", -1.0);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println();
        System.out.println("4. Test Shallow Immutability in record");
        List<String> items = new ArrayList<>();
        items.add("A");
        BucketShallow bucket = new BucketShallow(items);
        System.out.println("Before modifying original items, shallow bucket items are: " + bucket.items());
        items.add("B");
        System.out.println("After modifying original items, shallow bucket items are: " + bucket.items());

        BucketFixed bucket1 = new BucketFixed(items);
        System.out.println("Before modifying original items, fixed shallow bucket items are: " + bucket1.items());
        items.add("C");
        System.out.println("After modifying original items, fixed shallow bucket items are: " + bucket1.items());
        try {
            bucket1.items().add("X");
        } catch (UnsupportedOperationException ex) {
            System.out.println("Cannot mutate bucket1.items(): " + ex);
        }
    }


    record Task(int id, String name, double price) {

        Task {
            if(price < 0) {
                throw new IllegalArgumentException("price must be >= 0");
            }
        }

    }

    record BucketShallow(List<String> items) {
    }

    record BucketFixed(List<String> items) {

        BucketFixed {
            items = List.copyOf(items);
        }

    }

}

/*
Interview Questions on Java Records (with answer hints)

1) What problem do records solve?
   - Reduce boilerplate for data-carrier classes; compiler generates constructor/accessors/equals/hashCode/toString.

2) Are records immutable? Explain “shallow immutability”.
   - Components are final (no reassignment), but mutable component objects (e.g., List) can still change unless defensively copied.

3) How do you add validation in a record?
   - Use a compact constructor or canonical constructor and throw exceptions for invalid state.

4) What methods are auto-generated in a record?
   - Canonical constructor, component accessors (name()), equals, hashCode, toString.

5) Difference between a POJO and a record?
   - Record is a restricted data class (final, no extra instance fields, data-driven methods); POJO can be mutable and flexible.

6) Can a record extend a class? Can it be extended?
   - No; it extends java.lang.Record and is final (cannot be extended).

7) Can records implement interfaces?
   - Yes.

8) What’s the canonical constructor vs compact constructor?
   - Canonical lists all components explicitly; compact omits parameter list and is used for validation/normalization.

9) Can you override equals/hashCode/toString in a record?
   - Yes, but should preserve “data as data” semantics; otherwise confusing.

10) How do you enforce deep immutability with a List component?
   - Defensive copy (List.copyOf) in constructor; also consider validating elements.

11) How do record accessors differ from JavaBeans getters?
   - Accessor is componentName() (e.g., name()), not getName().

12) Can you declare a record locally (inside a method)? Why use it?
   - Yes; useful for small scoped DTOs without creating a top-level type.
*/
