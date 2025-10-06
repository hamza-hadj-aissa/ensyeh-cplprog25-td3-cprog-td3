package fr.uvsq.cprog.collex.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a DNS (Domain Name System) that manages a collection of DNS items, allowing for
 * loading from and saving to a file, as well as querying and adding items.
 */
public class Dns {
  private final List<DnsItem> items = new ArrayList<>();
  private final Path dbPath;

  /**
   * Constructs a Dns object and loads DNS items from the specified database file.
   *
   * @param dbFile the path to the database file
   * @throws IOException if an I/O error occurs while reading the file
   */
  public Dns(String dbFile) throws IOException {
    this.dbPath = Paths.get(dbFile);
    load();
  }

  /**
   * Loads DNS items list from the database file into the items list.
   *
   * @throws IOException if an I/O error occurs while reading the file
   */
  private void load() throws IOException {
    List<String> lines = Files.readAllLines(dbPath);
    for (String line : lines) {
      String[] parts = line.split(" ");
      items.add(new DnsItem(new AdresseIp(parts[1]), new NomMachine(parts[0])));
    }
  }

  /**
   * Saves the current DNS items list to the database file.
   *
   * @throws IOException if an I/O error occurs while writing to the file
   */
  private void save() throws IOException {
    List<String> lines =
        items.stream()
            .map(i -> i.getNom().getValue() + " " + i.getIp().getValue())
            .collect(Collectors.toList());
    Files.write(dbPath, lines);
  }

  /**
   * Retrieves a DNS item by its IP address.
   *
   * @param ip the IP address to search for
   * @return the corresponding DnsItem, or null if not found
   */
  public DnsItem getItem(AdresseIp ip) {
    return items.stream()
        .filter(i -> i.getIp().getValue().equals(ip.getValue()))
        .findFirst()
        .orElse(null);
  }

  /**
   * Retrieves a DNS item by its machine name.
   *
   * @param nom the machine name to search for
   * @return the corresponding DnsItem, or null if not found
   */
  public DnsItem getItem(NomMachine nom) {
    return items.stream()
        .filter(i -> i.getNom().getValue().equals(nom.getValue()))
        .findFirst()
        .orElse(null);
  }

  /**
   * Retrieves a list of DNS items for a specific domain, sorted by machine name.
   *
   * @param domaine the domain to filter by
   * @return a list of DnsItems belonging to the specified domain
   */
  public List<DnsItem> getItems(String domaine) {
    return items.stream()
        .filter(i -> i.getNom().getDomaine().equals(domaine))
        .sorted(Comparator.comparing(i -> i.getNom().getMachine()))
        .collect(Collectors.toList());
  }

  /**
   * Adds a new DNS item with the specified IP address and machine name.
   *
   * @param ip the IP address of the new item
   * @param nom the machine name of the new item
   * @throws IOException if an I/O error occurs while saving to the file
   * @throws IllegalArgumentException if the IP address or machine name already exists
   */
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
