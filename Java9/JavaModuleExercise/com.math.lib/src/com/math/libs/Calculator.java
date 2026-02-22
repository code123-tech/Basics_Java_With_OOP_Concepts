package Java9.JavaModuleExercise.com.math.lib.src.com.math.libs;

public class Calculator {

    public int add(int a, int b) {
        // Calls the hidden internal logic
        return InternalLogic.secretAdd(a, b);
    }

}
