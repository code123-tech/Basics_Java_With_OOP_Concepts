package BasicsJava;

import java.io.*;

/**
 * Serialization: Process of converting an Object into a byte stream object.
 * Object =====> Byte Stream Object (writeObject) ======>  Saved into file or DB or Memory
 * DeSerialization: Process through which a byte stream object is converted into an actual Java Object. (Reverse of Serialization)
 * Saved Byte Stream Object (DB or Memory or file =========> Byte Stream Object (readObject) ========> Object
 * For this, Java Provides Serializable, Externalizable Interfaces.
 * Serializable is a marker Interface.
 * Points:
 * 1. If Parent class has implemented Serializable Interface, then child class need not implement it. (But vice-versa is not allowed )
 * 2. If you want any class member not to be Serialized, then make it either static or transient.
 * 3. Constructor of an Object is never called during Deserialization.
 * 4. Associated Objects must be implementing Serializable Interface.
 * Read: https://www.scientecheasy.com/2021/07/serialization-in-java.html/
 * Interview Question: https://javarevisited.blogspot.com/2011/04/top-10-java-serialization-interview.html
 * Common Usage:
 * 1. When an object is to be sent over the network, objects need to be serialized.
 * 2. If the state of an object is to be saved into a hard disk, or file, objects need to be serialized.
 */

class AnotherDemo implements Serializable {
    public Boolean isPlaying = false;
}

class Demo implements Serializable {
//    private static final long serialversionUID = 129348938L; // Important Topic serialversionUID
    public int a;
    public String b;
    transient String c;

    public AnotherDemo anotherDemo = new AnotherDemo();  // This is associated object, it must be implementing Serializable Interface.

    public Demo(int a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}

public class Serialization {
    public static void main(String[] args) {
        Demo object = new Demo(1, "Abc", "Transient");
        String filename = "object.txt";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // write
            objectOutputStream.writeObject(object);

            objectOutputStream.close();
            fileOutputStream.close();

            System.out.println("Object has been Serialized");
        } catch (IOException e) {
            System.out.println("Exception caught During Serialization..."); // getting Exception: NotSerializableException as associated object has not implemented Serializable
        }


        Demo object1 = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            object1 = (Demo) (objectInputStream.readObject());

            objectInputStream.close();
            fileInputStream.close();

            System.out.println("Object has been DeSerialized");
            System.out.println("a: " + object1.a);
            System.out.println("b: " + object1.b); // object1.c is null as it is not considered while Serialization as c is transient.
        } catch (IOException e) {
            System.out.println("Exception caught During DeSerialization...");
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception caught During DeSerialization...");
        }
    }
}
