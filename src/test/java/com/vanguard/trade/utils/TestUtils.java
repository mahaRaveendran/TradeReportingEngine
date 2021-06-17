package com.vanguard.trade.utils;

import com.vanguard.trade.reporting.engine.filter.impl.TradeEventFileParserImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestUtils {

    private static final String OUTPUT_CSV = "src/main/resources/output.csv";

    public static boolean columnContainsValue(String assertString) throws IOException {

        Reader in = new FileReader(OUTPUT_CSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

        List<String> targetValues = Arrays.asList(assertString.split(","));
        for (CSVRecord record : records) {
            if (targetValues.get(0).equals(record.get(0))
                && targetValues.get(1).equals(record.get(1))
                && targetValues.get(2).equals(record.get(2))
                && targetValues.get(3).equals(record.get(3))) {
                    return true;
            }
        }
        return false;
    }

    public static long countCsvRecords() throws IOException {

        Reader in = new FileReader(OUTPUT_CSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        Iterator iterator = records.iterator();
        int count = 0;

        while(iterator.hasNext()) {
            iterator.next();
            count++;
        }

        return count;
    }

    public static void parseTradeEvent(String validEmuFile) {

        try {
            new PrintWriter(OUTPUT_CSV).close();
            TradeEventFileParserImpl tradeEventFileParser = new TradeEventFileParserImpl(new File(validEmuFile));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(validEmuFile));

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            tradeEventFileParser.parseFile(document, xpath);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException("Unable to read the XML file");
        }
    }


}
