package Java9.JavaModuleExercise.com.app.main.src.com.app.main;


import Java9.JavaModuleExercise.com.math.lib.src.com.math.libs.Calculator;

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("Modular Addition Result: " + calc.add(10, 20));
    }
}