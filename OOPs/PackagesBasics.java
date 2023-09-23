package Serialization.OOPs;

import Serialization.OOPs.utility.Singleton;

import static Serialization.OOPs.utility.Notification.publicMessage;

/**
 * Use Of Packages.
 * Use Of Static Keyword.
 * Singleton Class
 */
public class PackagesBasics {

    static int val;

    // When the class is being loaded in the memory (First time when its object is being created)
    // all its static statements are executed once.
    static { // static block
        val = 4;
    }

    // Inner class as static
    static class Test{
        String name;
        public Test(String name){
            this.name = name;
        }
    }

    public static void main(String[] args) {
        /**
         * Packages are basically "Folders (In Simple Terms)", which keeps your classes with same name compartmentalized
         * We can use only those things from a different package, which are publicly available for use in that package.
         * Read It: https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/packages.txt
         * Interview: https://www.wideskills.com/java-interview-questions/java-packages-interview-questions
         */
        publicMessage(); // can import public method
        // privateMessage(); // can not import as it is private

        /**
         * Static is used when a property is common to all objects of a class, and it directly belongs to class only.
         * Static can be a variable, a method. Outside the class can't be static, but a class inside a class can be static.
         * Why is main class static?  Ans: so that it can be called before creating actual object of the class.
         * Can we use this keyword inside a static method? Ans: No, as this basically refers the object.
         * Read: https://github.com/kunal-kushwaha/DSA-Bootcamp-Java/blob/main/lectures/17-oop/notes/static.txt
         * Interview: https://www.scientecheasy.com/2021/10/java-static-interview-questions.html/
         */
        System.out.println(PackagesBasics.val);
        Test test = new Test("Me");  // if class Test is not static, then object of Test is not created in static methods.
        System.out.println(test.name);
        /**
         * Singleton classes are those classes which allows users to create only one instance of those classes.
         * So, how can I prevent user to create the object of that class? Ans: By Making all the constructor as private of that class.
         */
        // Singleton singleton = new Singleton(); ---> Can not create object directly.
        Singleton singleton1 = Singleton.initializeObject();  // first time we can create,
        System.out.println(singleton1);
        Singleton singleton2 = Singleton.initializeObject();  // Second time we will get null.
        System.out.println(singleton2);

    }
}
