package fr.uvsq.cprog.collex.models;

public class NomMachine {

  private final String value;

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
