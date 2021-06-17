package com.vanguard.trade.reporting.engine.filter;

import org.w3c.dom.Document;

import javax.xml.xpath.XPath;

public interface TradeEventFileParser {

     void parseFile(Document document, XPath xPath);
}
