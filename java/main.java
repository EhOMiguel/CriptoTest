import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class main {
  public static StringBuffer reqs() {
    try {
      // URL do recurso desejado
      URL url = new URL("http://15.229.56.199/chaves");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.addRequestProperty("apikey", "123456");

      int responseCode = connection.getResponseCode();
      System.out.println("Código de Resposta: " + responseCode);


      if (responseCode != 200) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
              response.append(inputLine);
            }
            System.out.println("Resposta de Erro: " + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
      }
      
      if (responseCode == 200) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
        return response;
      } else {
        System.out.println("GET request not worked");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

    private static char[] InputStreamReader(InputStream errorStream) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'InputStreamReader'");
  }

    public static void main(String[] args) {

      System.out.println(reqs());

  }
}