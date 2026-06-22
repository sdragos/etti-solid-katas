package solid.dip.solution;

public class MySqlOrderRepository implements OrderRepository {
    public void save(String order) {
        System.out.println("Saving to MySQL: " + order);
    }
}
