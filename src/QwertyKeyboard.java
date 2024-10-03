package src;
import javax.swing.*;

public class QwertyKeyboard extends JButton {
	// Todas as letras do jogo, sem caracteres especiais como 'ç' e palavras com acento
    private char[] letters = {
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
            'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
            'z', 'x', 'c', 'v', 'b', 'n', 'm'
    };
    private QwertyButton[] buttons = new QwertyButton[26];

    public QwertyKeyboard() {
    	// Gera um botão para cada letra inserida em 'letters'
        for (int i=0; i<this.letters.length; i++) {
            this.buttons[i] = new QwertyButton(this.letters[i]);
        }
    }

    public void displayButtons(JPanel keyboardPanel) {
    	// Função responsável por exibir as letras em um JPanel
        for (QwertyButton button : this.buttons) {
            keyboardPanel.add(button);
        }
    }

    public void resetKeyboard() {
    	// Função responsável por resetar os botões do jogo
        for (QwertyButton button : this.buttons) {
            button.setEnabled(true);
            button.setBackground(null);
        }
    }

    public void disableKeyboard() {
    	// Função responsável por desabilitar o teclado, para casos endgame etc
        for (int i=0; i<this.buttons.length; i++) {
            this.buttons[i].setEnabled(false);
        }
    }

    public QwertyButton[] getButtons() {
    	// Função auxiliar 
        return this.buttons;
    }
}
