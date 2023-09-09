package BasicsJava;

public class TypeConversions {
    /**
     * Conversion from one Data Type to another Data Type.
     * Implicit TypeCasting, Explicit TypeCasting.
     * Implicit: Auto Conversion from lower type to higher type.
     * Explicit: Manually Conversion from Higher type to lower type.
     * Read: https://www.scientecheasy.com/2020/07/type-conversion-casting-java.html/
     */
    /*
            The below all are Implicit Type Conversion
           (lower datatype ----> Higher DataTypes)
           byte ---> short ----> int ---> long ---> double
           int ---> double
           float --> double
           char --> int ---> float
           long ---> float
     */
    public static void main(String[] args) {
        short a = 1;
        byte b = 1;
        int c = 2147483;
        // Implicit type change  (Widening)
        int sum_ =  (a + b + c);
        // Explicit Type change  (Narrowing)
        byte sum_1 = (byte)(a + b + c);  // here data is lost, as byte range is between -128 to 127 only.
        short sum_2 = (short) (a + b + c); // here data is lost, as short range is between -32768 to 32767 only.
        System.out.println(sum_);   // 2147485
        System.out.println(sum_1); // -99
        System.out.println(sum_2); // -15203

        /**
         * Class Casting: Conversion of one class type into another class type if the classes are having IS-A relationship.
         * Super_Class ----> Sub_Class
         * Super_Class super_class_refer = new Super_Class();
         * 1. Generalization: Conversion of subclass into superclass.
         * 2. Specialization: Conversion of Superclass into subclass.
         * super_class_refer = (Super_Class) new Sub_Class(); // Generalization
         * In Generalization, all the methods can be accessed which are present in Super class until and unless they are not overriden in subclass.
         *
         * Sub_Class sub_class_refer = new Sub_Class();
         * sub_class_refer = (Sub_Class) new Super_Class(); // Specialization
         * sub_class_refer can not access both super class methods and subclass methods.
         *
         * If we do like below:
         * Super_Class super_class_refer = new Sub_Class();
         * Sub_Class sub_class_refer = (Sub_Class) super_class_refer;
         *
         * sub_class_refer can access both super class and subclass methods.
         * Read: https://www.scientecheasy.com/2019/12/class-casting-in-java.html/
         */
    }
}
