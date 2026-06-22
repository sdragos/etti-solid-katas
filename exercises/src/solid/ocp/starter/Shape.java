package solid.ocp.starter;

public class Shape {
    public String type; // "RECTANGLE" or "CIRCLE"
    public double width;
    public double height;
    public double radius;

    public static Shape rectangle(double width, double height) {
        Shape s = new Shape();
        s.type = "RECTANGLE";
        s.width = width;
        s.height = height;
        return s;
    }

    public static Shape circle(double radius) {
        Shape s = new Shape();
        s.type = "CIRCLE";
        s.radius = radius;
        return s;
    }
}
