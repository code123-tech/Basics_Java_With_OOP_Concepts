package org.example.RecordPatterns;

/*
 * Focus Area: Record Patterns (JEP 440) — Java 21
 *
 * Exercise: Explore record pattern matching using instanceof and switch.
 *
 * Parts:
 *   Part 1 — Basic record pattern with instanceof
 *   Part 2 — Record pattern with switch + sealed interface
 *   Part 3 — Nested record patterns
 *   Part 4 — Guards (when) with record patterns
 *   Part 5 — var inference in record patterns
 */
public class RecordPatternsExercise {

    // -------------------------------------------------------------------------
    // Domain model
    // -------------------------------------------------------------------------

    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius)                   implements Shape {}
    record Rectangle(double width, double height)  implements Shape {}
    record Triangle(double base, double height)    implements Shape {}

    record Point(int x, int y) {}
    record Line(Point start, Point end) {}

    // -------------------------------------------------------------------------
    // Part 1: Basic record pattern with instanceof
    // -------------------------------------------------------------------------

    static void part1() {
        System.out.println("=== Part 1: instanceof record pattern ===");

        Object obj = new Point(3, 5);

        // Old way (Java 16)
        if (obj instanceof Point p) {
            System.out.println("Old way — x: " + p.x() + ", y: " + p.y());
        }

        // New way (Java 21) — destructure directly
        if (obj instanceof Point(int x, int y)) {
            System.out.println("New way — x: " + x + ", y: " + y);
        }
    }

    // -------------------------------------------------------------------------
    // Part 2: switch with sealed interface + record patterns
    // -------------------------------------------------------------------------

    static double area(Shape shape) {
        return switch (shape) {
            case Circle(double r)              -> Math.PI * r * r;
            case Rectangle(double w, double h) -> w * h;
            case Triangle(double b, double h)  -> 0.5 * b * h;
        };
        // Compiler enforces all cases — no default needed with sealed types
    }

    static void part2() {
        System.out.println("\n=== Part 2: switch with record patterns ===");
        System.out.printf("Circle area    : %.2f%n", area(new Circle(5)));
        System.out.printf("Rectangle area : %.2f%n", area(new Rectangle(4, 6)));
        System.out.printf("Triangle area  : %.2f%n", area(new Triangle(3, 8)));
    }

    // -------------------------------------------------------------------------
    // Part 3: Nested record patterns
    // -------------------------------------------------------------------------

    static void part3() {
        System.out.println("\n=== Part 3: Nested record patterns ===");

        Object obj = new Line(new Point(0, 0), new Point(3, 4));

        // Before — manual chained accessor calls
        if (obj instanceof Line l) {
            System.out.println("Old way — Start: " + l.start().x() + "," + l.start().y()
                    + "  End: " + l.end().x() + "," + l.end().y());
        }

        // After — single nested destructuring
        if (obj instanceof Line(Point(int x1, int y1), Point(int x2, int y2))) {
            System.out.println("New way — Start: " + x1 + "," + y1 + "  End: " + x2 + "," + y2);
        }
    }

    // -------------------------------------------------------------------------
    // Part 4: Guards (when) with record patterns
    // -------------------------------------------------------------------------

    static String classify(Shape shape) {
        return switch (shape) {
            case Circle(double r)              when r > 10 -> "Large circle (r=" + r + ")";
            case Circle(double r)              when r > 0  -> "Small circle (r=" + r + ")";
            case Circle(double r)                          -> "Invalid circle";
            case Rectangle(double w, double h) when w == h -> "Square (" + w + "x" + h + ")";
            case Rectangle(double w, double h)             -> "Rectangle (" + w + "x" + h + ")";
            case Triangle(double b, double h)              -> "Triangle (base=" + b + ", h=" + h + ")";
        };
    }

    static void part4() {
        System.out.println("\n=== Part 4: Guards (when) with record patterns ===");
        System.out.println(classify(new Circle(15)));
        System.out.println(classify(new Circle(5)));
        System.out.println(classify(new Rectangle(4, 4)));
        System.out.println(classify(new Rectangle(3, 6)));
        System.out.println(classify(new Triangle(4, 9)));
    }

    // -------------------------------------------------------------------------
    // Part 5: var inference in record patterns
    // -------------------------------------------------------------------------

    static void part5() {
        System.out.println("\n=== Part 5: var inference ===");

        Object obj = new Rectangle(4.0, 6.0);

        // var lets compiler infer component types from the record definition
        if (obj instanceof Rectangle(var w, var h)) {
            System.out.println("Width: " + w + ", Height: " + h + ", Area: " + (w * h));
        }
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        part1();
        part2();
        part3();
        part4();
        part5();
    }
}
