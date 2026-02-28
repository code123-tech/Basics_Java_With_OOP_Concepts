package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Sealed classes/interfaces let you restrict inheritance:
 * "Only these specific types are allowed to extend/implement this type."
 * <p>
 * Why it was introduced:
 * - To model "closed" domain hierarchies (fixed set of possibilities) more safely than using conventions.
 * - Useful for domain design (e.g., PaymentMethod can only be UPI/CreditCard/Cash).
 * <p>
 * When it was featured:
 * - Preview in Java 15, re-preview in Java 16, finalized in Java 17 (JEP 409).
 * <p>
 * Syntax / structure:
 * <pre>
 * sealed interface PaymentMethod permits UPI, CreditCard, Cash {}
 *
 * record UPI(String vpa) implements PaymentMethod {}
 * record CreditCard(String number) implements PaymentMethod {}
 * record Cash() implements PaymentMethod {}
 * </pre>
 * <p>
 * Key rules to remember:
 * - A sealed type must list permitted subtypes using {@code permits}
 *   (sometimes omitted if all subtypes are declared in the same source file).
 * - Every permitted subtype must be declared as exactly one of:
 *   {@code final}, {@code sealed}, or {@code non-sealed}.
 * - Permitted subtypes must be known/accessible at compile time and must directly extend/implement the sealed type.
 */

sealed interface PaymentMethod permits UPI, CreditCard, Cash {
    void doPayment();
}

record UPI(String vpa, double cost) implements PaymentMethod {
    @Override
    public void doPayment() {
        System.out.println("Doing UPI payment using id: " + vpa + " of Rs: " + cost);
    }
}

record CreditCard(String number, double cost) implements PaymentMethod {
    @Override
    public void doPayment() {
        System.out.println("Doing creditCard payment using number: " + number + " of Rs: " + cost);
    }
}

record Cash(double cost) implements PaymentMethod {
    @Override
    public void doPayment() {
        System.out.println("Doing cash payment of Rs: " + cost);
    }
}

public class UnderstandingSealedClasses {

    public static void doExercise(){

        System.out.println("Sealed Classes / Interfaces");
        createSealedClass();
    }

    private static void createSealedClass() {

        System.out.println("\nCreating sealed Payment interface which restricts UPI/CASH/CreditCard");
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new Cash(12.0));
        paymentMethods.add(new CreditCard("123456", 12.0));
        paymentMethods.add(new UPI("abc@hdfcbank", 12.0));

        paymentMethods.forEach(PaymentMethod::doPayment);
    }

}

/*
Interview Questions on Sealed Classes / Interfaces (with answer hints)

1) What is a sealed class/interface in Java?
   - Restricts which classes can extend/implement it using `sealed` + `permits`.

2) When was it introduced and when was it finalized?
   - Preview in Java 15, re-preview in 16, finalized in Java 17 (JEP 409).

3) What problem do sealed types solve?
   - Safe “closed” hierarchies for domain modeling; prevents unsupported external extensions.

4) What are the rules for permitted subclasses?
   - Must be in the `permits` list (or inferred if in same file), must be known at compile-time, must directly extend/implement.

5) Why must every permitted subclass be `final`, `sealed`, or `non-sealed`?
   - Forces you to explicitly declare whether the hierarchy stops, stays restricted, or re-opens.

6) Difference between `sealed` and `final`?
   - `final` stops inheritance completely; `sealed` allows inheritance but only to specific permitted types.

7) What does `non-sealed` mean and when would you use it?
   - Opens inheritance again from that subtype; useful as an “escape hatch” when you want controlled top-level types but extensible subtypes.

8) Can records be part of a sealed hierarchy? Why is it common?
   - Yes; records are implicitly `final`, ideal for leaf implementations in closed models.

9) Can you omit `permits`? When?
   - If all permitted subtypes are declared in the same source file as the sealed type.

10) Sealed types vs enums — what’s the difference?
   - Enum: fixed set of instances/values. Sealed: fixed set of types (subclasses).

11) What compiler error do you expect if you implement a sealed interface without being permitted?
   - An error indicating the class is not allowed to implement/extend the sealed type (not in permits).
*/
