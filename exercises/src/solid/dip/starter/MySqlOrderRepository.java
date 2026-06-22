package solid.dip.starter;

public class MySqlOrderRepository {
    public void save(String order) {
        System.out.println("Saving to MySQL: " + order);
    }
}
