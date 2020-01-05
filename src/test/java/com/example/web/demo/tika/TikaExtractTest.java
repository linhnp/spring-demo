package com.example.web.demo.tika;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.*;

class TikaExtractTest {
    InputStream stream;

    @Test
    void detectDoctypeFacade() throws IOException {
        String mediaType = TikaExtract.detectDoctypeFacade(stream);
        assertEquals("text/plain", mediaType);

    }

    @Test
    void extractMedataFacade() throws IOException, TikaException, SAXException {
        Metadata metadata = TikaExtract.extractMedataFacade(stream);
        assertEquals("org.apache.tika.parser.DefaultParser", metadata.get("X-Parsed-By"));
    }

    @Test
    void extractContentParser() throws IOException, TikaException, SAXException {
        String content = TikaExtract.extractContentParser(stream);
        assertThat(content, containsString("ありのままの姿見せるのよ"));
    }

    //TODO: open stream only once
    @BeforeEach
    void setUp() {
        stream = this.getClass().getClassLoader()
                .getResourceAsStream("demo.txt");
//        InputStream stream = new FileInputStream("/path/to/demo.txt");
    }

    @AfterEach
    void tearDown() throws IOException{
        stream.close();
    }
}