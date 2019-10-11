package io.cirrusly.metar.decoder;

import io.cirrusly.metar.ob.Observation;

public class DecoderFactory {
  private DecoderFactory() {
    // Factory utility class
  }

  /**
   * Returns the decoded observation from the provided encoded message.
   *
   * @param encoded The encoded observation.
   */
  public static Observation decode(String encoded) throws DecoderError {
    if (MetarDecoder.METAR_FORMAT.matcher(encoded).matches()) {
      Decoder decoder = new MetarDecoder(encoded);
      return decoder.decode();
    }
    throw new DecoderError("Unrecognized format");
  }
}
