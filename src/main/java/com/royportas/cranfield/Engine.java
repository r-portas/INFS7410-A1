package com.royportas.cranfield;

import java.lang.Character;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class RankedDocument implements Comparable<RankedDocument> {
    private CranfieldDocument doc;
    private int freq;

    public RankedDocument(CranfieldDocument doc, int freq) {
        this.doc = doc;
        this.freq = freq;
    }

    public int compareTo(RankedDocument other) {
        return other.freq - this.freq;
    }

    public int getFrequency() {
        return freq;
    }

    public CranfieldDocument getDocument() {
        return doc;
    }

    public String toString() {
        return "[" + freq + "] - " + doc;
    }
}

class IndexItem {
    private int frequency;
    private CranfieldDocument document;

    public IndexItem(int frequency, CranfieldDocument document) {
        this.frequency = frequency;
        this.document = document;
    }

    public String toString() {
        return "(" + document + ", " + frequency + ")";
    }

    public int getFrequency() {
        return frequency;
    }

    public CranfieldDocument getDocument() {
        return document;
    }
}

/**
 * The engine of the search engine
 */
public class Engine {

    private HashMap<String, List<IndexItem>> invertedIndex;
    private List<String> stopwords;

    public Engine(List<String> stopwords) {
        invertedIndex = new HashMap<String, List<IndexItem>>();
        this.stopwords = stopwords;
    }

    public void addCranfieldDocument(CranfieldDocument document) {
        String documentCorpus = new String();

        for (String sectionName : document.getSectionNames()) {
            String words = document.getSectionText(sectionName);
            documentCorpus += words;
        }

        HashMap<String, Integer> corpus = tokenize(documentCorpus);

        // Add to the inverted index
        for (String word : corpus.keySet()) {
            if (invertedIndex.containsKey(word)) {
                List<IndexItem> indexes = invertedIndex.get(word);
                indexes.add(new IndexItem(corpus.get(word), document));
            } else {
                List<IndexItem> indexes = new ArrayList<IndexItem>();
                indexes.add(new IndexItem(corpus.get(word), document));
                invertedIndex.put(word, indexes);
            }
        }
    }

    /**
     * Tokenizes a document
     */
    private HashMap<String, Integer> tokenize(String documentCorpus) {
        String temp = "";
        HashMap<String, Integer> corpus = new HashMap<String, Integer>();

        for (int i = 0; i < documentCorpus.length(); i++) {
            char c = documentCorpus.charAt(i);
            c = Character.toLowerCase(c);

            if (Character.isLetterOrDigit(c)) {
                // Alphanumeric
                temp += c; 
            } else {
                // Non-alphanumeric
                if (!temp.equals("")) {
                    addWord(corpus, temp);
                }
                temp = "";
            }
            
        }
        if (!temp.equals("")) {
            addWord(corpus, temp);
        }

        removeStopwords(corpus);

        return corpus;

    }

    public List<RankedDocument> query(String query) {
        String[] terms = query.split(" ");

        // Combine results
        // Sum of all frequencies in each document
        HashMap<CranfieldDocument, Integer> results = new HashMap<CranfieldDocument, Integer>();

        for (String term : terms) {
            List<IndexItem> items = invertedIndex.get(term);

            if (items != null) {
                for (IndexItem item : items) {
                    CranfieldDocument doc = item.getDocument();
                    if (results.containsKey(doc)) {
                        int newFreq = item.getFrequency() + results.get(doc);
                        results.put(doc, newFreq);
                    } else {
                        results.put(doc, item.getFrequency());
                    }
                }
            }
        }

        List<RankedDocument> ranked = new ArrayList<RankedDocument>();
        for (CranfieldDocument doc : results.keySet()) {
            RankedDocument d = new RankedDocument(doc, results.get(doc));
            ranked.add(d);
        }

        Collections.sort(ranked);

        return ranked;
    }

    /**
     * Adds a word to the corpus
     */
    private void addWord(HashMap<String, Integer> corpus, String word) {
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
    public void removeStopwords(HashMap<String, Integer> corpus) {
        for (String word : stopwords) {
            corpus.remove(word);
        }
    }

    public void printIndex() {
        for (String word : invertedIndex.keySet()) {
            System.out.println(word + ": " + invertedIndex.get(word));
        }
    }
}
