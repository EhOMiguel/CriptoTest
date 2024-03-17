import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.net.http.WebSocket;

public class ChatTela {
    private JFrame frame;
    private BigInteger primo, inteiro, alfa, segredo;
    private BigInteger alfaB = null;
    private BigInteger key = null;
    private JTextArea chatArea;
    private JTextArea infoArea;  // Referência para a área de chat
    private WebSocket webSocket;

    public ChatTela(BigInteger primo, BigInteger inteiro, BigInteger alfa, BigInteger segredo, WebSocket webSocket, String alfaBr) {
        this.primo = primo;
        this.inteiro = inteiro;
        this.alfa = alfa;
        this.segredo = segredo;
        this.webSocket = webSocket;
        // this.alfaB = alfaB;
        atualizar(alfaBr);
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Chat CriptoElmo A");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
    
        infoArea = new JTextArea();
        
        // Verifica se alfaB e key são null e atualiza o texto adequadamente
        String alfaBText = (alfaB == null) ? "Aguardando..." : alfaB.toString();
        String keyText = (key == null) ? "Aguardando..." : key.toString();
        
        infoArea.setText(String.format("Primo: %s\nInteiro: %s\nSegredo: %s\nAlfa a: %s\nAlfa b: %s\nChave de criptografia: %s",
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
                
                String mensagemCriptografada = criptografarCesar(message, key);
                webSocket.sendText("mensagemA:" + mensagemCriptografada, true); // Envia a mensagem criptografada

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

    public void atualizar(String alfaBr) {
        System.out.println("entrei");
        String[] partes = alfaBr.split(":");
        
        if (partes.length >= 2) {
            try {
                alfaB = new BigInteger(partes[1]);
                key = alfaB.modPow(segredo, primo);
                
                // Atualiza o texto de infoArea com os novos valores
                SwingUtilities.invokeLater(() -> {
                    String alfaBText = alfaB.toString();
                    String keyText = key.toString();
                    
                    infoArea.setText(String.format("Primo: %s\nInteiro: %s\nSegredo: %s\nAlfa a: %s\nAlfa b: %s\nChave de criptografia: %s",
                                                   primo.toString(), inteiro.toString(), segredo.toString(),
                                                   alfa.toString(), alfaBText, keyText));
                });
                
            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter o número para BigInteger: " + e.getMessage());
            }
        } else {
            System.out.println("Formato inválido recebido: " + alfaBr);
        }
    }

    private String criptografarCesar(String mensagem, BigInteger chave) {
        int deslocamento = chave.mod(BigInteger.valueOf(26)).intValue(); // Converte a chave para um deslocamento válido
        StringBuilder mensagemCriptografada = new StringBuilder();
        for (char caracter : mensagem.toCharArray()) {
            if (Character.isLetter(caracter)) {
                char base = Character.isLowerCase(caracter) ? 'a' : 'A';
                char caracterCriptografado = (char) ((caracter - base + deslocamento) % 26 + base);
                mensagemCriptografada.append(caracterCriptografado);
            } else {
                mensagemCriptografada.append(caracter); // Não encripta caracteres não alfabéticos
            }
        }
        return mensagemCriptografada.toString();
    }

    public String descriptografarCesar(String mensagemCriptografada, BigInteger chave) {
        int deslocamento = chave.mod(BigInteger.valueOf(26)).intValue();
        StringBuilder mensagemDescriptografada = new StringBuilder();
        for (char caracter : mensagemCriptografada.toCharArray()) {
            if (Character.isLetter(caracter)) {
                char base = Character.isLowerCase(caracter) ? 'a' : 'A';
                int deslocamentoReverso = 26 - deslocamento; // Deslocamento inverso para descriptografar
                char caracterDescriptografado = (char) ((caracter - base + deslocamentoReverso) % 26 + base);
                mensagemDescriptografada.append(caracterDescriptografado);
            } else {
                mensagemDescriptografada.append(caracter); // Não modifica caracteres não alfabéticos
            }
        }
        return mensagemDescriptografada.toString();
    }
    
    public void appendMensagem(String mensagem) {
        chatArea.append(mensagem + "\n");
    }
    
    public BigInteger getKey() {
        return this.key; 
    }
}
