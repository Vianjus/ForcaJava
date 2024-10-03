package src;
import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
	// Configurações das dimensões, funções e conteúdo de cada botão
    private String text;
    private int x;
    private int y;
    private int width;
    private int height;

    public Button(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.setBounds(this.x, this.y, this.width, this.height);
        this.setText(this.text);
        this.setFocusable(false);
    }
    
    public Button(String text) {
        this.text = text;

        this.setText(this.text);
        this.setFocusable(false);
    }
}

class PlayButton extends Button {
	// Botão de 'jogar' (propriedades diferentes de um botão letra - design x texto)
    private JPanel container;
    private CardLayout cardLayout;

    public PlayButton(String text, int x, int y, int width, int height, JPanel container, CardLayout cardLayout) {
        super(text, x, y, width, height);
        this.container = container;
        this.cardLayout = cardLayout;
    }

    public void swapCard(String cardNum) {
        this.cardLayout.show(this.container, cardNum);
    }
}

class ExitButton extends Button {
	// Botão de 'sair' (propriedades diferentes de um botão letra - design x texto)
    public ExitButton(String text, int x, int y, int width, int height) {
        super(text, x, y, width, height);
    }

}

class BackButton extends Button {
	// Botão de 'voltar' (propriedades diferentes de um botão letra - design x texto)
    private JPanel container;
    private CardLayout cardLayout;

    public BackButton(String text, int x, int y, int width, int height, JPanel container, CardLayout cardLayout) {
        super(text, x, y, width, height);
        this.container = container;
        this.cardLayout = cardLayout;
    }

    public BackButton(String text, JPanel container, CardLayout cardLayout) {
        super(text);
        this.container = container;
        this.cardLayout = cardLayout;
    }

    public void swapCard(String cardNum) {
        this.cardLayout.show(this.container, cardNum);
    }
}

class CategoryButton extends Button {
	// Botão de 'selecionar categoria' (propriedades diferentes de um botão letra - design e texto)
    private String category;
    private JPanel container;
    private CardLayout cardLayout;

    public CategoryButton(String text, JPanel container, CardLayout cardLayout) {
        super(text);
        this.container = container;
        this.cardLayout = cardLayout;
    }

    private void createListOfWords(Words words) {
        words.addWordsToListOfWords(this.getCategory());
    }

    public void swapCard(Words words, MainGamePanel mainGamePanel) { // define a palavra, lança na tela
        this.createListOfWords(words);
        Word word = words.selectRandomWord();
        word.splitWordToLetters();
        mainGamePanel.setRandomWord(word);
        mainGamePanel.setGuessedLetters(word);
        this.cardLayout.show(this.container, "3");
    }

    public String getCategory() { // retorna a categoria atual
        return this.category;
    }

    public void setCategory(String category) { // define a categoria atual
        this.category = category;
    }
}

class NewButton extends Button {
	// Criação de um novo botão específico, caso necessário
    public NewButton(String text, int x, int y, int width, int height) {
        super(text, x, y, width, height);
    }
}
