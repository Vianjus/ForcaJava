package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.Arrays;

public class MainGamePanel extends JPanel implements ActionListener {
	// Inicialização dos atributos e elementos decorativos que compõem a tela:
    private int WIDTH; 
    private int HEIGHT; 
    private Image backgroundImg = new ImageIcon("img\\chalkboard.gif").getImage();
    private Image[] gallows = new Image[7]; // Uma foto para cada parte do corpo do boneco na forca (7)
    private NewButton newButton;
    private BackButton backButton;
    private QwertyKeyboard qwertyKeyboard; // QWERTY é um padrão de teclado -> simula os teclados físicos atuais
    private Words words;
    private Word randomWord;
    private char[] guessedLetters;
    private int numOfGuesses;
    private boolean gameOver;

    public MainGamePanel(int WIDTH, int HEIGHT, JPanel container, CardLayout cardLayout, Words words) {
    	// Atributos e elementos da tela principal
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.words = words;
        this.setLayout(null);

        // Inicialização do teclado para adivinhação
        this.qwertyKeyboard = new QwertyKeyboard();
        // Número de chances máxima
        this.numOfGuesses = 6;
        
        for (int i=0; i<numOfGuesses + 1; i++) {
            Image img = new ImageIcon("img\\Gallows" + i + ".gif").getImage();
            this.gallows[i] = img;
        }
        // Configurações do jogo e botões do teclado virtual:
        this.gameOver = false;

        int buttonWidth = 90;
        int buttonHeight = 30;
        int buttonX = 10;
        
        // Botão "novo" -> definição, conteúdo, função e escuta;
        String newButtonText = "NOVO";
        int newButtonY = 10;
        this.newButton = new NewButton(newButtonText, buttonX, newButtonY, buttonWidth, buttonHeight);
        this.newButton.addActionListener(this);

        // Botão "voltar" -> definição, conteúdo, função e escuta;
        String backButtonText = "VOLTAR";
        int backButtonY = newButtonY + buttonHeight + 10;
        this.backButton = new BackButton(backButtonText, buttonX, backButtonY, buttonWidth, buttonHeight, container, cardLayout);
        this.backButton.addActionListener(this);

        // JPanel próprio para a área do teclado
        JPanel keyboard = new JPanel();
        int keyboardWidth = this.WIDTH - 20; 
        int keyboardHeight = this.HEIGHT / 2 - 150;
        int keyboardX = 2;
        int keyboardY = this.HEIGHT / 2 + 108;
        keyboard.setBounds(keyboardX, keyboardY, keyboardWidth, keyboardHeight); // define as dimensões do teclado calculadas
        keyboard.setLayout(new GridLayout(3, 9)); // 3 linhas e 9 colunas para as letras, simulando o padrão QWERTY

        // Mostrar e ouvir botões - teclado
        this.qwertyKeyboard.displayButtons(keyboard);
        this.addActionListenerToQwertyKeyboard();

        this.add(this.newButton);
        this.add(this.backButton);
        this.add(keyboard);
    }

    @Override
    public void paintComponent(Graphics g) { 
    	// *Contém elementos gerados via netbeans* - design

        Graphics2D g2D = (Graphics2D) g.create();
        super.paintComponent(g2D);

        char[] wordToChar = this.randomWord.getLettersInWord();
        int space = 10;
        
        // Cálculo das dimensões dos eixos e espaços
        int placeholderLength = this.calculatePlaceholderLength(this.randomWord.getWord(), space);
        int x1 = this.calculateX1(placeholderLength, this.randomWord.getWord(), space);
        int y1 = this.HEIGHT / 2 + 90;
        int x2 = x1 + placeholderLength;
        int y2 = y1;
        int fontSize = this.calculatePlaceholderLength(this.randomWord.getWord(), space);
        String fontName = g2D.getFont().getFontName();
        Font font = new Font(fontName, Font.BOLD, fontSize);

        // Elementos gráficos -- netbeans
        g2D.drawImage(this.backgroundImg, 0, 0, null);
        int gallowsX = (this.WIDTH / 2) - this.gallows[0].getWidth(null) / 2;
        int gallowsY = 20;
        g2D.drawImage(this.gallows[(this.numOfGuesses - 6) * -1], gallowsX, gallowsY, null);
        g2D.setPaint(Color.white);
        g2D.setStroke(new BasicStroke(5));
        g2D.setFont(font);
        this.drawLetterPlaceholders(g2D, wordToChar, x1, y1, x2, y2, space, placeholderLength);
        this.drawGuessedLetters(g2D, x1, y1, x2, space, placeholderLength);

        // Função que revela a palavra, caso o jogo seja encerrado
        if (this.gameOver && !this.guessedWordEqualsWord()) {
            this.drawMissingLetters(g2D, x1, y1, x2, space, placeholderLength);
        }
        // Função que ilustra o fim de jogo na tela;
        if (this.gameOver) {
            this.drawGameOverText(g2D);
        }
    }

