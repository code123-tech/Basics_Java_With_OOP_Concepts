# Record Patterns (JEP 440) — Java 21

## What Is It?

Record Patterns extend **pattern matching** to let you **destructure a record's components
directly** inside `instanceof` or `switch` — instead of casting and calling accessor methods manually.

> Finalized (non-preview) in Java 21 via JEP 440. No `--enable-preview` flag needed.

---

## The Problem It Solves

Before Record Patterns, even after a type check you had to manually extract components:

```java
record Point(int x, int y) {}

Object obj = new Point(3, 5);

// Java 16 — match type, then manually extract via accessors
if (obj instanceof Point p) {
    System.out.println(p.x() + ", " + p.y()); // manual .x() .y() calls
}
```

Record Patterns eliminate this boilerplate — match and destructure in one step.

---

## Basic Usage

```java
// Java 21 — match AND destructure in one expression
if (obj instanceof Point(int x, int y)) {
    System.out.println(x + ", " + y); // x and y directly in scope
}
```

---

## With `switch` (most powerful use)

```java
sealed interface Shape permits Circle, Rectangle, Triangle {}
record Circle(double radius)              implements Shape {}
record Rectangle(double width, double height) implements Shape {}
record Triangle(double base, double height)   implements Shape {}

static double area(Shape shape) {
    return switch (shape) {
        case Circle(double r)              -> Math.PI * r * r;
        case Rectangle(double w, double h) -> w * h;
        case Triangle(double b, double h)  -> 0.5 * b * h;
    };
}
```

- No casting
- No accessor calls
- Compiler enforces all cases of a sealed type are handled

---

## Nested Record Patterns

Destructure records within records in a single expression:

```java
record Point(int x, int y) {}
record Line(Point start, Point end) {}

Object obj = new Line(new Point(0, 0), new Point(3, 4));

if (obj instanceof Line(Point(int x1, int y1), Point(int x2, int y2))) {
    System.out.println("Start: " + x1 + ", " + y1);
    System.out.println("End  : " + x2 + ", " + y2);
}
```

No intermediate variables, no chained accessor calls.

---

## With `var` (infer component types)

```java
if (obj instanceof Point(var x, var y)) {
    System.out.println(x + ", " + y); // types inferred from record definition
}
```

---

## With Guards (`when`)

Add conditions alongside pattern matching:

```java
static String classify(Shape shape) {
    return switch (shape) {
        case Circle(double r)              when r > 10 -> "Large circle";
        case Circle(double r)                          -> "Small circle";
        case Rectangle(double w, double h) when w == h -> "Square";
        case Rectangle(double w, double h)             -> "Rectangle";
        case Triangle(double b, double h)              -> "Triangle";
    };
}
```

---

## Evolution of Pattern Matching in Java

```
Java 14 (Preview) → instanceof type pattern:    obj instanceof String s
Java 16 (Final)   → instanceof type pattern finalized
Java 21 (Final)   → Record patterns:            obj instanceof Point(int x, int y)
                     switch with record patterns
                     nested record patterns
```

---

## Before vs After Comparison

```java
// BEFORE — verbose, manual extraction
if (obj instanceof Line l) {
    Point start = l.start();
    Point end   = l.end();
    int x1 = start.x(), y1 = start.y();
    int x2 = end.x(),   y2 = end.y();
    System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
}

// AFTER — single destructuring expression
if (obj instanceof Line(Point(int x1, int y1), Point(int x2, int y2))) {
    System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
}
```

---

## Key Rules

1. Record pattern only works with `record` types — not regular classes
2. All components must be listed in the pattern (use `var` to infer types)
3. Works in both `instanceof` and `switch`
4. Can be nested to any depth
5. Compiler checks exhaustiveness in `switch` for sealed types

---

## Best Practices

1. Prefer `switch` with record patterns over chains of `if-instanceof`
2. Use `sealed interface` + records together — compiler enforces all cases
3. Use `var` when component types are obvious from context
4. Use `when` guards instead of nested `if` inside case blocks
5. Nested patterns are powerful but keep them readable — extract to variables if too deep

---

## Exercise

Check class: `RecordPatternsExercise.java`

---

## References

- [JEP 440 — Record Patterns](https://openjdk.org/jeps/440)
- [JEP 441 — Pattern Matching for switch](https://openjdk.org/jeps/441)
