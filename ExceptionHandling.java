package Serialization;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionHandling {
    public static void main(String[] args) {
        /**
         * Exception Handling is a mechanism to handle the runtime errors so that the regular flow of the application can be preserved.
         * Ex: Exceptions are IOException, Null Pointer Exception, RemoteException, ClassNotFoundException etc.
         * Error And Exception are two different terms.
         * Errors are irrcoverable like: StackOverflow Error, Memory leak
         * Exceptions are recoverable like: RunTime Exception, Null Pointer Exception.
         */
        /**
         * Hierarchy
         *                    Object
         *                      |
         *                   Throwable
         *                    /     \
         *                  /        \
         *            Exceptions     Errors
         *                |            |
         *         a. Checked         a. Virtual Machine Error
         *         b. Unchecked       b. Assertion Error etc.
         *  Checked: Compile Time Exceptions.
         *  Unchecked: Run Time Exceptions.
         *
         *  Types Of Exceptions:
         *  a. User Defined,  b. BuiltIn Exception (Checked Exception, Unchecked Exception)
         *  Exception Occurs ----> Creates An exception Object ---> JRE Finds a suitable Exception handler (Catch Block)  ----> It it finds, then assign it to Exception Object, otherwise terminates the program.
         */
        /**
         * Error can be handled in the ways
         * 1. try...catch block
         * 2. try...catch...finally block
         * 3. try...catch with Resource.
         *
         * Interview: https://www.scientecheasy.com/2020/09/exception-handling-interview-questions.html/
         */

        // 1.
        try{
            // some statements
        }catch (Exception e){
            e.printStackTrace();
        }

        // 2.
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("Exception");
        }

        // The below one is used when we open a file object, and wanted to close it inside the try block itself.
        // Whatever Class Object we are taking that should be imported from AutoClosable class.
        // File class object is not AutoClosable.
        try(FileReader fileReader =  new FileReader("/")){

        }catch (Exception e){
            e.printStackTrace();
        }

        /**
         * Interview Questions
         * 1. Throw and Throws difference:  Throws is used with a method, which shows that method might show an exception
         *                  Throw basically disrupt the flow of program.
         * 2. Checked V/S Unchecked Exception.
         * 3. Java7 ARM Feature: Basically It is try-with-resource feature
         * 4. final, finally and finalize difference.
         *  final --> Keyword used with class, methods, instance variables.
         *  finally --> Used with try--catch block
         *  finalize() --> used by Garbage collector before object is destroyed.
         *  5. Create Custom Exception: Can be created either by extending the Exception class or any of its subclass.
         */
        // What is wrong in below code?

        /*
            try{
                foo();
            }catch (IOException e){
                e.printStackTrace();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        */

        // Since IOException is Parent class of FileNotFoundException, so you will get an error,
        // FileNotFoundException is already caught.
    }
    public static void foo() throws IOException, FileNotFoundException{

    }
}

// Ans 5.  IOException is in Checked Exception which is subclass of Exception class
class MyCustomException extends IOException{
    private static final long serialUID = 4664456874499611218L;
    private String errorCode = "unknown_exception";

    public MyCustomException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }
}

//
