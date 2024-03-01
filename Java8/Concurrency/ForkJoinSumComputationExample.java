package Java;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSumComputationExample {
    private static final int[] arr = largeArr();
    private static final int length = arr.length;

    public static void main(String[] args) {
        // 1. task creation
        RecursiveSumTask recursiveSumTask = new RecursiveSumTask(0, length, arr);
        // 2. submit task to forkJoinPool
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        long start = System.currentTimeMillis();
        long sum = forkJoinPool.invoke(recursiveSumTask);
        System.out.println("The sum is : "
                + sum
                + ", Time Taken by Parallel(Fork/Join) Execution: "
                + (System.currentTimeMillis() - start) + " millis");
    }

    static class RecursiveSumTask extends RecursiveTask<Long> {
        private static final int SEQUENTIAL_COMPUTE_THRESHOLD = 4000;
        private final int startIndex;
        private final int endIndex;
        private final int[] data;

        RecursiveSumTask(int startIndex, int endIndex, int[] data) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.data = data;
        }

        @Override
        protected Long compute() {
            if(SEQUENTIAL_COMPUTE_THRESHOLD >= (endIndex  - startIndex)){
                long sum = 0;
                for(int i = startIndex; i < endIndex; i++){
                    sum += data[i];
                }
                return sum;
            }
            int mid = startIndex + (endIndex - startIndex)/2;
            RecursiveSumTask left = new RecursiveSumTask(startIndex, mid, data);
            RecursiveSumTask right = new RecursiveSumTask(mid, endIndex, data);
            left.fork();
            long rightSum = right.compute();
            return rightSum + left.join();  // join is a blocking call, current thread waits until left task is not done.
        }
    }
    private static int[] largeArr() {
        return new Random().ints(500000000, 10, 1000).toArray();
    }
}
