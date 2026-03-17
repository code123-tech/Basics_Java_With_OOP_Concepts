package org.example.PatternMatchingSwitch;


// ─── Sealed types for Exercise 4 ───────────────────────────────────────────
sealed interface Vehicle permits Car, Truck, Motorcycle {}
record Car(String brand, int seats) implements Vehicle {}
record Truck(String brand, double payloadTons) implements Vehicle {}
record Motorcycle(String brand, boolean hasSidecar) implements Vehicle {}

// ─── Records for Exercise 5 ────────────────────────────────────────────────
record Point(int x, int y) {}
record Line(Point start, Point end) {}

// ─── Sealed + Enum for Exercise 6 ──────────────────────────────────────────
sealed interface Payment permits CreditCard, DebitCard {}
enum CreditCard implements Payment { VISA, MASTERCARD, AMEX }
enum DebitCard implements Payment { VISA, MASTERCARD, RUPAY }

public class PatternMatchingSwitchExercise {

    // ── Exercise 1: Type Patterns ───────────────────────────────────────────
    private static String describeType(Object obj) {

        return switch (obj) {
            case Integer i -> "Integer " + i;
            case Double d  -> "Double " + d;
            case String s  -> "String " + s;
            case Boolean b -> "Boolean " + b;
            case int[] arr -> "int[] of length " + arr.length;
            default -> "Unknown Type " + obj.getClass().getSimpleName();
        };
    }

    // ── Exercise 2: Guarded Patterns ───────────────────────────────────────
    private static String classifyNumber(Object obj) {

        return switch (obj) {
          case Integer i when i < 0   -> "Negative: " + i;
          case Integer i when i == 0  -> "Zero";
          case Integer i when i < 100 -> "Small positive: " + i;
          case Integer i              -> "Large positive: " + i;
          default                     -> "Not an integer: " + obj;
        };
    }

    // ── Exercise 3: Null Handling ───────────────────────────────────────────
    private static String safeGreet(Object name) {

        return switch (name) {
            case null    -> "Hello, Guest!";
            case String s when s.equals("admin") -> "Welcome, Admin!";
            default      -> "Hello, " + name + "!";
        };
    }

    // ── Exercise 4: Sealed Types + Exhaustiveness ───────────────────────────
    private static String describeVehicle(Vehicle v) {

        return switch (v) {
            case Car c          -> c.brand() + " car with " + c.seats() + " seats";
            case Truck t        -> t.brand() + " truck, payload: " + t.payloadTons() + "T";
            case Motorcycle m   -> m.brand() + (m.hasSidecar() ? " with sidecar" : " solo");
            // No default needed — sealed, exhaustive!
        };
    }

    // ── Exercise 5: Record Patterns + Nested + Guards ───────────────────────
    private static String describeLine(Object obj) {

        return switch (obj) {
            case Line(Point(var x1, var y1), Point(var x2, var y2))
                    when x1 == x2 && y1 == y2  -> "A point, not a line: (" + x1 + "," + y1 + ")";
            case Line(Point(var x1, var y1), Point(var x2, var y2))
                    when y1 == y2               -> "Horizontal line at y=" + y1;
            case Line(Point(var x1, var y1), Point(var x2, var y2))
                    when x1 == x2               -> "Vertical line at x=" + x1;
            case Line(Point(var x1, var y1), Point(var x2, var y2))
                    -> "Diagonal from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")";
            default -> "Not a line";
        };
    }

    // ── Exercise 6: Qualified Enum Constants ────────────────────────────────
    private static String processPayment(Payment p) {

        return switch (p) {
            case CreditCard.VISA        -> "Credit: VISA (international)";
            case CreditCard.MASTERCARD  -> "Credit: Mastercard (international)";
            case CreditCard.AMEX        -> "Credit: Amex (premium)";
            case DebitCard.VISA         -> "Debit: VISA";
            case DebitCard.MASTERCARD   -> "Debit: Mastercard";
            case DebitCard.RUPAY        -> "Debit: RuPay (domestic)";
            // No default — exhaustive via sealed + qualified enums!
        };
    }

    // ── Main ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        // Exercise 1
        System.out.println("=== Exercise 1: Type Patterns ===");
        System.out.println("Describe type for ('12') is: " +  describeType(12));
        System.out.println("Describe type for ('12.34') is: " +  describeType(12.34));
        System.out.println("Describe type for ('hello') is: " +  describeType("hello"));
        System.out.println("Describe type for ('true') is: " +  describeType(true));
        System.out.println("Describe type for ('int[]') is: " +  describeType(new int[]{1, 2, 3}));

        // Exercise 2
        System.out.println("\n=== Exercise 2: Guarded Patterns ===");
        System.out.println(classifyNumber(-1));
        System.out.println(classifyNumber(0));
        System.out.println(classifyNumber(14));
        System.out.println(classifyNumber(1000));
        System.out.println(classifyNumber("Ramesh"));

        // Exercise 3
        System.out.println("\n=== Exercise 3: Null Handling ===");
        System.out.println(safeGreet(null));
        System.out.println(safeGreet("admin"));
        System.out.println(safeGreet("Alice"));

        // Exercise 4
        System.out.println("\n=== Exercise 4: Sealed Types ===");
        System.out.println(describeVehicle(new Car("Toyota", 5)));
        System.out.println(describeVehicle(new Truck("Tata", 10.5)));
        System.out.println(describeVehicle(new Motorcycle("Royal Enfield", true)));

        // Exercise 5
        System.out.println("\n=== Exercise 5: Record Patterns + Nested + Guards ===");
        System.out.println(describeLine(new Line(new Point(0, 0), new Point(0, 0))));
        System.out.println(describeLine(new Line(new Point(0, 3), new Point(5, 3))));
        System.out.println(describeLine(new Line(new Point(2, 0), new Point(2, 7))));
        System.out.println(describeLine(new Line(new Point(1, 1), new Point(4, 5))));

        // Exercise 6
        System.out.println("\n=== Exercise 6: Qualified Enum Constants ===");
        System.out.println(processPayment(CreditCard.AMEX));
        System.out.println(processPayment(DebitCard.RUPAY));
        System.out.println(processPayment(CreditCard.VISA));
    }

}
