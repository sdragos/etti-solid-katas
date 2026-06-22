package solid.dip.solution;

public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void placeOrder(String order) {
        System.out.println("Validating order: " + order);
        repository.save(order);
    }
}
