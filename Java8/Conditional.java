package Java8;

public class Conditional {
    public static void main(String[] args) {
        /**
         * Conditional Statements: these tells the computer to perform certain actions when some conditions are met.
         * IF__ELSE Block
         * Switch Block
         * Conditional Operator (?:)
         */
        if(true){
            System.out.println(1);
        }
        if(false){
            System.out.println(2);
        }else{
            System.out.println(3);
        }
        int a = 2;
        switch (a){
            case 1:
                System.out.println("Switch: " + a);
                break;
            case 2:
                System.out.println("Switch: " + a);
                break;
            default:
                System.out.println("Default: " + a);
                break;
        }
        int num = 3;

        if(num > 2 ? true: false){
            System.out.println(num);
        }

        /**
         * Switch Case v/s IF...Else (Which is faster?)
             * Ans: When Number of conditions are above 5, then prefer to use Switch Case as it becomes faster to search a particular key over all the cases and provided in switch statement.
             * whereas In IF...ELSE, if conditions are high, then program execution goes through each of the IF block.
             * Ans1: Because there are special bytecodes that allow efficient switch statement evaluation when there are a lot of cases.
             * If implemented with IF-statements you would have a check, a jump to the next clause, a check, a jump to the next clause and so on. With switch the JVM loads the value to compare and iterates through the value table to find a match, which is faster in most cases.
         */
    }
}
