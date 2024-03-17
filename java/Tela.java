import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.net.http.WebSocket;

public class Tela {
    private final JFrame frame;
    private final JLabel labelAviso;
    private JLabel labelPrimoInteiro;
    private final GridBagConstraints constraints;
    private static BigInteger primo;
    private static BigInteger inteiro;
    private WebSocket webSocket;
    private JButton iniciarChatBotao;
    private BigInteger segredo;
    private BigInteger alfa; 
    private String alfaB; 
    private static ChatTela chatTela;

    public Tela(WebSocket webSocket) {
        this.webSocket = webSocket;
        frame = new JFrame("CriptoElmo A");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 0, 5, 0);

        labelAviso = new JLabel();
        labelAviso.setForeground(Color.RED);
        labelAviso.setVisible(false);
        frame.add(labelAviso, constraints);

        labelPrimoInteiro = new JLabel("Primo:       Inteiro: ");
        constraints.gridy = 1;
        frame.add(labelPrimoInteiro, constraints);

        iniciarChatBotao = new JButton("Iniciar Chat");
        iniciarChatBotao.setEnabled(false); // Inicia desabilitado
        iniciarChatBotao.addActionListener(e -> iniciarChat());
        constraints.gridy = 6;
        frame.add(iniciarChatBotao, constraints);

        frame.setVisible(true);
    }

    private void addComponent(Component component, int gridy) {
        constraints.gridy = gridy;
        frame.add(component, constraints);
    }

    public void exibir(BigInteger primoR, BigInteger inteiroR) {
        labelAviso.setVisible(false);
        primo = primoR;
        inteiro = inteiroR;

        // Reutilizando o método para adicionar componentes
        labelPrimoInteiro.setText("Primo: " + primo + "       Inteiro: " + inteiro);
        
        addComponent(new JLabel("Digite a chave privada:"), 2);
        JTextField textChavePriv = new JTextField(20);
        addComponent(textChavePriv, 3);

        

        JButton butaumCalcular = new JButton("Calcular");
        iniciarChatBotao.setEnabled(false);
        butaumCalcular.addActionListener(e -> calcular(primo, inteiro, textChavePriv.getText()));
        addComponent(butaumCalcular, 6);

        // frame.pack(); // Ajusta o tamanho da janela com base nos componentes
        frame.setVisible(true);
    }

    public void atualizar(BigInteger primoR, BigInteger inteiroR) {
        primo = primoR;
        inteiro = inteiroR;
        labelPrimoInteiro.setText("Primo: " + primo + "       Inteiro: " + inteiro);   
    }

    private void iniciarChat() {
        if (chatTela == null) {
            chatTela = new ChatTela(primo, inteiro, alfa, segredo, webSocket, alfaB);
            Main.chatTela = Tela.chatTela;
        }
        frame.dispose(); // Fecha a janela atual
    }

    public void habilitarIniciarChat(String alfaBr) {
        this.alfaB = alfaBr;
        // this.segredo = key;
        SwingUtilities.invokeLater(() -> iniciarChatBotao.setEnabled(true));
    }

    public void calcular(BigInteger primo, BigInteger inteiro, String chavePrivadaTexto) {
        try {
            this.segredo = new BigInteger(chavePrivadaTexto);
            this.alfa = inteiro.modPow(segredo, primo);
            webSocket.sendText("alfaA:" + alfa.toString(), true); // Envia alfa para o servidor
            labelAviso.setText("<html>Alfa calculado: " + alfa + "</html>");
            labelAviso.setForeground(new Color(0, 128, 0));
            labelAviso.setVisible(true);
        } catch (NumberFormatException e) {
            labelAviso.setText("Erro ao calcular. Apenas números são aceitos.");
            labelAviso.setForeground(Color.RED);
            labelAviso.setVisible(true);
        }
    }
}
