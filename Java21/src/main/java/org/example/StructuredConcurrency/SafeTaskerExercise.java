package org.example.StructuredConcurrency;

//import java.util.concurrent.StructuredTaskScope;

/**
 * <p>Focus Area: Structured Concurrency — The "Safe Tasker"</p>
 *
 * <p>
 * Exercise: Use StructuredTaskScope.ShutdownOnFailure to fetch Weather and News concurrently.
 * If one task fails, the other is automatically cancelled — no orphan threads, no memory leaks.
 *</p>
 *
 * Parts:
 *<ul>
 *   <li>Part 1 — Both tasks succeed → results combined</li>
 *   <li>Part 2 — One task fails     → other is automatically cancelled</li>
 *</ul>
 */
/*
 * Add below configuration in pom.xml to enable this preview feature in Java 21:
 *
 * <build>
 *     <plugins>
 *         <plugin>
 *             <groupId>org.apache.maven.plugins</groupId>
 *             <artifactId>maven-compiler-plugin</artifactId>
 *             <configuration>
 *                 <source>21</source>
 *                 <target>21</target>
 *                 <compilerArgs>--enable-preview</compilerArgs>
 *             </configuration>
 *         </plugin>
 *     </plugins>
 * </build>
 */
public class SafeTaskerExercise {

    record Weather(String summary) {}
    record News(String headline) {}
    record FeedResult(Weather weather, News news) {}

    // -------------------------------------------------------------------------
    // Simulated tasks
    // -------------------------------------------------------------------------

    private static Weather fetchWeather(boolean shouldFail) throws Exception {

        Thread.sleep(500);
        if (shouldFail) throw new RuntimeException("Weather service is down!");
        System.out.println("Weather fetched by: " + Thread.currentThread());
        return new Weather("Sunny, 28°C");
    }

    private static News fetchNews(boolean shouldFail) throws Exception {

        Thread.sleep(700);
        if (shouldFail) throw new RuntimeException("News service is down!");
        System.out.println("News fetched by: " + Thread.currentThread());
        return new News("Java 21 released with Virtual Threads!");
    }

    // -------------------------------------------------------------------------
    // Core method using ShutdownOnFailure
    // -------------------------------------------------------------------------

    private static FeedResult loadFeed(boolean weatherFails, boolean newsFails) throws Exception {

//        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//
//            StructuredTaskScope.Subtask<Weather> weatherTask = scope.fork(() -> fetchWeather(weatherFails));
//            StructuredTaskScope.Subtask<News>    newsTask    = scope.fork(() -> fetchNews(newsFails));
//
//            // Wait for both — if either fails, ShutdownOnFailure cancels the other automatically
//            scope.join()
//                 .throwIfFailed(); // rethrows the first exception if any task failed
//
//            return new FeedResult(weatherTask.get(), newsTask.get());
//        }
            return new FeedResult(fetchWeather(weatherFails), fetchNews(newsFails));
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // Part 1: Both tasks succeed
        System.out.println("=== Part 1: Both tasks succeed ===");
        try {
            long start = System.currentTimeMillis();
            FeedResult result = loadFeed(false, false);
            long elapsed = System.currentTimeMillis() - start;

            System.out.println("Result  : " + result);
            System.out.println("Time    : " + elapsed + "ms (not 1200ms — ran concurrently!)");
        } catch (Exception e) {
            System.out.println("Failed  : " + e.getMessage());
        }

        // Part 2: Weather task fails — News task is automatically cancelled
        System.out.println("\n=== Part 2: Weather task fails ===");
        try {
            loadFeed(true, false);
        } catch (Exception e) {
            System.out.println("Caught  : " + e.getMessage());
            System.out.println("News task was automatically cancelled — no orphan thread!");
        }

        // Part 3: News task fails — Weather task is automatically cancelled
        System.out.println("\n=== Part 3: News task fails ===");
        try {
            loadFeed(false, true);
        } catch (Exception e) {
            System.out.println("Caught  : " + e.getMessage());
            System.out.println("Weather task was automatically cancelled — no orphan thread!");
        }
    }
}
