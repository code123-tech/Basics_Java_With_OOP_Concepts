**Local Variable Type Inference (var)**
   This is the feature you will see in 99% of Java 21 codebases. It allows you to stop repeating yourself when the type is obvious.
- **Java 8 (The Verbose Way)**
```java
Map<String, List<User>> userGroups = new HashMap<String, List<User>>();
```
- **Java 10 (The Concise Way)**
```java
var userGroups = new HashMap<String, List<User>>();
```

Rules for **var:**
- Use var only when the variable name clearly tells you what it is.

    **Good:** `var response = client.send(request, ...);`
    
    **Bad:** `var x = getThing(); (What is 'x'?)`

- You cannot use var for class fields, method parameters, or return types.


**G1 Garbage Collector Improvements**

In Java 8, the G1 Collector (now the default) had a weakness: its "Full GC" cycle was single-threaded. If your heap was 
large, a full GC could pause your app for a long time.

- **The Change:** Java 10 made the Full GC cycle parallel.
- **The Result:** Significant reduction in "Stop-the-World" pause times for large-scale applications.



**Container Awareness (The "Cloud-Native" fix)**
This is vital since you likely deploy in containers (Docker/K8s).

**The Problem in Java 8:** If you ran a Java 8 app in a Docker container with 2GB of RAM, the JVM would often "see" the 
RAM of the Host Machine (e.g., 64GB) instead of the container limit. It would then try to allocate a massive heap and 
get killed by the Docker OOM (Out of Memory) Killer.

**The Java 10 Solution:** The JVM was made "Container Aware." It now correctly identifies the CPU and RAM limits set 
by Docker/Kubernetes.


##### Questions
- Why `var name = null;` is not allowed? (Because the type cannot be inferred from null)
- Can you use `var` for method parameters? (No, it's only for local variables)
- What happens if you try to use `var` without initializing the variable? (It will cause a compilation error because the type cannot be inferred)
- How does the G1 Garbage Collector improve performance in Java 10? (By making the Full GC cycle parallel, reducing pause times for large heaps)
- What is the benefit of container awareness in Java 10? (It allows the JVM to correctly identify and respect the resource limits set by Docker/Kubernetes, preventing OOM errors)
- Can you use `var` for class fields? (No, `var` is only for local variables within methods)
- What is the default garbage collector in Java 10? (G1 Garbage Collector)
- How does Java 10's container awareness affect memory management? (It allows the JVM to adjust its memory usage based on the limits of the container, preventing it from trying to use more memory than is available)
- What happens if you try to use `var` for a variable that is initialized with a lambda expression? (The type will be inferred as the functional interface type of the lambda expression)
- Can you use `var` for a variable that is initialized with a method reference? (Yes, the type will be inferred as the functional interface type of the method reference)
- What happens if you try to use `var` for a variable that is initialized with an anonymous class? (The type will be inferred as the anonymous class type)
- Can you use `var` for a variable that is initialized with a primitive type? (Yes, the type will be inferred as the corresponding wrapper class type, e.g., `int` will be inferred as `Integer`)