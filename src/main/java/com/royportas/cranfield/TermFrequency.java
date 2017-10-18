package com.royportas.cranfield;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/**
 * Class for sorting the frequency of words in the corpus
 */
class SortedFrequency implements Comparable<SortedFrequency> {
    private String word;
    private int frequency;
    
    public SortedFrequency(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public int compareTo(SortedFrequency other) {
        return other.getFrequency() - getFrequency();
    }

    public boolean equals(SortedFrequency other) {
        return this.word.equals(other.getWord());
    }

    public String toString() {
        return word + ": " + frequency;
    }

    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int freq) {
        frequency = freq;
    }
}

/**
 * Stores the frequency of all terms in the corpus
 */
public class TermFrequency {

    /* Stores a list of sorted frequencies */
    private List<SortedFrequency> wordFreq;

    public TermFrequency() {
        this.wordFreq = new ArrayList<SortedFrequency>();
    }

    /**
     * Processes the corpus of words into a list of term frequencies
     */
    public void processCorpus(HashMap<String, Integer> corpus) {
        for (String word : corpus.keySet()) {
            SortedFrequency freq = new SortedFrequency(word, corpus.get(word));
            wordFreq.add(freq);
        }
    }

    public void printTermFrequencies() {
        Collections.sort(wordFreq);
        int count = 0;
        for (SortedFrequency s : wordFreq) {
            System.out.println(s); 
            count++;

            if (count == 10) {
                break;
            }
        }
    }

    /*
     * Returns the top N terms from the term dictionary
     * @param topTerms The number of terms to return
     * @return The term dictionary containing topTerms terms
     */
    public HashMap<String, Integer> getTermDictionary(int topTerms) {
        HashMap<String, Integer> terms = new HashMap<String, Integer>();

        Collections.sort(wordFreq);
        int count = 0;
        for (SortedFrequency s : wordFreq) {
            terms.put(s.getWord(), s.getFrequency());
            count++;

            if (count == topTerms) {
                break;
            }
        }

        return terms;
    }
}
