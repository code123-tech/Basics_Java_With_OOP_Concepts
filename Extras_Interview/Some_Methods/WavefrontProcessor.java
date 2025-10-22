package Extras_Interview.Some_Methods;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;

public class WavefrontProcessor {

    private final boolean[] rowReady;
    private final int[][] data;
    private final int numThreads;

    public WavefrontProcessor(int[][] data){
        this.data = copyMatrix(data);
        this.rowReady = new boolean[this.data.length];
        this.numThreads = Runtime.getRuntime().availableProcessors();
    }

    private int[][] copyMatrix(int[][] original) {

        int[][] copy = new int[original.length][];
        for(int i = 0; i < original.length; i++){
            copy[i] = original[i].clone();
        }
        return copy;
    }

    public void processRow(int rowIndex) {

        if(rowIndex > 0) {
            while (!rowReady[rowIndex - 1]){
                Thread.yield();
            }
        }

        for(int col = 0; col < data[rowIndex].length; col++) {
            if(data[rowIndex][col] != 0) {
                data[rowIndex][col] = computeMinWithNeighbours(rowIndex, col);
            }
        }

        rowReady[rowIndex] = true;
        System.out.println("Row " + rowIndex + " completed by " + Thread.currentThread().getName());
    }

    private int computeMinWithNeighbours(int row, int col) {

        int mini = data[row][col];

        for(int dr = -1; dr <= 1; dr++){
            for(int dc = -1; dc <= 1; dc++){

                int newRow = row + dr;
                int newCol = col + dc;

                if(isValidCell(newRow, newCol) && data[newRow][newCol] != 0){
                    mini = min(mini, data[newRow][newCol]);
                }
            }
        }

        return mini;
    }

    private boolean isValidCell(int row, int col){
        return row >= 0 && row < data.length && col >= 0 && col < data[0].length;
    }

    public void processAllRows() {

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        System.out.println("Starting wavefront processing with " + numThreads + " threads");
        System.out.println("Original matrix:");
        printMatrix();

        for(int i = 0; i < data.length; i++){
            final int rowIndex = i;
            executor.submit(() -> processRow(rowIndex));
        }

        executor.shutdown();

        try {
            if(!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                System.err.println("Processing timed out!");
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }

        System.out.println("\nFinal Processing matrix:");
        printMatrix();
    }

    public void printMatrix() {
        for(int[] row: data) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    public static void main(String[] args) {

        System.out.println("=== Wavefront Pattern Demo ===");
        System.out.println("This demonstrates when Thread.yield() is actually useful!");
        System.out.println();

        int[][] testMatrix = {
                {1, 0, 0, 0, 5}, {2, 3, 0, 0, 6}, {0, 4, 0, 7, 0}, {0, 0, 8, 9, 0}, {0, 0, 10, 11, 12}
        };

        WavefrontProcessor processor = new WavefrontProcessor(testMatrix);

        long startTime = System.currentTimeMillis();
        processor.processAllRows();
        long endTime = System.currentTimeMillis();

        System.out.println("Processing completed in " + (endTime - startTime) + "ms");

        System.out.println("\n=== Comparison With vs Without yield() ===");
    }

}
