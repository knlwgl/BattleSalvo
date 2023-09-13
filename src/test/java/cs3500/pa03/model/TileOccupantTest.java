package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for TileOccupant class
 */
class TileOccupantTest {

  /**
   * Tests represent() method
   */
  @Test
  void represent() {
    assertEquals('X', TileOccupant.represent(TileOccupant.HIT));
    assertEquals('O', TileOccupant.represent(TileOccupant.MISS));
    assertEquals('-', TileOccupant.represent(TileOccupant.EMPTY));
    assertEquals('C', TileOccupant.represent(TileOccupant.CARRIER));
    assertEquals('B', TileOccupant.represent(TileOccupant.BATTLESHIP));
    assertEquals('D', TileOccupant.represent(TileOccupant.DESTROYER));
    assertEquals('S', TileOccupant.represent(TileOccupant.SUBMARINE));
  }
}