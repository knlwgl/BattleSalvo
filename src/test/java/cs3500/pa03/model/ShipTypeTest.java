package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for ShipType class
 */
class ShipTypeTest {
  @Test
  void getLength() {
    assertEquals(6, ShipType.CARRIER.getLength());
    assertEquals(5, ShipType.BATTLESHIP.getLength());
    assertEquals(4, ShipType.DESTROYER.getLength());
    assertEquals(3, ShipType.SUBMARINE.getLength());
  }

  /**
   * Tests represent() method
   */
  @Test
  void represent() {
    assertEquals(TileOccupant.CARRIER, ShipType.represent(ShipType.CARRIER));
    assertEquals(TileOccupant.BATTLESHIP, ShipType.represent(ShipType.BATTLESHIP));
    assertEquals(TileOccupant.DESTROYER, ShipType.represent(ShipType.DESTROYER));
    assertEquals(TileOccupant.SUBMARINE, ShipType.represent(ShipType.SUBMARINE));
  }
}