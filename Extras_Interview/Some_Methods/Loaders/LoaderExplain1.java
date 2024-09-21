package Extras_Interview.Some_Methods.Loaders;

import java.util.ArrayList;
import java.util.List;

public class LoaderExplain1 {

    private static final List<String> cities;

    static {
        cities = new ArrayList<>();
        cities.add("A");
        cities.add("B");
        cities.add("C");
        System.out.println("Size: " + cities.size());
    }
}
