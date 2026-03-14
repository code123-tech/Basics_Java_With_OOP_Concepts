package org.example.VirtualThreads;

import java.util.concurrent.CountDownLatch;

/**
 * Article exercise: compare Platform Threads vs Virtual Threads for blocking (I/O-like) work.
 *<p>
 * Based on the idea from:
 * <a href="https://blog.ycrash.io/java-project-loom-virtual-threads/">Article</a>
 *</p>
 *
 *
 * <ul>
 * SAFE DEFAULTS:
 *  Platform threads can crash your process with: OutOfMemoryError: unable to create new native thread </li>
 * <li> So this class starts with safer numbers. Increase counts using program arguments. </li>
 * </ul>
 *
 * <ul>
 * Args (optional):
 * <li> args[0] = number of platform threads to try (default 10_000) </li>
 * <li> args[1] = number of virtual threads to try   (default 50_000) </li>
 * <li> args[2] = sleepMs per task                   (default 1_000) </li>
 * </ul>
 */
public class VirtualThreadsArticleExercise {

    public static void main(String[] args) throws InterruptedException {

        int platformCount = args.length > 0 ? Integer.parseInt(args[0]) : 10_000;
        int virtualCount = args.length > 1 ? Integer.parseInt(args[1]) : 50_000;
        int sleepMs = args.length > 2 ? Integer.parseInt(args[2]) : 1_000;

        System.out.println("=== Virtual Threads | Article Exercise ===");
        System.out.println("Platform threads to try: " + platformCount);
        System.out.println("Virtual threads to try  : " + virtualCount);
        System.out.println("Sleep per task (ms)     : " + sleepMs);
        System.out.println("Available processors    : " + Runtime.getRuntime().availableProcessors());

        System.out.println("\n--- 1) Explore PLATFORM threads ---");
        explorePlatformThreads(platformCount, sleepMs);

        System.out.println("\n--- 2) Explore VIRTUAL threads ---");
        exploreVirtualThreads(virtualCount, sleepMs);

        System.out.println("\nMain done.");
    }

    private static void explorePlatformThreads(int count, int sleepMs) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(count);

        long startMs = System.currentTimeMillis();
        try {
            for (int i = 0; i < count; i++) {
                int idx = i;
                Thread t = new Thread(() -> {
                    try {
                        if (idx < 3) {
                            Thread ct = Thread.currentThread();
                            System.out.println("Sample platform thread " + idx + ": " + ct);
                        }
                        Thread.sleep(sleepMs);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                }, "pt-" + i);
                t.start();
            }

            latch.await();
            System.out.println("Platform threads completed. Time (ms): " + (System.currentTimeMillis() - startMs));
        } catch (OutOfMemoryError oom) {
            System.out.println("Platform threads FAILED with OOM: " + oom);
            System.out.println("Tip: lower platformCount, or reduce -Xss, or use virtual threads for blocking work.");
        }
    }

    private static void exploreVirtualThreads(int count, int sleepMs) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(count);

        long startMs = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            int idx = i;
            Thread.ofVirtual()
                    .name("vt-", i)
                    .start(() -> {
                        try {
                            if (idx < 3) {
                                Thread ct = Thread.currentThread();
                                System.out.println("Sample virtual thread " + idx + ": " + ct + " | isVirtual=" + ct.isVirtual());
                            }
                            Thread.sleep(sleepMs);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            latch.countDown();
                        }
                    });
        }

        // Important: virtual threads are daemon by default.
        // If main exits early, they may be stopped. Latch keeps main alive until all complete.
        latch.await();
        System.out.println("Virtual threads completed. Time (ms): " + (System.currentTimeMillis() - startMs));
    }
}

