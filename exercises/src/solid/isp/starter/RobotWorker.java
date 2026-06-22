package solid.isp.starter;

/**
 * TASK (see exercises/docs/04-isp.md): this class is forced to implement
 * eat() and sleep() even though neither applies to a robot.
 */
public class RobotWorker implements Worker {
    public void work() { System.out.println("Robot is welding"); }

    public void eat() {
        throw new UnsupportedOperationException("Robots don't eat");
    }

    public void sleep() {
        throw new UnsupportedOperationException("Robots don't sleep");
    }
}
