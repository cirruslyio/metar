package io.cirrusly.metar.decoder;

import io.cirrusly.metar.ob.Observation;

public class DecoderFactory {
  private static final String METAR_FORMAT =
      "^([A-Z0-9]{4}) ([0-9]{2})([0-9]{4}Z) (AUTO |COR )?(\\d{3})(\\d{2,3})(G\\d{2,3})?KT.*";

  private DecoderFactory() {
    // Factory utility class
  }

  /**
   * Returns the decoded observation from the provided encoded message.
   *
   * @param encoded The encoded observation.
   */
  public static Observation decode(String encoded) throws DecoderError {
    if (encoded.matches(METAR_FORMAT)) {
      Decoder decoder = new MetarDecoder(encoded);
      return decoder.decode();
    }
    throw new DecoderError("Unrecognized format");
  }
}
