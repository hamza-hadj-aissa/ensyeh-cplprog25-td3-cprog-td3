package fr.uvsq.cprog.collex.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DnsItemTest {

  @Test
  public void testDnsItem() {
    AdresseIp ip = new AdresseIp("192.168.1.1");
    NomMachine nm = new NomMachine("serveur.local");
    DnsItem item = new DnsItem(ip, nm);
    assertEquals(ip, item.getIp());
    assertEquals(nm, item.getNom());
  }
}
