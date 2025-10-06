package fr.uvsq.cprog.collex.models;

import org.junit.Test;

public class NomMachineTest {

  @Test
  public void testNomMachineValide() {
    NomMachine nm = new NomMachine("www.uvsq.fr");
    assert nm.getMachine().equals("www");
    assert nm.getDomaine().equals("uvsq.fr");
  }

  @Test
  public void testNomMachineValideAvecSousDomaine() {
    NomMachine nm = new NomMachine("db1.internal.example.com");
    assert nm.getMachine().equals("db1");
    assert nm.getDomaine().equals("internal.example.com");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNomMachineInvalide() {
    new NomMachine("invalidName");
  }

  @Test
  public void testNomMachineAvecTiret() {
    NomMachine nm = new NomMachine("my-server.example.com");
    assert nm.getMachine().equals("my-server");
    assert nm.getDomaine().equals("example.com");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNomMachineCommenceParTiret() {
    new NomMachine("-invalid.example.com");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNomMachineTermineParPoint() {
    new NomMachine("invalid.");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNomMachineNull() {
    new NomMachine(null);
  }
}
