package fr.uvsq.cprog.collex.models;

public class DnsItem {
  private final AdresseIP ip;
  private final NomMachine nom;

  public DnsItem(AdresseIP ip, NomMachine nom) {
    this.ip = ip;
    this.nom = nom;
  }

  public AdresseIP getIp() {
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
