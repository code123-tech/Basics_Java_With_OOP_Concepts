package SOLID_PRINCIPALS.Interface_Seggregation;

interface IShape{
    void drawCircle();
    void drawSquare();
}

class Circle implements IShape{
    @Override
    public void drawCircle() {
        System.out.println("Drawing Circle...");
    }

    @Override
    public void drawSquare() {

    }
}

class Square implements IShape{
    @Override
    public void drawCircle() {
    }
    @Override
    public void drawSquare() {
        System.out.println("Drawing Square...");
    }
}
public class IS_Violation {
    public static void main(String[] args) {
        IShape circle = new Circle();
        IShape square = new Square();
        circle.drawCircle();
        square.drawSquare();
        // The above breaking SR principle as circle class has another respons. of drawing Aquare.
        // breaks rule of Interface Segregation principle.
    }
}
