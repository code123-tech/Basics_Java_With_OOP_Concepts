package org.example.VirtualThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class VirtualThreadBasics {

    public static void main(String[] args) throws InterruptedException {

        exercise1();
        exercise2();
    }


    private static void exercise1() throws InterruptedException {

        System.out.println("Exercise 1: Creating a virtual thread");

        Runnable task = () -> {

            Thread currentThread = Thread.currentThread();
            System.out.println("Thread: " + currentThread);
            System.out.println("Is current thread virtual? " + currentThread.isVirtual());
        };

        Thread virtualThread = Thread.startVirtualThread(task);
        virtualThread.join();

        System.out.println("Exercise 1: Done");
    }

    private static void exercise2() throws InterruptedException {

        exercise2A_StartVirtualThreadLoopAndJoin(10_000, 200);
        exercise2B_VirtualThreadPerTaskExecutor(10_000, 200);
        exercise2C_VirtualThreadFactoryWithPerTaskExecutor(10_000, 200);
        exercise2D_FixedPlatformThreadPool(10_000, 200, 200);
    }

    /**
     * Variant A: create N virtual threads directly, keep references, then join.
     */
    private static void exercise2A_StartVirtualThreadLoopAndJoin(int numberOfThreads, int sleepMs) throws InterruptedException {

        System.out.println("\nExercise 2A: Thread.startVirtualThread loop + join (numberOfThreads=" + numberOfThreads + ")");
        long startNs = System.nanoTime();

        List<Thread> threads = new ArrayList<>(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            final int idx = i;
            Thread t = Thread.startVirtualThread(sampleSleepTask("Sample thread", idx, sleepMs));
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

        long elapsedMs = (System.nanoTime() - startNs) / 1_000_000;
        System.out.println("Exercise 2A completed in (ms): " + elapsedMs);
    }

    /**
     * Variant B (recommended): one-task-per-virtual-thread executor.
     */
    private static void exercise2B_VirtualThreadPerTaskExecutor(int numberOfThreads, int sleepMs) {

        System.out.println("\nExercise 2B: Executors.newVirtualThreadPerTaskExecutor (numberOfThreads=" + numberOfThreads + ")");
        long startNs = System.nanoTime();

        try (var exec = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<?>> futures = new ArrayList<>(numberOfThreads);
            for (int i = 0; i < numberOfThreads; i++) {
                final int idx = i;
                futures.add(exec.submit(sampleSleepTask("Sample task", idx, sleepMs)));
            }

            for (Future<?> f : futures) {
                waitFor(f);
            }
        }

        long elapsedMs = (System.nanoTime() - startNs) / 1_000_000;
        System.out.println("Exercise 2B completed in (ms): " + elapsedMs);
    }

    /**
     * Variant C: explicit virtual-thread ThreadFactory + per-task executor.
     */
    private static void exercise2C_VirtualThreadFactoryWithPerTaskExecutor(int numberOfThreads, int sleepMs) {

        System.out.println("\nExercise 2C: Virtual ThreadFactory + Executors.newThreadPerTaskExecutor (numberOfThreads=" + numberOfThreads + ")");
        long startNs = System.nanoTime();

        ThreadFactory factory = Thread.ofVirtual().name("vt-", 0).factory();
        try (var exec = Executors.newThreadPerTaskExecutor(factory)) {
            List<Future<?>> futures = new ArrayList<>(numberOfThreads);
            for (int i = 0; i < numberOfThreads; i++) {
                final int idx = i;
                futures.add(exec.submit(sampleSleepTask("Sample task", idx, sleepMs)));
            }

            for (Future<?> f : futures) {
                waitFor(f);
            }
        }

        long elapsedMs = (System.nanoTime() - startNs) / 1_000_000;
        System.out.println("Exercise 2C completed in (ms): " + elapsedMs);
    }

    /**
     * Variant D (comparison): run the same workload on a fixed platform thread pool.
     *
     * Expected observation:
     * - With a fixed pool of size P, N blocking tasks roughly take about (N / P) * sleepMs time.
     * - With virtual threads, N blocking tasks can often complete close to ~sleepMs (plus overhead),
     *   because they don't require one OS thread per task.
     */
    private static void exercise2D_FixedPlatformThreadPool(int numberOfTasks, int sleepMs, int poolSize) {

        System.out.println("\nExercise 2D: Fixed platform thread pool (poolSize=" + poolSize + ", numberOfTasks=" + numberOfTasks + ")");
        long startNs = System.nanoTime();

        try (ExecutorService executor = Executors.newFixedThreadPool(poolSize)) {
            List<Future<?>> futures = new ArrayList<>(numberOfTasks);
            for (int i = 0; i < numberOfTasks; i++) {
                final int idx = i;
                futures.add(executor.submit(sampleSleepTask("Sample platform task", idx, sleepMs)));
            }

            for (Future<?> f : futures) {
                waitFor(f);
            }
        }

        long elapsedMs = (System.nanoTime() - startNs) / 1_000_000;
        System.out.println("Exercise 2D completed in (ms): " + elapsedMs);
    }

    private static void waitFor(Future<?> f) {

        try {
            f.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private static void sleepMillis(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private static Runnable sampleSleepTask(String label, int idx, int sleepMs) {
        return () -> {
            if (idx < 3) {
                Thread ct = Thread.currentThread();
                System.out.println(label + " " + idx + ": " + ct + " | isVirtual=" + ct.isVirtual());
            }
            sleepMillis(sleepMs);
        };
    }

}
