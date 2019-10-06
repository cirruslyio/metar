package io.cirrusly.metar.decoder;

import io.cirrusly.metar.ob.Metar;
import io.cirrusly.metar.ob.Observation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for METAR/SPECI observations.
 */
public class MetarDecoder implements Decoder {

  public static final Pattern METAR_FORMAT = Pattern.compile(
      // Station, Day, Time, Report Modifier
      "^([A-Z0-9]{4}) ([0-9]{2})([0-9]{4}Z) (AUTO|COR)? ?"
      // Wind, Wind Variability
      + "(?:(\\d{3}|VRB)?(\\d{2,3})(G\\d{2,3})?KT)? ?([0-9]{3}V[0-9]{3})? ?"
      // Visibility, RVR
      + "(0SM|M?[1-9][0-9/ ]{0,4}SM)? ?(R[0-9]{2}[LRC]?\\/[PM]?[0-9]{4}(?:V[0-9]{4})?FT)? ?"
      // Present Weather
      + "((?: ?(?:[-+]?|VC)(?:MI|PR|BC|DR|BL|SH|TS|FZ|DZ|RA|SN|SG|IC|PL|GR|GS|UP|BR|FG|FU|VA|DU"
      // Present Weather continued
      + "|SA|HZ|PY|PO|SQ|FC|SS|DS))*) ?"
      // Sky Condition
      + "((?: ?(?:SKC|CLR|FEW[0-9]{3}|SCT[0-9]{3}|BKN[0-9]{3}|OVC[0-9]{3}))+|VV[0-9]{3})? ?"
      // Temperature/Dewpoint, Altimeter, Remarks
      + "([0-9]{2}(?:\\/[0-9]{2})?)? ?(A(?:M|[0-9]{4}))? ?(RMK.*)?");

  private String encoded;

  public MetarDecoder(String encoded) {
    this.encoded = encoded;
  }

  @Override
  public Observation decode() throws DecoderError {
    Metar metar = new Metar(encoded, parseStation());
    metar.setDay(parseDay());
    metar.setTime(parseTime());
    metar.setReportModifier(parseReportModifier());
    metar.setWindDirection(parseWindDirection());
    metar.setWindSpeed(parseWindSpeed());
    metar.setWindGust(parseWindGust());
    metar.setWindVariability(parseWindVariability());
    metar.setVisibility(parseVisibility());
    metar.setPresentWeather(parsePresentWeather());
    metar.setSkyCondition(parseSkyCondition());
    metar.setTemperature(parseTemperature());
    metar.setDewpoint(parseDewpoint());
    metar.setAltimeter(parseAltimeter());
    metar.setRemarks(parseRemarks());
    return metar;
  }

  private Matcher getPrefixMatcher() throws DecoderError {
    Matcher matcher = METAR_FORMAT.matcher(encoded);
    if (!matcher.matches()) {
      throw new DecoderError("Observation does not have a valid format.");
    }
    return matcher;
  }

  protected String parseStation() throws DecoderError {
    return getPrefixMatcher().group(1);
  }

  protected String parseDay() throws DecoderError {
    return getPrefixMatcher().group(2);
  }

  protected String parseTime() throws DecoderError {
    return getPrefixMatcher().group(3);
  }

  protected String parseReportModifier() throws DecoderError {
    return getPrefixMatcher().group(4);
  }

  protected String parseWindDirection() throws DecoderError {
    return getPrefixMatcher().group(5);
  }

  protected String parseWindSpeed() throws DecoderError {
    return getPrefixMatcher().group(6);
  }

  protected String parseWindGust() throws DecoderError {
    String windGust = getPrefixMatcher().group(7);
    if (windGust != null) {
      return windGust.substring(1);
    }
    return null;
  }

  protected String parseWindVariability() throws DecoderError {
    return getPrefixMatcher().group(8);
  }

  protected String parseVisibility() throws DecoderError {
    return getPrefixMatcher().group(9);
  }

  protected String parsePresentWeather() throws DecoderError {
    String pw = getPrefixMatcher().group(11);
    if (pw != null && pw.isEmpty()) {
      return null;
    }
    return pw;
  }

  protected String parseSkyCondition() throws DecoderError {
    return getPrefixMatcher().group(12);
  }

  protected String parseTemperature() throws DecoderError {
    String temperatureGroup = getPrefixMatcher().group(13);
    if (temperatureGroup != null && temperatureGroup.indexOf('/') >= 0) {
      return temperatureGroup.substring(0, temperatureGroup.indexOf('/'));
    }
    return temperatureGroup;
  }

  protected String parseDewpoint() throws DecoderError {
    String temperatureGroup = getPrefixMatcher().group(13);
    if (temperatureGroup != null && temperatureGroup.indexOf('/') >= 0) {
      return temperatureGroup.substring(temperatureGroup.indexOf('/') + 1);
    }
    return null;
  }

  protected String parseAltimeter() throws DecoderError {
    return getPrefixMatcher().group(14);
  }

  protected String parseRemarks() throws DecoderError {
    return getPrefixMatcher().group(15);
  }
}
