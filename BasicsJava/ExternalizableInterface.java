package BasicsJava;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * To customize the process of Serialization, we can use Externalizable interface.
 * Serialization basically controlled by JVM, and when we use Externalizable interface, total control comes to Programmer
 * itself.
 * Read: https://www.scientecheasy.com/2021/08/externalization-in-java.html/
 */
public class ExternalizableInterface implements Externalizable {

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        // Logic for Serializing
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        // Logic for De Serializing
    }
}
