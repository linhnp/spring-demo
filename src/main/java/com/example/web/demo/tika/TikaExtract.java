package com.example.web.demo.tika;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

public class TikaExtract {
    public static String detectDoctypeFacade(InputStream inputStream) throws IOException {
        Tika tika = new Tika();
        return tika.detect(inputStream);
    }

    public static String extractContentFacade(InputStream inputStream) throws IOException, TikaException {
        Tika tika = new Tika();
        return tika.parseToString(inputStream);
    }

    public static Metadata extractMedataFacade(InputStream inputStream) throws IOException, TikaException {
        Tika tika = new Tika();
        Metadata metadata = new Metadata();

        tika.parse(inputStream, metadata);
        return metadata;
    }

    public static String extractContentParser(InputStream inputStream) throws IOException, TikaException, SAXException {
        Parser parser = new AutoDetectParser();
        ContentHandler contentHandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
//        context.set(PasswordProvider.class, new PasswordProvider() {
//            public String getPassword(Metadata metadata) {
//                return "password";
//            }
//        });

        parser.parse(inputStream, contentHandler, metadata, context);

        return contentHandler.toString();
    }
}
