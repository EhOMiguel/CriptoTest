import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.net.http.WebSocket;

public class ChatTela {
    private JFrame frame;
    private BigInteger primo, inteiro, alfa, segredo;
    private BigInteger alfaA = null;
    private BigInteger key = null;
    private JTextArea chatArea;
    private JTextArea infoArea;  // Referência para a área de chat
    private WebSocket webSocket;

    public ChatTela(BigInteger primo, BigInteger inteiro, BigInteger alfa, BigInteger segredo, WebSocket webSocket, String alfaAr) {
        this.primo = primo;
        this.inteiro = inteiro;
        this.alfa = alfa;
        this.segredo = segredo;
        this.webSocket = webSocket;
        // this.alfaB = alfaB;
        atualizar(alfaAr);
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Chat CriptoElmo B");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
    
        infoArea = new JTextArea();
        
        // Verifica se alfaB e key são null e atualiza o texto adequadamente
        String alfaBText = (alfaA == null) ? "Aguardando..." : alfaA.toString();
        String keyText = (key == null) ? "Aguardando..." : key.toString();
        
        infoArea.setText(String.format("Primo: %s\nInteiro: %s\nSegredo: %s\nAlfa: %s\nAlfa a: %s\nChave de criptografia: %s",
                                        primo.toString(), inteiro.toString(), segredo.toString(),
                                        alfa.toString(), alfaBText, keyText));
        infoArea.setEditable(false);

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        JTextField messageField = new JTextField();
        JButton sendButton = new JButton("Enviar");
        
        // Ação para o botão Enviar
        sendButton.addActionListener(e -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                chatArea.append("Você: " + message + "\n"); // Adiciona a mensagem ao chat
                messageField.setText("");// Limpa o campo de mensagem
                
                webSocket.sendText(message, true);
            }
        });

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(messageField, BorderLayout.CENTER);
        southPanel.add(sendButton, BorderLayout.EAST);
        
        chatPanel.add(southPanel, BorderLayout.SOUTH);

        frame.add(infoArea, BorderLayout.NORTH);
        frame.add(chatPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void atualizar(String alfaAr) {
        System.out.println("entrei");
        String[] partes = alfaAr.split(":");
        
        if (partes.length >= 2) {
            try {
                alfaA = new BigInteger(partes[1]);
                key = alfaA.modPow(segredo, primo);
                
                // Atualiza o texto de infoArea com os novos valores
                SwingUtilities.invokeLater(() -> {
                    String alfaAText = alfaA.toString();
                    String keyText = key.toString();
                    
                    infoArea.setText(String.format("Primo: %s\nInteiro: %s\nSegredo: %s\nAlfa a: %s\nAlfa b: %s\nChave de criptografia: %s",
                                                   primo.toString(), inteiro.toString(), segredo.toString(),
                                                   alfa.toString(), alfaAText, keyText));
                });
                
            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter o número para BigInteger: " + e.getMessage());
            }
        } else {
            System.out.println("Formato inválido recebido: " + alfaAr);
        }
    }
}
