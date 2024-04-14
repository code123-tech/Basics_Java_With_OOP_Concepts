package Java9;

import java.util.*;

/**
 * Suppose, a situation comes to create a pre-defined collection with some elements which can't be modified further.
 * As per Java 8, there exist a method {@link java.util.Collections#unmodifiableList(List)} which returns an unmodifiable view of the specified list.
 * But, this method requires a collection to be passed first, and then it returns an unmodifiable view of the collection.
 * What is the issue here?
 * 1. Firstly, need to create a list with elements which itself can be mutable or immutable.
 * 2. Need to pass that list to method, which will again create a list which has this immutable list reference.
 * 3. Number of code lines are increasing.
 * 4. The first list does not guarantee that it will be immutable, it's contents can be changed.
 * 5. This breaks the rule of unmodifiable list.
 *
 * ============================================================
 * Now, From Java 9, Some static methods like `of` are introduced in Collection Interface.
 * 1. List.of
 * 2. Map.of
 * 3. Set.of
 * These methods helps to create an immutable collection in a single line.
 * Pros:
 * 1. Helps to reduce number of lines of code and improve readability.
 * 2. No need of creating an extra list which is being taken by unmodifiableList method.
 * 3. This predefined collection can be declared in some common files, or at the top of the class. So whenever any new element
 *  needs to be added, just add it in the list. No need to scroll down to find the list.
 */
public class CollectionFactoryMethods {

    public static void main(String[] args) {
        unmodifiableListCreationTillJava8();
        unmodifiableListCreationFromJava9();
    }

    private static void unmodifiableListCreationFromJava9() {
        List<String> immutableList = List.of("A", "B", "C");

        // now, try to add element into immutableList
        try{
            immutableList.add("Sohan");
        }catch (UnsupportedOperationException e){
            System.out.println("UnsupportedOperationException (Using of method Java 9): " + e);
        }
    }

    private static void unmodifiableListCreationTillJava8() {
        // Since there was no of method in Java 8, so creating list with as method
        List<String> mutableList = new ArrayList<>(Arrays.asList("A", "B", "C")); // of method creates mutable list
        List<String> immutableList = Collections.unmodifiableList(mutableList);

        // now, try to add element into immutableList
        try{
            immutableList.add("Sohan");
        }catch (UnsupportedOperationException e){
            System.out.println("UnsupportedOperationException (In Java 8): " + e);
        }

        // now, add same element into mutableList, and iterate over immutableList
        try{
            mutableList.add("Sohan");
            immutableList.forEach(System.out::println);
        }catch (UnsupportedOperationException e){
            System.out.println("UnsupportedOperationException (In Java 8): " + e);
        }

    }

}
