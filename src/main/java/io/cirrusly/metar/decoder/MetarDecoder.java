package io.cirrusly.metar.decoder;

import io.cirrusly.metar.ob.Metar;
import io.cirrusly.metar.ob.Observation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for METAR/SPECI observations.
 */
public class MetarDecoder implements Decoder {

  private static final Pattern METAR_FORMAT = Pattern.compile(
      // Station, Day, Hour, Report Modifier, Winds
      "^([A-Z0-9]{4}) ([0-9]{2})([0-9]{4}Z) (AUTO|COR)? ?(\\d{3}|VRB)(\\d{2,3})(G\\d{2,3})?KT "
      // Wind variability, visibility (SM)
      + "([0-9]{3}V[0-9]{3})? ?(0SM|M?[1-9][0-9/ ]{0,4}SM) "
      // RVR
      + "(R[0-9]{2}[LRC]?\\/[PM]?[0-9]{4}(?:V[0-9]{4})?FT)? "
      // Present Weather
      + "?((?: ?(?:[-+]?|VC)(?:MI|PR|BC|DR|BL|SH|TS|FZ|DZ|RA|SN|SG|IC|PL|GR|GS|UP|BR|FG|FU|VA|DU"
      // Present Weather continued...
      + "|SA|HZ|PY|PO|SQ|FC|SS|DS))*) "
      // Sky Condition
      + "?((?: ?(?:SKC|CLR|FEW[0-9]{3}|SCT[0-9]{3}|BKN[0-9]{3}|OVC[0-9]{3}))+) "
      // Temperature/Dewpoint, Altimeter, Remarks
      + "([0-9]{2}(?:/[0-9]{2})?)? (A(?:M|[0-9]{4})) (RMK.*)");

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
}
