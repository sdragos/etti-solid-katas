package solid.dip.solution;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {
    private final List<String> orders = new ArrayList<>();

    public void save(String order) {
        orders.add(order);
        System.out.println("Saved to memory (test double): " + order);
    }

    public List<String> getOrders() {
        return orders;
    }
}
