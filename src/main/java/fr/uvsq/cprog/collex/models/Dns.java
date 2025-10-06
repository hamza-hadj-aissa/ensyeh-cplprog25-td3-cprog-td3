package fr.uvsq.cprog.collex.models;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Dns {
  private final List<DnsItem> items = new ArrayList<>();
  private final Path dbPath;

  public Dns(String dbFile) throws IOException {
    this.dbPath = Paths.get(dbFile);
    load();
  }

  private void load() throws IOException {
    List<String> lines = Files.readAllLines(dbPath);
    for (String line : lines) {
      String[] parts = line.split(" ");
      items.add(new DnsItem(new AdresseIp(parts[1]), new NomMachine(parts[0])));
    }
  }

  private void save() throws IOException {
    List<String> lines =
        items.stream()
            .map(i -> i.getNom().getValue() + " " + i.getIp().getValue())
            .collect(Collectors.toList());
    Files.write(dbPath, lines);
  }

  public DnsItem getItem(AdresseIp ip) {
    return items.stream()
        .filter(i -> i.getIp().getValue().equals(ip.getValue()))
        .findFirst()
        .orElse(null);
  }

  public DnsItem getItem(NomMachine nom) {
    return items.stream()
        .filter(i -> i.getNom().getValue().equals(nom.getValue()))
        .findFirst()
        .orElse(null);
  }

  public List<DnsItem> getItems(String domaine) {
    return items.stream()
        .filter(i -> i.getNom().getDomaine().equals(domaine))
        .sorted(Comparator.comparing(i -> i.getNom().getMachine()))
        .collect(Collectors.toList());
  }

  public void addItem(AdresseIp ip, NomMachine nom) throws IOException {
    if (getItem(ip) != null) {
      throw new IllegalArgumentException("ERREUR : L'adresse existe déjà !");
    }
    if (getItem(nom) != null) {
      throw new IllegalArgumentException("ERREUR : Le nom de machine existe déjà !");
    }
    items.add(new DnsItem(ip, nom));
    save();
  }
}
