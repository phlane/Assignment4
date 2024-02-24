package accidentpack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Object to hold logic for parsing CSV formatted text
 * @author Charles Winkelman
 */

class ReportParser {

    /**
     * Generates an array of Reports from the CSV file at filePath
     *
     * @param filePath   path to csv file
     * @return array of Reports
     * @thows FileNotFoundException  if file at filePath can't be found
     * @thows IOException            if there is a read issue with the file
     */
    public static Report[] fromFile(String filePath) throws IOException, FileNotFoundException {
        Report[] reports;
        String[] line;
        int fileLength;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            fileLength = countLines(filePath);
            reports = new Report[fileLength - 1];
            reader.readLine(); //discards header

            for (int i = 0; i < fileLength - 1; i++) {
                line = reader.readLine().split(",");
                reports[i] = parseLine(line);
            }
        }

        return reports;
    }

    /**
     * Takes path to file and counts line number through iteration
     * This method is relied upon to provide the initial length of
     * the Report array.
     *
     * @param filePath path to file
     * @return number of lines in file (including header)
     * @thows FileNotFoundException  if file at filePath can't be found
     * @thows IOException            if there is a read issue with the file
     */
    public static int countLines(String filePath) throws IOException, FileNotFoundException {
        int lineCounter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lineCounter++;
            }
        }
        return lineCounter;
    }

    /**
     * Parses single CSV line into a report object
     * parseCSV requires a CSV of a specific format to work. That format is:
     *   id, severity(1-4), start date + time, end date + time, street, city,
     *   county, state, tempurature (F), humidity (percent), visibility (mi),
     *   weather condition, crossing (true or false), (day or night)
     *
     * @param csv  a line of properly formatted CSV
     * @return     Report object constructed from CSV
     * @see parseDateTimeString for start/end time format
     */
    public static Report parseLine(String[] fields) {

        String id = fields[0];
        int severity = Integer.parseInt(fields[1]);
        LocalDateTime startTime = parseDateTimeString(fields[2]);
        LocalDateTime endTime = parseDateTimeString(fields[3]);
        String street = fields[4];
        String city = fields[5];
        String county = fields[6];
        String state = fields[7];
        float temperature = Float.parseFloat(fields[8]);
        float humidity = Float.parseFloat(fields[9]);
        float visibility = Float.parseFloat(fields[10]);
        String weather = fields[11];
        Boolean crossing = parseCSVBoolean(fields[12]);
        String timeOfDay = fields[13];

        return new Report(id, severity, startTime, endTime, street, city, county, state,
                          temperature, humidity, visibility, weather, crossing, timeOfDay);
    }

    /**
     * Parsed date and time String into LocalDateTimeObject
     *
     * @param dtString
     * @returns  LocalDateTime object parsed from input string
     */
    public static LocalDateTime parseDateTimeString(String dtString) {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (dtString.contains(".")) {
            return parseDateTimeString(dtString.substring(0, dtString.indexOf(".")));
        } else {
            return LocalDateTime.parse(dtString, dtFormat);
        }
    }

    /**
     * Parses string to either True or False, throws error if otherwise
     * Function expects
     */
    public static Boolean parseCSVBoolean(String valueString) {

        Boolean isTrue = (valueString.equalsIgnoreCase("true"));
        Boolean isFalse = (valueString.equalsIgnoreCase("false"));

        if (!isTrue && !isFalse) {
            throw new IllegalStateException("Failed to parse " + valueString + " as boolean");
        } else {
            return isTrue;
        }
    }
    
    public static String getState(String[] line) {
    	return line[7];
    }

}
