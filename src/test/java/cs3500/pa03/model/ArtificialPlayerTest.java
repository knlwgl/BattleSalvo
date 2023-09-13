package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for ArtificialPlayer class
 */
class ArtificialPlayerTest {
  Player artificial;
  Player artificial2;

  HashMap<ShipType, Integer> map;
  Board own;
  Board opp;
  List<Coord> salvo1;
  List<Coord> hits1;
  Ship carrier;
  Ship battle;
  Ship destroy;
  Ship sub;
  List<Ship> fleet1;


  /**
   * Initializes data for tests
   */
  @BeforeEach
  void initData() {
    own = new Board(6, 6);
    opp = new Board(6, 6);
    salvo1 = new ArrayList<>(Arrays.asList(new Coord(1, 2), new Coord(0, 0)));
    hits1 = new ArrayList<>();
    hits1.add(new Coord(0, 0));

    map = new HashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    carrier = new Ship(ShipType.CARRIER, new ArrayList<>(Arrays.asList(
        new Coord(4, 0), new Coord(4, 1), new Coord(4, 2), new Coord(4, 3),
        new Coord(4, 4), new Coord(4, 5))));
    battle = new Ship(ShipType.BATTLESHIP, new ArrayList<>(Arrays.asList(
        new Coord(5, 0), new Coord(5, 1), new Coord(5, 2), new Coord(5, 3),
        new Coord(5, 4))));
    destroy = new Ship(ShipType.DESTROYER, new ArrayList<>(Arrays.asList(new Coord(1, 2),
        new Coord(1, 3), new Coord(1, 4), new Coord(1, 5))));
    sub = new Ship(ShipType.SUBMARINE, new ArrayList<>(Arrays.asList(
        new Coord(1, 0), new Coord(2, 0), new Coord(3, 0))));
    fleet1 = new ArrayList<>(Arrays.asList(carrier, battle, destroy, sub));

    artificial = new ArtificialPlayer("FakePlayer", own, opp, new Random(1));
    artificial2 = new ArtificialPlayer("FakePlayer2", own, opp);
  }

  /**
   * Tests name() method
   */
  @Test
  void name() {
    assertEquals("FakePlayer", artificial.name());
    assertEquals("FakePlayer2", artificial2.name());
  }

  /**
   * Tests setup() method
   */
  @Test
  void setup() {
    assertEquals(fleet1, artificial.setup(6, 6, map));
  }

  /**
   * Tests reportDamage() method
   */
  @Test
  void reportDamage() {
    artificial.setup(6, 6, map);
    List<Coord> shotsAgainst = new ArrayList<>(List.of(new Coord(0, 0), new Coord(1, 1),
        new Coord(1, 0), new Coord(2, 0)));
    assertEquals(new ArrayList<>(List.of(new Coord(1, 0), new Coord(2, 0))),
        artificial.reportDamage(shotsAgainst));
  }

  /**
   * Tests successfulHits() method
   */
  @Test
  void successfulHits() {
    artificial.setup(6, 6, map);
    artificial.successfulHits(new ArrayList<>(List.of(new Coord(1, 2),
        new Coord(3, 2))));
    // prioritizes adjacent cells based on successful hits
    assertEquals(new ArrayList<>(List.of(new Coord(0, 2), new Coord(2, 2),
        new Coord(1, 1), new Coord(1, 3))), artificial.takeShots());
  }

  /**
   * Tests takeShots() method
   */
  @Test
  void takeShots() {
    artificial.setup(6, 6, map);
    assertEquals(new ArrayList<>(List.of(new Coord(5, 4), new Coord(2, 4),
        new Coord(2, 0), new Coord(2, 2))), artificial.takeShots());
    artificial.takeShots();
    artificial.takeShots();
    artificial.takeShots();
    artificial.takeShots();
    artificial.takeShots();
    artificial.takeShots();
    artificial.takeShots();
    assertEquals(new ArrayList<>(List.of(new Coord(0, 1), new Coord(4, 4),
        new Coord(5, 0), new Coord(4, 3))), artificial.takeShots());
  }

  /**
   * Tests endGame() method
   */
  @Test
  void endGame() {
    // doesn't do anything
    artificial.endGame(GameResult.LOSE, "All ships sunk.");
  }
}