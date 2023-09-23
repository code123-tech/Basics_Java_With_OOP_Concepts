package Serialization;

import java.util.ArrayList;
import java.util.List;

public class ForEachLoopJava8 {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i<100;i++){
            numbers.add(i);
        }
        numbers.stream().forEach(number -> System.out.println(number));
    }
}
