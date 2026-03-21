package org.example;

import java.lang.reflect.Field;

/*
 * At Java Jara path, we can use below
 *  java --enable-final-field-mutation=ALL-UNNAMED (For all fields in the Java jar)
 *  java --enable-final-field-mutation=M1,M2 (to enable final field mutation in specific modules)
 *
 * Even, if we use above: we will have to perform deep reflection as done below
 *
 * along with above, different ways of saying final field mutation
 *  --illegal-final-field-mutation=allow (allow mutation without warning)
 *  --illegal-final-field-mutation=warn  (allow mutation by giving warning per module, by default in jdk26)
 *  --illegal-final-field-mutation=debug (allow mutation by giving a warn and stack trace issued)
 *  --illegal-final-field-mutation=deny  (not allow below reflection API to be proceeded) (can be new default in future version after jdk26)
 */
public class FinalFieldMutable {

    private final int x;

    public FinalFieldMutable(int x) {
        this.x = x;
    }

    public static void main(String[] args) throws Exception {

        Field field = FinalFieldMutable.class.getDeclaredField("x");

        FinalFieldMutable finalFieldMutable = new FinalFieldMutable(10);
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
