# Basics_Java_With_OOP_Concepts — Claude Guide

Java fundamentals, OOP, and a feature tour across Java 8 → Java 26.

## Structure

```
OOPs/                  # Core OOP concepts
Java8/ … Java26/       # Feature folders per Java version
  Concurrency/         # (Java8) threads, executors, etc.
  Files/               # (Java8) NIO/file APIs
  Serialization/       # (Java8) serialization mechanics
  FunctionalPrgramming/# (Java8) lambdas, streams
Extras_Interview/      # Misc interview prep
MavenBuildTool.txt     # Maven reference notes
```

Each `JavaN/` folder contains a mix of standalone `.java` files (one concept per file, e.g. `StreamApiJava8.java`, `OptionalClassJava8.java`) and sub-packages for bigger topics.

## Conventions

- **One feature per file** named `<Feature>Java<N>.java` when the feature is version-specific (e.g., `LambdaFunctionsJava8.java`, `ForEachLoopJava8.java`). Version-agnostic topics drop the suffix (e.g., `Generics.java`).
- **Every file is runnable** — each has a `main` method demonstrating the feature.
- **Comments are teaching-oriented**: explain *why* the feature exists and what it replaces.
- The top-level `Basics_Java_With_OOP_Concepts.iml` is an IntelliJ module file — don't edit by hand.

## When adding a Java version feature

1. Drop the file in the correct `JavaN/` folder.
2. If it's a substantial topic (multiple classes), make a sub-package.
3. Update `README.md` only if adding a new top-level section — per-file additions don't need README edits.

## When adding OOP content

Place it in `OOPs/` with a file name that describes the concept (e.g., `Abstraction.java`). Keep examples small and focused.

## Cross-repo links

- Design patterns and SOLID → see `../Low-Level-Design-Questions/`
- Singleton pattern specifically → see `../Low-Level-Design-Questions/creational_Desing_pattern/Singleton/`
