package solid.srp.solution;

public class InvoiceMailer {
    public void send(String address, String text) {
        System.out.println("Emailing invoice to " + address + ":\n" + text);
    }
}
