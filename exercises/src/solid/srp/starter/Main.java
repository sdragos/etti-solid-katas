package solid.srp.starter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        InvoiceProcessor processor = new InvoiceProcessor();
        List<Item> items = List.of(
                new Item("Keyboard", 49.99, 2),
                new Item("Monitor", 199.99, 1)
        );

        String invoiceText = processor.formatInvoice("Acme Corp", items);
        System.out.println(invoiceText);

        processor.saveToFile(invoiceText, "output/invoice.txt");
        processor.emailInvoice("billing@acme.example", invoiceText);
    }
}
