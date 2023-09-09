public class Functions {
    int x = 10;
    public static long sum(int a, int b){
        return a+b;
    }
    public static String isEvenOdd(int num){
        return num%2==0 ? "Even": "Odd";
    }
    public static void main(String[] args) {
        /**
         * Function and method is way to reuse the same operation multiple time.
         * Ex: A function which takes two numbers as argument, and it returns two number's output.
         * Function/Method name follows camelCase Naming Conventions.
         * 1. Predefined Method.
         * 2. User Defined Method
         *      2. 1. Static Method. (Class Level Methods)
         *      2. 2. Instance Method. (Object Level Methods)
         *          2.2. 1. Accessor OR Getter Methods.
         *          2.2. 2. Mutator OR Setter Methods.
         *      3. Abstract Method.
         *      4. Factory Method (All Static Methods are Factory Methods)
         */
        /**
         * Methods are class Members, whereas functions can be outside the class (not in Java as it is OOPs language)
         * In Java, Pass By Value occurs, No Pass By Reference.
         * On Primitive Data Types, Only Pass By Value occurs,
         * On Non-Primitive Like String, the value of reference variable is passed.
         * There Are three Scopes:
         * 1. Function Scope
         * 2. Block Scope
         * 3. Loops Scope
         * Shadowing: declare a global variable inside a function scope again with another value, it means shadowing the value.
         *            Lower Level Scope is hiding the upper level scope.
         */

        sum(1, 3);
        // Q. Write A method to evaluate if a number is even or odd.
        int number = 4;
        System.out.println("Number " + number + " is: " + isEvenOdd(number));
    }
}
