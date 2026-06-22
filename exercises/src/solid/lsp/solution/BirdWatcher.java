package solid.lsp.solution;

import java.util.List;

public class BirdWatcher {
    public void makeAllFly(List<Flyable> flyers) {
        for (Flyable f : flyers) {
            f.fly();
        }
    }

    public void makeAllMove(List<Bird> birds) {
        for (Bird b : birds) {
            b.move();
        }
    }
}
