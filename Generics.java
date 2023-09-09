package BasicsJava;

import java.util.Arrays;

/**
 * Generics are basically used to solve the problem of creating different classes for storing different type of data into them.
 * Generics allow us to create only one class with type parameter, and we can create three different type of objects using that Generic class only.
 * Previously:
 * class Intg{
 *     Integer var;
 * }
 * class Str{
 *     String var;
 * }
 * class Lon{
 *     Long var;
 * }
 * Using Generics:
 * class Generic<T>{
 *     T var;
 * }
 * Read: https://www.scientecheasy.com/2021/10/generics-in-java.html/
 *
 * Before Java 1.5: Collections ArrayList used to store data as type of Object, now that does not ensure that only one type of data present in the list.
 * */

// CustomArrayList class without Generics (Only For Storing Integer Type Of Data)

class CustomArrayList{
    private int[] data;
    private final int DEFAULT_SIZE=10;
    private int size = 0;

    CustomArrayList(){
        this.data = new int[DEFAULT_SIZE];
    }

    private void growList(){
        int[] newData = new int[this.data.length*2];
        for(int i = 0;i<this.data.length;i++){
            newData[i] = this.data[i];
        }
        data = newData;
    }

    public void add(int el){
        if(size == DEFAULT_SIZE){
            growList();
        }
        data[size++] = el;
    }

    public int remove(){
        if(size == 0){
            throw new IllegalArgumentException("List is Empty");
        }
        int el = this.data[size];
        size--;
        return el;
    }

    public void replace(int index, int element){
        if (index >= size){
            throw new IllegalArgumentException("Out of Index.");
        }
        this.data[index] = element;
    }

    @Override
    public String toString() {
        return "CustomArrayList{" +
                "data=" + Arrays.toString(data) +
                ", size=" + size +
                '}';
    }
}

// Generic ArrayList class
class CustomGenArrayList<T> {
    private Object[] data;
    private final int DEFAULT_SIZE=10;
    private int size = 0;

    CustomGenArrayList(){
        this.data = new Object[DEFAULT_SIZE];
    }

    private void growList(){
        Object[] newData = new Object[this.data.length*2];
        for(int i = 0;i<this.data.length;i++){
            newData[i] = this.data[i];
        }
        data = newData;
    }

    public void add(T el){
        if(size == DEFAULT_SIZE){
            growList();
        }
        data[size++] = el;
    }

    public T remove(){
        if(size == 0){
            throw new IllegalArgumentException("List is Empty");
        }
        T el = (T) this.data[size];
        size--;
        return el;
    }

    public void replace(int index, T element){
        if (index >= size){
            throw new IllegalArgumentException("Out of Index.");
        }
        this.data[index] = element;
    }

    @Override
    public String toString() {
        return "CustomArrayList{" +
                "data=" + Arrays.toString(data) +
                ", size=" + size +
                '}';
    }
}

public class Generics {
    public static void main(String[] args) {
        CustomArrayList customArrayList = new CustomArrayList();
        for(int i = 1;i<=15;i++){
            customArrayList.add(i);
        }
        System.out.println(customArrayList);

        // Now the problem is that we can store only integer type of data into the custom list, if we want another type of data, we need to create another class for that data type.
        // So, Here Generics come into the picture, they reduce this problem, by defining a generic class
        CustomGenArrayList<Integer> customGenArrayList = new CustomGenArrayList<>();
        customGenArrayList.add(2);
        // customGenArrayList.add("Apple"); // does not allow to store string in the integer type of list.

        CustomGenArrayList<String> customGenArrayList1 =  new CustomGenArrayList<>();
        customGenArrayList1.add("Hello");
        // customGenArrayList1.add(1);  // Does not allow to store String type.

        CustomGenArrayList<Long> customGenArrayList2 = new CustomGenArrayList<>();
        customGenArrayList2.add(1L);
        // customGenArrayList2.add(1); // Does not allow to store int in Long type of Object List.
    }
}
