package ImmutableClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class CustomImmutable{  // why we make class final? reason is in readme.md
    private final int id;
    private final String name;

    private final List<String> strings;  // why we put each field as final and private? reason is in readme.md
    public CustomImmutable(int id, String name, List<String> strings){
        this.id = id;
        this.name = name;
        this.strings = Collections.unmodifiableList(strings); // Why we do this? reason is in readme.md
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public List<String> getStrings(){
        return new ArrayList<>(this.strings); // Why we do this? reason is in readme.md
    }
    @Override
    public String toString() {
        return "CustomImmutable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", strings=" + strings +
                '}';
    }
}
public class ImmutableClass {
    /**
     * Please go through the readme.md for the explanation in same folder.
     */
    public static void main(String[] args) {
        CustomImmutable customImmutable = new CustomImmutable(1, "Rahul", Arrays.asList("Rahul", "Kartik", "Naman"));
        System.out.println(customImmutable);
    }
}
