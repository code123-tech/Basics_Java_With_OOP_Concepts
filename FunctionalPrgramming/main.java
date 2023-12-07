package FunctionalPrgramming;


import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
interface CustomFunction<T, U, P, R>{
    R apply(T t, U u, P p);
}
class Student{
    private Integer age;
    private String name;

    private String rollNo;
    public Student(Integer age){
        this.age = age;
    }

    public Student(Integer age, String name){
        this.age = age;
        this.name = name;
    }

    public Student(Integer age, String name, String rollNo){
        this.age = age;
        this.name = name;
        this.rollNo = rollNo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name=" + name  +
                ", rollNo=" + rollNo +
                '}';
    }
}
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

        /**
         * Method Reference
         * to print each integer element of a list using lambda expression
         * ints.forEach(i -> System.out.println(i));
         * ==> i -> System.out.println(i) is a lambda expression.
         * Condition: The end method we use as method reference should have same type and number of arguments
         * as the abstract method in functional interface.
         * In below case, System.out.println() method has one argument of type Integer.
         *
         * Syntax==>
         * ClassName::methodName (In case methodName is static)
         * classInstance::methodName (In case methodName is non-static)
         *
         * Why this is so much useful?
         * This helps in handling checked exceptions.
         * Suppose we have a method which throws checked exception, and we want to use that method in lambda expression.
         * We can't do that, as lambda expression does not allow checked exceptions.
         * But we can use method reference, as it allows checked exceptions.
         * For example. Thread.sleep() method throws InterruptedException, and we want to use it in lambda expression.
         */
        ints.forEach(System.out::println); // method reference.
        numbersMap.forEach(MethodReference::print);

        /**
         * Constructor Reference
         * Syntax:
         * ClassName::new
         *
         * Supplier, Function or any functional interface which returns some object, can be replaced by constructor reference.
         */
        Function<Integer, Student> studentFunction = age -> new Student(age);
        // Above can be replaced by constructor reference as below:
        Function<Integer, Student> studentFunction1 = Student::new; // for 1-arg constructor.

        BiFunction<Integer, String, Student> studentBiFunction = Student::new; // for 2-arg constructor.

        CustomFunction<Integer, String, String, Student> customFunction = Student::new; // for 3-arg constructor.

        System.out.println(studentFunction.apply(10));
        System.out.println(studentFunction1.apply(10));
        System.out.println(studentBiFunction.apply(10, "Sunil"));
        System.out.println(customFunction.apply(10, "Sunil", "12345"));
    }

    static class MethodReference{
        public static <K, V> void print(K key, V value){
            System.out.println("Key: " + key + " Value: " + value);
        }
    }
}
