package com.royportas.cranfield;

import java.util.HashMap;
import java.lang.Math;

/**
 * Represents the vector space model
 */
public class VectorSpaceModel {

    /* The number of corpus terms within the corpus term dictionary */
    private final int CORPUS_TERMS = 1000;

    /* The term dictionary of the corpus */
    private HashMap<String, Integer> corpusTermDictionary;

    /* The total number of documents */
    private int N;

    public VectorSpaceModel() {

    }

    /**
     * Sets the corpus dictionary
     * @param corpusTerms The corpus dictionary of terms
     */
    public void setCorpusTermDictionary(TermFrequency corpusTerms) {
        corpusTermDictionary = corpusTerms.getTermDictionary(CORPUS_TERMS);
    }

    /**
     * Sets the dictionary, used for testing
     */
    public void setDictionary(HashMap<String, Integer> d) {
        corpusTermDictionary = d;
    }

    /**
     * Set the total number of documents in the engine
     * @param documents The number of documents
     */
    public void setNumberOfDocuments(int documents) {
        N = documents;
    }

    /**
     * Calculate the idf term
     */
    public double idf(String word) {
        return Math.log10((double)N / (double)corpusTermDictionary.get(word));
    }

    /**
     * Calculate the tf term
     */
    public double tf(String word, CranfieldDocument c) {
        double termFreq = c.getTermFrequency(word);
        double totalWords = c.getTotalWords();
        return termFreq / totalWords;
    }

    /**
     * Calculates the tfIdf term
     */
    public double tfIdf(String word, CranfieldDocument c) {
        return tf(word, c) * idf(word);
    }

}
