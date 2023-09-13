package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Board class
 */
class BoardTest {
  Board b1;
  TileOccupant[][] board1;
  char[][] expected;
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
    b1 = new Board(6, 6);
    salvo1 = new ArrayList<>(Arrays.asList(new Coord(1, 2), new Coord(0, 0)));
    hits1 = new ArrayList<>();
    hits1.add(new Coord(0, 0));

    carrier = new Ship(ShipType.CARRIER, new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3),
        new Coord(0, 4), new Coord(0, 5))));
    battle = new Ship(ShipType.BATTLESHIP, new ArrayList<>(Arrays.asList(new Coord(1, 1),
        new Coord(1, 2), new Coord(1, 3), new Coord(1, 4),
        new Coord(1, 5))));
    destroy = new Ship(ShipType.DESTROYER, new ArrayList<>(Arrays.asList(new Coord(2, 3),
        new Coord(3, 3), new Coord(4, 3), new Coord(5, 3))));
    sub = new Ship(ShipType.SUBMARINE, new ArrayList<>(Arrays.asList(
        new Coord(3, 5), new Coord(4, 5), new Coord(5, 5))));
    fleet1 = new ArrayList<>(Arrays.asList(carrier, battle, destroy, sub));

    board1 = new TileOccupant[6][6];
    for (TileOccupant[] row : board1) {
      Arrays.fill(row, TileOccupant.EMPTY);
    }

    expected = new char[6][6];
    for (char[] row : expected) {
      Arrays.fill(row, '-');
    }
  }

  /**
   * Tests getBoard() method
   */
  @Test
  void getBoard() {
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertSame(board1[i][j], b1.getBoard()[i][j]);
      }
    }
  }

  /**
   * Tests charBoard() method
   */
  @Test
  void charBoard() {
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertSame(expected[i][j], b1.charBoard()[i][j]);
      }
    }
  }

  /**
   * Tests getFleet() method
   */
  @Test
  void getFleet() {
    for (Ship s : fleet1) {
      b1.placeShip(s);
    }
    assertEquals(fleet1, b1.getFleet());
  }

  /**
   * Tests placeShips() method
   */
  @Test
  void placeShips() {
    for (Ship s : fleet1) {
      b1.placeShip(s);
    }
    board1[0][0] = TileOccupant.CARRIER;
    board1[0][1] = TileOccupant.CARRIER;
    board1[0][2] = TileOccupant.CARRIER;
    board1[0][3] = TileOccupant.CARRIER;
    board1[0][4] = TileOccupant.CARRIER;
    board1[0][5] = TileOccupant.CARRIER;
    board1[1][1] = TileOccupant.BATTLESHIP;
    board1[1][2] = TileOccupant.BATTLESHIP;
    board1[1][3] = TileOccupant.BATTLESHIP;
    board1[1][4] = TileOccupant.BATTLESHIP;
    board1[1][5] = TileOccupant.BATTLESHIP;
    board1[2][3] = TileOccupant.DESTROYER;
    board1[3][3] = TileOccupant.DESTROYER;
    board1[4][3] = TileOccupant.DESTROYER;
    board1[5][3] = TileOccupant.DESTROYER;
    board1[3][5] = TileOccupant.SUBMARINE;
    board1[4][5] = TileOccupant.SUBMARINE;
    board1[5][5] = TileOccupant.SUBMARINE;
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertSame(board1[i][j], b1.getBoard()[i][j]);
      }
    }
    assertEquals(fleet1, b1.getFleet());
  }

  /**
   * Tests addSalvo() method
   */
  @Test
  void addSalvo() {
    b1.addSalvo(salvo1);
    expected[1][2] = 'O';
    expected[0][0] = 'O';
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertSame(expected[i][j], b1.charBoard()[i][j]);
      }
    }
    board1[1][2] = TileOccupant.MISS;
    board1[0][0] = TileOccupant.MISS;
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertSame(board1[i][j], b1.getBoard()[i][j]);
      }
    }
  }

  /**
   * Tests addHits() method
   */
  @Test
  void addHits() {
    b1.addHits(hits1);
    expected[0][0] = 'X';
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertSame(expected[i][j], b1.charBoard()[i][j]);
      }
    }
    board1[0][0] = TileOccupant.HIT;
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertSame(board1[i][j], b1.getBoard()[i][j]);
      }
    }
  }

  /**
   * Tests howManyShots() method
   */
  @Test
  void howManyShots() {
    b1.placeShip(fleet1.get(0));
    assertEquals(1, b1.howManyShots());
    b1.placeShip(fleet1.get(1));
    assertEquals(2, b1.howManyShots());
    b1.placeShip(fleet1.get(2));
    assertEquals(3, b1.howManyShots());
    b1.placeShip(fleet1.get(3));
    assertEquals(4, b1.howManyShots());
    sub.takeHit(new Coord(3, 5));
    sub.takeHit(new Coord(4, 5));
    sub.takeHit(new Coord(5, 5));
    assertEquals(3, b1.howManyShots());
  }

  /**
   * Tests alreadyLaunched() method
   */
  @Test
  void alreadyLaunched() {
    assertFalse(b1.alreadyLaunched(new Coord(0, 0)));
    assertFalse(b1.alreadyLaunched(new Coord(1, 2)));
    b1.addSalvo(salvo1);
    assertTrue(b1.alreadyLaunched(new Coord(0, 0)));
    assertTrue(b1.alreadyLaunched(new Coord(1, 2)));
  }
}