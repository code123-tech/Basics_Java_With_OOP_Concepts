package Serialization;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A stream does not store data and, in that sense, is not a data structure. It also never modifies the underlying data source
 * streams are wrappers around a data source, allowing us to operate with that data source and making bulk processing convenient and fast.
 * Read: https://stackify.com/streams-guide-java-8/
 * Collection V/S Stream: https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#:~:text=No%20storage.,Functional%20in%20nature.
 * Interview: https://www.java67.com/2018/10/java-8-stream-and-functional-programming-interview-questions-answers.html
 */
public class StreamApiJava8 {
    public static void main(String[] args) {
        // Stream Creation.
        String[] strings = new String[]{"Ram", "Shyam", "Mohan", "Rohan"};

        // 1. From Existing array.
        Stream<String> streamStrings = Stream.of(strings);

        // 2. Obtain streams from existing list. stream() method on collection Interface.
        List<String> stringCollections = Arrays.asList(strings);
        Stream<String> streamStrings1 = stringCollections.stream();

        // 3. Using Stream Builder
        Stream.Builder<String> stringBuilder = Stream.builder();
        stringBuilder.accept(strings[0]);
        stringBuilder.accept(strings[1]);
        Stream<String> stringStream2 = stringBuilder.build();

        // Operations on Stream
        // 1. ForEach Method.
        streamStrings.forEach(el -> System.out.println(el));

        // Collections V/S Stream
        // 1. Collection stores elements, but stream does not store (Stream is not a Data Structure)

        // 2. Stream element (once used), can not be used again, whereas collection element can be used more than once.
        // Once a stream is consumed, it can not be consumed again.
        // will get Exception like: stream has already been operated upon or closed
        try {
            streamStrings.forEach(el -> System.out.println(el));
        }catch (Exception e){
            System.out.println(e);
        }

        // 3. Any Changes made on stream obtained from a collection, does not actually changes the original source.
        Stream<String> filteredStrings = streamStrings1.filter(el -> el.length() > 4);

        // 4. Collections are finite in size, whereas stream are not.

        // Internal Methods: The methods which support more further stream operation, and allows to create pipeline.
        // terminal Methods: The methods which stops the Stream Pipeline.
        // Ex: stream().map(String:toUpperCase).filter(el -> el.includes("A").count();
        // Here map(), filter() are Internal Methods, and count(), forEach() are Terminal Methods.

        /**
         * Stream Operations are always Lazy Operations, until they don't find a terminal, they do not perform action.
         * In above, example, count() function asks to filter to give result, and filter asks map to provide the streams, and map checks the actual stream pointing to some Collection or Array.
         * Watch this: https://www.youtube.com/watch?v=ekFPGD2g-ps&ab_channel=Devoxx
         * Power of Stream and Lambda Functions.
         */
        List<Integer> numbers = Arrays.asList(1, 2, 3, 5, 7, 4);
        // find the double of first number which is greater than 3 and even.
        // 1. Imperative method.
        Integer result = 0;
        for(Integer el: numbers){
            if(el > 3 && el%2 == 0){
                result = el*2;
                break;
            }
        }
        System.out.println("Result from Imperative Pro: " + result);
        // 2. Using Function Programming
        Integer resultFromFP = numbers.stream().filter(el -> el > 2).filter(el -> el%2 == 0).map(el -> el*2).findFirst().orElse(0);
        System.out.println("Result from Functional Pro: " + resultFromFP);

        // The first method does stop until, it find the required result, or stops only when list numbers are iterated and we could not find the required result.

        // In Second Method, which is Lazy evaluation, it goes to first filter, it says we will do it later,
        // goes to second, will do it later
        // goes to map function, will do it later
        // at the end when terminal method comes, now it asks from its parent, then parent asks result from its parent, and so on.

        /**
         * Parallel Stream: https://www.youtube.com/watch?v=J7YqYlaev7g&ab_channel=JavaTechie
         */


        // Example of normal
        int num = 3;
        int temp = compute(num);
        if(num > 3 && temp > 7){
            System.out.println("Path 1");
        }else {
            System.out.println("Path 2");
        }
        // In the above case, is compute() method will be called.
        // YES, Why we wasted time on calling compute(), when it is not being of use in the code.
        // That's where, we come into the picture of Lambda functions which provides Lazy Evaluation.
        // Lambda are stateless, they are just pure functions.
        // 2. Lazy Evaluation
        Supplier<Integer> number = () -> compute(num);
        if(num > 3 && number.get() > 7){
            System.out.println("Path 1");
        }else {
            System.out.println("Path 2");
        }

        // Did you check the output:
        /*
            Result of first evaluation
            Called
            Path 2

            Result of second evaluation
            Path 2

         */
        // As you can see, the in second lazy evaluation, compute method is not executed, as it was of no use to call it.
        // This improves the performance of the applications.
//        numbers.stream().map(el -> el).collect(Collectors.toList())
    }

    public static int compute(int num){
        System.out.println("Called");
        return num*2;
    }
}
