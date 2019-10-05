package io.cirrusly.metar.decoder;

import io.cirrusly.metar.ob.Observation;

public interface Decoder {
  Observation decode() throws DecoderError;
}
