package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.SalvoDisplay;
import cs3500.pa03.view.View;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for ActualPlayer class
 */
class ActualPlayerTest {
  Player actual;
  Player actual2;
  Player actual3;
  View display;
  StringBuilder output;
  StringReader input1;
  View display2;
  StringReader input2;

  HashMap<ShipType, Integer> map;
  Board own;
  Board opp;
  Board own1;
  Board opp1;
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
    own = new Board();
    own.initBoard(6, 6);
    opp = new Board();
    opp.initBoard(6, 6);
    own1 = new Board();
    own1.initBoard(6, 6);
    opp1 = new Board();
    opp1.initBoard(6, 6);
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

    input1 = new StringReader("""
        1 10
        11 -1
        12 12
        1 1
        1 0
        0 1
        2 2
        3 0
        1 0
        1 0
        1 0
        1 0
        2 2
        3 0
        4 5
        5 4
        4 5
        5 4
        3 3
        3 4
        """);
    input2 = new StringReader("""
        10 1
        1 1
        2 1
        3 4
        -1 1
        1 2
        2 3
        3 3
        1 -1
        1 0
        1 1
        0 1
        1 1
        2 2
        3 3
        4 4
        """);
    output = new StringBuilder();

    display = new SalvoDisplay(input1, output);
    display2 = new SalvoDisplay(input2, output);

    actual = new ActualPlayer("RealPlayer", own, opp, display, new Random(1));
    actual2 = new ActualPlayer("RealPlayer2", own, opp, display);
    actual3 = new ActualPlayer("RealPlayer3", own1, opp1, display2, new Random(1));
  }


  /**
   * Tests name() method
   */
  @Test
  void name() {
    assertEquals("RealPlayer", actual.name());
    assertEquals("RealPlayer2", actual2.name());
  }

  /**
   * Tests setup() method
   */
  @Test
  void setup() {
    assertEquals(fleet1, actual.setup(6, 6, map));
  }

  /**
   * Tests reportDamage() method
   */
  @Test
  void reportDamage() {
    actual.setup(6, 6, map);
    List<Coord> shotsAgainst = new ArrayList<>(List.of(new Coord(0, 0), new Coord(1, 1),
        new Coord(1, 0), new Coord(2, 0)));
    assertEquals(new ArrayList<>(List.of(new Coord(1, 0), new Coord(2, 0))),
        actual.reportDamage(shotsAgainst));
  }

  /**
   * Tests successfulHits() method
   */
  @Test
  void successfulHits() {
    actual.setup(6, 6, map);
    actual.successfulHits(new ArrayList<>(List.of(new Coord(1, 2),
        new Coord(3, 2))));
    actual.takeShots();
    assertEquals("""
        ------------------------------------------------------
        Opponent Board Data:
           - - - - - -\s
           - - X - - -\s
           - - - - - -\s
           - - X - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           S - - - - -\s
           S - - - - -\s
           C C C C C C\s
           B B B B B -\s
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - - - - - -\s
           - - X - - -\s
           - - - - - -\s
           - - X - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           S - - - - -\s
           S - - - - -\s
           C C C C C C\s
           B B B B B -\s
        ------------------------------------------------------
        Invalid shots entered. Please enter 4 shots:
        ------------------------------------------------------
        """, output.toString());
  }

  /**
   * Tests takeShots() method
   */
  @Test
  void takeShots() {
    actual.setup(6, 6, map);
    assertEquals(new ArrayList<>(List.of(new Coord(1, 0), new Coord(0, 1),
            new Coord(2, 2), new Coord(3, 0))),
        actual.takeShots());
    assertEquals("""
        ------------------------------------------------------
        Opponent Board Data:
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           S - - - - -\s
           S - - - - -\s
           C C C C C C\s
           B B B B B -\s
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           S - - - - -\s
           S - - - - -\s
           C C C C C C\s
           B B B B B -\s
        ------------------------------------------------------
        Invalid shots entered. Please enter 4 shots:
        ------------------------------------------------------
        """, output.toString());
    output.delete(0, output.length());
    assertEquals(new ArrayList<>(List.of(new Coord(4, 5), new Coord(5, 4),
            new Coord(3, 3), new Coord(3, 4))),
        actual.takeShots());
    assertEquals("""
        ------------------------------------------------------
        Opponent Board Data:
           - O - - - -\s
           O - - - - -\s
           - - O - - -\s
           O - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           S - - - - -\s
           S - - - - -\s
           C C C C C C\s
           B B B B B -\s
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - O - - - -\s
           O - - - - -\s
           - - O - - -\s
           O - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           S - - - - -\s
           S - - - - -\s
           C C C C C C\s
           B B B B B -\s
        ------------------------------------------------------
        Invalid shots entered. Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - O - - - -\s
           O - - - - -\s
           - - O - - -\s
           O - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           S - - - - -\s
           S - - - - -\s
           C C C C C C\s
           B B B B B -\s
        ------------------------------------------------------
        Invalid shots entered. Please enter 4 shots:
        ------------------------------------------------------
        """, output.toString());
    actual3.setup(6, 6, map);
    assertEquals(new ArrayList<>(List.of(new Coord(1, 1), new Coord(2, 2),
        new Coord(3, 3), new Coord(4, 4))), actual3.takeShots());
  }

  /**
   * Tests endGame() method
   */
  @Test
  void endGame() {
    actual.takeShots();
    actual.endGame(GameResult.WIN, "Sunk all opponent's ships.");
    assertEquals("""
        ------------------------------------------------------
        Opponent Board Data:
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
        You sunk all the opponents ships! You WIN!
        """, output.toString());
    output.delete(0, output.length());
    actual.endGame(GameResult.LOSE, "Opponent sunk all your ships.");
    assertEquals("""
        ------------------------------------------------------
        Opponent Board Data:
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
        The opponent sunk all the your ships! You LOSE!
        """, output.toString());
    output.delete(0, output.length());
    actual.endGame(GameResult.DRAW, "All ships sunk!");
    assertEquals("""
        ------------------------------------------------------
        Opponent Board Data:
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
           - - - - - -\s
        You both sunk all of each other's ships! You DRAW!
        """, output.toString());
    output.delete(0, output.length());
  }
}