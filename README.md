Trade Reporting Engine

A Java program written to report the trades that match a certain criteria. 

This project uses Spring boot, Java 8 and Maven. The Test suites are written using Mockito. 

It follows a simple Spring framework approach with a Main Application class invoking a Service class which uses a filter to parse the file to achieve the expected result. The service class and the filter class extend an interface.Enums are used to specify the various XPathExpressions and the Criteria based on which the trade event is filtered. Used Lombok to keep the code cleaner. 

The test suit contains two main test class. One that tests all the corner cases for the parsing of the event and the data that is written into the CSV file and another test to test the service class to see if the right directory and event files are being read for parsing. 

The path to the event files are passed via property files. 

Build the Maven project using the below commands. 

mvn clean compile package 

Run as independent Spring boot application. mvn spring-boot:run either via command line or via intellij

The event payload shared in the email is used in the resources at the moment and pointed to that path in the properties file. In order to change the path, please edit the property "event.files.path" in application.properties or add the files in this path (src/main/resources/events) to see the results. The output is printed in this location - src/main/resources/output.csv 
