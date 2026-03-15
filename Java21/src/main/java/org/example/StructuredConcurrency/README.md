# Structured Concurrency (JEP 453) — Java 21 Preview

## What Is It?

Structured Concurrency treats a **group of related tasks running in different threads as a single unit of work**.
It ensures subtasks are properly coordinated — the parent task waits for all subtasks to finish (or applies a shutdown policy), before proceeding.

> Defined in JEP 453 as a Preview Feature in Java 21. Requires `--enable-preview` flag.

---

## Why Use It?

Without structured concurrency, managing multiple concurrent tasks is brittle:

- If one thread fails, others keep running (resource leak)
- Cancellation must be wired manually
- Stack traces are disconnected — hard to debug

Structured Concurrency solves all of this:

| Problem                  | Solution                                      |
|--------------------------|-----------------------------------------------|
| Leaked threads on failure | Shutdown policies cancel siblings             |
| Manual cancellation       | Automatic via `ShutdownOnFailure/Success`     |
| Unreadable thread dumps   | `jcmd` shows full task scope hierarchy        |
| Boilerplate error handling| `throwIfFailed()` propagates cleanly          |

---

## Core API: `StructuredTaskScope`

Located in `java.util.concurrent`. Always used with **try-with-resources** for automatic cleanup.

```java
try (var scope = new StructuredTaskScope<Object>()) {
    Subtask<String>  t1 = scope.fork(task1);   // fork subtasks
    Subtask<Integer> t2 = scope.fork(task2);

    scope.join();                               // wait for all

    // safe to access results here
    String  result1 = t1.get();
    Integer result2 = t2.get();
}                                               // auto-shutdown on exit
```

### Key Methods

| Method                         | Description                                          |
|--------------------------------|------------------------------------------------------|
| `scope.fork(callable)`         | Submits a subtask, returns a `Subtask<T>` handle     |
| `scope.join()`                 | Waits for all forked subtasks to complete            |
| `scope.join().throwIfFailed()` | Waits, then rethrows first exception if any          |
| `subtask.get()`                | Returns result (only safe after `join()`)            |
| `subtask.state()`              | `SUCCESS`, `FAILED`, or `UNAVAILABLE`                |

---

## Built-in Shutdown Policies

### 1. `ShutdownOnFailure` — All Must Succeed

Cancels all sibling subtasks as soon as **one fails**. Use for all-or-nothing operations.

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    var t1 = scope.fork(() -> fetchUser(1));
    var t2 = scope.fork(() -> fetchOrders(1));

    scope.join().throwIfFailed(); // cancels both if either throws

    return new Dashboard(t1.get(), t2.get());
}
```

### 2. `ShutdownOnSuccess` — First One Wins

Cancels remaining subtasks as soon as **one succeeds**. Use for race/fallback patterns.

```java
try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
    scope.fork(() -> fetchFromPrimaryDB());
    scope.fork(() -> fetchFromReplicaDB());

    scope.join();
    return scope.result(); // result of whichever finished first
}
```

---

## Concurrency Flow

```
main thread
│
├── scope.fork() ──► subtask1 (virtual thread)  ──┐
├── scope.fork() ──► subtask2 (virtual thread)  ──┤
│                                                  │
└── scope.join() ◄─────────────────────────────────┘
    (waits for both, ~max(t1, t2) time — not t1+t2)
```

Tasks run **in parallel**, so total time ≈ slowest task, not sum of all tasks.

---

## Basic Example: Load User Dashboard

Fetch user profile and orders concurrently, combine into a single result.

Can check class: `StructuredConcurrencyExercise.java`

---

## Custom Shutdown Policy

Extend `StructuredTaskScope` and override `handleComplete()` for custom coordination logic:

```java
public class CollectingScope<T> extends StructuredTaskScope<T> {

    private final Queue<Subtask<? extends T>> successful = new LinkedTransferQueue<>();
    private final Queue<Subtask<? extends T>> failed     = new LinkedTransferQueue<>();

    @Override
    protected void handleComplete(Subtask<? extends T> subtask) {
        if (subtask.state() == Subtask.State.SUCCESS) successful.add(subtask);
        else if (subtask.state() == Subtask.State.FAILED) failed.add(subtask);
    }

    public Stream<Subtask<? extends T>> successfulTasks() {
        super.ensureOwnerAndJoined(); // must call before accessing results
        return successful.stream();
    }

