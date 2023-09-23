package Serialization;

class BasicSyntax{  // 1. class: A Blueprint for a real world instance/object

    int variable;  // 2. instance varibale
    static int num = 4; // 3. static variable

    public String method(){  // 4. Method of instance: Shows behaviour of class
        return "Hello World";
    }

    public static void staticMethod(){ // 5. Static method of Class
        System.out.println("Static Method");
    }

    public static void main(String... args) {  // Entry Point of Java Programme.
        BasicSyntax bs = new BasicSyntax();  // 6. Object: bs is instance of BasicSyntax class
        /**
         * 7. Comments:
         * Single Line Comment (Using double slash)
         * Multi Line Comment (/* --Comment-- )
         * Doc Comment (/** -- )
         */
        // bs is instance of BasicSyntax class which is a blueprint for the instance bs.
        System.out.println(BasicSyntax.num);
        System.out.println(bs);
        System.out.println(bs.method());

        /**
         * 8. Case Sensitivity:
         * variable bs and Bs are different, they are allocated their own Memory Space.
         */

        /**
         * 9. Naming Conventions:
         * 1. Class Name, and Variable Name follows Pascal Naming Conventions
         * Ex: BasicSyntax
         * 2. Function Name follows Camel Case Naming Conventions.
         * Ex: addTwoNumbers(int a, int b)
         */

        /**
         * 10. Access Modifiers
         * Access Modifiers: default (accessible within class, or within package,
         * private (can be access only within class), protected (Within class, inherited class), public (Accessible everywhere)
         * Non-access: final, abstract, strictfp
         */

        /**
         * 11. Keywords: Some reserved words in java to Perform specific actions.
         * Ex: byte, long, short, class, void, public etc.
         */

    }
}