package solid.dip.starter;

/**
 * TASK (see exercises/docs/05-dip.md): OrderService is welded to one
 * concrete storage technology and can't be tested without it.
 */
public class OrderService {
    private final MySqlOrderRepository repository = new MySqlOrderRepository();

    public void placeOrder(String order) {
        System.out.println("Validating order: " + order);
        repository.save(order);
    }
}
