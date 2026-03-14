## Virtual Threads (Java 21) — Introduction

This introduction is based on the article: [Java Virtual Threads – Easy introduction (yCrash)](https://blog.ycrash.io/java-virtual-threads-quick-introduction/).

### What problem do Virtual Threads solve?
In many real applications, a thread spends most of its life **waiting**:
- waiting in a thread pool for work
- waiting for a DB/network response

That means the thread is doing “real work” only for short periods, and is idle/waiting most of the time.

### Platform Threads (classic threads)
- A **platform thread** maps **1:1** to an **OS thread**.
- Even when the thread is blocked/waiting, its OS thread is still “occupied”.
- OS threads are limited and expensive (memory + OS scheduling), so creating a lot of platform threads doesn’t scale well.

### Virtual Threads (Project Loom)
- A **virtual thread** is a lightweight thread managed by the JVM.
- A virtual thread runs on a platform thread called a **carrier thread** only while it is executing.
- When a virtual thread blocks/waits, it can be unmounted from the carrier and remain as a lightweight object in the Java heap (the article calls this a “stack chunk object”).

### Why Virtual Threads are useful (high-level benefits)
- Better throughput/availability for workloads with lots of blocking I/O
- Reduced chance of `OutOfMemoryError: unable to create new native thread`
- Reduced memory usage when your app needs a lot of concurrent “waiting” tasks
- You can keep the simple “one thread per request/task” style without writing complex async code everywhere

### How to create Virtual Threads (common APIs)
- `Thread.startVirtualThread(runnable)`
- `Thread.ofVirtual().start(runnable)`
- `Executors.newVirtualThreadPerTaskExecutor()`
- `Executors.newThreadPerTaskExecutor(ThreadFactory)`

## Project Loom / Virtual Threads — Key points from the article

This section summarizes: [Java Project Loom: Unlocking the Power of Java Virtual Threads](https://blog.ycrash.io/java-project-loom-virtual-threads/).

### When to use Virtual Threads
- Best for tasks that spend time **waiting / blocking**, like DB calls, REST calls, message broker operations.
- Not a big win for **CPU-intensive** work (heavy computation/serialization). CPU is still the limit.

### How Virtual Threads work (under the hood)
- Virtual threads are scheduled by the **JVM**, not the OS scheduler.
- Work is mounted onto a platform thread called a **carrier thread**.
- When a virtual thread blocks/waits, it can be **parked (unmounted)** and its state stored on the heap.
- When it is ready to continue, it is **unparked** and mounted again on a carrier thread.

### Important factors / pitfalls (especially for Java 21)
- **Daemon by default**: virtual threads are daemon threads, so the app may exit unless you wait (e.g., `join()`, `CountDownLatch`, waiting on futures).
- **Do not pool virtual threads** like platform threads; the model expects threads to be cheap to create.
- **Pinning**: if a virtual thread enters a `synchronized` block/method, it may become pinned and cannot be unmounted while blocked. This can reduce scalability if your code uses lots of intrinsic locking.

### Practical takeaway
- Virtual threads don’t make code “magically faster”, but they can make Java apps dramatically more scalable for **I/O-bound concurrency** while keeping code readable (blocking style).

