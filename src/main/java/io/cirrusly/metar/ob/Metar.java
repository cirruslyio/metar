package io.cirrusly.metar.ob;

/**
 * METAR/SPECI Surface Weather Observation.
 */
public class Metar extends Observation {

  private String altimeter;
  private String day;
  private String dewpoint;
  private String encoded;
  private String presentWeather;
  private String remarks;
  private String reportModifier;
  private String runwayVisualRange;
  private String skyCondition;
  private String station;
  private String temperature;
  private String time;
  private String visibility;
  private String windDirection;
  private String windGust;
  private String windSpeed;
  private String windVariability;

  /**
   * Initializes the METAR with the required fields.
   *
   * @param encoded The encoded message of the METAR.
   * @param station The station from which the METAR was produced.
   */
  public Metar(String encoded, String station) {
    this.encoded = encoded;
    this.station = station;
  }

  public String getAltimeter() {
    return altimeter;
  }

  public void setAltimeter(String altimeter) {
    this.altimeter = altimeter;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getDewpoint() {
    return dewpoint;
  }

  public void setDewpoint(String dewpoint) {
    this.dewpoint = dewpoint;
  }

  /**
   * Returns the observation as an encoded FM-15 METAR message.
   */
  @Override
  public String getEncoded() {
    return encoded;
  }

  /**
   * Sets the encoded message of the METAR.
   */
  public void setEncoded(String encoded) {
    this.encoded = encoded;
  }

  public String getPresentWeather() {
    return presentWeather;
  }

  public void setPresentWeather(String presentWeather) {
    this.presentWeather = presentWeather;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getReportModifier() {
    return reportModifier;
  }

  public void setReportModifier(String reportModifier) {
    this.reportModifier = reportModifier;
  }

  public String getRunwayVisualRange() {
    return runwayVisualRange;
  }

  public void setRunwayVisualRange(String runwayVisualRange) {
    this.runwayVisualRange = runwayVisualRange;
  }

  public String getSkyCondition() {
    return skyCondition;
  }

  public void setSkyCondition(String skyCondition) {
    this.skyCondition = skyCondition;
  }

  @Override
  public String getStation() {
    return station;
  }

  /**
   * Sets the station of the METAR.
   */
  public void setStation(String station) {
    this.station = station;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public String getWindDirection() {
    return windDirection;
  }

  public void setWindDirection(String windDirection) {
    this.windDirection = windDirection;
  }

  public String getWindGust() {
    return windGust;
  }

  public void setWindGust(String windGust) {
    this.windGust = windGust;
  }

  public String getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(String windSpeed) {
    this.windSpeed = windSpeed;
  }

  public String getWindVariability() {
    return windVariability;
  }

  public void setWindVariability(String windVariability) {
    this.windVariability = windVariability;
  }
}
