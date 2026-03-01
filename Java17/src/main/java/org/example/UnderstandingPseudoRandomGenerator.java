package org.example;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * Enhanced Pseudo-Random Number Generators (Java 17) (JEP 356)
 * <p>
 * What it is:
 * - Java 17 introduced a modern random API under {@code java.util.random}.
 * - It provides a common interface ({@link RandomGenerator}) for many PRNG algorithms.
 * <p>
 * What problem it solves:
 * - You can choose different random algorithms in a consistent way (quality/performance/reproducibility).
 * - Easier generation of streams of random numbers (ints/longs/doubles).
 * - Better testing support via seeded generators (repeatable output).
 * <p>
 * Typical usage:
 * <pre>
 * RandomGenerator rng = RandomGenerator.getDefault();
 * int n = rng.nextInt(100);
 * </pre>
 * You can also choose an algorithm explicitly (by name) using {@code RandomGenerator.of("...")}.
 * <p>
 * Important note:
 * - This is NOT for cryptography. For security tokens/passwords use {@code java.security.SecureRandom}.
 */
public class UnderstandingPseudoRandomGenerator {

    public static void doExercise() {
        System.out.println("Enhanced Pseudo-Random Number Generators (Java 17)");

        // Hands-on will go here (seeded vs unseeded, algorithm choice, streams).
        RandomGenerator rng = RandomGenerator.getDefault();
        System.out.println("Sample nextInt(100): " + rng.nextInt(100));

        System.out.println("\nList of available random generator algorithms");
        // 1. List all available algorithms
        RandomGeneratorFactory.all()
                .map(factory -> factory.group() + " : " + factory.name())
                .forEach(System.out::println);

        List<String> algorithmNames = RandomGeneratorFactory.all().map(RandomGeneratorFactory::name)
                .toList();

        // 2. The Modern Way: Pick a "High Performance" algorithm
        String chosenAlgorithm = algorithmNames.get(ThreadLocalRandom.current().nextInt(algorithmNames.size()));
        System.out.println("\nChosen algorithm: " + chosenAlgorithm);
        RandomGenerator generator = RandomGenerator.of(chosenAlgorithm);

        // 3. Generate a stream of random numbers
        generator.ints(5, 1, 101)
                .forEach(n -> System.out.println("Random Number: " + n));
    }
}

