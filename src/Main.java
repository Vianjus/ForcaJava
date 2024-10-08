package src;
import javax.swing.*;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        final int WIDTH = 800; // LARGURA DA TELA
        final int HEIGHT = 600; // ALTURA DA TELA
        final String TITLE = "Forca (Java Edition)";
        final ImageIcon backgroundImg = new ImageIcon("img\\start_screen.png");

        // Inicialização da tela e configurações
        JFrame screen = new JFrame();
        CardLayout cardLayout = new CardLayout();
        screen.setSize(new Dimension(WIDTH, HEIGHT));
        screen.setTitle(TITLE);
        screen.setLocationRelativeTo(null);
        screen.setResizable(false); // Não será possível alterar o tamanho da tela
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel container = new JPanel();
        container.setLayout(cardLayout);
        screen.add(container); // Add de elementos à tela principal

        Words words = new Words();
        // Init. final da tela com as configurações acima
        StartScreenPanel startScreenPanel = new StartScreenPanel(WIDTH, HEIGHT, backgroundImg, container, cardLayout);
        MainGamePanel mainGamePanel = new MainGamePanel(WIDTH, HEIGHT, container, cardLayout, words);
        CategoryScreenPanel categoryScreenPanel = new CategoryScreenPanel(WIDTH, HEIGHT, backgroundImg, container, cardLayout, mainGamePanel, words);

        container.add(startScreenPanel, "1");
        container.add(categoryScreenPanel, "2");
        container.add(mainGamePanel, "3");

        screen.setVisible(true);
    }
}
