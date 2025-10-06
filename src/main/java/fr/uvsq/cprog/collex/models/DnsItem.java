package fr.uvsq.cprog.collex.models;

/** Represents a DNS item containing an IP address and a machine name. */
public class DnsItem {
  private final AdresseIp ip;
  private final NomMachine nom;

  /**
   * Constructs a DnsItem with the specified IP address and machine name.
   *
   * @param ip the IP address
   * @param nom the machine name
   */
  public DnsItem(AdresseIp ip, NomMachine nom) {
    this.ip = ip;
    this.nom = nom;
  }

  public AdresseIp getIp() {
    return ip;
  }

  public NomMachine getNom() {
    return nom;
  }

  @Override
  public String toString() {
    return ip + " " + nom;
  }
}
