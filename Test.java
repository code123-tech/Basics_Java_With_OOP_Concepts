package Serialization;

abstract class A {
    private int x;
    A(int x){
        System.out.println("Value of x: " +x);
    }
    abstract void m1(int x, double y);
}
class B extends A {
    private int y;
    B(int y){
        super(10);
        System.out.println("Value of y: " +y);
    }
    void m1(int x, double y){
        System.out.println("One");
    }
    void m2(){
        System.out.println("Two");
        this.m1(5, 10.50);
    }
}
class C extends B {
    C(){
        super(30);
    }
    void m1(int x, double y){
        super.m1(10, 15.15);
        System.out.println("Three");
    }
}
public class Test {
    public static void main(String[] args){
        B b = new C();
        b.m1(10, 20.50);
        b.m2();
    }
}
