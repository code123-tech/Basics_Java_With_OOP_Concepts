package org.example.VirtualThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * Traditional (platform threads) vs Virtual Threads benchmark.
 *
 * <p>What it does per task:</p>
 * <ol>
 * <li> CPU work: sum of primes up to a random limit</li>
 * <li> Blocking work: Thread.sleep(IO_SLEEP_MS) to simulate I/O wait</li>
 *</ol>
 *
 * <p>Notes:</p>
 * <ul>
 * <li> Keep prints low; printing dominates timings.</li>
 * <li> Run on JDK 21+ (virtual threads).</li>
 * </ul>
 */
public class TraditionalVsVirtualBenchmark {

    private static final int TASK_COUNT_PLATFORM = 10_000;
    private static final int TASK_COUNT_VIRTUAL = 100_000;

    private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int IO_SLEEP_MS = 10;

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        System.out.println("=== Traditional vs Virtual Threads Benchmark ===");
        System.out.println("CPU Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Platform tasks: " + TASK_COUNT_PLATFORM);
        System.out.println("Virtual tasks: " + TASK_COUNT_VIRTUAL);
        System.out.println("Platform pool size: " + POOL_SIZE);
        System.out.println("IO sleep time (ms): " + IO_SLEEP_MS);

        long platformMs = runPlatformThreads(TASK_COUNT_PLATFORM);
        System.out.println("Platform threads total time (ms): " + platformMs);

        long virtualThreadsMs = runVirtualThreads(TASK_COUNT_VIRTUAL);
        System.out.println("Virtual threads total time (ms): " + virtualThreadsMs);
    }

    private static long runPlatformThreads(int taskCount) {

        long start = System.currentTimeMillis();
        try(ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE)) {
            List<Future<?>> futures = new ArrayList<>(taskCount);

            for(int i = 0; i < taskCount; i++){
                final int taskId = i;
                futures.add(pool.submit(() -> processTask(taskId)));
            }

            for (Future<?> f : futures) {
                waitForTask(f);
            }

            return System.currentTimeMillis() - start;
        }

    }

    private static long runVirtualThreads(int taskCount) {

        long start = System.currentTimeMillis();

        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<?>> futures = new ArrayList<>();

            for(int i = 0; i < taskCount; i++){
                final int taskId = i;
                futures.add(executor.submit(() -> processTask(taskId)));
            }

            for (Future<?> f : futures) {
                waitForTask(f);
            }

            return System.currentTimeMillis() - start;
        }
    }

    private static void processTask(int taskId) {

        int limit = RANDOM.nextInt(5000) + 1000;
        int primeSum = calculateSumOfPrimes(limit);

        blockOperation();

        if(taskId % 2 == 0) {
            System.out.printf("Task %d processed: sumPrimes(upTo=%d)=%d | thread=%s | isVirtual=%s%n",
                    taskId, limit, primeSum, Thread.currentThread(), Thread.currentThread().isVirtual());
        }
    }


    private static int calculateSumOfPrimes(int limit){

        return IntStream.rangeClosed(2, limit)
                .filter(TraditionalVsVirtualBenchmark::isPrime)
                .sum();
    }

    private static boolean isPrime(int number) {

        if(number < 2) {
            return false;
        }

        int max = (int) Math.sqrt(number);
        for(int i = 2; i <= max; i++){
            if(number % i == 0){
                return false;
            }
        }
        return true;
    }

    private static void blockOperation() {

        try {
            Thread.sleep(IO_SLEEP_MS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private static void waitForTask(Future<?> f) {

        try {
            f.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }

    }

}
