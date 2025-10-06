package fr.uvsq.cprog.collex.models;

/** 
 * Represents an IP address with validation.
 * Validates that the IP address is in the correct IPv4 format.
 */
public class AdresseIp {
  private final String value;

  /**
   * Constructs an AdresseIp object with the specified value.
   *
   * <p>Expects a valid IPv4 format (e.g., 192.168.1.1)</p>
   *
   * @param value the IP address to validate and store
   * @throws IllegalArgumentException if the value is null or does not match the required pattern
   */
  public AdresseIp(String value) {
    if (!value.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$")) {
      throw new IllegalArgumentException("Adresse IP invalide : " + value);
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