    private void drawLetterPlaceholders(Graphics2D g2D, char[] wordToChar, int x1, int y1, int x2, int y2, int space, int placeholderLength) {

        int placeholderX1 = x1;
        int placeholderX2 = x2;
        int placeholderY1 = y1;
        int placeholderY2 = y2;

        for (int i=0; i<wordToChar.length; i++) {
            char ch = wordToChar[i];
            int charX = placeholderX1 + (placeholderLength / 2) - (g2D.getFontMetrics().stringWidth(String.valueOf(ch).toUpperCase()) / 2);
            int charY = placeholderY1 - 10;

            if (String.valueOf(ch).matches("[a-zA-Z0-9]")) {
                g2D.drawLine(placeholderX1, placeholderY1, placeholderX2, placeholderY2);
            }
            else {
                g2D.drawString(String.valueOf(ch).toUpperCase(), charX, charY);
            }

            placeholderX1 = placeholderX2 + space;
            placeholderX2 = placeholderX1 + placeholderLength;
        }
    }

    private void drawGuessedLetters(Graphics2D g2D, int x1, int y1, int x2, int space, int placeholderLength) {
    	// Função para exibir na tela quais as letras já selecionadas pelo jogador
        int charX1 = x1;
        int charX2 = x2;

        for (int i=0; i<this.guessedLetters.length; i++) {
            char ch = this.guessedLetters[i];
            int x = charX1 + (placeholderLength / 2) - (g2D.getFontMetrics().stringWidth(String.valueOf(ch).toUpperCase()) / 2);
            int y = y1 - 10;

            if (String.valueOf(ch).matches("[a-zA-Z0-9]")) {
                g2D.drawString(String.valueOf(ch).toUpperCase(), x, y);
            }
            charX1 = charX2 + space;
            charX2 = charX1 + placeholderLength;
        }
    }

    private void drawMissingLetters(Graphics2D g2D, int x1, int y1, int x2, int space, int placeholderLength) {
    	// Função que mostra quais letras ainda faltam 
        g2D.setPaint(Color.RED);

        int charX1 = x1;
        int charX2 = x2;

        for (int i=0; i<this.randomWord.getLettersInWord().length; i++) {
            char ch = this.randomWord.getLettersInWord()[i];
            int x = charX1 + (placeholderLength / 2) - (g2D.getFontMetrics().stringWidth(String.valueOf(ch).toUpperCase()) / 2);
            int y = y1 - 10;

            if (String.valueOf(ch).matches("[a-zA-Z0-9]") && this.guessedLetters[i] != this.randomWord.getLettersInWord()[i]) {
                g2D.drawString(String.valueOf(ch).toUpperCase(), x, y);
            }
            charX1 = charX2 + space;
            charX2 = charX1 + placeholderLength;
        }
    }

    private String gameOverText() {
    	// função boolean que mostra o resultado do jogo
        return this.gameOver && this.guessedWordEqualsWord() ? "VOCÊ VENCEU!" : "VOCÊ PERDEU!";
    }

    private void drawGameOverText(Graphics2D g2D) { // gerado via netbeans - elementos gráficos
    	
    	// Função que decora a tela de acordo com o resultado do jogo -> verde para vitória, vermelho para derrota;	
        String text = this.gameOverText();
        String fontName = g2D.getFont().getFontName();
        int fontSize = 100;
        Font font = new Font(fontName, Font.BOLD, fontSize);
        g2D.setFont(font);

        int textX = (this.WIDTH / 2) - (g2D.getFontMetrics().stringWidth(text) / 2);
        int textY = (this.HEIGHT / 2) - (g2D.getFontMetrics().getHeight() / 2);

        if (text.equals("VOCÊ VENCEU!")) {
            g2D.setPaint(Color.GREEN);
        }
        else {
            g2D.setPaint(Color.RED);
        }
        g2D.drawString(text, textX, textY);
    }

    private int calculatePlaceholderLength(String word, int space) {
    	// Função suporte -> garante que as letras não extrapolem os limites de largura e altrua, distorçam a tela

        int placeholderLength;
        if (word.length() <= 10) {
            placeholderLength = ((this.WIDTH - 40) / 10) - space;
        }
        else {
            placeholderLength = ((this.WIDTH - 20) / word.length()) - space;
        }
        return placeholderLength;
    }

    private int calculateX1(int placeholderLength, String word, int space) {
    	// Cálculo do elemento x - largura
        int x = (this.WIDTH / 2) - ((placeholderLength * word.length() + space * (word.length() + 1)) / 2);
        return x;
    }

