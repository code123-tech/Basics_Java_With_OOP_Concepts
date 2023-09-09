package Learning.Basics.OOPs;

class Base{
    final public void m1(){
        System.out.println("Base class");
    }
}

/**
 * Use Of Classes and Objects
 * Use of Constructor
 * Use of final, this and new keyword
 * Run time Memory Allocation.
 */
public class OopsBasics extends Base{

    // Not allowed static constructor, as it directly belongs to class now, and we can't create a object of it using new keyword.
//    static OopsBasics(){
//
//    }
    // constructor chaining: call one constructor from another constructor, either use super() to call Base class constructor, or use this() to call same class constructor.
    // constructor can be private, but then we can't create object of It. For that we will have to create an object using static method.

    /*
        public void m1(){  // can't declare it as this method in its parent class is declared with final keyword. IT means we can't override it.
            System.out.println("Child Class");
        }
    */
    // We can make main method as final method.
    final public static void main(String[] args) {

        /**
         * Classes
         * Objects
         * Interfaces
         * Inheritance
         * Polymorphism
         * Abstraction
         * Encapsulation
         */
        /*
            Suppose, You are said to create a data structure which store a student info (age, name).
            Now, you will think of array, but for the array you can take only type of data, either you take int (for age),
            or take String (for name).
            Now, the concept of grouping comes, where we can group multiple type of data into a single entity (Class), and
            can use that entity for defining different properties values for object.

            class Student{
                int age;
                String name;
            }

            Student student1 = new Student();
            Here Student student1 is executed at compile time, as reference variable is stored at compile time.
            Now, using new Keywords, dynamically memory is allocated to that reference variable (student1), and Student() works
            as a constructor for the Student class.
            student1 point out to its own memory space where
        */
        /**
         * Read Of Classes: https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/classes.txt
         * Read: https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/constructors_this_finalize.txt
         * Question of this keyword: http://www.instanceofjava.com/2016/03/this-keyword-in-java-with-example.html
         * Question of final keyword: https://www.scientecheasy.com/2021/11/java-final-interview-questions.html/
         * Question of constructor: https://www.javatpoint.com/java-constructor-interview-questions
         */
        // to create a class object we use
        // <Class Name> object_name = new <Class_Name>();
        // new keyword assign Memory at run time to class Object <object_name>
        // By default <Class_Name>() constructor is called, when we create a class object.
        // final applies on 3 things: 1. on classes 2. On methods 3. On Instance variables.
        // final is kind of constant, once value is assigned to final variables, it can't be re-assigned further in the code.
        // final can not apply on constructor, as they can't be override in their sub classes.
        // abstract method v/s final method
        final int value = 3;
        // value = 4;
        OopsBasics ob = new OopsBasics();
        ob.m1();
    }
}
