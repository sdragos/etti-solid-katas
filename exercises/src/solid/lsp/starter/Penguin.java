package solid.lsp.starter;

public class Penguin extends Bird {
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}
