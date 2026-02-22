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