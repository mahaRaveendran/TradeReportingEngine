package com.vanguard.trade.reporting.engine.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;

@AllArgsConstructor
public class CsvPrinterUtil {

    private final static String CSV_FILEPATH = "src/main/resources/output.csv";

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private CSVPrinter csvPrinter;

    public static final ThreadLocal<CSVPrinter> csvWriter = ThreadLocal.withInitial(() -> {

        try {
            CSVPrinter printer = CSVFormat.DEFAULT
                    .withHeader("SELLER PARTY", "BUYER PARTY", "PREMIUM AMOUNT", "PREMIUM CURRENCY")
                    .print(new FileWriter(CSV_FILEPATH,true));
            return printer;

        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while write record to the CSV file");
        }

    });
}
