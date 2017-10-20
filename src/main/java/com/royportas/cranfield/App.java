package com.royportas.cranfield;

import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.Duration;

/**
 * Entrypoint of application
 *
 */
public class App {

    private static String docsDirectory;
    private static String stopwordsFile;
    private static BufferedReader reader;

    public static void main(String[] args) {

        docsDirectory = "resources/cranfield";
        stopwordsFile = "resources/common_words.txt";

        getDirectories();

        List<String> stopwords = FileLoader.loadStopwordsFile(stopwordsFile);

        List<CranfieldDocument> documents = FileLoader.loadCranfieldDocuments(docsDirectory);

        Engine engine = new Engine(stopwords);


        // engine.addCranfieldDocument(documents.get(0));
        for (CranfieldDocument d : documents) {
            engine.addCranfieldDocument(d);
        }

        // Setup the vector model space once all cranfield documents has been loaded
        engine.setupVectorModelSpace();

        cli(engine);
    }

    private static void getDirectories() {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter Cranfield Document folder (default: '" + docsDirectory + "')");
            System.out.print("> ");
            System.out.flush();

            String docs = reader.readLine().trim();
            if (!docs.equals("")) {
                docsDirectory = docs;
            }


            System.out.println("Enter stopwords file (default: '" + stopwordsFile + "')");
            System.out.print("> ");
            System.out.flush();

            String sw = reader.readLine().trim();

            if (!sw.equals("")) {
                stopwordsFile = sw;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void vsQuery(Engine engine, String input) {
        Instant startTime = Instant.now();
        List<VSRankedDocument> docs = engine.vsModelQuery(input.trim());

        int results = 10;

        if (docs.size() < results) {
            results = docs.size();
        }

        System.out.println("Displaying " + results + " search results");

        Instant endTime = Instant.now();
        Duration dur = Duration.between(startTime, endTime);
        System.out.println("Query took " + dur.toMillis() + " milliseconds");
        

        for (int i = 0; i < results; i++) {
            System.out.println(docs.get(i));
        }

    }

    private static void query(Engine engine, String input) {
        Instant startTime = Instant.now();
        List<RankedDocument> docs = engine.query(input.trim());

        int results = 10;

        if (docs.size() < results) {
            results = docs.size();
        }

        System.out.println("Displaying " + results + " search results");

        Instant endTime = Instant.now();
        Duration dur = Duration.between(startTime, endTime);
        System.out.println("Query took " + dur.toMillis() + " milliseconds");
        

        for (int i = 0; i < results; i++) {
            System.out.println(docs.get(i));
        }

    }

    private static void cli(Engine engine) {
        try {
            // engine.printTermFrequencies();

            System.out.println("Enter query");

            while (true) {
                System.out.print(">> ");
                System.out.flush();
                String input = reader.readLine();

                vsQuery(engine, input);
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
