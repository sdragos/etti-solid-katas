package solid.ocp.starter;

import java.util.List;

/**
 * TASK (see exercises/docs/02-ocp.md): adding a new shape means editing
 * this method. Refactor so that's no longer true.
 */
public class AreaCalculator {
    public double totalArea(List<Shape> shapes) {
        double total = 0;
        for (Shape s : shapes) {
            if (s.type.equals("RECTANGLE")) {
                total += s.width * s.height;
            } else if (s.type.equals("CIRCLE")) {
                total += Math.PI * s.radius * s.radius;
            } else {
                throw new IllegalArgumentException("Unknown shape: " + s.type);
            }
        }
        return total;
    }
}
