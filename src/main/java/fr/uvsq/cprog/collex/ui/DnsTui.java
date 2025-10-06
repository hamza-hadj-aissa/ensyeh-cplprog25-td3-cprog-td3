import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.uvsq.cprog.collex.models.AdresseIp;
import fr.uvsq.cprog.collex.models.Dns;
import fr.uvsq.cprog.collex.models.DnsItem;
import fr.uvsq.cprog.collex.models.NomMachine;


/**
 * Textual User Interface for DNS management. Supports commands to list, add, and query DNS entries.
 */
public class DnsTui {

  private final Dns dns;
  private final Scanner scanner = new Scanner(System.in);

  private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3}");

  /**
   * Constructor initializing the DNS from a file.
   *
   * @param filePath Path to the DNS file.
   * @throws IOException If there is an error reading the file.
   */
  public DnsTui(String filePath) throws IOException {
    this.dns = new Dns(filePath);
  }

  /** Main loop to run the TUI. */
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

  /**
   * Handles a single command input.
   *
   * @param input The command input string.
   * @throws IOException If there is an error processing the command.
   */
  private void handleCommand(String input) throws IOException {
    String[] parts = input.split("\\s+");

    if (parts[0].equals("ls")) {
      handleListCommand(parts);
    } else if (parts[0].equals("add")) {
      handleAddCommand(parts);
    } else if (IP_PATTERN.matcher(parts[0]).matches()) {
      handleIpQuery(parts[0]);
    } else if (parts[0].contains(".")) {
      handleNameQuery(parts[0]);
    } else {
      System.out.println("Commande inconnue : " + parts[0]);
    }
  }

  /**
   * Handles the 'ls' command to list DNS entries.
   *
   * @param parts The command parts split by whitespace.
   */
  private void handleListCommand(String[] parts) {
    boolean sortByIp = false;
    String domain;

    if (parts.length == 3 && parts[1].equals("-a")) {
      sortByIp = true;
      domain = parts[2];
    } else if (parts.length == 2) {
      domain = parts[1];
    } else {
      System.out.println("Usage : ls [-a] domaine");
      return;
    }

    List<DnsItem> items = dns.getItems(domain);

    if (sortByIp) {
      items.sort(Comparator.comparing(i -> i.getIp().getValue()));
    }

    for (DnsItem item : items) {
      System.out.println(item.getIp().getValue() + " " + item.getNom().getValue());
    }
  }

  /**
   * Handles the 'add' command to add a new DNS entry.
   *
   * @param parts The command parts split by whitespace.
   * @throws IOException If there is an error adding the entry.
   */
  private void handleAddCommand(String[] parts) throws IOException {
    if (parts.length != 3) {
      System.out.println("Usage : add adr.es.se.ip nom.qualifie.machine");
      return;
    }

    AdresseIp ip = new AdresseIp(parts[1]);
    NomMachine nom = new NomMachine(parts[2]);
    dns.addItem(ip, nom);
  }

  /**
   * Handles a query by machine name.
   *
   * @param name The machine name to query.
   */
  private void handleNameQuery(String name) {
    DnsItem item = dns.getItem(new NomMachine(name));
    if (item == null) {
      System.out.println("ERREUR : Nom de machine inconnu");
    } else {
      System.out.println(item.getIp().getValue());
    }
  }

  /**
   * Handles a query by IP address.
   *
   * @param ipStr The IP address to query.
   */
  private void handleIpQuery(String ipStr) {
    DnsItem item = dns.getItem(new AdresseIp(ipStr));
    if (item == null) {
      System.out.println("ERREUR : Adresse IP inconnue");
    } else {
      System.out.println(item.getNom().getValue());
    }
  }

  /**
   * Program entry point.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage : java DnsTui <fichier_dns>");
      return;
    }

    try {
      DnsTui tui = new DnsTui(args[0]);
      tui.run();
    } catch (IOException e) {
      System.out.println("Erreur de lecture du fichier : " + e.getMessage());
    }
  }
}
