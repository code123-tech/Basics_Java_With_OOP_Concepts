package org.example;

/**
 * Just like Pattern Matching for instanceOf introduced in if...else blocks (Java 15 feature)
 * <pre>
 *  if (obj instanceof String s) {
 *    // Here, 's' is automatically cast to String and can be used within this block.
 *    System.out.println("The string is: " + s);
 *  }
 * </pre>
 *
 * Similarly, Pattern matching for `switch` case is also introduced
 */
public class UnderstandingSwitchPatternMatching {

    public static void doExercise() {

        System.out.println("Pattern Matching for Switch case");

        Integer p = 10;
        describe(p);
    }

    private static void describe(Object obj) {

        switch (obj) {
            case String s -> System.out.println("we got string type of object: " + s);
            case Integer integer -> System.out.println("we got integer type of object: " + integer);
            default -> System.out.println("object is matched with none");
        }
    }

}
