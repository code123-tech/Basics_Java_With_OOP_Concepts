package OOPs;

/**
 * Abstract class
 * Interfaces
 * Interfaces comes into the picture as we can't implement multiple inheritance in Java.
 */
abstract class Parent{  // Can not create object of abstract classes (As if am able to create, that means, abstract methods are accessible through objects)
    int age;
    public Parent(int age){
        this.age = age;
    }
    static void hello(){} // Static method are allowed to be created.

   //abstract static void find(); // can not create, as static method can not be overriden.
    abstract void career(String field);
    abstract void career(String name, int age);
}
class Son extends Parent{

    public Son(int age) {
        super(age);
    }

    void career(String field) {
        // Method body
    }

    void career(String name, int age) {

    }
}
interface Engine{
    int PRICE = 78000;
    void start();
    void stop();
    void acc();
    default void voice(){  // after java 8
        System.out.println("doing voice");
    }
    static void greeting() { // after java 8, need to defined body also
        System.out.println("Greeting");
    }

    class Greeting{  // Inside interfaces, classes are available.
        private int number = 4;

        public int getNumber(){
            return number;
        }
        public void setNumber(){
            this.number = number;
        }
    }
}
interface Break{
    void apply();
}
class Car implements Engine,Break{

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void acc() {

    }

    @Override
    public void apply() {

    }
}
public class Interfaces {
    /**
     * Abstract class: Abstraction allows us to define some methods (Only their signatures), and define the behaviour of those methods in different classes as per the diff. class requirements.
     * Abstraction is needed for hiding Unnecessary details/implementation from the user, and just give them relevant information.
     * Suppose, Parents worried of their son's career, and partner.
     * The uniqueness of abstract class's that it does not define the body of methods, only defines generalize form of method signatures.
     * Now, each and every subClass who is extending these abstract class, need to define all the methods body (methods written in Abstract class)
     * Read: https://www.scientecheasy.com/2020/05/java-abstraction.html/
     * Interview: https://www.scientecheasy.com/2021/02/abstract-class-interview-questions.html/
     */

    /**
     * Using Abstract classes, still can not achieve multiple inheritance, if i inherit abstract class, then after i can't inherit another class in child classes.
     * That's where interfaces come into the picture.
     * Marker interface: doesn't have data members or methods.
     * Read: https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/interface.txt
     * Interview: https://www.scientecheasy.com/2021/02/interface-interview-questions.html/
     */

    /** Interface v/s abstract class
     * https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/abstract.txt
     */

    Engine engine = new Car();
    Engine.Greeting greeting = new Engine.Greeting();
    // greeting.getNumber(); Not accessible.

}
