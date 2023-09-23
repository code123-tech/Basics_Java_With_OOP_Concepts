package Serialization;

/**
 * See: https://www.youtube.com/watch?v=0_V-z6QcaWc&ab_channel=MarcusBiel
 */
public class ObjectClone {

    @Override
    public ObjectClone clone(){   // covarient return type: changed the return type of overriden methods.
        try{
            return (ObjectClone) super.clone(); // Class Casting
        }catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
    public static void main(String[] args) {

    }
}
