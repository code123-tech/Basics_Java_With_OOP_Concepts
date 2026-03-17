# Pattern Matching for Switch (JEP 441) — Finalized in Java 21

## Evolution History

| Version | JEP | Key Changes |
|---------|-----|-------------|
| Java 17 | 406 | First preview. Guards used `&&`: `case String s && s.length() > 1`. `case null` introduced. |
| Java 18 | 420 | Second preview. Minor refinements to dominance checking. |
| Java 19 | 427 | Third preview — **MAJOR**. Replaced `&&` with `when` keyword. Null semantics refined. |
| Java 20 | 433 | Fourth preview. Further refinements, no major syntax changes. |
| Java 21 | 441 | **FINALIZED.** Removed parenthesized patterns. Added qualified enum constants. Throws `MatchException` (instead of `IncompatibleClassChangeError`) for unmatched sealed types. |

---

## 1. Type Patterns

The selector expression can be **any reference type** (or `int`). A type pattern `case Type varName` matches if the value is an instance of `Type` and binds it to `varName`.

Before (if-else chain):
```java
if (obj instanceof String s) { return "String: " + s; }
else if (obj instanceof Integer i) { return "int: " + i; }
else { return obj.toString(); }
```

After (pattern switch):
```java
static String formatValue(Object obj) {
    return switch (obj) {
        case Integer i  -> String.format("int %d", i);
        case Long l     -> String.format("long %d", l);
        case Double d   -> String.format("double %f", d);
        case String s   -> String.format("String %s", s);
        case int[] arr  -> String.format("int[] of length %d", arr.length);
        default         -> obj.toString();
    };
}
```

---

## 2. Guarded Patterns (`when` keyword)

Add a boolean condition to a pattern. Matches only if both the pattern matches AND the guard is `true`.

```java
static void classify(Object obj) {
    switch (obj) {
        case Integer i when i < 0            -> System.out.println("negative: " + i);
        case Integer i when i == 0           -> System.out.println("zero");
        case Integer i when i > 0 && i < 100 -> System.out.println("small positive: " + i);
        case Integer i                       -> System.out.println("large: " + i);
        case String s when s.isEmpty()       -> System.out.println("empty string");
        case String s when s.length() > 5    -> System.out.println("long string");
        case String s                        -> System.out.println("short string: " + s);
        default                              -> System.out.println("other");
    }
}
```

**Notes:**
- `when` is a **context-sensitive keyword** — only a keyword inside switch case labels; can still be used as a variable name elsewhere.
- Guards are only permitted on **pattern labels**, NOT on constant labels.
- Java 17-18 used `&&` syntax (e.g., `case String s && s.isEmpty()`). Changed to `when` in Java 19+.

---

## 3. Null Handling (`case null`)

Historically, switch **always** threw `NullPointerException` for null selectors. Java 21 adds explicit null handling.

```java
static void testNull(String s) {
    switch (s) {
        case null         -> System.out.println("Got null!");
        case "Foo", "Bar" -> System.out.println("Got Foo or Bar");
        default           -> System.out.println("Something else: " + s);
    }
}
```

**Rules:**
- `case null` explicitly matches null values.
- Without `case null`, pattern switches **still throw NPE** (backward compatibility preserved).
- `default` does **NOT** implicitly match null.
- `case null` **cannot** combine with type/record patterns: `case null, String s` is **ILLEGAL**.
- `case null` **can** combine with `default`:
```java
case null, default -> System.out.println("null or unhandled");
```

---

## 4. Dominance and Ordering of Case Labels

Labels are tested **in order**. A compile-time error occurs if a case label is dominated (made unreachable) by a preceding label.

**Dominance rules:**
1. `case CharSequence cs` dominates `case String s` (String is a subtype of CharSequence)
2. Unguarded pattern dominates guarded pattern of same type: `case String s` dominates `case String s when s.length() > 5`
3. Unguarded pattern dominates constants: `case Integer i` dominates `case 42`
4. Guarded pattern does NOT dominate constants (guards are undecidable)

```java
// COMPILE ERROR: CharSequence dominates String
switch (obj) {
    case CharSequence cs -> ...;  // too broad
    case String s        -> ...;  // ERROR: unreachable!
}

// CORRECT order: most specific first
switch (value) {
    case -1, 1                           -> "unit value";   // 1. Constants first
    case Integer i when i > 0 && i < 100 -> "small";       // 2. Guarded patterns
    case Integer i when i < 0            -> "negative";
    case Integer i                       -> "other integer"; // 3. Unguarded last
}
```

---

## 5. Exhaustiveness with Sealed Types

Switch expressions must be exhaustive. Pattern switch statements using pattern labels must also be exhaustive.

Sealed classes enable exhaustiveness without a `default`:

