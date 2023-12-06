package FunctionalPrgramming;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class main {
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ints.forEach(i -> System.out.println(i));
        /**
         * What is the good approach to be followed from below two?
         * Filter out elements which are even, and above 5
         */
        // Approach 1:
        ints.stream().filter(i -> i % 2 == 0 && i > 5).forEach(i -> System.out.println(i));

        // Approach 2: by creating predicate for each condition
        Predicate<Integer> isEven = i -> i % 2 == 0;
        Predicate<Integer> isAbove5 = i -> i > 5;
        // Anonymous class lambda expression for Predicate
        Predicate<Integer> isLessThanEqual5 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer <= 5;
            }
        };

        ints.stream().filter(isEven.and(isLessThanEqual5)).forEach(i -> System.out.println(i));

        /**
         * Answer: Approach 2 is better.
         * 1. Suppose we want elements less than equal to 5.
         * We can just do, isAbove5.negate(), instead of that short-circuiting approach 1 (Internal structure is getting changed).
         */


        /**
         * Function functional interface
         * Used Mostly with
         * 1. map() on Stream API
         * 2. computeIfAbsent() on Map
         */
        // 1. map() on Stream API: Need to convert string to Integer.
        List<String> strings = Arrays.asList("1", "2", "3", "4", "5");

        // Anonymous class lambda expression for Function
        Function<String, Integer> atoi = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };
        // Above anonymous class lambda expression can be written as below:
        strings.stream().map(s -> Integer.parseInt(s)).forEach(i -> System.out.println(i));

        // 2. computeIfAbsent() on Map
        Map<String, Integer> numbersMap = new HashMap<>(Map.of(
                "Two", "Two".length(),
                "THREE", "THREE".length(),
                "FOUR", "FOUR".length(),
                "FIVE", "FIVE".length()
        ));
        numbersMap.computeIfAbsent("ONE", s -> s.length());
        // breaking the above code
        /**
         * Function<String, Integer> function = new Function<String, Integer>() {
         *        @Override
         *        public Integer apply(String s) {
         *            return s.length();
         *        }
         *  };
         */


        /**
         * BiFunction functional interface
         * Takes two arguments and returns one value.
         */
        numbersMap.replaceAll((key, value) -> value*value);
        /**
         * Breaking above code
         * BiFunction<String, Integer, Integer> biFunction = new BiFunction<String, Integer, Integer>() {
         *            @Override
         *            public Integer apply(String s, Integer integer) {
         *                 return integer*integer;
         *            }
         *  };
         */


    }
}
