package SOLID_PRINCIPALS.Interface_Seggregation;
interface ICircleShape{
    void drawCircle();
}
interface ISquareShape{
    void drawSquare();
}
interface ITriangleShape{
    void drawTriangle();
}
class Circle_ implements ICircleShape{
    @Override
    public void drawCircle() {
        System.out.println("Drawing Circle...");
    }
}

class Square_ implements ISquareShape{
    @Override
    public void drawSquare() {
        System.out.println("Drawing Square...");
    }
}

class Triangle_ implements ITriangleShape{
    @Override
    public void drawTriangle() {
        System.out.println("Drawing Triangle...");
    }
}

public class IS_Follows {
    public static void main(String[] args) {
        ICircleShape circle = new Circle_();
        ISquareShape square = new Square_();
        ITriangleShape triangle = new Triangle_();
        circle.drawCircle();
        square.drawSquare();
        triangle.drawTriangle();
        // Follows SR principle, and Interface segregation principle.
    }
}
