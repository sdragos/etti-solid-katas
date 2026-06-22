package solid.lsp.solution;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        BirdWatcher watcher = new BirdWatcher();

        Sparrow sparrow = new Sparrow();
        Penguin penguin = new Penguin();

        watcher.makeAllMove(List.of(sparrow, penguin)); // safe for every Bird
        watcher.makeAllFly(List.of(sparrow));            // only ever asked of Flyable things
    }
}
