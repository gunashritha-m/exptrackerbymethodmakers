import java.io.FileWriter;
import java.io.IOException;

public class TransactionLogger {
    private static final String LOG_FILE = "transactions.log";

    public static void logTransaction(String transactionDetails) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(transactionDetails + "\n");
        } catch (IOException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }
}