import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

public class Tela {
    private final JFrame frame;
    private final JLabel labelAviso;
    private final GridBagConstraints constraints;

    public Tela() {
        frame = new JFrame("CriptoElmo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        // constraints.gridwidth = GridBagConstraints.REMAINDER;
        // constraints.anchor = GridBagConstraints.NORTH;
        // constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 0, 5, 0);

        labelAviso = new JLabel();
        labelAviso.setForeground(Color.RED);
        labelAviso.setVisible(false);

        frame.add(labelAviso, constraints); // Adiciona apenas uma vez

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
    }

    private void addComponent(Component component, int gridy) {
        constraints.gridy = gridy;
        frame.add(component, constraints);
    }

    public void exibir(BigInteger primo, BigInteger inteiro) {
        labelAviso.setVisible(false);

        // Reutilizando o método para adicionar componentes
        addComponent(new JLabel("Primo: " + primo + "       Inteiro: " + inteiro), 1);
        
        addComponent(new JLabel("Digite a chave privada 1:"), 2);
        JTextField textChavePriv = new JTextField(20);
        addComponent(textChavePriv, 3);

        addComponent(new JLabel("Digite a chave privada 2:"), 4);
        JTextField textChavePriv2 = new JTextField(20);
        addComponent(textChavePriv2, 5);
        

        JButton butaumCalcular = new JButton("Calcular");
        butaumCalcular.addActionListener(e -> calcular(primo, inteiro, textChavePriv.getText(), textChavePriv2.getText()));
        addComponent(butaumCalcular, 6);

        // frame.pack(); // Ajusta o tamanho da janela com base nos componentes
        frame.setVisible(true);
    }

    private void calcular(BigInteger primo, BigInteger inteiro, String chavePrivadaTexto, String chavePrivadaTexto2) {
        try {
            BigInteger segredo = new BigInteger(chavePrivadaTexto);
            BigInteger segredo2 = new BigInteger(chavePrivadaTexto2);
            BigInteger alfa = inteiro.modPow(segredo, primo);
            BigInteger alfa2 = inteiro.modPow(segredo2, primo);
            labelAviso.setText("<html>Alfa: " + alfa + "<br>Beta: " + alfa2 + "</html>");
            
            labelAviso.setForeground(new Color(0, 128, 0)); // Um verde mais escuro
        } catch (NumberFormatException err) {
            labelAviso.setText("Apenas números serão aceitos!");
            labelAviso.setForeground(Color.RED);
        }
        labelAviso.setVisible(true);
    }
}
