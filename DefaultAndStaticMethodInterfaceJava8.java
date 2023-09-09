package BasicsJava;

public interface DefaultAndStaticMethodInterfaceJava8 {
    default void show(){  // introduced in Java 8.
        System.out.println("Default Method.");
    }
    static void print(){
        System.out.println("Static Method.");
    }
}
