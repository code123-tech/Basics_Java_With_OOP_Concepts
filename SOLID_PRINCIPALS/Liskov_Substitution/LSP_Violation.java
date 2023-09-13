package SOLID_PRINCIPALS.Liskov_Substitution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Animal class
 * Subclasses, Pigeon, Cow.
 *
 * Animal has two methods getNumberOfLegs(), getFlyingSpeed().
 */
class Animal{
    public Integer getNumberOfLegs(){
        return 2;
    }
    public Integer getFlyingSpeed(){
        return 20;
    }
}

class Pigeon extends Animal{

}

class Cow extends Animal{
    public Integer getFlyingSpeed(){
        return null;
    }
}

public class LSP_Violation {
    public static void main(String[] args) {
        Animal pigeon = new Pigeon();
        Animal cow = new Cow();
        List<Animal> animals = Arrays.asList(pigeon, cow);
        for(Animal animal: animals){
            System.out.println("Speed: "  + animal.getFlyingSpeed().toString());
        }
        // Getting error for cow, as it is not a flying animal
    }
}
