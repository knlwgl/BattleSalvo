package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Ship class
 */
class ShipTest {
  Ship s1;
  Ship s2;
  Ship s3;

  /**
   * Initializes data for tests
   */
  @BeforeEach
  void initData() {
    s1 = new Ship(ShipType.BATTLESHIP, new ArrayList<>(Arrays.asList(
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3), new Coord(0, 4),
        new Coord(0, 5))));
    s2 = new Ship(ShipType.SUBMARINE, new ArrayList<>(Arrays.asList(
        new Coord(1, 2), new Coord(2, 2), new Coord(3, 2))));
    s3 = new Ship(ShipType.CARRIER, new ArrayList<>(Arrays.asList(new Coord(2, 3),
        new Coord(3, 3))));
    s3.takeHit(new Coord(2, 3));
    s3.takeHit(new Coord(3, 3));
  }

  /**
   * Tests isSunk() method
   */
  @Test
  void isSunk() {
    assertTrue(s3.isSunk());
    assertFalse(s1.isSunk());
    assertFalse(s2.isSunk());
    s2.takeHit(new Coord(1, 2));
    s2.takeHit(new Coord(2, 2));
    s2.takeHit(new Coord(3, 2));
    assertTrue(s2.isSunk());
  }

  /**
   * Tests takeHit() method
   */
  @Test
  void takeHit() {
    assertTrue(s2.takeHit(new Coord(1, 2)));
    assertFalse(s2.takeHit(new Coord(3, 5)));
    assertTrue(s2.takeHit(new Coord(2, 2)));
    assertFalse(s2.takeHit(new Coord(6, 8)));
    assertTrue(s2.takeHit(new Coord(3, 2)));
    assertTrue(s2.isSunk());
  }

  /**
   * Tests getPosn() method
   */
  @Test
  void getPosn() {
    assertEquals(new ArrayList<>(Arrays.asList(
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3), new Coord(0, 4),
        new Coord(0, 5))), s1.getPosn());
    assertEquals(new ArrayList<>(Arrays.asList(
        new Coord(1, 2), new Coord(2, 2), new Coord(3, 2))), s2.getPosn());
    assertEquals(new ArrayList<>(), s3.getPosn());
  }

  /**
   * Tests getType() method
   */
  @Test
  void getType() {
    assertEquals(ShipType.BATTLESHIP, s1.getType());
    assertEquals(ShipType.SUBMARINE, s2.getType());
    assertEquals(ShipType.CARRIER, s3.getType());
  }

  /**
   * Tests overriden equals() method
   */
  @Test
  void testEquals() {
    assertTrue(s1.equals(new Ship(ShipType.BATTLESHIP, new ArrayList<>(Arrays.asList(
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3), new Coord(0, 4),
        new Coord(0, 5))))));
    assertFalse(s1.equals(new Ship(ShipType.CARRIER, new ArrayList<>(Arrays.asList(
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3), new Coord(0, 4),
        new Coord(0, 5))))));
    assertFalse(s1.equals(new Ship(ShipType.BATTLESHIP, new ArrayList<>(Arrays.asList(
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3))))));
    assertFalse(s1.equals(s2));
    assertFalse(s2.equals(s3));
    assertFalse(s1.equals(new Coord(1, 1)));
  }

  /**
   * Tests overriden hashCode() method
   */
  @Test
  void testHashCode() {
    assertEquals(5432101, s1.hashCode());
    assertEquals(32122203, s2.hashCode());
    assertEquals(0, s3.hashCode());
  }
}