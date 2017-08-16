package com.royportas.cranfield;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.lang.Exception;

/**
 * Contains functions for loading all files
 *
 * @author Roy Portas
 */
public class FileLoader {

    /**
     * Loads the stopwords file
     *
     * @param filename  The filename and path of the stopwords file
     * @retunr List<String> A list of stop words
     */
    public static List<String> loadStopwordsFile(String filename) {

        List<String> words = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return words;
    }

    /**
     * Loads all the cranfield documents
     *
     * @param path The path to the cranfield files
     * @return List<CranfieldDocument> A list of documents
     */
    public static List<CranfieldDocument> loadCranfieldDocuments(String path) {
        List<CranfieldDocument> documents = new ArrayList<CranfieldDocument>();
        File folder = new File(path);
        File[] files = folder.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                documents.add(new CranfieldDocument(files[i].getName(), files[i]));
            }
        }

        return documents;
    }

}
