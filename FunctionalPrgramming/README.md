#### Introduction
- `Functional Prgramming` is kind of programming paradigm which totally focus on evaluating the result only if it is needed, 
    otherwise why to waste CPU cycle.
- It's a kind of programming where functions are constructed in from of a chain using `apply()` and `composing functions`.
- In Java, `lambda Expression` is base of functional programming.
- `Lambda Expressions` themselves are objects, or we can say they are concrete implementations of `Functional Interfaces`.
- A `Functional Interface` is an interface with only one abstract method, and can have several `static` and `default` method definitions.
- To validate an interface is functional or not, Java has introduced an annotation `@FunctionalInterface`.

```java
Adder adder = new Adder() {
    @Override
    public int add(int n1, int n2) {
       return n1 + n2;
    }
}

// Now, in lambda form 
Adder adder = (int n1, int n2) -> return n1 + n2;

// We dont even have to specify Datatypes, remember, compiler will 
// automatically infer it. 
// But we need simple brackets because we have 2 inputs.
Adder adder = (n1, n2) -> return n1 + n2;

// And, we don't need return keyword as well
Adder adder = (n1, n2) -> n1 + n2;

```

#### BuiltIn Functional Interfaces
- `java.util.function` package contains all the built-in functional interfaces.
- There are 43 functional interfaces in this package.
- These interfaces are categorized into 5 categories
    - `Supplier` - Takes no input, returns output
    - `Consumer` - Takes input, returns no output
    - `Predicate` - Takes input, returns boolean
    - `Function` - Takes input, returns output
    - `UnaryOperator` - Takes input, returns output of same type
    - `BinaryOperator` - Takes two input, returns output of same type
    - `BiFunction` - Takes two input, returns output of any type
    - `BiConsumer` - Takes two input, returns no output

#### Predicate functional interface
- `Predicate` is a functional interface which takes input and returns boolean.
- It has a method `test()` which takes input and returns boolean.
- It has 3 default methods
    - `and()` - Takes another predicate and returns a predicate which is a result of `AND` operation between two predicates.
    - `or()` - Takes another predicate and returns a predicate which is a result of `OR` operation between two predicates.
    - `negate()` - Returns a predicate which is a result of `NOT` operation on current predicate.
- It has 2 static methods
    - `isEqual()` - Takes an object and returns a predicate which checks equality of input object with the object passed in this method.
    - `not()` - Returns a predicate which is a result of `NOT` operation on current predicate.

#### Function functional interface
- `Function` is a functional interface which takes input and returns output.
- It has a method `apply()` which takes input and returns output.
- It has 2 default methods
    - `andThen()` - Takes another function and returns a function which is a result of `AND` operation between two functions.
    - `compose()` - Takes another function and returns a function which is a result of `COMPOSE` operation between two functions.
- It has 1 static methods
  - `identity()` - Returns a function which returns the same input as output.


#### Ways of Implementing Functional Interfaces
1. Anonymous Inner Class (Covered)
2. Lambda Expression  (Covered)
3. Method Reference  
4. Constructor Reference

#### Method Reference

- [Read More about FP from here](https://medium.com/@cs.vivekgupta/java-functional-programming-179334150eb2)