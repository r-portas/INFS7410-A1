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
        if (corpusTermDictionary.containsKey(word)) {
            return Math.log10((double)N / (double)corpusTermDictionary.get(word));
        } else {
            return 0;
        }
    }

    /**
     * Calculate the tf term
     */
    public double tf(String word, CranfieldDocument c) {
        double termFreq = c.getTermFrequency(word);
        double totalWords = c.getTotalWords();
        return termFreq / totalWords;
    }

    public double tfIdf(String word, CranfieldDocument c) {
        System.out.println(c);
        return tf(word, c) * idf(word);
    }

    /**
     * Converts the query into a query vector
     */
    public HashMap<String, Double> queryVector(String[] terms) {
        int termsNum = terms.length;

        HashMap<String, Integer> cosine = new HashMap<String, Integer>();

        for (String term : terms) {
            int count = cosine.containsKey(term) ? cosine.get(term) : 0;
            cosine.put(term, count + 1);
        }

        HashMap<String, Double> normalized = new HashMap<String, Double>();

        for (String key : cosine.keySet()) {
            int count = cosine.get(key);
            normalized.put(key, (double)count / (double)termsNum);
        }

        return normalized;
    }

    public HashMap<String, Double> documentVector(String[] terms, CranfieldDocument d) {
        HashMap<String, Double> vector = new HashMap<String, Double>();
        for (String term : terms) {
            vector.put(term, tfIdf(term, d));
        }
        return vector;
    }

    /**
     * Calculates the cosine simularity between a term and a document
     */
    public double cosineSimularity(String[] terms, CranfieldDocument d) {
        HashMap<String, Double> qv = queryVector(terms);
        HashMap<String, Double> dv = documentVector(terms, d);

        double dot = 0;
        double qv_sum = 0;
        double dv_sum = 0;

        for (String term : terms) {
            dot += qv.getOrDefault(term, 0.0) * dv.getOrDefault(term, 0.0);

            qv_sum += Math.pow(qv.getOrDefault(term, 0.0), 2);
            dv_sum += Math.pow(dv.getOrDefault(term, 0.0), 2);
        }

        double normalized = Math.sqrt(qv_sum) * Math.sqrt(dv_sum);

        return dot / normalized;
    }

}
