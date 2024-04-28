##### Java 9 Features
- `JShell:` Interactive shell tool which allows us to execute Java code and get immediate results. Just like Python shell.
            In `Intellij`, if you have `JDK > 8`, then you can open `JShell` by `Tools -> JShell Console`. It lets you test the 
            code without writing the whole class.
- `Compact Strings:` This is also one of the  old feature brought back in JDK 9. In JDK 6, Oracle introduced a feature called 
            `Compressed Strings` which was removed in JDK 7. In JDK 9, Oracle has brought back this feature with a new name 
            `Compact Strings`. This feature is enabled by default in JDK 9. This feature is used to reduce the memory 
            consumption of the `String` class. In JDK 8, the `String` class uses a `char` array to store the characters of the 
            string. In JDK 9, the `String` class uses a `byte` array to store the characters of the string. 
  - Let's go on the detailing of this feature:
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

- `JPMS (Java Programming Module System)`: A new feature introduced in Java 9 which was taken into action a long back in 2005, and released with Java 9 in 2017.
    - This is very powerful change which introduces `Module Programming` in Java. Module is a collection of packages
      which are related to each other as well as some resources (media files, XML files etc).
    - Module is powerful concept than jar files, as it combines all the required packages into a single module. Now, where to put all the 
      require packages inside that module. For that there is a special file called `module-info.java` which tells Java to include all the 
      requested Java packages in the module.
    -  `module-info.java` file can include which is also known as module descriptor file:
      - `Name - ` name of our module, there are certain rules for defining it:
        - It should be unique. 
        - Two approaches can be followed: `Project-style` and `Reverse-DNS` for naming conventions. 
          but it is recommended to use `reverse-DNS` approach, just as a package name follows. 
        - As Module is group of related packages, so name can be related to package names.
        - It allows dots, not dashes in the name.
        - can see more on [here](https://blog.joda.org/2017/04/java-se-9-jpms-module-naming.html)
      - `Dependencies - `It includes dependencies needed by the module. It can be of two types:
        - `requires` - It is used to include the required module in the current module.
        - `exports` - It is used to export the packages (make packages public) to other modules. If we don't export a package
          of a module, It becomes `Hidden package` for the module. `Export of a package can be done specific package of other 
          module using **to** keyword.`
      - `Services - ` Module can help to provide services to other modules as well as they can consume services from other
        modules. This is done using `provides` and `uses` keywords. [Self Explanatory keywords]
      - `Reflections - ` It is used to access the private members of the module. It is done using `opens` keyword.
    - Module System Design Principles - Check some [here](https://openjdk.org/jeps/200)
    - 
      