package Java8.Concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Demonstrates the difference between:
 * - thenCompose(): use when the next step depends on the previous result AND returns another CompletableFuture
 *   (i.e., "async call -> async call", flattened into one pipeline).
 * - thenCombine(): use when you have two independent CompletableFutures running in parallel and want to merge results.
 *
 * This class prints thread names and uses small sleeps so the execution order is visible.
 */
public class ComposeAndCombineUsage {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            exerciseThenCompose(executor);
            exerciseThenCombine(executor);
        } finally {
            executor.shutdown();
        }
    }

    /**
     * thenCompose demo (dependent async chain):
     * fetchUserId(username) -> fetchUserProfile(userId)
     */
    private static void exerciseThenCompose(ExecutorService executor) {
        System.out.println("\n=== Exercise: thenCompose (dependent async chain) ===");

        CompletableFuture<String> profileFuture =
                fetchUserId("alex", executor)
                        .thenCompose(userId -> fetchUserProfile(userId, executor));

        String profile = profileFuture.join(); // join only at the end of this exercise
        System.out.println("Final profile: " + profile);
    }

    /**
     * thenCombine demo (parallel merge):
     * fetchWeather(city) + fetchNews(city) -> merged string
     */
    private static void exerciseThenCombine(ExecutorService executor) {
        System.out.println("\n=== Exercise: thenCombine (parallel merge) ===");

        CompletableFuture<String> weatherF = fetchWeather("Delhi", executor);
        CompletableFuture<String> newsF = fetchNews("Delhi", executor);

        CompletableFuture<String> merged =
                weatherF.thenCombine(newsF, (weather, news) -> "MERGED => " + weather + " | " + news);

        System.out.println(merged.join()); // join only at the end of this exercise
    }

    private static CompletableFuture<Integer> fetchUserId(String username, ExecutorService executor) {

        return CompletableFuture.supplyAsync(() -> {
            log("fetchUserId(" + username + ")");
            sleepRandom(200, 500);
            int userId = Math.abs(username.hashCode() % 10_000);
            log("fetchUserId result = " + userId);
            return userId;
        }, executor);
    }

    private static CompletableFuture<String> fetchUserProfile(int userId, ExecutorService executor) {

        return CompletableFuture.supplyAsync(() -> {
            log("fetchUserProfile(" + userId + ")");
            sleepRandom(300, 700);
            String profile = "UserProfile{id=" + userId + ", tier=GOLD}";
            log("fetchUserProfile result = " + profile);
            return profile;
        }, executor);
    }

    private static CompletableFuture<String> fetchWeather(String city, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            log("fetchWeather(" + city + ")");
            sleepRandom(200, 600);
            return "Weather{city=" + city + ", tempC=29}";
        }, executor);
    }

    private static CompletableFuture<String> fetchNews(String city, ExecutorService executor) {

        return CompletableFuture.supplyAsync(() -> {
            log("fetchNews(" + city + ")");
            sleepRandom(200, 600);
            return "News{city=" + city + ", headline='Metro expansion approved'}";
        }, executor);
    }

    private static void log(String msg) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + msg);
    }

    private static void sleepRandom(int minMsInclusive, int maxMsInclusive) {

        int ms = ThreadLocalRandom.current().nextInt(minMsInclusive, maxMsInclusive + 1);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}

