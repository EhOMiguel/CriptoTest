import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigInteger;

public class Tela {
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

        JButton butaumCalcular = new JButton("Calcular");
        butaumCalcular.setPreferredSize(new Dimension(100, 30)); // Define um tamanho preferencial
        painel.fill = GridBagConstraints.NONE; 
        painel.gridy = 3; // Terceira linha
        frame.add(butaumCalcular, painel);



        butaumCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigInteger segredo;
                try {
                    segredo = new BigInteger(textChavePriv.getText());
                }catch(Exception err){

                    JLabel labelAviso = new JLabel("Apenas números serão aceitos!");
                    labelAviso.setForeground (Color.red);
                    painel.gridy = 4; // Primeira linha
                    painel.fill = GridBagConstraints.HORIZONTAL;
                    frame.add(labelAviso, painel);

                    return;
                }
        
            
                BigInteger alfa = inteiro.modPow(segredo, primo);
                System.out.println("Alfa: " + alfa);
            };
        });

        frame.setVisible(true);
    }
}
