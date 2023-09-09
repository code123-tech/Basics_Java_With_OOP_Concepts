package BasicsJava.Files;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

/**
 * File System in Every Programming Lang is basically means, "The System which provides some operations so that we can
 * perform manipulation on the files present on the secondary Storage Device (Hard Disk)."
 * Java's File APIs are:
 * Original, java.io.File available Since Java 1.0 (since 1996)
 * New, java.nio.file.Path available Since Java 1.7 (Since 2011)
 * Read: https://www.marcobehler.com/guides/java-files
 * Note: For Starting File System in your new Project, It is recommended to use nio.file.Path API as it improves exception handling in various methods which are not present in File API.
 * Diff: https://www.oracle.com/technical-resources/articles/javase/nio.html
 */
public class FileSystem {
    public static void main(String[] args) throws URISyntaxException, IOException {
        // 1. You need file reference pointer for which, we will use Path API.
        // two ways to reference a file, Using Path.of() method (Present FROM JDK11), Paths.get() method (was being used TILL JDK10).

        // Using Path.of(), My JDK Version is not supported.
        // Path path = Path.of("C:/Users/swa80/OneDrive/Desktop/java/Learning/BasicsJava/Files/temp1.txt");
        // Path path = Path.of("C:\\Users\\swa80\\OneDrive\\Desktop\\java\\Learning\\BasicsJava\\Files\\temp1.txt");
        // Path path = Path.of("C:", "Users","swa80","OneDrive","Desktop", "java","Learning","BasicsJava","Files","temp1.txt");
        // Path path = Path.of(new URI("file:///C:/Users/swa80/OneDrive/Desktop/java/Learning/BasicsJava/Files/temp1.txt"));

        // My System has JDK 8, so Using Paths.get().
        // FROM JDK11, Paths.get() internally calls Path.of()
        Path path1 = Paths.get("C:/Users/swa80/OneDrive/Desktop/java/Learning/BasicsJava/Files/temp1.txt");
        Path path2 = Paths.get(new URI("file:///C:/Users/swa80/OneDrive/Desktop/java/Learning/Basics/Files/temp2.txt"));
        Path path3 = Paths.get(new URI("file:///C:/Users/swa80/OneDrive/Desktop/java/Learning/Basics/Files/temp3.txt"));

        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path3);

        // Since now, you have created only reference variable, now need to verify if exist or not.
        /**
         * Common File Operations
         */
        // To check if file exist or not, path reference variable which we have created.
        System.out.println("Is temp1 Exist: " + Files.exists(path1));
        System.out.println("Is temp2 Exist: " + Files.exists(path2));
        System.out.println("Is temp3 Exist: " + Files.exists(path3));

        // To compare two files, SINCE JDK12
        // Files.mismatch(path, Paths.get("c:\\dev\\whatever.txt")); --> Return First mismatch index.

        /**
         * Writing And Reading Files Using Path APIs
         * No Method readString TILL JDK10, AFTER JDK11, readString() was introduced.
         */
        List<String> lines1 = Files.readAllLines(path1);
        System.out.println(lines1);
        List<String> lines2 = Files.readAllLines(path2);
        System.out.println(lines2);

        /**
         * Similary, For creating file Files.createFile.
         */
        if(!Files.exists(path3)){
            Files.createFile(path3);
            Files.write(path3, Collections.singleton("Hello From temp3."));
        }else{
            Files.write(path3, Collections.singleton("Hello From temp3."), StandardOpenOption.APPEND);
        }
        List<String> lines3 = Files.readAllLines(path3);
        System.out.println(lines3);
    }
}
