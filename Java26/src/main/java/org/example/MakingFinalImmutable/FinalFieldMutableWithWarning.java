package org.example.MakingFinalImmutable;

import java.lang.reflect.Field;


/*
The below error we got be default when we ran this class

WARNING: Final field x in class org.example.MakingFinalImmutable.FinalFieldMutableWithWarning has been mutated reflectively by class org.example.MakingFinalImmutable.FinalFieldMutableWithWarning in unnamed module @3a71f4dd (file:/Users/swapnil/Desktop/Learning/Basics_Java_With_OOP_Concepts/Java26/target/classes/)
WARNING: Use --enable-final-field-mutation=ALL-UNNAMED to avoid a warning
WARNING: Mutating final fields will be blocked in a future release unless final field mutation is enabled

Now,
Ran with --illegal-final-field-mutation=deny in Intellij JVM args

got below error

Exception in thread "main" java.lang.IllegalAccessException: class org.example.MakingFinalImmutable.FinalFieldMutableWithWarning (in unnamed module @2a84aee7) cannot set final field org.example.MakingFinalImmutable.FinalFieldMutableWithWarning.x (in unnamed module @2a84aee7), unnamed module @2a84aee7 is not allowed to mutate final fields
	at java.base/java.lang.reflect.Field.preSetFinal(Field.java:1504)
	at java.base/java.lang.reflect.Field.setFinal(Field.java:1447)
	at java.base/java.lang.reflect.Field.set(Field.java:909)
	at org.example.MakingFinalImmutable.FinalFieldMutableWithWarning.main(FinalFieldMutableWithWarning.java:34)
 */

public class FinalFieldMutableWithWarning {

    private final int x;

    public FinalFieldMutableWithWarning(int x) {
        this.x = x;
    }

    public static void main(String[] args) throws Exception {

        Field field = FinalFieldMutableWithWarning.class.getDeclaredField("x");

        FinalFieldMutableWithWarning finalFieldMutable = new FinalFieldMutableWithWarning(10);
        System.out.println("Before using reflection API, final field x is: " + finalFieldMutable.x);

        System.out.println("setting final accessible");
        field.setAccessible(true);

        System.out.println("setting final field x to: " + 20);
        field.set(finalFieldMutable, 20);
        System.out.println("After using reflection API, field x is: " + finalFieldMutable.x);

        System.out.println("setting final field x to: " + 30);
        field.set(finalFieldMutable, 30);
        System.out.println("After using reflection API, field x is: " + finalFieldMutable.x);
    }

}
