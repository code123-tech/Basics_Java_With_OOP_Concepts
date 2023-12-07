package Serialization;


/**
 * On Primitive data types such as int, long, double, if we sort them, JVM internally takes care of their comparison.
 * When we talk about sorting of list of Objects, such as list of CustomObjects, list of wrapper classes, for that
 * Java introduced two interfaces Comparable and Comparator.
 *
 ** Comparable only sorts same type of objects belonging to same class, but in Comparator we can pass two different class
 *   Objects.
 ** Suppose a class implements Comparable interface, and we are not allowed to modify the compareTo method,
 *   but we want to override its sort conditions, how can we do it? we can write our own Comparator in that case.
 ** Comparable does natural ordering, and comparator follows custom ordering.
 ** Comparable has compareTo() method, and Comparator has compare() method.
 ** Comparable provides single ordering sequence (we can sort the collection on the basis of a single element such as
 *   id, name, and price.), but Comparator provides multiple ordering sequence (we can sort the collection on the basis
 *   of multiple elements such as id, name,` price, etc.)
 *
 *   [Comparable v/s Comparator] https://medium.com/@cs.vivekgupta/java-functional-programming-179334150eb2 (At the end of this article)
 */

import java.util.Arrays;
import java.util.Comparator;
class Student implements Comparable<Student>{
    int age;
    float mark;

    public Student(int age, float mark){
        this.age = age;
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", mark=" + mark +
                '}';
    }

    public float getMark(){
        return this.mark;
    }

    public int getAge(){
        return this.age;
    }

    @Override
    public int compareTo(Student o) {
        return (this.mark != o.mark) ? (int)(this.mark - o.mark): this.age - o.age;
    }
}
public class Comparison {
    public static void main(String[] args) {
        Student rahul = new Student(16, 95.50f);
        Student kartik = new Student(14, 96.50f);
        Student Naman = new Student(15, 97.50f);
        Student Raju = new Student(13, 97.50f);

        Student[] students = {rahul, kartik, Naman, Raju};
        System.out.println(Arrays.toString(students));

        // Using Comparator operator.
        Arrays.sort(students, Comparator.comparing(Student::getAge));

        System.out.println(Arrays.toString(students)); // result based on age sorting, as we have overriden the comparator.

        // Not using Comparator operator, based on Comparable Operator written implemented inside Class.
        Arrays.sort(students); // Will throw error, if Student class does not implement Comparable Interface.
        System.out.println(Arrays.toString(students)); // will sort based on the marks
    }
}
