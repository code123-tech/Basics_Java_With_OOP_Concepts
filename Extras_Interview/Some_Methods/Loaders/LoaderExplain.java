package Extras_Interview.Some_Methods.Loaders;


public class LoaderExplain {

    static {
        System.out.println("Difference between Class.forName() and ClassLoader.loadClass()");
        System.out.println("---------------------------------------------------------------------");
    }

    public static void main(String[] args) {
            Difference1();
            System.out.println("-------------------------------------");
            Difference2();
            System.out.println("-------------------------------------");
            WhenToUseWhich();
    }

    private static void Difference1() {
        try {
            System.out.println("1. Class.forName() initializes (load, link and initialize) the class, whereas ClassLoader.loadClass() does not initialize (load, and link) the class.");
            System.out.println("Loading class using Class.forName()");
            Class.forName("Extras_Interview.Some_Methods.Loaders.LoaderExplain1");

            System.out.println(".....");

            System.out.println("Loading class using ClassLoader.loadClass()");
            ClassLoader.getSystemClassLoader().loadClass("Extras_Interview.Some_Methods.Loaders.LoaderExplain1");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void Difference2() {
        System.out.println("Class.forName() by default uses system classpath to load and initialize the class, but can be controlled using Class.forName(path, initialize, classLoader)");
        System.out.println("ClassLoader.loadClass() needs to be explicitly initialize the class by default. such as ClassLoader.getSystemClassLoader() or ClassLoader.getPlatformClassLoader().loadClass()");
    }

    private static void WhenToUseWhich(){
        System.out.println("1. If you need initialization of class, use Class.forName()");
        System.out.println("1. If you don't need initialization of class, and have to control classLoader instance, use ClassLoader.loadClass()");
    }
}
