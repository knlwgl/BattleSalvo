package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for SalvoDisplay class
 */
class SalvoDisplayTest {
  View display;
  StringBuilder output;
  StringReader input1;
  StringReader input2;
  StringReader input3;
  char[][] opp1;
  char[][] own1;

  /**
   * Initializes data for tests
   */
  @BeforeEach
  void initData() {
    output = new StringBuilder();
    input1 = new StringReader("""
        5 6
        6 s
        6 6 6
        10 8
        """);
    input2 = new StringReader("""
        -1 10 12 1
        1 2 2 4
        1 2 s 1
        1 2 2 1 1
        1 1 1 1
        """);
    input3 = new StringReader("""
        1 1
        10 20
        1 1
        2 2 3
        1 s
        1 1
        2 2
        """);
    opp1 = new char[6][6];
    own1 = new char[6][6];
    for (char[] row : opp1) {
      Arrays.fill(row, '-');
    }
    for (char[] row : own1) {
      Arrays.fill(row, '-');
    }
    own1[0][0] = 'C';
    own1[0][1] = 'C';
    own1[0][2] = 'C';
    own1[0][3] = 'C';
    own1[0][4] = 'C';
    own1[0][5] = 'C';
    own1[1][1] = 'B';
    own1[1][2] = 'B';
    own1[1][3] = 'B';
    own1[1][4] = 'B';
    own1[1][5] = 'B';
    own1[2][3] = 'D';
    own1[3][3] = 'D';
    own1[4][3] = 'D';
    own1[5][3] = 'D';
    own1[3][5] = 'S';
    own1[4][5] = 'S';
    own1[5][5] = 'S';
  }

  /**
   * Tests requestBoardSize() method
   */
  @Test
  void requestBoardSize() {
    display = new SalvoDisplay(input1, output);
    assertEquals(new Coord(5, 6), display.requestBoardSize(false));
    assertEquals("""
                
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        """, output.toString());
    assertEquals(new Coord(10, 8), display.requestBoardSize(true));
    assertEquals("""
                        
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        """, output.toString());
  }

  /**
   * Tests requestFleetSize() method
   */
  @Test
  void requestFleetSize() {
    display = new SalvoDisplay(input2, output);
    assertEquals(new ArrayList<>(List.of(-1, 10, 12, 1)),
        display.requestFleetSize(4, 8, false));
    assertEquals("""
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 8.
        ------------------------------------------------------
        """, output.toString());
    assertEquals(new ArrayList<>(List.of(1, 2, 2, 4)),
        display.requestFleetSize(4, 8, true));
    assertEquals("""
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 8.
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 8.
        ------------------------------------------------------
        """, output.toString());
    assertEquals(new ArrayList<>(List.of(1, 1, 1, 1)),
        display.requestFleetSize(4, 8, true));
    assertEquals("""
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 8.
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 8.
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 8.
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 8.
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 8.
        ------------------------------------------------------
        """, output.toString());
  }

  /**
   * Tests requestCoords() method
   */
  @Test
  void requestCoords() {
    display = new SalvoDisplay(input3, output);
    assertEquals(new ArrayList<>(List.of(new Coord(1, 1), new Coord(10, 20))),
        display.requestCoords(2, false));
    assertEquals("""
        Please enter 2 shots:
        ------------------------------------------------------
        """, output.toString());
    assertEquals(new ArrayList<>(List.of(new Coord(1, 1), new Coord(2, 2))),
        display.requestCoords(2, true));
    assertEquals("""
        Please enter 2 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Invalid shots entered. Please enter 2 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Invalid shots entered. Please enter 2 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Invalid shots entered. Please enter 2 shots:
        ------------------------------------------------------
        """, output.toString());
  }

  /**
   * Tests displayBoards() method
   */
  @Test
  void displayBoards() {
    display = new SalvoDisplay(input1, output);
    display.displayBoards(opp1, own1);
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
           C C C C C C\s
           - B B B B B\s
           - - - D - -\s
           - - - D - S\s
           - - - D - S\s
           - - - D - S\s
           """, output.toString());
  }

  /**
   * Tests displayResult() method
   */
  @Test
  void displayResult() {
    display = new SalvoDisplay(input1, output);
    display.displayResult(GameResult.WIN);
    assertEquals("""
        You sunk all the opponents ships! You WIN!
        """, output.toString());
    output.delete(0, output.length());
    display.displayResult(GameResult.LOSE);
    assertEquals("""
        The opponent sunk all the your ships! You LOSE!
        """, output.toString());
    output.delete(0, output.length());
    display.displayResult(GameResult.DRAW);
    assertEquals("""
        You both sunk all of each other's ships! You DRAW!
        """, output.toString());
  }
}