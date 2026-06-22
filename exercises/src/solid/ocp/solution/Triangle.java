package solid.ocp.solution;

// Added later, after AreaCalculator already existed - proves the design is
// open for extension without modifying AreaCalculator.
public class Triangle implements Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    public double area() {
        return 0.5 * base * height;
    }
}
