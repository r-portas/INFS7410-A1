package com.royportas.cranfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;

public class VectorSpaceModelTests {

    private VectorSpaceModel vsModel;

    private CranfieldDocument cran1;

    @BeforeEach
    void setup() {
        vsModel = new VectorSpaceModel(); 
        vsModel.setNumberOfDocuments(2);

        HashMap<String, Integer> dict = new HashMap<String, Integer>();
        dict.put("caesar", 1);
        dict.put("brutus", 1);
        vsModel.setDictionary(dict);
    }

    @Test
    void testTf() {
        String word = "caesar";

        cran1 = mock(CranfieldDocument.class);
        when(cran1.getTermFrequency(word)).thenReturn(2);
        when(cran1.getTotalWords()).thenReturn(6);

        double tf = vsModel.tf(word, cran1);
        assertEquals(tf, 0.3333, 0.001);
    }

    @Test
    void testTf2() {
        String word = "brutus";

        cran1 = mock(CranfieldDocument.class);
        when(cran1.getTermFrequency(word)).thenReturn(2);
        when(cran1.getTotalWords()).thenReturn(4);

        double tf = vsModel.tf(word, cran1);
        assertEquals(tf, 0.5, 0.001);
    }

    @Test
    void testIdf1() {
        String word = "caesar";

        double idf = vsModel.idf(word);
        assertEquals(idf, 0.3, 0.01);
    }

    @Test
    void testIdf2() {
        String word = "brutus";

        double idf = vsModel.idf(word);
        assertEquals(idf, 0.3, 0.01);
    }

    @Test
    void testTfIdf1() {
        String word = "caesar";

        cran1 = mock(CranfieldDocument.class);
        when(cran1.getTermFrequency(word)).thenReturn(2);
        when(cran1.getTotalWords()).thenReturn(6);

        double tfIdf = vsModel.tfIdf(word, cran1);
        assertEquals(tfIdf, 0.099, 0.01);
    }

    @Test
    void testTfIdf2() {
        String word = "brutus";

        cran1 = mock(CranfieldDocument.class);
        when(cran1.getTermFrequency(word)).thenReturn(2);
        when(cran1.getTotalWords()).thenReturn(4);

        double tfIdf = vsModel.tfIdf(word, cran1);
        assertEquals(tfIdf, 0.15, 0.01);
    }

}
