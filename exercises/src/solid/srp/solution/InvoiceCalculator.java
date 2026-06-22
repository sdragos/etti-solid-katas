package solid.srp.solution;

import java.util.List;

public class InvoiceCalculator {
    public double calculateTotal(List<Item> items) {
        double total = 0;
        for (Item item : items) {
            total += item.getUnitPrice() * item.getQuantity();
        }
        return total;
    }
}
