package com.java11.features;

import java.util.List;
import java.util.function.Predicate;

public class StringAndArrayMethodUpdates {

        private final String value = "value";

        public static void run() {

            stringMethods();
            arrayChanges();
            otherChanges();

            new StringAndArrayMethodUpdates().new InnerClass().accessOuter();
        }

        private static void stringMethods() {
            System.out.println();
            System.out.println("---- String method updates ----");

            var s1 = "   Hello, Java 11!   ";
            System.out.println("Original string: '" + s1 + "'");

            /**
             * String methods
             * - strip(), stripLeading(), stripTrailing() methods (Unicode-aware whitespace removal)
             * - isBlank() method (checks if string is empty or contains only whitespace)
             * - lines()
             * - repeat(int count) method
             */
            // strip() removes leading and trailing whitespace (including non-breaking spaces)
            var stripped = s1.strip();
            System.out.println("After strip(): '" + stripped + "'");

            // stripLeading() removes only leading whitespace
            var leadingStripped = s1.stripLeading();
            System.out.println("After stripLeading(): '" + leadingStripped + "'");

            // stripTrailing() removes only trailing whitespace
            var trailingStripped = s1.stripTrailing();
            System.out.println("After stripTrailing(): '" + trailingStripped + "'");

            // isBlank() checks if the string is empty or contains only whitespace
            var blankString = "   ";
            System.out.println("Is blankString blank? " + blankString.isBlank());

            var s2 = "Hello\nWorld\nJava\n11";
            System.out.println("Original string: '" + s2 + "'");
            List<String> lines = s2.lines().toList();
            System.out.println("Lines: " + lines);

            var repeated = "abc".repeat(3);
            System.out.println("Repeated string: '" + repeated + "'");
        }

        private static void arrayChanges() {

            /**
             * toArray(IntFunction<A[]> generator) method on Stream
             * - Allows you to specify the type of array to create when collecting a Stream into an
             */

            System.out.println();
            System.out.println("---- Array method updates ----");

            var list = List.of("Java", "Python", "C++");
            // Old way: toArray() returns Object[], so we need to cast it
            String[] oldArray = list.toArray(new String[0]);
            System.out.println("Old array: " + java.util.Arrays.toString(oldArray));

            // New way: toArray(IntFunction<A[]> generator) allows us to specify the array type directly
            String[] newArray = list.toArray(String[]::new);
            System.out.println("New array: " + java.util.Arrays.toString(newArray));

        }

        private static void otherChanges() {
            /**
             * var keyword being used in lambda parameters (e.g., (var x) -> x + 1)
             * Predicate Not: A new method added to the Predicate interface that allows you to negate a predicate using a more readable
             *      syntax (e.g., predicate.not() instead of x -> !predicate.test(x))
             */

            System.out.println();
            System.out.println("---- Other updates ----");

            // Using var in lambda parameters
            var numbers = List.of(1, 2, 3, 4, 5);
            var squared = numbers.stream()
                    .map((var x) -> x * x) // Using var in lambda parameter
                    .toList();
            System.out.println("Squared numbers: " + squared);


            // negate()
            // before Java 11
            System.out.println("Names that are not blank (before Java 11):");
            List<String> names = List.of("Alice", "", "Charlie", "David");
            names.stream().filter(s -> !s.isBlank()).forEach(System.out::println);

            // After Java 11 using Predicate.not()
            System.out.println("Names that are not blank (after Java 11):");
            names.stream().filter(Predicate.not(String::isBlank)).forEach(System.out::println);




        }

    /**
     * * Nest based access control:
     *   In Java 11, nested members have been relaxed to allow access to use private members of their enclosing class.
     *   Before Java 11, getter methods were required to access private members.
     */

    class InnerClass {
        public void accessOuter() {
            // This is allowed in Java 11 due to nest-based access control
            System.out.println("Accessing outer class's private field: " + value);
        }
    }


}
