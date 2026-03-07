package Java8.Concurrency;

import java.util.concurrent.*;

/**
 * CompletableFuture helps to make chain of tasks, and combine results asynchronously
 * It is an answer to `Future.get()` which blocks the thread to wait for result,
 * but CompletableFuture doesn't block the thread,
 * <h4>What it is</h4>
 * <ul>
 *     <li>
 *         <b>CompletableFuture</b> implements both:
 *          <ul>
 *              <li><b>Future</b> (represents a result that will be available later)</li>
 *              <li><b>CompletionStage</b> (lets you attach stages that run when the result is ready) </li>
 *          </ul>
 *     </li>
 *     <li>It can be completed explicitly using <b>complete(value)</b> or <b>completeExceptionally(ex)</b>
 *     </li>
 * </ul>
 *
 * <h4>What problems it solves</h4>
 * <ul>
 *     <li><b>No Blocking by default:</b> instead of calling get(), you attach "what to do next"</li>
 *     <li><b>Composition:</b>  chain async steps (thenCompose), combine results (thenCombine), and wait for many (allOf/anyOf) </li>
 *     <li>Clear Exception Handling</li>
 * </ul>
 *
 * <h4>Basic mental model (pipeline)</h4>
 * <ol>
 *     <li>start an async computation</li>
 *     <li>transform results</li>
 *     <li>combine with other async work</li>
 *     <li>handle errors</li>
 *     <li>end with a final action</li>
 * </ol>
 *
 * <pre>
 * import java.util.concurrent.CompletableFuture;
 *
 * public class Example {
 *     public static void main(String[] args) {
 *         CompletableFuture.supplyAsync(() -> "hello")     // start async
 *                 .thenApply(String::toUpperCase)          // transform
 *                 .thenAccept(System.out::println)         // consume
 *                 .exceptionally(ex -> {                   // handle error
 *                     ex.printStackTrace();
 *                     return null;
 *                 });
 *     }
 * }
 * </pre>
 * 
 * <h4>Exercise 1: Async Aggregator</h4>
 * <ul>
 *     <li>Simulate an async aggregator that fetches data from DB, Auth Data, and Inventory Data.</li>
 *     <li>Use thenCombine to combine the results of DB and Auth Data, and then combine the results of the combined result and Inventory Data.</li>
 *     <li>Use thenApply to combine the results of DB, Auth Data, and Inventory Data.</li>
 *     <li>Use allOf to wait for all the futures to complete.</li>
 *     <li>Use join to block the main thread until all the futures are complete.</li>
 * </ul>
 */
public class CompletableFutureHandsOn {

    public static void main(String[] args) {
        Exercise();
    }

    private static void Exercise() {
        System.out.println("Simulating Async Aggregator...");

        long startMs = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<String> dbFetch = CompletableFuture.supplyAsync(() -> fetchData("DB Data", 800, "DB_OK"), executorService);
        CompletableFuture<String> authDataFetch = CompletableFuture.supplyAsync(() -> fetchData("Auth Data", 500, "AUTH_OK"));
        CompletableFuture<String> inventoryDataFetch = CompletableFuture.supplyAsync(() -> fetchData("Inventory Data", ThreadLocalRandom.current().nextInt(100, 1000), "INVENTORY_OK"));


        // Go with Way A, and Way B in separate methods
        CompletableFuture<OrderSummary> a = wayA(dbFetch, authDataFetch, inventoryDataFetch);
        CompletableFuture<OrderSummary> b = wayB(dbFetch, authDataFetch, inventoryDataFetch);

        // Step 6: Block only at the end.
        CompletableFuture.allOf(a, b).join();

        System.out.println("\nWay A summary: " + a.join());
        System.out.println("Way B summary: " + b.join());
        System.out.println("Total time taken (ms): " + (System.currentTimeMillis() - startMs));
        executorService.shutdown();
    }

    private static String fetchData(String source, int waitingTime, String message) {

        System.out.printf("Fetching %s after: %s ms on thread: %s%n",
                source, waitingTime, Thread.currentThread().getName());
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        // Step 5: Make one service sometimes fail (Inventory).
        if ("Inventory Data".equals(source) && (System.currentTimeMillis() % 2 == 0)) {
            throw new RuntimeException("Inventory down");
        }
        return message;
    }

