package solid.ocp.solution;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Rectangle(3, 4),
                new Circle(2),
                new Triangle(5, 6) // new shape added, AreaCalculator was not touched
        );
        AreaCalculator calculator = new AreaCalculator();
        System.out.println("Total area: " + calculator.totalArea(shapes));
    }
}
