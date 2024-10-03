package src;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Words {
	// Configuração do arquivo com as palavras
    private ArrayList<Word> listOfWords;
    private Random rand;
    private Word emergencyWord;

    public Words() { // Todas as palavras, sorteadas em rand
        this.listOfWords = new ArrayList<>();
        this.rand = new Random();
        this.emergencyWord = new Word("forca");
    }

    public void addWordsToListOfWords(String wordCategory) {

        try { // Tenta abrir o arquivo com as palavras via try, lê as palavras e as armazena com 'scan' -> add
            File words = new File("words\\" + wordCategory + ".txt");
            Scanner reader = new Scanner(words);

            while (reader.hasNextLine()) {
                String data = reader.nextLine().strip();
                Word word = new Word(data.toLowerCase());
                word.splitWordToLetters();
                this.listOfWords.add(word);
            }

            reader.close();
        }
        catch (FileNotFoundException e) { // Caso não encontre o arquivo com as palavras
        }
    }

    public Word selectRandomWord() {
    	// Seleção de uma palavra qualquer em rand
        if (this.listOfWords.size() > 0) {
            int upperbound = this.listOfWords.size();
            return this.listOfWords.get(rand.nextInt(upperbound));
        }
        return this.emergencyWord;
    }

    public void resetListOfWords() { // Reseta as palavras já usadas
        this.listOfWords.clear();
    }
}
