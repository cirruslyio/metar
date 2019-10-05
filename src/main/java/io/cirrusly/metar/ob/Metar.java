package io.cirrusly.metar.ob;

/**
 * METAR/SPECI Surface Weather Observation.
 */
public class Metar extends Observation {

  private String day;
  private String encoded;
  private String reportModifier;
  private String station;
  private String time;
  private String windDirection;
  private String windGust;
  private String windSpeed;

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

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
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

  public String getReportModifier() {
    return reportModifier;
  }

  public void setReportModifier(String reportModifier) {
    this.reportModifier = reportModifier;
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

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
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
}
