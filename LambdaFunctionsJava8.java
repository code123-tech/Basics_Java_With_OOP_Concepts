package BasicsJava;

import java.util.ArrayList;

/**
 * Lambda Expression or Lambda Functions is an inline block of code, which takes some arguments, and do some processing on those arguments, and return results.
 * parameter ->  expression.
 */
interface Operation{
    int operation(int a, int b);
}
public class LambdaFunctionsJava8 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0;i < 10; i++){
            numbers.add(i);
        }
        numbers.forEach((number) -> System.out.println(number));

        Operation sum = (a, b) -> (a+b);  // can be done only if interface contains one method.
        Operation prod = (a, b) -> (a*b);
        Operation sub = (a, b) -> (a-b);

        LambdaFunctionsJava8 myCalculator = new LambdaFunctionsJava8();
        System.out.println("Sum: " + myCalculator.operate(2,3 , sum));
        System.out.println("Product: " + myCalculator.operate(2,3 , prod));
        System.out.println("Sub: " + myCalculator.operate(2,3 , sub));
    }

    private int operate(int a, int b, Operation op){
        return op.operation(a, b);
    }
}
