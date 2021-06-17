package com.vanguard.trade.service;

import com.vanguard.trade.reporting.engine.exception.EmptyDirectoryPathException;
import com.vanguard.trade.reporting.engine.filter.impl.TradeEventFileParserImpl;
import com.vanguard.trade.reporting.engine.service.TradeReportingService;
import com.vanguard.trade.reporting.engine.service.impl.TradeReportingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.vanguard.trade.utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest(properties = "event.files.path=src/main/resources/events")
@ActiveProfiles("test")
public class TradeReportingServiceTest {

    TradeReportingService tradeReportingService = new TradeReportingServiceImpl();

    @Mock
    TradeEventFileParserImpl tradeEventFileParser;

    @Test
    public void testValidDirectoryIsPrintedInCSV() throws Exception {


        assertEquals(7, countCsvRecords());
    }

    @Test
    public void testInvalidDirDoesNotPrintInCSV() throws Exception {

        Exception exception = assertThrows(EmptyDirectoryPathException.class, () ->
                tradeReportingService.processTradeEvents());
        Assertions.assertEquals("Directory path is empty. Please update value of event.files.path in property file.", exception.getMessage());

    }
}
