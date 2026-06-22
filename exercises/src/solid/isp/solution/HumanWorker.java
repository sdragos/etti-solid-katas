package solid.isp.solution;

public class HumanWorker implements Workable, Eatable, Sleepable {
    public void work() { System.out.println("Human is working"); }
    public void eat() { System.out.println("Human is eating lunch"); }
    public void sleep() { System.out.println("Human is sleeping"); }
}
