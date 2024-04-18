package Java9;

import java.util.List;
import java.util.Optional;

/**
 * Some new changes in Java 9 are as follows
 * 1. Private method introduction from Java 9 we can write some repetitive and common code in private methods.
 * 2. _ is declared as keyword from Java9, till now it can be used as identifier.
 * 3. takeWhile and dropWhile methods are introduced in Stream API which can replace limit() and skip() methods.
 * 4. Optional API improvements.
 */
public class Changes implements PrivateMethodExample{
    public static void main(String[] args) {
        PrivateMethodExample privateMethodExample = new Changes();
        privateMethodExample.printUsingDefault();
        // int _ = 10; // will throw error as _ is declared as keyword from Java 9

        // takeWhile example
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // Using limit
        list.stream().limit(5).forEach(System.out::println);
        // Using takeWhile
        list.stream().takeWhile(i -> i < 5).forEach(System.out::println);

        // Advantage of using takeWhile is that it takes condition till which it is true, and then stops.

        // dropWhile example
        List<Integer> list1 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // Using skip
        list1.stream().skip(5).forEach(System.out::println);
        // Using dropWhile
        list1.stream().dropWhile(i -> i < 5).forEach(System.out::println);
        // Advantage of using dropWhile is that it skips the condition till which it is true, and then starts.

        /**
         * Optional API improvements
         * 1. ifPresentOrElse
         * 2. or
         * 3. stream
         */

        // ifPresentOrElse
        // ifPresentOrElse is used to perform some operation if value is present, else perform some other operation.
        Optional<String> optional = Optional.of("Sohan");
        optional.ifPresentOrElse(System.out::println, () -> System.out.println("Value is not present"));
        // This purely let a developer to follow functional programming approach instead of going to Imperative approach.

        // or
        // or is used to return the value if present, else return the value passed in or method.
        Optional<String> optional1 = Optional.empty();
        System.out.println(optional1.or(() -> Optional.of("Value is not present")));

        // stream
        // stream is used to convert Optional to Stream.
        Optional<String> optional2 = Optional.of("Sohan");
        optional2.stream().forEach(System.out::println);
    }
}

interface PrivateMethodExample{

    default void printUsingDefault(){
        System.out.println("Printing using Interface default method");
        printUsingPrivate();
    }

    private void printUsingPrivate(){
        System.out.println("Printing using Interface private method");
    }
}