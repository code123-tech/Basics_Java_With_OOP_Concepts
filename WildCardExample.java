package Serialization;

import java.util.ArrayList;

/**
 * Basically, Wildcard means Making Class More Restrict to accept a particular type of DataType.
 * Suppose, You have a super class Vechile, Now You want that your Generic class should accept only those Classes which are either subclass of Vechile, or it is Vechile class only.
 * In that Case, you can use WildCards in Generics
 * For ex: class ClassName<T extends Vechile>.
 * Thus, as per above syntax, you can restrict.
 * Ex: USing Number class as super class, which is extended by Integer, Float, Double, Long etc., but not by String.
 * <? extends Number> is wildcard.
 */
class WildCard<T extends Number>{

    public ArrayList<? extends Number> numbers(ArrayList<? extends Number> list){
        return list;
    }
}
public class WildCardExample {
    public static void main(String[] args) {
        // WildCard<String> wildCard = new WildCard<String>();  ---> Not allows
        WildCard<Integer> wildCard = new WildCard<>();
        wildCard.numbers(new ArrayList<Integer>());
    }
}
