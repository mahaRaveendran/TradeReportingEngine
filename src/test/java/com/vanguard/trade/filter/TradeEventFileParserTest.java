package com.vanguard.trade.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.*;

import static com.vanguard.trade.utils.TestUtils.columnContainsValue;
import static com.vanguard.trade.utils.TestUtils.parseTradeEvent;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TradeEventFileParserTest {

    private static final String VALID_EMU_FILE = "src/test/resources/event.files/validCriteria/ValidEmuBank.xml";
    private static final String NO_SELLER_PARTY_FILE = "src/test/resources/event.files/invalidCriteria/NoSellerParty.xml";
    private static final String NO_BUYER_PARTY_FILE = "src/test/resources/event.files/invalidCriteria/NoBuyerParty.xml";
    private static final String NO_CURRENCY_FILE = "src/test/resources/event.files/invalidCriteria/NoCurrency.xml";
    private static final String VALID_EMU_WITH_INVALID_CCY_FILE = "src/test/resources/event.files/invalidCriteria/ValidEmuBankWithInvalidCcy.xml";
    private static final String VALID_BISON_FILE = "src/test/resources/event.files/validCriteria/ValidBisonBank.xml";
    private static final String VALID_BISON_WITH_INVALID_CCY_FILE = "src/test/resources/event.files/invalidCriteria/ValidBisonBankWithInvalidCcy.xml";
    private static final String INVALID_BISON_FILE = "src/test/resources/event.files/InvalidCriteria/InvalidBisonBank.xml";
    private static final String INVALID_FILE = "src/test/resources/event.files/InvalidCriteria/EmptyFile.xml";
    private static final String SELLER_PARTY_ANAGRAM_FILE = "src/test/resources/event.files/invalidCriteria/InvalidSellerPartyAsAnagram.xml";
    private static final String BUYER_PARTY_ANAGRAM_FILE = "src/test/resources/event.files/invalidCriteria/InvalidBuyerPartyAsAnagram.xml";

    @Test
    public void testEmuBankValidValueIsPrintedInCSV() throws Exception {

        parseTradeEvent(VALID_EMU_FILE);
        assertTrue(columnContainsValue("EMU_BANK,LEFT_BANK,200.00,AUD"));
    }

    @Test
    public void testNoSellerPartyIsNotPrintedInCSV() throws Exception {

        parseTradeEvent(NO_SELLER_PARTY_FILE);
        assertFalse(columnContainsValue("EMU_BANK,LEFT_BANK,200.00,AUD"));
    }

    @Test
    public void testNoBuyerPartyIsNotPrintedInCSV() throws Exception {

        parseTradeEvent(NO_BUYER_PARTY_FILE);
        assertFalse(columnContainsValue("EMU_BANK,LEFT_BANK,200.00,AUD"));
    }

    @Test
    public void testNoCurrencyIsNotPrintedInCSV() throws Exception {

        parseTradeEvent(NO_CURRENCY_FILE);
        assertFalse(columnContainsValue("EMU_BANK,LEFT_BANK,200.00,AUD"));
    }

    @Test
    public void testEmuBankWithInValidCurrencyValueIsNotPrintedInCSV() throws Exception {

        parseTradeEvent(VALID_EMU_WITH_INVALID_CCY_FILE);
        assertFalse(columnContainsValue("EMU_BANK,LEFT_BANK,200.00,AUD"));
    }

    @Test
    public void testSellerPartyInValidAnagramValueIsNotPrintedInCSV() throws Exception {

        parseTradeEvent(SELLER_PARTY_ANAGRAM_FILE);
        assertFalse(columnContainsValue("EMU_BANK,LEFT_BANK,200.00,AUD"));
    }

    @Test
    public void testBuyerPartyInValidAnagramValueIsPrintedInCSV() throws Exception {

        parseTradeEvent(BUYER_PARTY_ANAGRAM_FILE);
        assertFalse(columnContainsValue("EMU_BANK,LEFT_BANK,200.00,AUD"));
    }

    @Test
    public void testBisonBankValidValueIsPrintedInCSV() throws Exception {

        parseTradeEvent(VALID_BISON_FILE);
        assertTrue(columnContainsValue("BISON_BANK,EMU_BANK,600.00,USD"));
    }

    @Test
    public void testBisonBankWithInValidCcyValueIsPrintedInCSV() throws Exception {

        parseTradeEvent(VALID_BISON_WITH_INVALID_CCY_FILE);
        assertFalse(columnContainsValue("BISON_BANK,EMU_BANK,600.00,USD"));
    }

    @Test
    public void testBisonBankInValidValueIsNotPrintedInCSV() throws IOException {

        parseTradeEvent(INVALID_BISON_FILE);
        assertFalse(columnContainsValue("BISON_BANK,EMU_BANK,150.00,AUD"));
    }

    @Test
    public void testInValidValueIsNotPrintedInCSV() {

        Exception exception = assertThrows(RuntimeException.class, () ->
                parseTradeEvent(INVALID_FILE));
        assertEquals("Unable to read the XML file", exception.getMessage());
    }

}
