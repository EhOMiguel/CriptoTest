import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

    public static StringBuffer getPrimoInt() {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL("http://15.229.56.199/chaves");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("apikey", "123456");

            int responseCode = connection.getResponseCode();
            System.out.println("Código de Resposta: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                responseCode == 200 ? connection.getInputStream() : connection.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
