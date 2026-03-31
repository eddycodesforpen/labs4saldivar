public class SenderApp {

    public static void main(String[] args) {
        try {
            // 1. Create Pizza
            Pizza pizza = new Pizza("Large", "Thin", "Tomato", "Cheese", 12.99);

            // 2. Convert to Flat File
            String flatData = PizzaFlatFileUtil.toFlatFile(pizza);
            System.out.println("Flat File Sent: " + flatData);

            // 3. Convert to JSON
            String jsonData = PizzaJsonUtil.toJson(pizza);
            System.out.println("JSON Sent: " + jsonData);

            // 4. Send to App 2 (Web Service)
            send("http://localhost:8080/pizza/flat", flatData);
            send("http://localhost:8080/pizza/json", jsonData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to send HTTP POST
    public static void send(String urlStr, String data) throws Exception {
        java.net.URL url = new java.net.URL(urlStr);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        java.io.OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        os.close();

        conn.getResponseCode();
    }
}

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReceiverApp {

    public static void main(String[] args) {
        SpringApplication.run(ReceiverApp.class, args);
    }
}