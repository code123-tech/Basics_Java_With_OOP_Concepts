package Extras_Interview.Some_Methods;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Map_EntrySet {
    public static void main(String[] args) {
        // entrySet() method is used to get a set view of the mappings contained in this map.
        Map<String, Integer> mapi = new HashMap<>();
        mapi.put("A", 65);
        mapi.put("B", 66);
        Set<Map.Entry<String, Integer>> mappings = mapi.entrySet();
        for(Map.Entry<String, Integer> mapping: mappings){
            System.out.println(mapping.getKey() + " -> " + mapping.getValue());
        }
    }
}
