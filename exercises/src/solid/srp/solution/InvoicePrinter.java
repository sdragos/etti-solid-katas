package solid.srp.solution;

import java.util.List;
import java.util.Locale;

public class InvoicePrinter {
    private final InvoiceCalculator calculator;

    public InvoicePrinter(InvoiceCalculator calculator) {
        this.calculator = calculator;
    }

    public String format(String customer, List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice for ").append(customer).append("\n");
        for (Item item : items) {
            sb.append(String.format(Locale.US, "  %-10s x%d @ %.2f%n",
                    item.getName(), item.getQuantity(), item.getUnitPrice()));
        }
        sb.append("Total: ").append(calculator.calculateTotal(items)).append("\n");
        return sb.toString();
    }
}
