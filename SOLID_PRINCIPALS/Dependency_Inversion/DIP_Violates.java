package SOLID_PRINCIPALS.Dependency_Inversion;

class DBMode{
    public void save(String content){
        System.out.println("Saving content: '" + content + "' into db..." );
    }
}

class FileMode{
    public void save(String content){
        System.out.println("Saving content: '" + content + "' into File..." );
    }
}
class FileSave1{
    private DBMode dbMode = new DBMode();
    private FileMode fileMode = new FileMode();
    public void saveContent(String content){
        dbMode.save(content);
        fileMode.save(content);
    }
}

public class DIP_Violates {
    public static void main(String[] args) {
        FileSave1 fileSave1 = new FileSave1();
        fileSave1.saveContent("Hello");
    }
}
