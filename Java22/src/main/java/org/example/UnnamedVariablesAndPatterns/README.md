# Unnamed Variables and Patterns (JEP 456) — Finalized in Java 22

## Evolution

| Version | JEP | Status |
|---------|-----|--------|
| Java 1–7 | — | `_` was a valid identifier |
| Java 8 | — | Compile-time **warning** for single `_` as identifier |
| Java 9 | 213 | Single `_` became a **compile-time error** — reclaimed for future use |
| Java 21 | 443 | `_` reintroduced as unnamed variable/pattern — **Preview** |
| Java 22 | 456 | **Finalized without any changes** from preview |

---

## The Problem: What Was Missing

Java frequently forces you to name things you never intend to use:

```java
// Exception never examined — forced to invent a name
} catch (NumberFormatException ignored) { ... }

// Loop variable never touched
for (Order order : orders) total++;

// Lambda param forced by interface signature
map.replaceAll((k, v) -> "DEFAULT");   // k is irrelevant

// Resource acquired only for lifecycle, never accessed
try (ScopedContext ctx = ScopedContext.acquire()) { doWork(); }

// Type check only — binding never used
if (obj instanceof String s) { handleString(); }
```

Problems: fake names (`ignored`, `e`, `rb`) mislead readers, trigger "unused variable" IDE warnings, and don't express intent.

---

## 1. Unnamed Variables (`_`) — Six Contexts

Key rule: `_` places **nothing in scope**. It cannot be read, written, or referenced after its declaration. Multiple `_` in the same block are legal (no shadowing).

### 1a. Catch Block

```java
try {
    int i = Integer.parseInt(s);
} catch (NumberFormatException _) {         // exception object irrelevant
    System.out.println("Bad number: " + s);
}

// Multi-catch also valid
try {
    doRisky();
} catch (IOException | SQLException _) {
    handleFailure();
}

// Multiple catch blocks, each using _
try {
    riskyOp();
} catch (TransientException _) {
    retry();
} catch (OptimisticLockException _) {
    entity = db.reload(entity);
}
```

### 1b. Enhanced For Loop

```java
int total = 0;
for (Order _ : orders)   // element never needed
    total++;
```

### 1c. Local Variable Declaration

```java
Queue<Integer> q = ...;
while (q.size() >= 3) {
    var x = q.remove();
    var _ = q.remove();   // consumed but not needed
    var _ = q.remove();   // multiple _ in same block — legal!
    points.add(new Point(x, 0));
}
```

### 1d. Try-With-Resources

```java
try (var _ = ScopedContext.acquire()) {
    doSensitiveWork();                // context closed automatically
}

try (var conn = dataSource.getConnection();
     var _ = MDC.putCloseable("requestId", reqId)) {
    conn.createStatement().execute(sql); // conn used, MDC entry not
}
```

### 1e. Lambda Parameters

```java
map.forEach((key, _) -> System.out.println(key));   // value ignored

stream.collect(Collectors.toMap(
    String::toUpperCase,
    _ -> "NODATA"                                   // input ignored
));

BiFunction<String, Integer, String> f = (_, _) -> "constant"; // both ignored
```

### 1f. Basic For Loop Init

```java
for (int i = 0, _ = sideEffect(); i < 10; i++) {
    // i used; side effect of sideEffect() needed but result is not
}
```

---

## 2. Unnamed Patterns (`_`) — Three Contexts

Two distinct constructs:
- **Unnamed pattern variable**: `case MyType _` — type IS checked, binding variable is unnamed
- **Unnamed pattern** (bare `_`): inside record `Box(_)` — unconditional match, no type check

### 2a. `instanceof` — Ignoring the Binding Variable

```java
// Only need the type check, not the extracted value
if (obj instanceof String _) {
    handleString();
}

// In nested record pattern — ignore specific components
record ColoredPoint(Point p, Color c) {}

if (r instanceof ColoredPoint(Point(int x, int y), Color _)) { // ignore color
    System.out.println("x=" + x + " y=" + y);
}

if (r instanceof ColoredPoint(_, Color c)) {   // bare _ ignores Point entirely
    System.out.println("color=" + c);
}
```

