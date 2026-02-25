package org.example;


public class Main {
    public static void main(String[] args) {

        /**
         * 1. Sealed Classes:
         *      Sealed classes and interfaces restrict their inheritance to specific classes or inheritance.
         */

        /*
            public sealed abstract class  Expr permits ConstantExpr, SumExpr, ProductExpr {
            }  // This is a sealed class that can only be extended by ConstantExpr, SumExpr, and ProductExpr.
         */


        /**
         * 2. Pattern Matching for instanceof:
         *      This feature allows you to use pattern matching with the instanceof operator, making it easier
         *      to work with types and avoid explicit casting.
         *      Helps to explicitly check type, do casting and use the variable in one step.
         */

        /*
            if (obj instanceof String s) {
                // Here, 's' is automatically cast to String and can be used within this block.
                System.out.println("The string is: " + s);
            }
         */


        /**
         * 3. Text Blocks:
         *      Text blocks are a new way to represent multi-line string literals in Java.
         *      Reducing need for complicated string concatenation and escaping.
         */

        /*
            String sqlQuery = """
                SELECT *
                FROM users
                WHERE age > 30
                ORDER BY name;
                """;
         */

        /**
         * 4. Hidden Classes:
         *       Hidden classes are a new type of class that cannot be accessed directly by the bytecode of other classes.
         *
         * 5. Edwards-Curve Digital Signature Algorithm (EdDSA):
         *         modern, high-performance signing algorithm that provides better security and  performance compared to
         *         traditional algorithms like RSA and ECDSA.
         *
         * 6. Remove the Nosharn JS Engine:
         *
         * 7. Disable and Deprecate Biased Locking (JEP 374)
         *
         * 8. ZGC: A Scalable Low-Latency Garbage Collector (JEP 377)
         *          improves app performance with large heaps and many processor cores.
         *          enabled ZGC as follows
         *
         *          java -XX:+UseZGC myapp.jar
         *
         * 9. Deprecate RMI Activation for Future Removal (JEP 385)
         */


    }
}