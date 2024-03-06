import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CryptoLogic {

    public static BigInteger extractPrimo(StringBuffer response) {
        Pattern pattern = Pattern.compile("primo:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return new BigInteger(matcher.group(1));
        }
        return null; // Ou lance uma exceção personalizada
    }

    public static BigInteger extractInt(StringBuffer response) {
        Pattern pattern = Pattern.compile("inteiro:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return new BigInteger(matcher.group(1));
        }
        return null; // Ou lance uma exceção personalizada
    }

    public static BigInteger calculateAlfa(BigInteger inteiro, BigInteger segredo, BigInteger primo) {
        return inteiro.modPow(segredo, primo);
    }
}
