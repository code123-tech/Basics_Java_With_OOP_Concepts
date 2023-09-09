package BasicsJava.OOPs.utility;

public class Singleton {
    private static int instanceCount = 0;
    private Singleton(){
        // private constructor
    }

    public static Singleton initializeObject(){
        if(instanceCount > 0){
            return null;
        }
        instanceCount++;
        return new Singleton();
    }
}