    private static CompletableFuture<OrderSummary> wayA(CompletableFuture<String> dbFetch,
                                                       CompletableFuture<String> authDataFetch,
                                                       CompletableFuture<String> inventoryDataFetch) {

        System.out.println("Way A: Fetching data from DB, Auth Data, and Inventory Data with thenCombine");

        return dbFetch
                .thenCombine(authDataFetch, DbAuth::new)
                .thenCombine(inventoryDataFetch, (dbAuth, inv) -> new OrderSummary(dbAuth.db, dbAuth.auth, inv))
                .handle((summary, ex) -> {
                    if (ex == null) return summary;
                    System.out.println("Way A failed: " + rootCause(ex));
                    return OrderSummary.fallback("WAY_A_FALLBACK", rootCause(ex).getMessage());
                });
    }

    private static CompletableFuture<OrderSummary> wayB(CompletableFuture<String> dbFetch,
                                                       CompletableFuture<String> authDataFetch,
                                                       CompletableFuture<String> inventoryDataFetch) {

        System.out.println("Way B: Fetching data from DB, Auth Data, and Inventory Data with thenApply (allOf)");
       
        return CompletableFuture
                .allOf(dbFetch, authDataFetch, inventoryDataFetch)
                .thenApply(v -> new OrderSummary(dbFetch.join(), authDataFetch.join(), inventoryDataFetch.join()))
                .handle((summary, ex) -> {
                    if (ex == null) return summary;
                    System.out.println("Way B failed: " + rootCause(ex));
                    return OrderSummary.fallback("WAY_B_FALLBACK", rootCause(ex).getMessage());
                });
    }

    private static Throwable rootCause(Throwable ex) {
        Throwable t = ex;
        if (t instanceof CompletionException && t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }

    private static final class DbAuth {
        private final String db;
        private final String auth;

        private DbAuth(String db, String auth) {
            this.db = db;
            this.auth = auth;
        }
    }

    private static final class OrderSummary {
        private final String db;
        private final String auth;
        private final String inventory;

        private OrderSummary(String db, String auth, String inventory) {
            this.db = db;
            this.auth = auth;
            this.inventory = inventory;
        }

        private static OrderSummary fallback(String label, String reason) {
            return new OrderSummary(label, "AUTH_UNKNOWN", "INVENTORY_UNKNOWN (" + reason + ")");
        }

        @Override
        public String toString() {
            return "OrderSummary{" +
                    "db='" + db + '\'' +
                    ", auth='" + auth + '\'' +
                    ", inventory='" + inventory + '\'' +
                    '}';
        }
    }

}

/*
Questions (quick interview checks)
- What is the difference between `Future` and `CompletableFuture`?
- When do you use `thenCompose()` vs `thenCombine()`?
- What is the default executor used by `*Async()` methods if you don’t pass one?
- What’s the difference between `exceptionally()`, `handle()`, and `whenComplete()`?
- Why is calling `get()` inside a pipeline considered a bad practice?

Answers (short)
- Difference between `Future` and `CompletableFuture`
  - `Future`: represents a value “later”, but isn’t naturally composable; you often block with `get()`.
  - `CompletableFuture`: a `Future` + `CompletionStage` pipeline; you attach stages (`thenApply/...`) and compose async work without blocking.

- When to use `thenCompose()` vs `thenCombine()`
  - `thenCompose`: when the next step returns another `CompletableFuture` (dependent async call). It “flattens” nested futures.
  - `thenCombine`: when you have two independent futures and want to combine results after both complete.

- Default executor used by `*Async()` methods (no executor provided)
  - `ForkJoinPool.commonPool()` (as per Java 8 `CompletableFuture` Javadoc).

- Difference between `exceptionally()`, `handle()`, and `whenComplete()`
  - `exceptionally(fn)`: runs only on failure; returns a recovery value.
  - `handle((value, ex) -> ...)`: runs on success or failure; returns a value (transform or recover).
  - `whenComplete((value, ex) -> ...)`: runs on success or failure for side effects (logging/metrics); keeps the same result/exception unless you throw.

- Why `get()` inside a pipeline is bad
  - It blocks threads (kills async benefits), can cause thread starvation/deadlocks on limited pools, and usually belongs only at the very end (or not at all).
*/
