package Java8.Concurrency;

import java.util.concurrent.*;

/**
 * Java 8 timeouts with CompletableFuture (manual pattern).
 *
 * Topic: implement a timeout using ScheduledExecutorService + applyToEither
 * so the "work future" races a "timeout future", and whichever completes first wins.
 *
 * You will add the code here step-by-step.
 */
public class CompletableFutureTimeouts {

    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    static <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit timeUnit) {

        CompletableFuture<T> promise = new CompletableFuture<>();

        scheduler.schedule(() -> promise.completeExceptionally(new TimeoutException("Exception...")), timeout, timeUnit);

        return promise;
    }

    static <T> CompletableFuture<T> withTimeout(CompletableFuture<T> work, long timeout, TimeUnit timeUnit) {
        return work.applyToEither(timeoutAfter(timeout, timeUnit), x -> x);
    }

    static CompletableFuture<String> slowServiceCall(ExecutorService executor, long sleepMs){

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(sleepMs);
            } catch (InterruptedException e) {
                  throw new RuntimeException(e);
            }
            return "OK";
        }, executor);
    }

    public static void main(String[] args) {

        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {

            System.out.println("=== Case A: work finishes before timeout ===");
            CompletableFuture<String> fastWork = slowServiceCall(executor, 200);
            CompletableFuture<String> fastResult = withTimeout(fastWork, 500, TimeUnit.MILLISECONDS);

            System.out.println("Result: " + fastResult.join());

            System.out.println("\n=== Case B: timeout happens before work finishes ===");
            CompletableFuture<String> slowWork = slowServiceCall(executor, 1200);
            CompletableFuture<String> slowResult = withTimeout(slowWork, 300, TimeUnit.MILLISECONDS);

            try {
                System.out.println("Result: " + slowResult.join());
            } catch (CompletionException ex) {
                Throwable cause = ex.getCause();
                System.out.println("Timed out with: " + cause.getClass().getSimpleName() + " -> " + cause.getMessage());
            }

            System.out.println("\nNote: even if timeout wins, the slowWork may still keep running.");
        } finally {
            scheduler.shutdown();
        }

    }

}

