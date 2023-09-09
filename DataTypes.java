public class DataTypes {
    public static void main(String[] args) {
        /**
         * DataTypes: Defines the size of a variable (How big value a variable can store)
         * Primitive DataType
         * Non-Primitive DataType
         */
        /**
         * Primitive Datatypes: this type are not user defined data type, these provides a fixed size of data to its variable.
         * Ex: boolean, char, byte, short, int, long, float, double etc.
         */
        boolean test = false; // 1-bit Range: [0,1]
        byte a = 10; // 8-bit, range: [-128,127]
        short b = 110;  // 16-bit, range: [-32768, 32767]
        int t = 1100; // 32-bit [-2^31,2^31-1]
        char ch = 'a'; // 16-bit
        long lg = 10; // 64-bit [-2^63, 2^63-1]
        float f = 2.15f;  // 32-bit  (4-byte)
        double d = 2.15;  // 64-bit  (8-byte)

        /**
         * A varaible can not take more value than its size
         */
        // short nn = 2147483647;  // Not allowed

        /**
         * An integer data type declared variable can take char value
         */
        int inti = 'a';  // store ASCII value in the init.
        /**
         * Can not declare same variable in same scope
         */

        // int t = 100;
        // short t = 100;

        /**
         * Non-Primitive Data Type: User Defined types of variables.
         * ex: classes, interfaces, Arrays etc.
         */

        // byte byt = (byte) false; => Can't caste to boolean to byte data type.
        /**
         * Interview Questions On DataTypes
         * 1. Wrapper Classes in Java?
         * 2. Primitive Literals: integer literals, char literals, float literals, boolean literals.
         *    For ex: char 'a' is char literal, and ASCII code 100 is int literals.
         * 3. Primitive Variables v/s Reference Variables.
         *   Primitive Variables holds the value of Primitive literals. int a = 10;
         *   Reference Var. holds the reference of an object. Class obj = new Class();
         * 4. How long Primitive Data types exist? (It depends on the scope of the variables).
         * 5. Autoboxing, and Unboxing.
         *    Autoboxing: Auto Conversion of Primitive data into their wrapper classes.
         *    Unboxing: Converting of Wrapper class to its primitive data type.
         * 6. Casting: conversion of one primitive data type value to another data type value.
         *      Implicit Casting: automatically conversion (from smaller size datatype to higher size)
         *      Explicit Casting: explicitly need to cast.
         */
    }
}
