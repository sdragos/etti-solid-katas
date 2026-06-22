package solid.ocp.solution;

import java.util.List;

public class AreaCalculator {
    public double totalArea(List<Shape> shapes) {
        double total = 0;
        for (Shape s : shapes) {
            total += s.area();
        }
        return total;
    }
}
