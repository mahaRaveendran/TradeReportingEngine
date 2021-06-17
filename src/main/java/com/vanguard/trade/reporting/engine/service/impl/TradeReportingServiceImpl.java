package com.vanguard.trade.reporting.engine.service.impl;

import com.vanguard.trade.reporting.engine.exception.EmptyDirectoryPathException;
import com.vanguard.trade.reporting.engine.exception.NoEventFilesException;
import com.vanguard.trade.reporting.engine.filter.impl.TradeEventFileParserImpl;
import com.vanguard.trade.reporting.engine.service.TradeReportingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
public class TradeReportingServiceImpl implements TradeReportingService {

    @Value("${event.files.path}")
    private String directoryPath;

    @Value("${event.files.format}")
    private String fileFormat;

    @Value("${csv.file.path}")
    private String outputCsvFilePath;

    private final static String CSV_FILEPATH = "src/main/resources/output.csv";

    @Override
    public void processTradeEvents() throws EmptyDirectoryPathException, IOException {

        log.info(format("Processing Trade Events from directory path : %s", directoryPath));

        if(StringUtils.isEmpty(directoryPath)) {
            throw new EmptyDirectoryPathException("Directory path is empty. Please update value of event.files.path in property file.");
        }

        List<File> eventFiles = new ArrayList<>();
        //Retrieve Event files.
        if(Files.exists(Paths.get(directoryPath))) {
            eventFiles = Files.list(Paths.get(directoryPath))
                    .filter(file -> file.getFileName().toString().matches(fileFormat))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }

        if(eventFiles.size() == 0) {
            throw new NoEventFilesException(format("No trade event files found in the directory path : %s ", directoryPath));
        }

        log.info(format("Number of Trade event files picked for processing : %s", eventFiles.size()));

        clearCSVContents();
        //Filter events and write to CSV file
        for (File file : eventFiles) {
            new Thread(new TradeEventFileParserImpl(file)).start();
        }
    }

    public static void clearCSVContents() {
        FileWriter fwOb = null;
        try {
            fwOb = new FileWriter(CSV_FILEPATH, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        try {
            fwOb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
