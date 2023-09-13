package SOLID_PRINCIPALS.Dependency_Inversion;

import java.util.Arrays;
import java.util.List;

interface Modes{
    void save(String content);
}

class DBMode_ implements Modes{
    public void save(String content){
        System.out.println("Saving content: '" + content + "' into db..." );
    }
}

class FileMode_ implements Modes{
    public void save(String content){
        System.out.println("Saving content: '" + content + "' into File..." );
    }
}

class LocalStorageMode_ implements Modes{
    public void save(String content){
        System.out.println("Saving content: '" + content + "' into LocalStorage..." );
    }
}
class FileSave2{
    private List<Modes> savingModes = Arrays.asList(new DBMode_(), new FileMode_(), new LocalStorageMode_());

    public void saveContent(String content){
        for(Modes mode: savingModes){
            mode.save("Hello");
        }
    }
}

public class DIP_Follows {
    public static void main(String[] args) {
        FileSave2 fileSave2 = new FileSave2();
        fileSave2.saveContent("Hello");
    }
}