```java
sealed interface Shape permits Circle, Rectangle, Triangle {}
record Circle(double radius) implements Shape {}
record Rectangle(double length, double width) implements Shape {}
record Triangle(double base, double height) implements Shape {}

static double area(Shape shape) {
    return switch (shape) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.length() * r.width();
        case Triangle t  -> 0.5 * t.base() * t.height();
        // No default needed — compiler knows all subtypes are covered!
    };
}
```

**If the sealed hierarchy changes after compilation** (new subtype added but switch not recompiled), the JVM throws `java.lang.MatchException` at runtime (changed from `IncompatibleClassChangeError` in Java 21).

---

## 6. Record Patterns in Switch

Deconstruct records directly in case labels (combines JEP 440 + JEP 441):

```java
record Point(int x, int y) {}
record ColoredPoint(Point p, String color) {}

// Basic record pattern
static String describe(Shape shape) {
    return switch (shape) {
        case Circle(var radius)            -> "circle r=" + radius;
        case Rectangle(var len, var width) -> "rect " + len + "x" + width;
        case Triangle(var base, var h)     -> "triangle b=" + base + " h=" + h;
    };
}

// Nested record pattern (deep destructuring) + guard
static String analyze(Object obj) {
    return switch (obj) {
        case ColoredPoint(Point(var x, var y), var color)
            when x == 0 && y == 0 -> color + " point at origin";
        case ColoredPoint(Point(var x, var y), var color)
            -> color + " point at (" + x + "," + y + ")";
        default -> "unknown";
    };
}
```

---

## 7. Qualified Enum Constants (NEW in Java 21)

Use `EnumType.CONSTANT` syntax in case labels. Essential when the selector is a supertype (like a sealed interface) that multiple enums implement.

```java
sealed interface CardClassification permits Standard, Tarot {}
enum Standard implements CardClassification { SPADE, HEART, DIAMOND, CLUB }
enum Tarot implements CardClassification { SPADE, HEART, DIAMOND, CLUB, TRUMP }

static void describe(CardClassification c) {
    switch (c) {
        case Standard.SPADE   -> System.out.println("Standard Spades");
        case Standard.HEART   -> System.out.println("Standard Hearts");
        case Tarot.SPADE      -> System.out.println("Tarot Piques");
        case Tarot.HEART      -> System.out.println("Tarot Coeurs");
        case Tarot.TRUMP      -> System.out.println("Tarot Atouts");
        // ... exhaustive, no default needed
    }
}
```

**Rules:**
- When selector IS the enum type: both unqualified (`SPADE`) and qualified (`Standard.SPADE`) work.
- When selector is a **supertype**: you MUST use qualified names — compiler can't determine which enum's constant otherwise (compile error).

---

## 8. Unnamed Patterns `_` (Preview in Java 21)

JEP 443 — preview in Java 21, finalized in Java 22. Requires `--enable-preview`. Use `_` to ignore record components you don't need.

```java
case Point(var x, _)                     -> "x = " + x;          // ignore y
case ColoredPoint(Point(var x, var y), _) -> ...;                 // ignore color
case ColoredPoint(Point(var x, _), _)     -> "x = " + x;         // ignore y and color

// In switch with sealed types
switch (box) {
    case Box(RedBall _), Box(BlueBall _) -> processBox(box);
    case Box(GreenBall _)                -> stopProcessing();
    case Box(_)                          -> pickAnotherBox();     // any remaining ball type
}
```

---

## 9. Key Differences from Traditional Switch

| Feature | Traditional Switch | Switch Expressions (Java 14+) | Pattern Switch (Java 21) |
|---------|-------------------|------------------------------|--------------------------|
| Selector types | int, byte, short, char, String, enum | Same | Any reference type or int |
| Case labels | Constants only | Constants only | Patterns + constants + null |
| Null handling | Always NPE | Always NPE | `case null` supported |
| Fall-through | Default (need `break`) | No (arrow syntax) | Forbidden from pattern cases |
| Exhaustiveness | Not checked | Required | Required |
| Guards | N/A | N/A | `when` keyword |
| Returns value | No (statement) | Yes | Both forms |

**Fall-through restriction:** Cannot fall through from a case that declares a pattern variable (compile error):
```java
// COMPILE ERROR
switch (obj) {
    case String s:
        System.out.println(s);
        // no break -> falls through — ILLEGAL with pattern variables
    case Integer i:
        System.out.println(i);
}
```

---

## 10. MatchException (New in Java 21)

`java.lang.MatchException` is thrown when:
- A pattern switch (expression or statement) over a sealed type encounters a value that matches no case, due to out-of-sync compilation.
- A record component accessor throws an exception during pattern matching.

This replaces `IncompatibleClassChangeError` for exhaustive switch mismatches and `NullPointerException` for record accessors.

---

## Exercise

See `PatternMatchingSwitchExercise.java` in this folder.

The exercise covers:
1. Type patterns — classifying mixed-type input
2. Guarded patterns — range-based classification
3. Null handling — safe null-aware switch
4. Sealed types + exhaustiveness — shape area calculator
5. Record patterns + nested deconstruction
6. Qualified enum constants — multi-enum sealed interface
