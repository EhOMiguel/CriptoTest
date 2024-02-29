import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class main {
  public static StringBuffer pegaPrimo() {
    try {
      // URL do recurso desejado
      URL url = new URL("http://15.229.56.199/chaves");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.addRequestProperty("apikey", "123456");

      int responseCode = connection.getResponseCode();
      System.out.println("Código de Resposta: " + responseCode);


      if (responseCode != 200) {
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
          String linha;
          StringBuffer response = new StringBuffer();
          while ((linha = leitor.readLine()) != null) {
            response.append(linha);
          }
          // System.out.println("Resposta de Erro: " + response.toString());
          return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
      }
      
      if (responseCode == 200) {
        BufferedReader leitor = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String linha;
        StringBuffer response = new StringBuffer();
        while ((linha = leitor.readLine()) != null) {
          response.append(linha);
        }

        // System.out.println(response.toString());
        return response;
      } else {
        System.out.println("GET request not worked");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {

    StringBuffer response = pegaPrimo();
    System.out.println(response);
    BigInteger inteiro = null;
    BigInteger primo = null;

    
    Pattern pattern = Pattern.compile("\"error_msg\":\"(.*?)\"");
    Matcher matcher = pattern.matcher(response);
    if (matcher.find()) {
        System.out.println(matcher.group(1));
        return;
    }


    pattern = Pattern.compile("primo:\"(.*?)\"");
    matcher = pattern.matcher(response);
    if (matcher.find()) {
        System.out.println(matcher.group(1));
        primo = new BigInteger(matcher.group(1));
    }


    pattern = Pattern.compile("inteiro:\"(.*?)\"");
    matcher = pattern.matcher(response);
    if (matcher.find()) {
        System.out.println(matcher.group(1));
        inteiro = new BigInteger(matcher.group(1));
    }


    BigInteger segredo = new BigInteger(20, new SecureRandom());
    BigInteger alfa = inteiro.modPow(segredo, primo);
    System.out.println(alfa);

  }
}