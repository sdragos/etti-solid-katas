package solid.srp.starter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * TASK (see exercises/docs/01-srp.md): this class has more than one reason
 * to change. Find the responsibilities and split them up.
 */
public class InvoiceProcessor {

    public double calculateTotal(List<Item> items) {
        double total = 0;
        for (Item item : items) {
            total += item.getUnitPrice() * item.getQuantity();
        }
        return total;
    }

    public String formatInvoice(String customer, List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice for ").append(customer).append("\n");
        for (Item item : items) {
            sb.append(String.format(Locale.US, "  %-10s x%d @ %.2f%n",
                    item.getName(), item.getQuantity(), item.getUnitPrice()));
        }
        sb.append("Total: ").append(calculateTotal(items)).append("\n");
        return sb.toString();
    }

    public void saveToFile(String text, String path) {
        try {
            Files.createDirectories(Path.of(path).getParent());
            Files.writeString(Path.of(path), text);
        } catch (IOException e) {
            throw new RuntimeException("Could not save invoice", e);
        }
    }

    public void emailInvoice(String address, String text) {
        // simulated - a real implementation would call an email provider's API/SDK
        System.out.println("Emailing invoice to " + address + ":\n" + text);
    }
}
