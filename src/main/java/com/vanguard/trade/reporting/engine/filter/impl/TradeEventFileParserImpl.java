package com.vanguard.trade.reporting.engine.filter.impl;

import com.vanguard.trade.reporting.engine.filter.TradeEventFileParser;
import com.vanguard.trade.reporting.engine.model.enums.Criteria;
import com.vanguard.trade.reporting.engine.model.enums.XmlElements;
import com.vanguard.trade.reporting.engine.util.CsvPrinterUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVPrinter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

@Slf4j
public class TradeEventFileParserImpl extends Thread implements TradeEventFileParser {

    @Getter
    @Setter
    private File file;

    private final CSVPrinter csvPrinter = CsvPrinterUtil.csvWriter.get();

    public TradeEventFileParserImpl(File file) {
        this.file = file;
    }

    @Override
    public void run() {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document document = dbBuilder.parse(file);
            document.getDocumentElement().normalize();

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            parseFile(document, xpath);
        } catch (ParserConfigurationException | SAXException | IOException  e) {
            throw new RuntimeException("Unable to read the XML file");
        }
    }

    public void parseFile(Document document, XPath xpath) {

        try {
            XPathExpression buyerPartyXPathExpression =
                    xpath.compile(XmlElements.BUYER_PARTY.getXPathExpression());
            XPathExpression sellerPartyXPathExpression =
                    xpath.compile(XmlElements.SELLER_PARTY.getXPathExpression());
            XPathExpression premiumAmountXPathExpression =
                    xpath.compile(XmlElements.PREMIUM_AMOUNT.getXPathExpression());
            XPathExpression premiumCurrencyXPathExpression =
                    xpath.compile(XmlElements.PREMIUM_CURRENCY.getXPathExpression());

            String buyerParty = buyerPartyXPathExpression.evaluate(document, XPathConstants.STRING).toString();
            String sellerParty = sellerPartyXPathExpression.evaluate(document, XPathConstants.STRING).toString();
            String premiumAmount = premiumAmountXPathExpression.evaluate(document, XPathConstants.STRING).toString();
            String premiumCurrency = premiumCurrencyXPathExpression.evaluate(document, XPathConstants.STRING).toString();

            if((Criteria.EMU_BANK.getSellerParty().equalsIgnoreCase(sellerParty)
                    && Criteria.EMU_BANK.getPremiumCurrency().equalsIgnoreCase(premiumCurrency)) ||
                    (Criteria.BISON_BANK.getSellerParty().equalsIgnoreCase(sellerParty)
                            && Criteria.BISON_BANK.getPremiumCurrency().equalsIgnoreCase(premiumCurrency))) {
                log.info(format("Printing record to CSV format :: Buyer Party -> %s, Seller Party -> %s, Amount -> %s, Currency -> %s",
                        buyerParty, sellerParty, premiumAmount, premiumCurrency));
                csvPrinter.printRecord(sellerParty, buyerParty, premiumAmount, premiumCurrency);
                csvPrinter.flush();
            }
        }catch (XPathExpressionException | IOException e) {
            throw new RuntimeException("Unable to parse and write the XML file");
        }
    }
}
