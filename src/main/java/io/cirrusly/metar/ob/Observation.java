package io.cirrusly.metar.ob;

/**
 * A weather observation that originates from a decoded text product.
 */
public abstract class Observation {

  /**
   * Returns the observation as an encoded observation.
   */
  public abstract String encode();
}
