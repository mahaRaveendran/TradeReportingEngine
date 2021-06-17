package com.vanguard.trade.reporting.engine.service;

import com.vanguard.trade.reporting.engine.exception.EmptyDirectoryPathException;

import java.io.IOException;

public interface TradeReportingService {

    void processTradeEvents() throws EmptyDirectoryPathException, IOException;
}
