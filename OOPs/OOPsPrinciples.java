package BasicsJava.OOPs;


class Super {
    int l;
    Super(){

    }
    Super(int l){
        this.l = l;
    }

}
class Sub extends Super{
    int h;

    Sub(){

    }
    Sub(int h, int l){
        super(l);
        this.h = h;
    }
}
// Polymorphism: We are doing method overriding/Dynamic Binding/Late Binding/ Dynamic Dispatch here
class Shapes{
    public void area(){
        System.out.println("I am in Shapes");
    }

    static public void Identity(){
        System.out.println("I am Shape");
    }
}
class Square extends Shapes{
    @Override
    public void area(){
        System.out.println("Square area is: side*side");
    }

    static public void Identity(){
        System.out.println("I am Square");
    }
}
class Circle extends Shapes{
    @Override  // this annotation over a method tells us whether a method is over-riden or not.
    public void area(){
        System.out.println("Circle area is: pie*r*r");
    }

    static public void Identity(){
        System.out.println("I am Circle");
    }
}

/**
 * Inheritance, and super keyword.
 * Polymorphism
 * Encapsulation
 * Abstraction
 */
public class OOPsPrinciples {
    public static void main(String[] args) {
        /**
         * Inheritance:  Inherit properties of the parent in child classes.
         * ex: class Base{public int value = 4}
         * class Sub extends Base{} // Sub class reference object can access Base class properties like value.
         * Every class in Java is inherited from Object class directly or Indirectly.
         * Types: Single Inheritance, MultiLevel Inheritance, Hierarchial Inheritance (Multiple classes are inheriting single class) etc.
         * Multiple Inheritance is not allowed in Java, as subclass can not decide which super class method is called, if two same methods are in the super classes.
         * Hybrid Inheritance is also not allowed as it follows multiple inheritance
         * Diamond Problem is basically a Hybrid Inheritance.
         * Read: https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/inheritance.txt
         * Questions: https://www.scientecheasy.com/2021/02/inheritance-interview-questions.html/
         */
        Super superobj = new Super();  // allowed, Reference var of Super class -----Pointing to----> Super Class object, can access only variable l
        Sub sub = new Sub();  // allowed, Reference var of Sub class ---Pointing to---> Sub class object, can access its own variable, and super class variables which are public.

        Super super2 = new Sub();  // Reference var of Parent class ---Pointing to---> Child class object.
        // Now super2 can only access its own variables defined in Super class.

        // Sub sub2 = new Super();  // Not allowed, as you can not put reference var of child class pointing to parent class, as parent
        // class does not know anything about the child class.

        /**
         * Polymorphism: Many Ways to represent a single entity, like a method inside a class can be represented in many ways, like: two same name methods are declared in the class, but their return type is not same.
         * See class Shapes, inherited by Circle, Square.
         * can we override static methods of Base class? We can override the static method, but always parent class's static method will be called.
         * Polymorphism: 1. Static Polymorphism (Compile Time) (Overloading)  2. RunTime Polymorphism/Dynamic Method Dispatch  (Overriding)
         * Method Overloading: https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/overloading.txt
         * Method Overriding: https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/overriding.txt
         * Questions: https://www.scientecheasy.com/2021/02/polymorphism-interview-questions.html/
         */
        // Reference Variable: Parent Class, Object: Child class
        Shapes shapes = new Circle();
        shapes.area();  // Circle area is: pie*r*r, here area() of Parent is overriden as it is not static

        shapes.Identity(); // static method can't be overriden, Static does not depend on Object.

        /**
         * Encapsulation: Wrapping up the implementation of the data members & methods in a class, so that it can be protected from outside classes.
         * Can be achieved via access specifiers. We can use getters and setters for those private information.
         * process of containing Information.
         * It solves Implementation level issue.
         * Read: https://www.scientecheasy.com/2020/07/encapsulation-in-java.html/
         * Questions: https://www.scientecheasy.com/2018/06/real-encapsulation-interview-questions-answers.html/
         * Tightly Encapsulated Classes follows Data Hiding Process.
         */

        /**
         * Abstraction: Hiding Un-necessary details, and only showing necessary details.
         * It provides external stuffs.
         * Can be achieved via abstract classes and Interfaces.
         * process of gaining information.
         * It solves Design Level Issue.
         * Read: https://www.scientecheasy.com/2020/05/java-abstraction.html/
         */

        /**
         * Class Relationships in Java: https://www.scientecheasy.com/2021/02/class-relationships-in-java.html/
         */

    }
}
