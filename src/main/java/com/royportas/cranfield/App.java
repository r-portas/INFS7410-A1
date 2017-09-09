package com.royportas.cranfield;

import java.util.List;
import java.util.Arrays;

/**
 * Entrypoint of application
 *
 */
public class App {


    public static void main( String[] args ) {
        if (args.length < 2) {
            System.out.println("Expected args: path/to/cranfield/docs path/to/stopwords.txt"); 
            System.exit(0);
        }

        List<String> stopwords = FileLoader.loadStopwordsFile(args[1]);

        List<CranfieldDocument> documents = FileLoader.loadCranfieldDocuments(args[0]);

        Engine engine = new Engine(stopwords);

        // engine.addCranfieldDocument(documents.get(0));
        for (CranfieldDocument d : documents) {
            engine.addCranfieldDocument(d);
        }

        engine.printIndex();
    }
}
