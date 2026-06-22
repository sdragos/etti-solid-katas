package solid.isp.starter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Worker> workers = List.of(new HumanWorker(), new RobotWorker());
        for (Worker w : workers) {
            w.work();
            w.eat(); // crashes for RobotWorker
        }
    }
}
