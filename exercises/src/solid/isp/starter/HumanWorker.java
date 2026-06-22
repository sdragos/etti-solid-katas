package solid.isp.starter;

public class HumanWorker implements Worker {
    public void work() { System.out.println("Human is working"); }
    public void eat() { System.out.println("Human is eating lunch"); }
    public void sleep() { System.out.println("Human is sleeping"); }
}
