package fr.uvsq.cprog.collex.models;

/**
 * Represents a machine name (hostname) with validation.
 * A machine name consists of a name and a domain, for example: www.uvsq.fr
 */
public class NomMachine {

  private final String value;

  /**
   * Constructs a NomMachine object with the specified value.
   *
   * <p>Expects a valid FQDN (Fully Qualified Domain Name) format.
   * Ex: sciences.uvsq.fr, mail.google.com</p>
   *
   * @param value the machine name to validate and store
   * @throws IllegalArgumentException if the value is null or does not match the required pattern
   */
  public NomMachine(String value) {
    // Regex pour valider un nom de machine (ex: www.uvsq.fr)
    String regex = "^(?!-)([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";

    if (value == null || !value.matches(regex)) {
      throw new IllegalArgumentException("Nom de machine invalide : " + value);
    }

    this.value = value;
  }


  public String getMachine() {
    return value.split("\\.")[0];
  }

  public String getDomaine() {
    return value.substring(value.indexOf('.') + 1);
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
