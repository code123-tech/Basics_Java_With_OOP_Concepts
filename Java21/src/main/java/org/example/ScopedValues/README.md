# Scoped Values (JEP 446) — Java 21 Preview

## What Is It?

A **preview feature** in Java 21 that allows sharing immutable values down a call stack
**without passing them as method parameters** — like an invisible, read-only parameter
that flows through every method in the chain.

> Defined in JEP 446. Requires `--enable-preview` flag.

---

## The Problem It Solves

Imagine a web framework receiving a request. It needs `TenantID` and `UserID` accessible
deep inside service → repository → audit layers — without polluting every method signature:

```java
// Without any context carrier — method signature pollution
void serviceLayer(String tenantId, String userId) { ... }
void repositoryLayer(String tenantId, String userId) { ... }
void auditLayer(String tenantId, String userId) { ... }
```

The old solution was `ThreadLocal`. The new solution is `ScopedValue`.

---

## Core Concept

A `ScopedValue` is like **an implicit method parameter**:
- Bound once at the entry point
- Readable by any method in the call chain below
- **Automatically unbound** when the scope exits — no cleanup needed
- **Immutable** — callees can read but never modify it

---

## Key API

| Method | Description |
|---|---|
| `ScopedValue.newInstance()` | Creates an unbound scoped value |
| `ScopedValue.where(key, value)` | Creates a binding (returns `Carrier`) |
| `carrier.where(key, value)` | Chains multiple bindings |
| `carrier.run(Runnable)` | Runs with bindings active |
| `carrier.call(Callable)` | Runs with bindings, returns a result |
| `scopedValue.get()` | Reads the bound value |
| `scopedValue.isBound()` | Checks if currently bound |
| `scopedValue.orElse(default)` | Returns value or fallback if unbound |

---

## Basic Usage Pattern

```java
// 1. Declare — private static final, treated as a capability/key
private static final ScopedValue<String> TENANT_ID = ScopedValue.newInstance();
private static final ScopedValue<String> USER_ID   = ScopedValue.newInstance();

// 2. Bind at entry point and run
ScopedValue.where(TENANT_ID, "tenant-X")
           .where(USER_ID, "user-001")
           .run(() -> serviceLayer());
// Automatically unbound when run() exits

// 3. Read anywhere in the call chain
void serviceLayer()    { System.out.println(TENANT_ID.get()); repositoryLayer(); }
void repositoryLayer() { System.out.println(TENANT_ID.get()); auditLayer();      }
void auditLayer()      { System.out.println(TENANT_ID.get()); }
```

### Flow

```
handleRequest()
│
└── ScopedValue.where(TENANT_ID, "tenant-X").where(USER_ID, "user-001").run(...)
    │
    ├── serviceLayer()        → TENANT_ID.get() = "tenant-X" ✓
    │   └── repositoryLayer() → TENANT_ID.get() = "tenant-X" ✓
    │       └── auditLayer()  → TENANT_ID.get() = "tenant-X" ✓
    │
    └── run() exits → TENANT_ID automatically unbound ✓
```

---

## Nested Rebinding

Inner scope can temporarily override a value — outer value is automatically restored:

```java
ScopedValue.where(TENANT_ID, "tenant-A").run(() -> {
    TENANT_ID.get(); // "tenant-A"

    ScopedValue.where(TENANT_ID, "tenant-B").run(() -> {
        TENANT_ID.get(); // "tenant-B"
    });

    TENANT_ID.get(); // "tenant-A" — automatically restored
});
```

---

## ScopedValue vs ThreadLocal

| | `ThreadLocal` | `ScopedValue` |
|---|---|---|
| Mutability | Mutable — any code can `set()` | Immutable — write-once per scope |
| Lifetime | Until `remove()` is called | Auto-ends when `run()` exits |
| Child thread cost | Copies all values (expensive) | Shares by pointer (free) |
| Virtual thread friendly | No | Yes — designed for it |
| Memory leak risk | Yes (missing `remove()`) | No |
| Nested rebinding | Possible but messy | Clean — outer value auto-restored |

---

## Why ScopedValue Wins with Virtual Threads

With `ThreadLocal`, spawning child threads is expensive — each child **copies** all
parent thread-local values. With millions of virtual threads this kills memory.

With `ScopedValue`, child threads **share the parent binding by pointer** — no copy, zero overhead.

```java
Thread t1 = Thread.ofVirtual().start(() -> handleRequest("tenant-A", "user-101"));
Thread t2 = Thread.ofVirtual().start(() -> handleRequest("tenant-B", "user-202"));
// tenant-A and tenant-B contexts are fully isolated — no cross-contamination
```

---

## Best Practices

1. Declare as `private static final` — treat it as a capability/key
2. Bind multiple values using `.where().where().run()` in one chain
3. If passing many values, wrap in a `record` and bind a single `ScopedValue<Context>`
4. Never use for mutable state — use `ThreadLocal` for per-thread mutable caches
5. Combine with **Structured Concurrency** for safe context inheritance across virtual threads

---

## Compile & Run

```bash
javac --enable-preview --release 21 ScopedValuesExercise.java
java  --enable-preview ScopedValuesExercise
```

---

## Exercise

Check class: `ScopedValuesExercise.java`

---

## References

- [JEP 446 — Scoped Values](https://openjdk.org/jeps/446)
- [Oracle Docs — Java 21 Scoped Values](https://docs.oracle.com/en/java/javase/21/core/scoped-values.html)
- [ScopedValue Javadoc](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ScopedValue.html)
