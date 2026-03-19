package org.example.FinalIntegration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Check Readme.md to get context
record StockData(String ticker, String company, double price, double change, String trend) {}

sealed interface ApiResponse permits ApiResponse.Success, ApiResponse.Failure {
    record Success(StockData data)                          implements ApiResponse {}
    record Failure(String ticker, String reason, int code)  implements ApiResponse {}
}

class MockStockApi {

    private static final Random RANDOM = new Random();

    private static final List<StockData> MOCK_DATA = List.of(
            new StockData("AAPL",  "Apple Inc.",          182.50,  +1.23, "BULLISH"),
            new StockData("GOOGL", "Alphabet Inc.",        140.30,  -0.85, "BEARISH"),
            new StockData("MSFT",  "Microsoft Corp.",      415.20,  +2.10, "BULLISH"),
            new StockData("AMZN",  "Amazon.com Inc.",      185.60,  +0.45, "NEUTRAL"),
            new StockData("TSLA",  "Tesla Inc.",           172.80,  -3.50, "BEARISH"),
            new StockData("NVDA",  "NVIDIA Corp.",         875.40,  +8.75, "BULLISH"),
            new StockData("META",  "Meta Platforms Inc.",  505.10,  +1.90, "BULLISH"),
            new StockData("NFLX",  "Netflix Inc.",         628.00,  -1.20, "NEUTRAL")
    );

    static ApiResponse fetch(String ticker) {

        // Simulate network latency (50–200ms)
        try {Thread.sleep(50 + RANDOM.nextInt(150));} catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        // Simulate ~20% failure rate
        if (RANDOM.nextInt(5) == 0) {
            return new ApiResponse.Failure(ticker, "Connection timeout", 503);
        }

        Optional<StockData> response = MOCK_DATA.stream()
                .filter(s -> s.ticker().equals(ticker))
                .findFirst();

        return response.isPresent()? new ApiResponse.Success(response.get()): new ApiResponse.Failure(ticker, "Ticker not found", 404);
    }
}


class ResultStore {

    private static final int MAX_SIZE = 5;
    private static final LinkedList<StockData> results = new LinkedList<>();  // SequencedCollection

    synchronized void addData(StockData data) {
        results.addLast(data);
        if(results.size() > MAX_SIZE) {
            results.removeFirst();
        }
    }

    synchronized void printSummary() {

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║       RESULT STORE — Last " + MAX_SIZE + " Successful Fetches   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        if (results.isEmpty()) {
            System.out.println("  (no results)");
            return;
        }

        System.out.println("  Oldest : " + formatStock(results.getFirst()));
        System.out.println("  Newest : " + formatStock(results.getLast()));

        System.out.println("\n  All results (newest → oldest via reversed()):");

        for(StockData s: results.reversed()) {
            System.out.println("    " + formatStock(s));
        }
    }

    private String formatStock(StockData data) {
        String arrow = data.change() >= 0 ? "▲" : "▼";
        return String.format("%-6s | %-22s | $%7.2f | %s%.2f | %s", data.ticker(), data.company(), data.price(), arrow, Math.abs(data.change()), data.trend());
    }

}

public class ModernArchitectApp {

    private static final List<String> TICKERS = List.of("AAPL", "GOOG", "MSFT", "AZM", "TSLA", "NVDA", "META", "NFLX");

    public static void main(String[] args) throws Exception {

        ResultStore store = new ResultStore();

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║       Modern Architect — Stock Fetcher           ║");
        System.out.println("║  Fetching " + TICKERS.size() + " stocks via Virtual Threads...        ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        List<Future<ApiResponse>> futures = new ArrayList<>();

        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
            for(String ticker : TICKERS){
                futures.add(executorService.submit(() -> MockStockApi.fetch(ticker)));
            }
        }

        System.out.println("  Processing responses:\n");

        for(Future<ApiResponse> future : futures){

            ApiResponse response = future.get();

            switch (response) {
                case ApiResponse.Success(StockData stockData) -> {
                    String verdict = classifyStock(stockData);
                    System.out.printf("  ✔ %-6s | $%7.2f | %s | → %s%n",
                            stockData.ticker(), stockData.price(), stockData.trend(), verdict);
                    store.addData(stockData);
                }
                case ApiResponse.Failure(var ticker, var reason, var code) -> System.out.printf("  ✘ %-6s | ERROR %d: %s%n", ticker, code, reason);
            }
        }

        store.printSummary();
    }

    // Pattern Matching
    static String classifyStock(StockData stockData) {

        return switch (stockData) {

            case StockData(_, _, _, var change, var trend) when change > 5.0 && trend.equals("BULLISH")    -> "STRONG BUY";
            case StockData(_, _, _, var change, var trend) when change > 0   && trend.equals("BULLISH")    -> "BUY";
            case StockData(_, _, _, var change, var trend) when change < -2.0 && trend.equals("BEARISH")   -> "STRONG SELL";
            case StockData(_, _, _, _, var trend) when trend.equals("BEARISH")                             -> "SELL";
            default                                                                                        -> "HOLD";
        };
    }

}
