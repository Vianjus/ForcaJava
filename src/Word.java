package src;
public class Word {

    private String word;
    private char[] lettersInWord;
    
    // Palavra do jogo
    public Word(String word) {
        this.word = word;
    }
    // Separação da palavra em char, para adivinhação do jogador
    public void splitWordToLetters() {

        this.lettersInWord = this.word.toCharArray();
    }

    public char[] getLettersInWord() {
        return this.lettersInWord;
    }

    public String getWord() {
        return this.word;
    }
}
