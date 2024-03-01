#### Immutability in Java
- Immutability means the state of an Object is not changed after it is created, State means the data inside the object.

#### Rules for creating Immutable Class
1. Make the class final, so that no other classes can extend it.
- But why? Because a Subclass can change the actual behaviour of the class by overriding the Getter methods of immutable class.

2. Do not include any setter methods.
- But why? Because setter methods can change the state of the object.

3. Take care of constructors taking external references.
- Inside a constructor, if we are passing any mutable object, then we should make sure to create a copy of it and assign it to the instance variable.
- For example,
```java
public class ImmutableClass {
    private final int id;
    private final String name;
    private final List<String> list;

    public ImmutableClass(int id, String name, List<String> list) {
        this.id = id;
        this.name = name;
        this.list = new ArrayList<>(list); // here if we write code as below
        // this.list = list; // then it will be a problem, because if the list is changed outside, then it will change the state of the object.
    }
}
```
4. Take care of Getters which returns mutable objects.
- If we are returning any mutable object from a getter, then we should make sure to return a copy of it and not the actual object reference.
- For example,
```java
public class ImmutableClass {
    private final int id;
    private final String name;
    private final List<String> list;

    public ImmutableClass(int id, String name, List<String> list) {
        this.id = id;
        this.name = name;
        this.list = new ArrayList<>(list);
    }

    public List<String> getList() {
        return new ArrayList<>(list); // here if we write code as below
        // return list; // then it will be a problem, because if the list is changed outside, then it will change the state of the object.
    }
}
```
- **_Note_:** Is above two rules are good that each time we are creating a new object and returning it?
- Ans: No, it is not good, because it will create a new object each time, and it will be a performance issue.

#### Deep Immutability
- If we are creating an immutable class, then we should make sure that the class is deeply immutable.
- Deeply immutable means, if the class contains any mutable object, then that object should also be immutable.
- For example,

```java
import java.util.Collections;
import java.util.List;

class Address {
    private final String city;
    private final String state;

    public Address(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}

public class ImmutableClass {
    private final int id;
    private final String name;
    private final List<Address> address;

    public ImmutableClass(int id, String name, List<Address> address) {
        this.id = id;
        this.name = name;
        this.address = Collections.unmodifiableList(address); // this will make sure that the list is immutable.
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public List<Address> getAddress() {
//        return address; 
//    }
    /**
     * Here (In the above method), we are returning the actual list, but it is immutable, so it is fine.
     * But the inner objects are mutable, so we should make sure that the inner objects are also immutable.
     * So, instead of returning the actual list, we should return a copy of it.
     * The below method is fine. This is called Deep Immutability.
     */
    public List<Address> getAddress() {
        return new ArrayList<>(address);
    }
}
```

5. Make all the fields final and private.
- private is fine, so that no other class can access it.
- But why field should be final, as class can not be extended, no setters in class, and fields are also private?
- Ans: Agree, in the whole class itself, the value of field can not be changed. But there is much more strong reason for this.
- As per Java JLS 17.5, Firstly look at the below facts
```java
/**
 * 1. An object is considered to be completely initialized when its constructor finishes.
 * 2. A thread that can only see a reference to an object after that object has been completely initialized is guaranteed to 
 * see the correctly initialized values for that object’s final fields. 
 * The constructor of an object is said to be finished once all the final fields are initialized. 
 * It doesn’t care about the non-final fields. This is the catch. This is what we are looking for. 
 * And this is the reason why we need to mark the fields as final in the immutable classes. 
 */
```
- Now, look at the below example,
```java
public class FinalField{
    private final int id;
    private int age;
    
    static FinalField finalField;
    
    public FinalField(){
        id = 1;  // final field is initialized here
        age = 20; // non-final field is initialized here
    }
    
    static void writer(){
        finalField = new FinalField();
    }
    
    static void reader(){
        if(finalField != null){
            int tempId = finalField.id; // this will always return 1
            int tempAge = finalField.age; // this may return 20 or 0 (Here is the exact catch)
        }
    }
}
/**
 * Threads can only see the objects once they are fully constructed.
 * So, if the object is fully constructed, then it is guaranteed that the final fields are initialized.
 * The constructor is said to be finished once all the final fields are initialized. It doesn't care for the non-final fields.
 */
```

#### Why should we care about Immutable classes?
- As Immutable classes are causing a lot of issue for Heap memory, so why should we care about it?
- Ans: Because, Immutable classes are **_Thread Safe_** which means they can be used in a multi-threaded environment without any synchronization.
- **_Caching:_** Perfect for caching, as their states can not be changed.

#### reference
1. [Java Immutable Class](https://medium.com/@cs.vivekgupta/everything-about-immutable-classes-in-java-9f5fe8e6ca54)

