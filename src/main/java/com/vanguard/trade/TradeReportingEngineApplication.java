package com.vanguard.trade;

import com.vanguard.trade.reporting.engine.service.TradeReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradeReportingEngineApplication implements CommandLineRunner {
   @Autowired
   TradeReportingService tradeReportingService;

	public static void main(String[] args) {
		SpringApplication.run(TradeReportingEngineApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        getTradeReportingService().processTradeEvents();
    }

    private TradeReportingService getTradeReportingService() {
        return tradeReportingService;
    }
}
