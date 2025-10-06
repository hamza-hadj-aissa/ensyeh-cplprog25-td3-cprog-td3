package fr.uvsq.cprog.collex.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DnsTest {

  private Path tempFile;
  private Dns dns;

  @Before
  public void setUp() throws IOException {
    // Créer un fichier temporaire avec des entrées DNS
    tempFile = Files.createTempFile("dns_test", ".txt");
    List<String> lines =
        List.of(
            "www.uvsq.fr 193.51.31.90",
            "poste.uvsq.fr 193.51.31.154",
            "ecampus.uvsq.fr 193.51.25.12");
    Files.write(tempFile, lines);

    dns = new Dns(tempFile.toString());
  }

  @After
  public void tearDown() throws IOException {
    Files.deleteIfExists(tempFile);
  }

  @Test
  public void testGetItemByIP() {
    DnsItem item = dns.getItem(new AdresseIP("193.51.31.90"));
    assertNotNull(item);
    assertEquals("www.uvsq.fr", item.getNom().getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetItemByInvalidIP() {
    dns.getItem(new AdresseIP("999.999.999.999"));
  }

  @Test
  public void testGetItemByName() {
    DnsItem item = dns.getItem(new NomMachine("poste.uvsq.fr"));
    assertNotNull(item);
    assertEquals("193.51.31.154", item.getIp().getValue());
  }

  @Test
  public void testGetItemByInvalidName() {
    DnsItem item = dns.getItem(new NomMachine("nonexistent.uvsq.fr"));
    assertNull(item);
  }

  @Test
  public void testGetItemsByDomain() {
    List<DnsItem> items = dns.getItems("uvsq.fr");
    assertEquals(3, items.size());
    // Vérifier que les noms sont triés par ordre alphabétique
    assertEquals("ecampus.uvsq.fr", items.get(0).getNom().getValue());
  }

  @Test
  public void testAddNewItem() throws IOException {
    AdresseIP ip = new AdresseIP("193.51.25.24");
    NomMachine nom = new NomMachine("welcome.uvsq.fr");
    dns.addItem(ip, nom);

    DnsItem added = dns.getItem(ip);
    assertNotNull(added);
    assertEquals("welcome.uvsq.fr", added.getNom().getValue());

    // Verify it was written to file
    List<String> lines = Files.readAllLines(tempFile);
    assertTrue(lines.stream().anyMatch(l -> l.contains("welcome.uvsq.fr")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDuplicateIPThrows() throws IOException {
    dns.addItem(new AdresseIP("193.51.31.90"), new NomMachine("newhost.uvsq.fr"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDuplicateNameThrows() throws IOException {
    dns.addItem(new AdresseIP("193.51.99.99"), new NomMachine("www.uvsq.fr"));
  }
}
