package fr.uvsq.cprog.collex.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Tests unitaires pour la classe AdresseIP. */
public class AdresseIPTest {

  @Test
  public void testAdresseIPValide() {
    AdresseIP ip = new AdresseIP("192.168.1.1");
    assertEquals("192.168.1.1", ip.getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdresseIPInvalide() {
    new AdresseIP("999.999.999.999");
  }
}