### 2b. Switch Type Patterns — Ignoring the Binding Variable

```java
sealed abstract class Ball permits RedBall, BlueBall, GreenBall {}

switch (ball) {
    case RedBall _   -> process(ball);   // type matched, binding not needed
    case BlueBall _  -> process(ball);
    case GreenBall _ -> stopProcessing();
}
```

### 2c. Switch Record Patterns — Ignoring Record Components

```java
record Box<T extends Ball>(T content) {}

// Ignore the extracted component
switch (box) {
    case Box(RedBall _)   -> processBox(box);
    case Box(BlueBall _)  -> processBox(box);
    case Box(GreenBall _) -> stopProcessing();
}

// Bare _ as catch-all for remaining component types
switch (box) {
    case Box(RedBall _) -> processRed();
    case Box(_)         -> processOther();   // matches any remaining Ball type
}

// Multi-pattern case — unnamed required when grouping patterns
switch (box) {
    case Box(RedBall _), Box(BlueBall _) -> processColored();
    case Box(GreenBall _)                -> stopProcessing();
}

// Selective component extraction from multi-field record
record Order(String id, String customerId, double amount, String status) {}

switch (order) {
    case Order(var id, _, _, "GOLD")   -> applyGoldDiscount(id);
    case Order(var id, _, _, "SILVER") -> applySilverDiscount(id);
    case Order(var id, _, _, _)        -> noDiscount(id);
}
```

---

## 3. Interaction with Sealed Types — Exhaustiveness

Unnamed patterns still satisfy exhaustiveness checks. The compiler tracks coverage by type, not by whether a binding variable was declared.

```java
sealed interface Shape permits Circle, Rectangle, Triangle {}
record Circle(double radius)           implements Shape {}
record Rectangle(double w, double h)   implements Shape {}
record Triangle(double b, double h)    implements Shape {}

// EXHAUSTIVE — no default needed
String describe(Shape s) {
    return switch (s) {
        case Circle _    -> "round";
        case Rectangle _ -> "rectangular";
        case Triangle _  -> "triangular";
    };
}

// Safe: adding a new permitted type causes a COMPILE ERROR (unlike default which silently swallows it)
```

**`case _` vs `default`:**
- `case _` at the top level of a switch is **NOT allowed** (compile error) — use `default`
- Bare `_` INSIDE a record pattern component is allowed: `Box(_)`

---

## 4. What `_` Is NOT Allowed For

| Forbidden | Reason |
|---|---|
| `log(_.getMessage())` — reading after catch | Not in scope |
| `_ = other()` — writing after declaration | Not in scope |
| `obj instanceof _` — top-level unnamed pattern | Must have a type |
| `case _` — top-level switch case | Use `default` instead |
| `case Box(A a), Box(B b)` — named var in multi-pattern | None of the patterns may bind variables |
| `void process(String _)` — method parameter | Non-goal, not supported |
| `private int _;` — field declaration | Non-goal, not supported |
| `void _() {}` — method named `_` | Illegal since Java 9 |

---

## 5. Key Differences at a Glance

| Before Java 22 | After Java 22 |
|---|---|
| `catch (NumberFormatException ignored)` | `catch (NumberFormatException _)` |
| `case RedBall rb -> process(ball)` (rb unused) | `case RedBall _ -> process(ball)` |
| `(k, v) -> "DEFAULT"` (v unused) | `(k, _) -> "DEFAULT"` |
| `for (Order order : orders) total++` | `for (Order _ : orders) total++` |
| `try (ScopedContext ctx = ...)` (ctx unused) | `try (var _ = ...)` |
| `if (obj instanceof String s)` (s unused) | `if (obj instanceof String _)` |

---

## Exercise

See `UnnamedVariablesExercise.java` in this folder.

The exercise covers:
1. Catch block — exception unused, replaced with `_`
2. Enhanced for loop — element unused
3. Lambda — parameter unused
4. Switch type patterns — binding variable unused
5. Switch record patterns — ignore all fields
6. Switch record patterns — ignore specific fields selectively
7. Multi-pattern case labels with unnamed patterns
8. Sealed types + exhaustiveness with unnamed patterns
