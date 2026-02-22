package com.Java10.features;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // 1. keyword var for local variable type inference
        // var name = null; // This will cause a compile-time error because the type cannot be inferred from null

        var list = List.of("Bhilwara", "Bengaluru", "Thailand");
        for (var city : list) {
            System.out.println(city);
        }


        var myList = new ArrayList<>(); // (Hover over myList. What is its type? Is it ArrayList<Object> or something else?)
        // This is diamond trap

    }
}