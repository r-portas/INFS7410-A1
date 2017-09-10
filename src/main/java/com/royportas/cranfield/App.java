package com.royportas.cranfield;

import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        cli(engine);
    }

    private static void cli(Engine engine) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("Enter query");

            while (true) {
                System.out.print(">> ");
                System.out.flush();
                String input = reader.readLine();

                List<RankedDocument> docs = engine.query(input.trim());

                int results = 10;

                if (docs.size() < results) {
                    results = docs.size();
                }

                System.out.println("Displaying " + results + " search results");

                for (int i = 0; i < results; i++) {
                    System.out.println(docs.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (reader != null) {
                reader.close(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
