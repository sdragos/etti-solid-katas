package solid.srp.solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InvoiceFileRepository {
    public void save(String text, String path) {
        try {
            Files.createDirectories(Path.of(path).getParent());
            Files.writeString(Path.of(path), text);
        } catch (IOException e) {
            throw new RuntimeException("Could not save invoice", e);
        }
    }
}
