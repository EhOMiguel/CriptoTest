import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class Main {
    private static int contador = 0;
    private static JTextArea textArea;
    private static Tela tela;
    public static ChatTela chatTela;
    private static WebSocket webSocketGlobal;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
            connectToWebSocket("ws://localhost:8080/chat"); // Substitua pela URI do seu WebSocket
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Dados do WebSocket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.pack();
        frame.setSize(400, 200); // Tamanho da janela
        frame.setVisible(true);
    }

    private static void connectToWebSocket(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        WebSocket.Listener listener = new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                System.out.println("Conexão WebSocket aberta.");
                webSocket.request(1);
                // tela = new Tela(webSocketGlobal);
            }

            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                contador++;
                System.out.println("Mensagem recebida: " + data);
                if (contador == 1) {
                        // Converte CharSequence para StringBuffer para usar com CryptoLogic
                    StringBuffer responseBuffer = new StringBuffer(data.toString());

                    BigInteger primo = CryptoLogic.extractPrimo(responseBuffer);
                    BigInteger inteiro = CryptoLogic.extractInt(responseBuffer);
                    

                    if (primo != null && inteiro != null) {
                        SwingUtilities.invokeLater(() -> tela.exibir(primo, inteiro));
                    } else {
                        System.out.println("Não foi possível extrair os números.");
                    }
                }else{
                    if(data.toString().startsWith("alfaA:")) {
                        tela.habilitarIniciarChat(data.toString()); // Habilita o botão "Iniciar Chat"

                    }else if(data.toString().startsWith("mensagemA:")){
                        
                        String mensagemCriptografada = data.toString().substring("mensagemA:".length());
                    
                        if (chatTela != null) {
                            System.out.println(mensagemCriptografada);
                            // Usa a chave para descriptografar a mensagem
                            String mensagemDescriptografada = chatTela.descriptografarCesar(mensagemCriptografada, chatTela.getKey());
                            System.out.println(mensagemDescriptografada);
                            SwingUtilities.invokeLater(() -> {
                                chatTela.appendMensagem("Stranger: " + mensagemDescriptografada);
                            });
                        }

                    }else{
                        StringBuffer responseBuffer = new StringBuffer(data.toString());

                        BigInteger primo = CryptoLogic.extractPrimo(responseBuffer);
                        BigInteger inteiro = CryptoLogic.extractInt(responseBuffer);
                        if (primo != null && inteiro != null) {
                            SwingUtilities.invokeLater(() -> tela.atualizar(primo, inteiro));
                        } else {
                            System.out.println("Não foi possível extrair os números.");
                        }
                        tela.atualizar(primo, inteiro);
                    }
                    
                }
                webSocket.request(1); // Solicita a próxima mensagem
                return WebSocket.Listener.super.onText(webSocket, data, last);
                
            }

        };

        try {
            webSocketGlobal = client.newWebSocketBuilder()
                    .buildAsync(URI.create(uri), listener)
                    .join();
                    tela = new Tela(webSocketGlobal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
