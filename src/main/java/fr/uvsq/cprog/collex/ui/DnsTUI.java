package fr.uvsq.cprog.collex.ui;

import fr.uvsq.cprog.collex.models.AdresseIP;
import fr.uvsq.cprog.collex.models.Dns;
import fr.uvsq.cprog.collex.models.DnsItem;
import fr.uvsq.cprog.collex.models.NomMachine;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DnsTUI {

  private final Dns dns;
  private final Scanner scanner = new Scanner(System.in);

  private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3}");

  public DnsTUI(String filePath) throws IOException {
    this.dns = new Dns(filePath);
  }

  public void run() {
    System.out.println("=== DNS TUI ===");
    System.out.println("Tapez une commande (ls, add, nom.qualifie, adr.ip ou 'exit' pour quitter)");

    while (true) {
      System.out.print("> ");
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
        break;
      }

      if (input.isEmpty()) {
        continue;
      }

      try {
        handleCommand(input);
      } catch (Exception e) {
        System.out.println("ERREUR : " + e.getMessage());
      }
    }
  }

  private void handleCommand(String input) throws IOException {
    String[] parts = input.split("\\s+");

    if (parts[0].equals("ls")) {
      handleListCommand(parts);
    } else if (parts[0].equals("add")) {
      handleAddCommand(parts);
    } else if (IP_PATTERN.matcher(parts[0]).matches()) {
      handleIPQuery(parts[0]);
    } else if (parts[0].contains(".")) {
      handleNameQuery(parts[0]);
    } else {
      System.out.println("Commande inconnue : " + parts[0]);
    }
  }

  private void handleListCommand(String[] parts) {
    boolean sortByIP = false;
    String domain;

    if (parts.length == 3 && parts[1].equals("-a")) {
      sortByIP = true;
      domain = parts[2];
    } else if (parts.length == 2) {
      domain = parts[1];
    } else {
      System.out.println("Usage : ls [-a] domaine");
      return;
    }

    List<DnsItem> items = dns.getItems(domain);

    if (sortByIP) {
      items.sort(Comparator.comparing(i -> i.getIp().getValue()));
    }

    for (DnsItem item : items) {
      System.out.println(item.getIp().getValue() + " " + item.getNom().getValue());
    }
  }

  private void handleAddCommand(String[] parts) throws IOException {
    if (parts.length != 3) {
      System.out.println("Usage : add adr.es.se.ip nom.qualifie.machine");
      return;
    }

    AdresseIP ip = new AdresseIP(parts[1]);
    NomMachine nom = new NomMachine(parts[2]);
    dns.addItem(ip, nom);
  }

  private void handleNameQuery(String name) {
    DnsItem item = dns.getItem(new NomMachine(name));
    if (item == null) {
      System.out.println("ERREUR : Nom de machine inconnu");
    } else {
      System.out.println(item.getIp().getValue());
    }
  }

  private void handleIPQuery(String ipStr) {
    DnsItem item = dns.getItem(new AdresseIP(ipStr));
    if (item == null) {
      System.out.println("ERREUR : Adresse IP inconnue");
    } else {
      System.out.println(item.getNom().getValue());
    }
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage : java DnsTUI <fichier_dns>");
      return;
    }

    try {
      DnsTUI tui = new DnsTUI(args[0]);
      tui.run();
    } catch (IOException e) {
      System.out.println("Erreur de lecture du fichier : " + e.getMessage());
    }
  }
}
