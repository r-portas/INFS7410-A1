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

    /* The number of documents */
    private int N;

    public VectorSpaceModel() {

    }

    public void setCorpusTermDictionary(TermFrequency corpusTerms) {
        corpusTermDictionary = corpusTerms.getTermDictionary(CORPUS_TERMS);
    }

    public void setNumberOfDocuments(int documents) {
        N = documents;
    }

    public double idf(String word) {
        return Math.log(N);
    }

}
