package SOLID_PRINCIPALS.Liskov_Substitution;
// check notes for explanation

import java.util.Arrays;
import java.util.List;

class Animal_ {
    public Integer getNumberOfLegs() {
        return 2;
    }
}

class FlyingAnimal extends Animal_ {
    public Integer getFlyingSpeed() {
        return 20;
    }
}

class EarthAnimal extends Animal_ {
    public Integer getWalkingSpeed() {
        return 5;
    }
}

class Pigeon_ extends FlyingAnimal {

}

class Cow_ extends EarthAnimal {
}

public class LSP_Follows {
    public static void main(String[] args) {
        Animal_ pigeon = new Pigeon_();
        Animal_ cow = new Cow_();
        List<Animal_> animal_s = Arrays.asList(pigeon, cow);
        for (Animal_ animal : animal_s) {
            System.out.println(animal.getNumberOfLegs());
//            System.out.println(animal.getFlyingSpeed());   // not accessible, as this two methods are not part of Animal
//            System.out.println(animal.getWalkingSpeed());  // not accessible, as this two methods are not part of Animal
        }

        FlyingAnimal flyingAnimal = new Pigeon_();
//        FlyingAnimal flyingAnimal1 = new Cow_();  // not allowed
//        EarthAnimal earthAnimal = new Pigeon_(); // not allowed
    }
}