    private boolean checkLetter(char buttonValue) {
    	// Função que checa se a letra selecionada faz parte da palavra ou não
        for (int i=0; i<this.guessedLetters.length; i++) {
            if (buttonValue == this.randomWord.getLettersInWord()[i]) {
                return true;
            }
        }
        return false;
    }

    private void updateLetters(char buttonValue) { // Lembrar de trocar - função mal otimizada
        for (int i=0; i<this.guessedLetters.length; i++) {
            if (buttonValue == this.randomWord.getLettersInWord()[i]) {
                this.guessedLetters[i] = this.randomWord.getLettersInWord()[i];
            }
        }
    }

    private void updateScreen(QwertyButton qwertyButton) {
    	// Função que atualiza a tela: as chances do jogador e a cor-tema de acordo com o palpite
        boolean letterInWord = this.checkLetter(qwertyButton.getValue());

        if (letterInWord) {
            this.updateLetters(qwertyButton.getValue());
            qwertyButton.setBackground(Color.green);
        }
        else {
            this.numOfGuesses--;
            qwertyButton.setBackground(Color.red);
        }
        qwertyButton.setEnabled(false);
        repaint();
    }

    private void checkForWin() {

        if (this.numOfGuesses == 0) {
            this.qwertyKeyboard.disableKeyboard();
            this.gameOver = true;
        }

        if (this.guessedWordEqualsWord()) {
            this.qwertyKeyboard.disableKeyboard();
            this.gameOver = true;
        }
    }

    private boolean guessedWordEqualsWord() {
    	// Função que checa se a palavra palpitada pelo jogador é a correta
        String word = this.randomWord.getWord();
        String guessedWord = String.valueOf(this.guessedLetters);

        return guessedWord.equals(word);
    }

    private void resetGame(ActionEvent e) {
    	// FUnção que reseta o jogo: palavras, temas, letras e elementos background
        Object[] options = {"Sim", "Não"};

        if (e.getSource() == this.backButton) {
            int result = JOptionPane.showOptionDialog(
                null, 
                "Deseja voltar?",      // Mensagem
                "Confirmação",           // Título
                JOptionPane.YES_NO_OPTION,     // Tipo de opções
                JOptionPane.QUESTION_MESSAGE,  // Tipo de mensagem
                null,                     // Ícone (null para ícone padrão)
                options,                       // Opções personalizadas
                options[0]                     // Padrão (primeira opção selecionada)
            );

            if (result == 0) {
                this.words.resetListOfWords();
                this.qwertyKeyboard.resetKeyboard();
                this.backButton.swapCard("2");
            }
        }

        else {
            int result = JOptionPane.showOptionDialog(
                null, 
                "Deseja iniciar um novo jogo?", // Mensagem
                "Confirmação",                    // Título
                JOptionPane.YES_NO_OPTION,              // Tipo de opções
                JOptionPane.QUESTION_MESSAGE,           // Tipo de mensagem
                null,                              // Ícone (null para ícone padrão)
                options,                                // Opções personalizadas
                options[0]                              // Padrão (primeira opção selecionada)
        );
            if (result == 0) {
                Word word = words.selectRandomWord();
                word.splitWordToLetters();
                this.setRandomWord(word);
                this.setGuessedLetters(word);
                this.qwertyKeyboard.resetKeyboard();
                repaint();
            }
        }
        this.numOfGuesses = 6;
        this.gameOver = false;
    }

    public void setRandomWord(Word word) {
        this.randomWord = word;
    }

    // Função de set para as letras já palpitadas - suporte
    public void setGuessedLetters(Word word) {
        char[] wordToChar = word.getLettersInWord();
        this.guessedLetters = new char[wordToChar.length];

        for (int i=0; i<wordToChar.length; i++) {
            if (!String.valueOf(wordToChar[i]).matches("[a-zA-Z0-9]")) {
                this.guessedLetters[i] = wordToChar[i];
            }
        }
    }
    // Função que define as funções de escuta para cada tecla
    private void addActionListenerToQwertyKeyboard() {
        for (QwertyButton qwertyButton : this.qwertyKeyboard.getButtons()) {
            qwertyButton.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
    	// Outra função de escuta, 
    	//para os elementos da tela inicial do jogo
        
    	if (e.getSource() == this.newButton) {
            this.resetGame(e); 
        }
        if (e.getSource() == this.backButton) {
            this.resetGame(e);
        }
        
        // loop que procura por atualizações na tela, e confere se o jogo acabou
        for (QwertyButton qwertyButton : this.qwertyKeyboard.getButtons()) {
            if (e.getSource() == qwertyButton) {
                this.updateScreen(qwertyButton);
                this.checkForWin();
            }
        }
    }
}
