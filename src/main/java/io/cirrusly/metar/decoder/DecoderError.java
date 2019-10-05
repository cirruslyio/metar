package io.cirrusly.metar.decoder;

/**
 * An error that is thrown when there are errors with decoding an encoded weather observation.
 */
public class DecoderError extends Exception {

  private static final long serialVersionUID = 3417294481746350331L;

  public DecoderError(String message) {
    super(message);
  }
}