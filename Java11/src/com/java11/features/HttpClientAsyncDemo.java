package com.java11.features;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class HttpClientAsyncDemo {

    private HttpClientAsyncDemo() {}

    public static void run() {

        System.out.println();
        System.out.println("---- Modern HttpClient (async GET) ----");

        // HttpClient is immutable + thread-safe; keep one and reuse it.
        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
                .GET()
                .build();


         // client.send(request, HttpResponse.BodyHandlers.ofString()); // Synchronous call (blocks until response is received)

        var future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    var body = response.body();
                    if (!body.isBlank()) {
                        System.out.println("HTTP status (only if body not blank): " + response.statusCode());
                    }
                    return body;
                })
                .thenAccept(body -> System.out.println("Body preview: " + preview(body, 120)));

        // Block only at the end so this demo can finish before main exits.
        future.join();
    }

    private static String preview(String s, int maxChars) {

        if (s == null) return "null";
        if (s.length() <= maxChars) return s;
        return s.substring(0, maxChars) + "...";
    }
}
