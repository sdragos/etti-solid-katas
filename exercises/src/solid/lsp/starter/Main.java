package solid.lsp.starter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        BirdWatcher watcher = new BirdWatcher();
        List<Bird> birds = List.of(new Sparrow(), new Penguin());
        watcher.makeAllFly(birds); // throws UnsupportedOperationException
    }
}
