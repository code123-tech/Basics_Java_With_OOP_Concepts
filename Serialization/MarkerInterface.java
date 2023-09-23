package Serialization;

/**
 * Interfaces which does not contain any field, or methods are known as Marker Interface.
 * Ex: Serializable, Cloneable.
 * Why to create an empty Marker Interface?
 * Basically this interfaces gives indication to JVM, that the class which has implemented marker interface, to perform
 * special operation on it based on interface we have used.
 * Ex: for serializable, it provides capability to that class to convert class object into byte stream object.
 *
 * Why to use Marker interface as indicator? can't we use any boolean flag?
 * yes, we can but this interfaces provides more cleanable, or more efficient way of indication. that is why we use them.
 * But now-a-days, we use Annotation to provide indication for something to JVM.
 *
 * Read: https://javarevisited.blogspot.com/2012/01/what-is-marker-interfaces-in-java-and.html
 */
public interface MarkerInterface {
}
