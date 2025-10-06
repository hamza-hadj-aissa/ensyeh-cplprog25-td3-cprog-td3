package fr.uvsq.cprog.collex.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Tests unitaires pour la classe AdresseIp. */
public class AdresseIpTest {

  @Test
  public void testAdresseIpValide() {
    AdresseIp ip = new AdresseIp("192.168.1.1");
    assertEquals("192.168.1.1", ip.getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdresseIpInvalide() {
    new AdresseIp("999.999.999.999");
  }
}
