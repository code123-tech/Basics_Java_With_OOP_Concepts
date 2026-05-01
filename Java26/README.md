## New topics introduced

- 500:	[Prepare to Make Final Mean Final](https://openjdk.org/jeps/500) 
- 504:	[Remove the Applet API](https://openjdk.org/jeps/504) 
- 516:	[Ahead-of-Time Object Caching with Any GC](https://openjdk.org/jeps/516) 
- 517:	[HTTP/3 for the HTTP Client API](https://openjdk.org/jeps/517)
- 522:	[G1 GC: Improve Throughput by Reducing Synchronization](https://openjdk.org/jeps/522) 
- 524:	[PEM Encodings of Cryptographic Objects (Second Preview)](https://openjdk.org/jeps/524)
- 525:	[Structured Concurrency (Sixth Preview)](https://openjdk.org/jeps/525)
- 526:	[Lazy Constants (Second Preview)](https://openjdk.org/jeps/526)
- 529:	[Vector API (Eleventh Incubator)](https://openjdk.org/jeps/529)
- 530:	[Primitive Types in Patterns, instanceof, and switch (Fourth Preview)](https://openjdk.org/jeps/530)

Article to understand all: [Article](https://medium.com/@codefarm0/java-26-deep-dive-http-3-structured-concurrency-lazy-constants-more-334a8d076617)


### Lazy Constants (Preview feature)
Lazy constants allow values to be initialized only when they are first accessed.
This can reduce unnecessary computation during application startup.

```java
static final Lazy<String> VALUE =
        Lazy.of(() -> computeExpensiveValue());
```

Benefits:
- Reduced startup time 
- Better memory efficiency 
- Deferred computation

### Primitive Types in Patterns (Preview feature)
JDK 26 expands pattern matching to support primitive types in constructs like:
- instanceof
- switch

```java
switch (value) {
    case int i -> System.out.println("Integer: " + i);
    case double d -> System.out.println("Double: " + d);
}
```

This improves code clarity and reduces boilerplate.
