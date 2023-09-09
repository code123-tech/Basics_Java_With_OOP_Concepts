public class Loops {
    public static void main(String[] args) {
        /**
         * Loops are used to iterate a part of program several times.
         * 1. For Loop (Use it when iterations are fixed)
         *  1. 1. forEach Loop: can be used only on iterable objects.
         * 2. While Loop (Use it when iterations are not fixed)
         * 3. Do..While Loop (Use it when you have to execute loop atleast one time.
         */
        for(int i = 0;i < 10; i++){
            System.out.print(i);
        }
        int arr[] = {1, 2, 3, 4, 5};
        for(int el: arr){
            System.out.println(el);
        }

        while(true){
            if(true){
                break;
            }
        }
        do{
            if(true) break;
        }while(true);
    }
}
