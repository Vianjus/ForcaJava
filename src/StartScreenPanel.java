package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreenPanel extends JPanel implements ActionListener { 
	// TELA INICIAL DO JOGO
	
    private PlayButton playButton;
    private ExitButton exitButton;

    public StartScreenPanel(int WIDTH, int HEIGHT, ImageIcon backgroundImg, JPanel container, CardLayout cardLayout) {
    	// Imagem de fundo personalizada para a tela inicial
        JLabel startScreenBg = new JLabel(backgroundImg);

        // Botões com maior destaque
        int buttonWidth = 100;
        int buttonHeight = 50;
        int buttonY = HEIGHT - (buttonHeight * 3);

        // Configurações de dimensão e função do botão que inicia o jogo
        String playButtonText = "JOGAR";
        int playButtonX = (WIDTH / 2) - (buttonWidth + 20);
        this.playButton = new PlayButton(playButtonText, playButtonX, buttonY, buttonWidth, buttonHeight, container, cardLayout);
        this.playButton.addActionListener(this);
        // Configurações de dimensão e função do botão que sai do jogo
        String exitButtonText = "SAIR";
        int exitButtonX = (WIDTH / 2) + 20;
        this.exitButton = new ExitButton(exitButtonText, exitButtonX, buttonY, buttonWidth, buttonHeight);
        this.exitButton.addActionListener(this);

        // Implementação dos botões na tela
        this.setLayout(new BorderLayout());
        this.add(startScreenBg);
        startScreenBg.setLayout(null);
        startScreenBg.add(this.playButton);
        startScreenBg.add(this.exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	// Execução das funções dos botões descritos
        if (e.getSource() == this.playButton) {
            this.playButton.swapCard("2");
        }

        if (e.getSource() == this.exitButton) {
            System.exit(0);
        }
    }
}
