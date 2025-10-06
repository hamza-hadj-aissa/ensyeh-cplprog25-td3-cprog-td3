package fr.uvsq.cprog.collex.models;

public class DnsItem {
  private final AdresseIp ip;
  private final NomMachine nom;

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
