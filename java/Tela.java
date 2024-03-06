import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class Tela {
    private JFrame frame;
    private JLabel labelAviso; // Label para aviso ou valor de Alfa
    private GridBagConstraints painel;

    public Tela() {
        frame = new JFrame("CriptoElmo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());

        painel = new GridBagConstraints();
        painel.gridwidth = GridBagConstraints.REMAINDER;
        painel.anchor = GridBagConstraints.NORTH;
        painel.fill = GridBagConstraints.HORIZONTAL;
        painel.insets = new Insets(5, 0, 5, 0);

        labelAviso = new JLabel();
        labelAviso.setForeground(Color.red);
        labelAviso.setVisible(false); // Inicialmente invisível, será usado para mensagens de erro ou valor de Alfa
    }

    public void exibir(BigInteger primo, BigInteger inteiro) {
        JFrame frame = new JFrame("CriptoElmo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Ajuste no tamanho para melhor visualização

        frame.setLayout(new GridBagLayout());

        GridBagConstraints painel = new GridBagConstraints();
        painel.gridwidth = GridBagConstraints.REMAINDER; // Faz o componente ocupar toda a largura
        painel.anchor = GridBagConstraints.NORTH; // Ancora no topo
        painel.fill = GridBagConstraints.HORIZONTAL; // Estica horizontalmente 
        painel.insets = new Insets(5, 0, 5, 0); // Espaçamento para estética

        // Inicializa o JLabel de aviso aqui para que possa ser reutilizado
        labelAviso = new JLabel();
        labelAviso.setForeground(Color.red);
        labelAviso.setVisible(false);

        // Primeiro JLabel com os valores de primo e inteiro
        JLabel labelValores = new JLabel("Primo: " + primo + "       Inteiro: " + inteiro);
        painel.gridy = 0; // Primeira linha
        frame.add(labelValores, painel);

        // JLabel para "Digite sua chave privada"
        JLabel labelChavePrivada = new JLabel("Digite sua chave privada:");
        painel.gridy = 1; // Segunda linha
        frame.add(labelChavePrivada, painel);

        // JTextField para entrada do usuário
        JTextField textChavePriv = new JTextField();
        textChavePriv.setPreferredSize(new Dimension(200, 30)); // Define um tamanho preferencial
        painel.gridy = 2; // Terceira linha
        frame.add(textChavePriv, painel);


        // Adiciona labelAviso ao frame
        painel.gridy = 4; // Quarta linha
        frame.add(labelAviso, painel);

        JButton butaumCalcular = new JButton("Calcular");
        butaumCalcular.setPreferredSize(new Dimension(100, 30));
        painel.gridy = 3; 
        frame.add(butaumCalcular, painel);

        textChavePriv.setPreferredSize(new Dimension(200, 30));
        painel.gridy = 2; 
        frame.add(textChavePriv, painel);

        butaumCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BigInteger segredo = new BigInteger(textChavePriv.getText());
                    BigInteger alfa = inteiro.modPow(segredo, primo);
                    labelAviso.setText("Alfa: " + alfa);
                    
                    labelAviso.setForeground(Color.green); 
                } catch (NumberFormatException err) {
                    labelAviso.setText("Apenas números serão aceitos!");
                    labelAviso.setForeground(Color.red); 
                }
                labelAviso.setVisible(true); 
                frame.revalidate();
                frame.repaint();
            }
        });

        frame.setVisible(true);
    }
}

