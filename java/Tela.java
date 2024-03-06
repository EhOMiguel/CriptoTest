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
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 0, 5, 0);

        labelAviso = new JLabel();
        labelAviso.setForeground(Color.RED);
        labelAviso.setVisible(false);

        frame.add(labelAviso, constraints); // Adiciona apenas uma vez
    }

    private void addComponent(Component component, int gridy) {
        constraints.gridy = gridy;
        frame.add(component, constraints);
    }

    public void exibir(BigInteger primo, BigInteger inteiro) {
        labelAviso.setVisible(false);

        // Reutilizando o método para adicionar componentes
        addComponent(new JLabel("Primo: " + primo + "       Inteiro: " + inteiro), 0);
        addComponent(new JLabel("Digite sua chave privada:"), 1);

        JTextField textChavePriv = new JTextField(20);
        addComponent(textChavePriv, 2);

        JButton butaumCalcular = new JButton("Calcular");
        butaumCalcular.addActionListener(e -> calcular(primo, inteiro, textChavePriv.getText()));
        addComponent(butaumCalcular, 3);

        frame.pack(); // Ajusta o tamanho da janela com base nos componentes
        frame.setVisible(true);
    }

    private void calcular(BigInteger primo, BigInteger inteiro, String chavePrivadaTexto) {
        try {
            BigInteger segredo = new BigInteger(chavePrivadaTexto);
            BigInteger alfa = inteiro.modPow(segredo, primo);
            labelAviso.setText("Alfa: " + alfa);
            labelAviso.setForeground(Color.GREEN);
        } catch (NumberFormatException err) {
            labelAviso.setText("Apenas números serão aceitos!");
            labelAviso.setForeground(Color.RED);
        }
        labelAviso.setVisible(true);
    }
}
