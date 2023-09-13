package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Coord class
 */
class CoordTest {
  Coord c1;
  Coord c2;
  Coord c3;
  Coord c4;

  /**
   * Initializes data for tests
   */
  @BeforeEach
  void initData() {
    c1 = new Coord(1, 2);
    c2 = new Coord(2, 2);
    c3 = new Coord(13, 14);
    c4 = new Coord(1, 1);
  }

  /**
   * Tests overriden equals() method
   */
  @Test
  void testEquals() {
    assertTrue(c1.equals(new Coord(1, 2)));
    assertFalse(c1.equals(c2));
    assertFalse(c1.equals(c3));
    assertFalse(c1.equals(c4));
    assertFalse(c1.equals(TileOccupant.MISS));
  }

  /**
   * Tests overriden hashCode() method
   */
  @Test
  void testHashCode() {
    assertEquals(1002, c1.hashCode());
    assertEquals(2002, c2.hashCode());
    assertEquals(13014, c3.hashCode());
    assertEquals(1001, c4.hashCode());
  }
}