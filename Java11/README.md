1. **New String Methods:** Java 11 added small but powerful methods to the String class. You no longer need Apache Commons or complex Regex for basic checks.
- `isBlank():` Returns true if the string is empty OR contains only whitespace (vastly better than .isEmpty()).
- `lines():` Splits the string into a Stream of lines, making it easy to process multi-line strings.
- `strip(), stripLeading(), stripTrailing():` These methods remove whitespace more effectively than trim
- `repeat(int count):` Repeats the string a specified number of times, which is great for simple string generation tasks.
- Check `StringAndArrayMethodUpdates.java` for examples.


2. **The other updates are:**
- **toArray Method:** collection interface now has a default method to convert a collection to an array without needing to pass an array constructor reference.
- **Not Predicate method:** A new static method in the Predicate interface that allows you to negate a predicate without writing a lambda.
- **var keyword in lambda parameters:** You can now use `var` in lambda parameters, which allows you to add annotations to them if needed.
- **Nested Based Access Control:** This allows nested classes to access private members of their enclosing class, and vice versa, without needing synthetic accessor methods.
- Check `StringAndArrayMethodUpdates.java` for examples.

3. **The Files API Upgrade:** Reading and writing files became a one-liner in Java 11.
- **Files.readString(path):** Reads the entire file into a String. 
- **Files.writeString(path, content):** Writes a String to a file.
- Check `CcnfigFileRefactorDemo.java` for examples.

4. **The Other changes**
- **Epsilon: No-Op Garbage Collector:**
  - This is a new GC that does nothing. It’s useful for testing and benchmarking when you want to measure the performance of your code without GC interference.
  - It is useful for short-lived applications.

- **Dynamic class-file constants:**
  - Java 11 extended the constant pool to include dynamic constants:
  ```java
    // Java 11
    public class DynamicConstantsExample {
        public static void main(String[] args) {
            // Using a dynamic constant
            switch (obj.getClass()) {
            case String.class -> System.out.println("It's a String");
            case Integer.class -> System.out.println("It's an Integer");
            default -> System.out.println("Something else");
        }
    }
  }
  ```

- **ZGC (Z Garbage Collector):**
  - experimental design introduced in Java 11
  - for Low Latency (< 10ms pause time)
  - scalable (multi-terabyte heaps)
  - concurrent (most work done concurrently with application threads)


5. **HTTP Client API:**
- Before Java 11, developers relied on third-party libraries like Apache HttpClient or OkHttp for
  making HTTP requests.
- In Java 11, a new HttpClient API was introduced in the `java.net.http` package, providing a modern
  and efficient way to perform HTTP operations.
- Check `HttpClientAsyncDemo.java` for examples.