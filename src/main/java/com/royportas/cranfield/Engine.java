package com.royportas.cranfield;

import java.lang.Character;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class WordFreqPair implements Comparable<WordFreqPair> {
    String word;
    int freq;

    public WordFreqPair(String word, int freq) {
        this.word = word;
        this.freq = freq;
    }

    public int compareTo(WordFreqPair other) {
        return other.freq - this.freq;
    }
}

/**
 * The engine of the search engine
 */
public class Engine {

    private HashMap<String, Integer> corpus;

    public Engine() {
        corpus = new HashMap<String, Integer>();
    }

    public void addCranfieldDocument(CranfieldDocument document) {
        String documentCorpus = new String();

        for (String sectionName : document.getSectionNames()) {
            String words = document.getSectionText(sectionName);
            documentCorpus += words;
        }

        tokenize(documentCorpus);
    }

    /**
     * Tokenizes a document
     */
    public void tokenize(String documentCorpus) {
        String temp = "";

        for (int i = 0; i < documentCorpus.length(); i++) {
            char c = documentCorpus.charAt(i);
            c = Character.toLowerCase(c);

            if (Character.isLetterOrDigit(c)) {
                // Alphanumeric
                temp += c; 
            } else {
                // Non-alphanumeric
                if (!temp.equals("")) {
                    addWord(temp);
                }
                temp = "";
            }
            
        }
        if (!temp.equals("")) {
            addWord(temp);
        }
    }

    /**
     * Adds a word to the corpus
     */
    private void addWord(String word) {
        if (corpus.containsKey(word)) {
            int current = corpus.get(word);
            corpus.put(word, current+1);
        } else {
            corpus.put(word, 1);
        }
    }

    /**
     * Removes stop words from the corpus
     * @param stopwords The stopwords to remove
     */
    public void removeStopwords(List<String> stopwords) {
        for (String word : stopwords) {
            corpus.remove(word);
        }
    }

    /**
     * Print the frequency of words
     * @param number The top number of words to print
     */
    public void printFrequency(int number) {
        List<WordFreqPair> words = new ArrayList<WordFreqPair>();

        Set<String> tokens = corpus.keySet();

        for (String token : tokens) {
            WordFreqPair pair = new WordFreqPair(token, corpus.get(token));
            words.add(pair);
        }

        Collections.sort(words);

        for (int i = 0; i < number; i++) {
            WordFreqPair pair = words.get(i);
            System.out.println(pair.word + ": " + pair.freq);
        }
    }

    /**
     * Prints a summary of the corpus
     */
    public void printCorpus() {
        Set<String> tokens = corpus.keySet();

        int total = 0;

        for (String token : tokens) {
            total += corpus.get(token);
        }

        System.out.println("Total number of words: " + total);
        System.out.println("Total number of unique words: " + tokens.size());

        printFrequency(50);
    }

}
