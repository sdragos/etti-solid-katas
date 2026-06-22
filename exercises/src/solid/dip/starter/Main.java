package solid.dip.starter;

public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        service.placeOrder("2x Keyboard, 1x Monitor");
    }
}
