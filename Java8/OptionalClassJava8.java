package Java8;

import java.util.Optional;

/**
 * Read IT: https://stackify.com/optional-java/
 * These class is basically created to handle NullPointerException.
 */
public class OptionalClassJava8 {
    public static void main(String[] args) {
        Optional.of("1").orElse("Example");
        Optional.of("1").orElseGet(() -> "Example");  // Lazy evaluation.

        Optional.ofNullable(null).orElse("Example"); // accept null ofnullable
        Optional.ofNullable(null).orElseGet(() ->"Example");
    }
}
