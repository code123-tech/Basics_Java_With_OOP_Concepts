package Extras_Interview.Some_Methods.StringRelated;

/**
 * String pool
 *
 * <p>It efficiently stores a string object in string pool, if not already exist.
 * and return the reference of that interned string object.</p>
 *
 * <p>String.intern() method is not Thread-sage, so it should be used carefully in multithreaded environments.</p>
 *
 * <p>Interning strings should be used judiciously, and only when the performance benefits outweigh the potential memory overhead.</p>
 */
public class String_Intern {
    public static void main(String[] args) {

        System.out.println("--String reference compare without intern");
        StringComparisonWithoutIntern();
        System.out.println("--String reference compare with intern");
        StringComparisonWithIntern();
        System.out.println("--String reference compare with string literal auto interned");
        StringComparisonWithStringLiteralAutoInterned();
    }

    private static void StringComparisonWithoutIntern() {
        String s1 = new String("String intern example");
        String s2 = "String intern example";

        System.out.println("Comparison reference of s1 and s2 without intern: " + (s1 == s2));
    }

    private static void StringComparisonWithIntern() {
        String s1 = new String("String intern example").intern(); // String with new keyword is not interned, needs to be explicitly interned
        String s2 = "String intern example";

        System.out.println("Comparison reference of s1 and s2 with intern: " + (s1 == s2));
    }

    private static void StringComparisonWithStringLiteralAutoInterned() {
        String s1 = "String intern example with intern auto apply"; // String literals are automatically interned by the compiler
        String s2 = s1;

        System.out.println("Comparison reference of s1 and s2 with intern auto applied: " + (s1 == s2));
    }
}
