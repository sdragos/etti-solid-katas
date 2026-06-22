package solid.lsp.solution;

public class Sparrow extends Bird implements Flyable {
    public void move() {
        System.out.println("Sparrow hops along the ground");
    }

    public void fly() {
        System.out.println("Sparrow flies away");
    }
}