    public Stream<Subtask<? extends T>> failedTasks() {
        super.ensureOwnerAndJoined();
        return failed.stream();
    }
}
```

> Always call `ensureOwnerAndJoined()` before returning results — it verifies the current thread owns the scope and `join()` has completed.

---

## Compile & Run

```bash
# Compile
javac --enable-preview --release 21 StructuredConcurrencyDemo.java

# Run
java --enable-preview StructuredConcurrencyDemo
```

---

## Best Practices

1. Always use **try-with-resources** — ensures scope is shut down even on exceptions
2. Use `ShutdownOnFailure` for **all-or-nothing** parallel calls
3. Use `ShutdownOnSuccess` for **race / fallback** patterns
4. Call `.throwIfFailed()` after `join()` to propagate errors cleanly
5. Call `ensureOwnerAndJoined()` in custom scopes before exposing results
6. Combine with **virtual threads** for lightweight, scalable concurrency

---

# Structured Concurrency vs ForkJoin Framework

They look similar (`fork`/`join` naming) but solve **very different problems**.

## ForkJoin Framework (Java 7)

Designed for **CPU-intensive, recursive divide-and-conquer** tasks.

can read about ForkJoin framework at [ForkJoin](./../../../../../../../Java8/Concurrency/README.md)
```
Problem
├── subproblem1 (fork)
│   ├── subproblem1a (fork)
│   └── subproblem1b (fork)
└── subproblem2 (fork)
    └── ...
join all → combine result
```

- Splits **one big computation** into smaller pieces (e.g. merge sort, parallel streams)
- Uses a **work-stealing thread pool** — threads steal tasks from each other's queues
- All subtasks do the **same kind of work** (recursive, homogeneous)
- No built-in failure propagation — you handle it yourself
- Primarily for **data parallelism**

## Structured Concurrency (Java 21)

Designed for **I/O-bound, independent service calls** running concurrently.

```
Request
├── fetchUser()    (fork) → hits User DB
├── fetchOrders()  (fork) → hits Orders DB
└── fetchPayment() (fork) → hits Payment API

join → combine into response
```

- Coordinates **independent tasks** that do completely different things
- Uses **virtual threads** — one per task, very cheap
- Tasks are **heterogeneous** (different jobs, different return types)
- Built-in **failure propagation & cancellation** via shutdown policies
- Primarily for **task parallelism / service orchestration**

## Side-by-Side Comparison

|                   | ForkJoin                    | Structured Concurrency         |
|-------------------|-----------------------------|--------------------------------|
| Introduced        | Java 7                      | Java 21 (Preview)              |
| Purpose           | Recursive data splitting    | Concurrent service calls       |
| Task type         | Homogeneous (same work)     | Heterogeneous (different work) |
| Thread type       | Platform threads (pooled)   | Virtual threads                |
| Failure handling  | Manual                      | Built-in (`ShutdownOnFailure`) |
| Cancellation      | Manual                      | Automatic (sibling cancel)     |
| Use case          | Parallel streams, merge sort| API aggregation, dashboards    |
| Observability     | Poor                        | Rich (`jcmd` scope hierarchy)  |

## The Real Problem SC Solves

Before Structured Concurrency, doing concurrent I/O looked like this:

```java
// Old way — brittle, leaks threads on failure
Future<User>   userFuture   = executor.submit(() -> fetchUser(1));
Future<Orders> ordersFuture = executor.submit(() -> fetchOrders(1));

User   user   = userFuture.get();   // blocks
Orders orders = ordersFuture.get(); // blocks

// If fetchUser() throws → ordersFuture keeps running forever (leaked thread!)
// If thread is interrupted → no way to cancel the other
```

Structured Concurrency fixes exactly this:
- **Thread leak** — scope shutdown cancels all children automatically
- **Silent failures** — `throwIfFailed()` surfaces errors immediately
- **Debugging** — thread dumps show task hierarchy, not just flat thread list

## One-line Summary

> **ForkJoin** = split one computation into many pieces and recombine.
> **Structured Concurrency** = run many independent I/O tasks safely and combine results.

The `fork/join` naming in SC is coincidental — the concept is closer to **structured programming**
(`if/while` blocks have clear entry/exit) applied to **concurrency** (scope has clear start/end, all threads contained within it).

---

## References

- [JEP 453 — Structured Concurrency](https://openjdk.org/jeps/453)
- [Oracle Docs — Java 21 Structured Concurrency](https://docs.oracle.com/en/java/javase/21/core/structured-concurrency.html)
- [StructuredTaskScope Javadoc](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/StructuredTaskScope.html)
