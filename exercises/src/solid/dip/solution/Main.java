package solid.dip.solution;

public class Main {
    public static void main(String[] args) {
        OrderService realService = new OrderService(new MySqlOrderRepository());
        realService.placeOrder("2x Keyboard, 1x Monitor");

        InMemoryOrderRepository testRepo = new InMemoryOrderRepository();
        OrderService testService = new OrderService(testRepo); // same class, swapped dependency
        testService.placeOrder("Test order");
        System.out.println("Orders captured in test repo: " + testRepo.getOrders());
    }
}
