package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for BattleIntel class
 */
class BattleIntelTest {
  BattleIntel intel;
  BattleIntel intel2;

  Board b1;
  Board b2;
  Board empty;
  Board empty2;

  List<Coord> salvo1;
  List<Coord> hits1;
  Ship carrier;
  Ship battle;
  Ship destroy;
  Ship sub;
  List<Ship> fleet1;

  Ship s1;
  Ship s2;
  Ship s3;
  Ship s4;
  List<Ship> fleet2;

  Ship neo;

  /**
   * Initializes data for tests
   */
  @BeforeEach
  void initData() {
    intel = new BattleIntel(15, 6);
    intel2 = new BattleIntel(15, 0); // for testing only

    b1 = new Board(6, 6);
    b2 = new Board(6, 6);
    empty = new Board(6, 6);
    neo = new Ship(ShipType.SUBMARINE, new ArrayList<>(Arrays.asList(new Coord(2, 3),
        new Coord(3, 3))));
    neo.takeHit(new Coord(2, 3));
    neo.takeHit(new Coord(3, 3));
    empty.placeShip(neo);

    empty2 = new Board(6, 6);
    empty2.placeShip(neo);

    salvo1 = new ArrayList<>(Arrays.asList(new Coord(1, 2), new Coord(0, 0)));
    hits1 = new ArrayList<>();
    hits1.add(new Coord(0, 0));

    carrier = new Ship(ShipType.CARRIER, new ArrayList<>(Arrays.asList(
        new Coord(0, 0), new Coord(0, 1), new Coord(0, 2), new Coord(0, 3),
        new Coord(0, 4), new Coord(0, 5))));
    battle = new Ship(ShipType.BATTLESHIP, new ArrayList<>(Arrays.asList(
        new Coord(1, 1), new Coord(1, 2), new Coord(1, 3), new Coord(1, 4),
        new Coord(1, 5))));
    destroy = new Ship(ShipType.DESTROYER, new ArrayList<>(Arrays.asList(new Coord(2, 3),
        new Coord(3, 3), new Coord(4, 3), new Coord(5, 3))));
    sub = new Ship(ShipType.SUBMARINE, new ArrayList<>(Arrays.asList(
        new Coord(3, 5), new Coord(4, 5), new Coord(5, 5))));
    fleet1 = new ArrayList<>(Arrays.asList(carrier, battle, destroy, sub));

    for (Ship s : fleet1) {
      b1.placeShip(s);
    }

    s1 = new Ship(ShipType.BATTLESHIP, new ArrayList<>(List.of(
        new Coord(0, 1), new Coord(1, 1))));
    s1.takeHit(new Coord(1, 1));
    s2 = new Ship(ShipType.SUBMARINE, new ArrayList<>(List.of(
        new Coord(1, 2), new Coord(2, 2))));
    s2.takeHit(new Coord(2, 2));
    s3 = new Ship(ShipType.CARRIER, new ArrayList<>(Arrays.asList(new Coord(2, 3),
        new Coord(3, 3))));
    s3.takeHit(new Coord(2, 3));
    s3.takeHit(new Coord(3, 3));
    s4 = new Ship(ShipType.DESTROYER, new ArrayList<>(List.of(
        new Coord(4, 1), new Coord(4, 2))));
    fleet2 = new ArrayList<>(List.of(s1, s2, s3, s4));

    for (Ship s : fleet2) {
      b2.placeShip(s);
    }
  }

  /**
   * Tests validSize() method
   */
  @Test
  void validSize() {
    assertTrue(intel.validSize(6, 6));
    assertTrue(intel.validSize(8, 10));
    assertFalse(intel.validSize(5, 6));
    assertFalse(intel.validSize(6, 5));
    assertFalse(intel.validSize(6, 17));
    assertFalse(intel.validSize(16, 5));
  }

  /**
   * Tests validFleet() method
   */
  @Test
  void validFleet() {
    intel2.validSize(0, 1);
    assertThrows(IllegalStateException.class,
        () -> intel2.validFleet(new ArrayList<>(Arrays.asList(1, 2, 3, 1))));
    intel2.validSize(1, 0);
    assertThrows(IllegalStateException.class,
        () -> intel2.validFleet(new ArrayList<>(Arrays.asList(1, 2, 3, 1))));
    assertThrows(IllegalStateException.class,
        () -> intel.validFleet(new ArrayList<>(Arrays.asList(1, 2, 3, 1))));
    intel.validSize(6, 10);
    assertTrue(intel.validFleet(new ArrayList<>(Arrays.asList(1, 1, 3, 1))));
    assertTrue(intel.validFleet(new ArrayList<>(Arrays.asList(2, 1, 1, 2))));
    assertFalse(intel.validFleet(new ArrayList<>(Arrays.asList(1, 1, 3, 10))));
    assertFalse(intel.validFleet(new ArrayList<>(Arrays.asList(0, 10, 1, 2))));
    assertFalse(intel.validFleet(new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1))));
  }

  /**
   * Tests addBoards() method
   */
  @Test
  void addBoards() {
    intel.validSize(6, 6);
    intel.validFleet(new ArrayList<>(List.of(1, 1, 1, 1)));
    intel.addBoards(b1, b2);
    // test that boards were added by mutating board and seeing if isGameOver changes
    assertFalse(intel.isGameOver());
    s1.takeHit(new Coord(0, 1));
    s2.takeHit(new Coord(1, 2));
    s4.takeHit(new Coord(4, 1));
    s4.takeHit(new Coord(4, 2));
    assertTrue(intel.isGameOver());
  }

  /**
   * Tests isGameOver() method
   */
  @Test
  void isGameOver() {
    intel.validSize(6, 6);
    intel.validFleet(new ArrayList<>(List.of(1, 1, 1, 1)));
    // illegal state
    assertThrows(IllegalStateException.class, () -> intel.isGameOver());
    intel.addBoards(null, b1);
    assertThrows(IllegalStateException.class, () -> intel.isGameOver());
    intel.addBoards(b1, null);
    assertThrows(IllegalStateException.class, () -> intel.isGameOver());
    // not over
    intel.addBoards(b2, b1);
    assertFalse(intel.isGameOver());
    // p2 wins
    intel.addBoards(empty, b2);
    assertTrue(intel.isGameOver());
    // p1 wins
    intel.addBoards(b1, empty);
    assertTrue(intel.isGameOver());
    // draw
    intel.addBoards(empty, empty2);
    assertTrue(intel.isGameOver());
  }

  /**
   * Tests getResult() method
   */
  @Test
  void getResult() {
    // p1 wins
    intel.addBoards(b1, empty);
    assertSame(GameResult.WIN, intel.getResult());
    // p1 loses
    intel.addBoards(empty, b2);
    assertSame(GameResult.LOSE, intel.getResult());
    // draw
    intel.addBoards(empty, empty2);
    assertSame(GameResult.DRAW, intel.getResult());
    // game not over
    intel.addBoards(b2, b1);
    assertThrows(IllegalStateException.class,
        () -> intel.getResult());
  }
}