
/**
 * Module name can follow reverse-DNS approach.
 * For module name, can follow the article <a href="https://blog.joda.org/2017/04/java-se-9-jpms-module-naming.html"> Here </a>
 */
module org.com.example{
    exports Java9.module.org1.com.example1;
    exports Java9.module.org2.com.example2;
}