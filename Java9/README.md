#### Java 9: Foundations of Modern Java
#####  `JShell (REPL - Read-Eval-Print Loop):` 
  - Interactive shell tool which allows us to execute Java code and get immediate results. Just like Python shell. 
  - In `Intellij`, if you have `JDK > 8`, then you can open `JShell` by `Tools -> JShell Console`. It lets you test the 
              code without writing the whole class.


##### `Compact Strings (JEP 254):` 
This is an architectural optimization of the java.lang.String class to reduce heap memory footprint.

The Evolution:
- JDK 6: Compressed Strings was introduced but later removed in JDK 7 due to performance overhead in complex string manipulations. 
- JDK 9+: Compact Strings was introduced as a more robust, default-enabled feature.

How it Works:
Historically, Java stored strings as a `char[] (UTF-16)`, using 2 bytes per character. Analysis showed that most strings in typical applications contain only Latin-1 characters, which only require 1 byte.
- **The Change:** The internal storage changed from `char[]` to `byte[]`. 
- **The "Coder" Flag:** A new field `private final byte coder` was added to the String class to track the encoding:
  - 0 (LATIN1): 1 byte per character. Used if all characters fit in the ISO-8859-1 range. 
  - 1 (UTF16): 2 bytes per character. Used if even one character requires a broader range (e.g., emojis or non-Latin scripts). 
- **Impact**: * Memory: Up to 50% reduction in String-related heap usage. 
  - **Garbage Collection:** Reduced memory pressure leads to fewer and faster GC cycles. 
- **Control:** While enabled by default, it can be disabled using the VM flag: -XX:-CompactStrings.
This is also one of the  old feature brought back in JDK 9. In JDK 6, Oracle introduced a feature called 
            `Compressed Strings` which was removed in JDK 7. In JDK 9, Oracle has brought back this feature with a new name 
            `Compact Strings`. This feature is enabled by default in JDK 9. This feature is used to reduce the memory 
            consumption of the `String` class. In JDK 8, the `String` class uses a `char` array to store the characters of the 
            string. In JDK 9, the `String` class uses a `byte` array to store the characters of the string. 

Let's go on the detailing of this feature:
- In JDK 8, the `String` class uses a `char` array to store the characters of the string. Each character in the `char` 
  array uses 2 bytes of memory. So, if we have a string of length 10, then it will use 20 bytes of memory. 
- As per [OpenJDK 254](https://openjdk.org/jeps/254), `strings are a major component of heap usage and, moreover, that 
most String objects contain only Latin-1 characters. Such characters require only one byte of storage, hence half of the 
space in the internal char arrays of such String objects is going unused.`
- So, to handle all of this, JDK developers introduced a new feature of `Compact Strings` where they use a `byte` array to 
  store the characters of the string. Each character in the `byte` array uses 1 byte of memory. 
- Now, how to know that string has used which byte of storage. For this a flag is used in the `String` class which is 
  `coder`. This `coder` flag is used to determine whether the `String` class is using `LATIN1` or `UTF16` encoding.
- `LATIN1` encoding is used when the string contains only Latin-1 characters. `LATIN1` encoding uses a `byte` array to 
  store the characters of the string. Each character in the `byte` array uses 1 byte of memory.
- `UTF16` encoding is used when the string contains non-Latin-1 characters. `UTF16` encoding uses a `char` array to store 
  the characters of the string. Each character in the `char` array uses 2 bytes of memory.
- So, If any character a the string uses 2 bytes of memory, then whole string will use `UTF16` encoding. If all the 
  characters of the string uses 1 byte of memory, then whole string will use `LATIN1` encoding.
- Pros:
  - REduction in memory consumption.
  - reduction in GC activity to remove the unused memory.
- Before this concept, In JDK6, Oracle introduced a feature called `Compressed Strings` which was removed in JDK 7 due to 
some performance issues.
- From JDK 9, this feature is default enabled. If you want to disable this feature, then you can use `-XX:-CompactStrings` in VM args 
while running Java program.


##### `JPMS (Java Platform Module System - Project Jigsaw)` 
JPMS introduced Module-Oriented Programming, shifting Java from a flat classpath to a modularized, 
encapsulated system.

**Core Definitions:**
A Module is a distribution format that groups related packages, resources (XML, images), and a Module 
Descriptor (**module-info.java**).

**The Module Descriptor (module-info.java):**
- **Naming:** Use the Reverse-DNS approach (e.g., com.saurabh.logic). Names must be unique and use dots, not dashes.
- **Directives:**
  - **requires <module>**: Declares a dependency. Without this, you cannot access another module's classes, even if they are public. 
  - **exports <package>**: Makes a package accessible to other modules. Hidden Packages are a key concept here; if a package isn't exported, it is private to the module. 
  - **exports <package> to <module>**: Qualified Export—grants access only to specific modules. 
  - **opens <package>**: Enables Deep Reflection at runtime. Necessary for frameworks like Spring and Hibernate to access private members. 
  - **uses <interface> / provides <interface> with <impl>:** Implements the Service Provider Interface (SPI) pattern for clean, decoupled architectures.

**Architectural Principles:**
1. **Strong Encapsulation:** You can finally hide internal implementation details (even public classes) from consumers.
 
2. **Reliable Configuration:** The JVM checks for missing dependencies at startup rather than throwing NoClassDefFoundError at runtime.

3. **Scalable Java (jlink):** Allows developers to assemble only the required modules into a custom, lightweight JRE (e.g., creating 40MB Docker images).

Module System Design Principles - Check some [here](https://openjdk.org/jeps/200)
      