package Java8;

/**
 * To free up the unused Objects from Memory, Garbage collector called the finalize method associated with the Object.
 * It is Opposite of constructor.
 * Garbage Collector auto removes the unused object from memory.
 * But, When objects hold other kind of resources such as Opening files, Network connection, in that case, Garbage collector does free up this resources.
 * that is why, we override finalize() method, and write manual code to close the above resources.
 */
public class ObjectFinalizations {

    // this method is present in Object class, which is super class of all other class.
    @Override
    public void finalize(){
        System.out.println("Finalize method invoked.");
    }

    public static void main(String[] args) {
        ObjectFinalizations objectFinalizations = new ObjectFinalizations();
        System.gc(); // invoke Garbage collector.
    }
}
