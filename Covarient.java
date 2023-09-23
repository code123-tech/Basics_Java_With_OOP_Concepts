package Serialization;

/**
 * Covarient Return Type Feature is introduced after Java5.
 * Basically this feature allows programmers to change the return type of overriden methods.
 */

class Super{
    Super m1(){
        System.out.println("In super Class");
        return this;
    }
}
class Sub extends Super{

    @Override
    Sub m1(){
        System.out.println("In Sub Class");
        return this;
    }
}
public class Covarient {
    public static void main(String[] args) {
        Super super_ = new Sub();
        super_.m1();
    }
}
