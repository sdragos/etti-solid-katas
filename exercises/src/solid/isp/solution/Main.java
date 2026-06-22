package solid.isp.solution;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Workable> workers = List.of(new HumanWorker(), new RobotWorker());
        for (Workable w : workers) {
            w.work(); // every Workable can really do this, no exceptions
        }

        Eatable human = new HumanWorker();
        human.eat(); // only asked of things that actually eat
    }
}
