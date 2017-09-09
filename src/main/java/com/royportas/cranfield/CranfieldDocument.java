package com.royportas.cranfield;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Represents a Cranfield document
 */
public class CranfieldDocument {

    /* The title of the document */
    private String title;

    private HashMap<String, String> section;

    /**
     * Constructs a document
     *
     * @param file The content of a cranfield file
     */
    public CranfieldDocument(String title, File file) {
        this.title = title;

        section = new HashMap<String, String>();

        parseFile(file);
    }

    public String getSectionText(String sectionName) {
        return section.get(sectionName);
    }

    public Set<String> getSectionNames() {
        return section.keySet();
    }

    /**
     * Parses the XML file
     * @param file The file to parse
     */
    private void parseFile(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            // System.out.println("----------------------------");

            Node node = doc.getDocumentElement().getFirstChild();

            while (node != null) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // System.out.println("\nCurrent Element :" + node.getNodeName());
                    // System.out.println(node.getTextContent());

                    section.put(node.getNodeName(), node.getTextContent());
                }

                node = node.getNextSibling();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title;
    }
}
