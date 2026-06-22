package solid.ocp.starter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                Shape.rectangle(3, 4),
                Shape.circle(2)
        );
        AreaCalculator calculator = new AreaCalculator();
        System.out.println("Total area: " + calculator.totalArea(shapes));
    }
}
