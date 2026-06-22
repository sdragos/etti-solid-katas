package solid.srp.solution;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Item> items = List.of(
                new Item("Keyboard", 49.99, 2),
                new Item("Monitor", 199.99, 1)
        );

        InvoiceCalculator calculator = new InvoiceCalculator();
        InvoicePrinter printer = new InvoicePrinter(calculator);
        InvoiceFileRepository repository = new InvoiceFileRepository();
        InvoiceMailer mailer = new InvoiceMailer();

        String invoiceText = printer.format("Acme Corp", items);
        System.out.println(invoiceText);

        repository.save(invoiceText, "output/invoice.txt");
        mailer.send("billing@acme.example", invoiceText);
    }
}
