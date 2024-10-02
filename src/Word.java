package src;
public class Word {

    private String word;
    private char[] lettersInWord;

    public Word(String word) {
        this.word = word;
    }

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
