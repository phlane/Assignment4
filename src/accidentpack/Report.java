package accidentpack;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

/**
 * This class provides an object for storing reports.
 *
 * @author Charles Winkelman
 */
class Report {

    private String id;
    private int severity;
    private LocalDateTime startTime;
    private LocalDate date;
    private int time;
    private LocalDateTime endTime;
    private String street;
    private String city;
    private String county;
    private String state;
    private float temperature;
    private float humidity;
    private float visibility;
    private String weather;
    private Boolean crossing;
    private String timeOfDay;

    /**
     * @param id           an identify in format A-xxxxxx
     * @param severity     a measure of accident severity, ranging from 1 to 4
     * @param startTime    time and date of when the accident started
     * @param endTime      time and date of when the accident ended
     * @param location     where the accident happened, with street, city, county and state
     * @param street       street name
     * @param city         city name
     * @param county       county name
     * @param state        state, 2 letter abbreviation
     * @param temperature  temp in Farenheit at time of accident
     * @param humidity     percent humidity at time of accident
     * @param visibility   miles of visibility at time of accident
     * @param weather      weather conditions at time of accident
     * @param crossing     whether a street crossing was present at accident, should be true or false
     * @param timeOfDay    time of day of accident, should be either "Night" or "Day"
     */
    public Report(String id, int severity, LocalDateTime startTime, LocalDateTime endTime,
                  String street, String city, String county, String state, float temperature,
                  float humidity, float visibility, String weather, Boolean crossing, String timeOfDay) {
        this.id          = id;
        this.severity    = severity;
        this.startTime   = startTime;
        this.endTime     = endTime;
        this.street      = street;
        this.city        = city;
        this.county      = county;
        this.state       = state;
        this.temperature = temperature;
        this.humidity    = humidity;
        this.visibility  = visibility;
        this.weather     = weather;
        this.crossing    = crossing;
        this.timeOfDay   = timeOfDay;
        
        setDateAndTime();
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
    public Report(String[] fields) {

        id = fields[0];
        severity = Integer.parseInt(fields[1]);
        startTime = ReportParser.parseDateTimeString(fields[2]);
        endTime = ReportParser.parseDateTimeString(fields[3]);
        street = fields[4];
        city = fields[5];
        county = fields[6];
        state = fields[7];
        temperature = Float.parseFloat(fields[8]);
        humidity = Float.parseFloat(fields[9]);
        visibility = Float.parseFloat(fields[10]);
        weather = fields[11];
        crossing = ReportParser.parseCSVBoolean(fields[12]);
        timeOfDay = fields[13];
        
        setDateAndTime();
    }
    
    private void setDateAndTime() {
    	date = startTime.toLocalDate();
    	time = startTime.get(ChronoField.MINUTE_OF_DAY);
    }


    public String getId() { return id; }

    public int getSeverity() { return severity; }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public String getWeather() {
        return weather;
    }

    public Boolean getCrossing() {
        return crossing;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }
    
    public LocalDate getDate() {
    	return date;
    }
    
    public int getTime() {
    	return time;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s vis: %.2f", id, street, city, county, state, visibility);
    }
}

