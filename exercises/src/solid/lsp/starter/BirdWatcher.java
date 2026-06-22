package solid.lsp.starter;

import java.util.List;

/**
 * TASK (see exercises/docs/03-lsp.md): makeAllFly crashes the moment a
 * Penguin is in the list, even though Penguin IS a Bird. Fix the
 * abstraction, not just this method.
 */
public class BirdWatcher {
    public void makeAllFly(List<Bird> birds) {
        for (Bird b : birds) {
            b.fly();
        }
    }
}
