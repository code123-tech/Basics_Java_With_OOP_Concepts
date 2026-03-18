package org.example.UnnamedVariablesAndPatterns;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

// ─── Sealed types for Exercises 4, 5, 7, 8 ─────────────────────────────────
sealed abstract class Ball permits RedBall, BlueBall, GreenBall {}
final class RedBall   extends Ball {}
final class BlueBall  extends Ball {}
final class GreenBall extends Ball {}

record Box<T extends Ball>(T content) {}

sealed interface Shape permits Circle, Rectangle, Triangle {}
record Circle(double radius)           implements Shape {}
record Rectangle(double w, double h)   implements Shape {}
record Triangle(double base, double h) implements Shape {}

// ─── Records for Exercise 6 ─────────────────────────────────────────────────
record Order(String id, String customerId, double amount, String status) {}

public class UnnamedVariablesExercise {

    // ── Exercise 1: Catch block — exception unused ───────────────────────────
    static int parseOrDefault(String s, int defaultVal) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException _) {       // _ instead of "ignored" / "e"
            return defaultVal;
        }
    }

    // ── Exercise 2: Enhanced for loop — element unused ───────────────────────
    static int countItems(List<String> items) {
        int count = 0;
        for (String _ : items)                    // element never touched
            count++;
        return count;
    }

    // ── Exercise 3: Lambda — parameter unused ────────────────────────────────
    static Map<String, String> buildConstantMap(List<String> keys) {
        return keys.stream().collect(
            Collectors.toMap(
                String::toUpperCase,
                _ -> "N/A"                        // value mapper ignores input
            )
        );
    }

    static void printKeys(Map<String, Integer> map) {
        map.forEach((key, _) -> System.out.println("  key: " + key));
    }

    // ── Exercise 4: Switch type pattern — binding unused ─────────────────────
    static String describeBall(Ball ball) {
        return switch (ball) {
            case RedBall _   -> "It's a red ball";
            case BlueBall _  -> "It's a blue ball";
            case GreenBall _ -> "It's a green ball";
        };
    }

    // ── Exercise 5: Switch record pattern — ignore ALL fields ────────────────
    static String describeBox(Box<? extends Ball> box) {
        return switch (box) {
            case Box(RedBall _)   -> "Box contains red";
            case Box(BlueBall _)  -> "Box contains blue";
            case Box(GreenBall _) -> "Box contains green";
        };
    }

    // ── Exercise 6: Switch record pattern — ignore SPECIFIC fields ───────────
    static String processOrder(Order order) {
        return switch (order) {
            case Order(var id, _, _, var s) when s.equals("GOLD")   -> "Gold discount for order: " + id;
            case Order(var id, _, _, var s) when s.equals("SILVER") -> "Silver discount for order: " + id;
            case Order(var id, _, var amt, _)                        -> "No discount. Order: " + id + ", amount: " + amt;
        };
    }

    // ── Exercise 7: Multi-pattern case — unnamed required ────────────────────
    static String categorizeBox(Box<? extends Ball> box) {
        return switch (box) {
            case Box(RedBall _), Box(BlueBall _) -> "colored ball";
            case Box(GreenBall _)                -> "green ball";
        };
    }

    // ── Exercise 8: Sealed types + exhaustiveness with unnamed patterns ───────
    static double area(Shape shape) {
        return switch (shape) {
            case Circle(var r)           -> Math.PI * r * r;
            case Rectangle(var w, var h) -> w * h;
            case Triangle(var b, var h)  -> 0.5 * b * h;
            // No default — exhaustive! Adding new Shape subtype = compile error
        };
    }

    static String shapeType(Shape shape) {
        return switch (shape) {
            case Circle _    -> "circle";
            case Rectangle _ -> "rectangle";
            case Triangle _  -> "triangle";
        };
    }

    // ── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        // Exercise 1
        System.out.println("=== Exercise 1: Catch with _ ===");
        System.out.println(parseOrDefault("42", -1));    // 42
        System.out.println(parseOrDefault("abc", -1));   // -1

        // Exercise 2
        System.out.println("\n=== Exercise 2: For loop with _ ===");
        System.out.println(countItems(List.of("a", "b", "c", "d"))); // 4

        // Exercise 3
        System.out.println("\n=== Exercise 3: Lambda with _ ===");
        System.out.println(buildConstantMap(List.of("foo", "bar")));
        printKeys(Map.of("x", 1, "y", 2, "z", 3));

        // Exercise 4
        System.out.println("\n=== Exercise 4: Switch type pattern with _ ===");
        System.out.println(describeBall(new RedBall()));
        System.out.println(describeBall(new BlueBall()));
        System.out.println(describeBall(new GreenBall()));

        // Exercise 5
        System.out.println("\n=== Exercise 5: Switch record — ignore all fields ===");
        System.out.println(describeBox(new Box<>(new RedBall())));
        System.out.println(describeBox(new Box<>(new BlueBall())));
        System.out.println(describeBox(new Box<>(new GreenBall())));

        // Exercise 6
        System.out.println("\n=== Exercise 6: Switch record — ignore specific fields ===");
        System.out.println(processOrder(new Order("O1", "C1", 500.0, "GOLD")));
        System.out.println(processOrder(new Order("O2", "C2", 200.0, "SILVER")));
        System.out.println(processOrder(new Order("O3", "C3", 100.0, "BRONZE")));

        // Exercise 7
        System.out.println("\n=== Exercise 7: Multi-pattern case with _ ===");
        System.out.println(categorizeBox(new Box<>(new RedBall())));
        System.out.println(categorizeBox(new Box<>(new BlueBall())));
        System.out.println(categorizeBox(new Box<>(new GreenBall())));

        // Exercise 8
        System.out.println("\n=== Exercise 8: Sealed types + exhaustiveness ===");
        System.out.printf("Circle area    : %.2f%n", area(new Circle(5)));
        System.out.printf("Rectangle area : %.2f%n", area(new Rectangle(4, 6)));
        System.out.printf("Triangle area  : %.2f%n", area(new Triangle(3, 8)));
        System.out.println(shapeType(new Circle(1)));
        System.out.println(shapeType(new Rectangle(1, 1)));
        System.out.println(shapeType(new Triangle(1, 1)));
    }
}
