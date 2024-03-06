import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        StringBuffer response = ApiClient.getPrimoInt();
        System.out.println(response);

        BigInteger primo = CryptoLogic.extractPrimo(response);
        BigInteger inteiro = CryptoLogic.extractInt(response);

        if (primo != null && inteiro != null) {
            System.out.println("Primo = " + primo);
            System.out.println("Inteiro = " + inteiro);

            Tela tela = new Tela();
            tela.exibir(inteiro, primo);
        } else {
            System.out.println("Não foi possível extrair o primo ou o inteiro.");
        }
    }
}
