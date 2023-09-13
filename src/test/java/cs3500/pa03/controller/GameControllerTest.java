package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.BattleIntel;
import cs3500.pa03.model.Model;
import cs3500.pa03.view.SalvoDisplay;
import cs3500.pa03.view.View;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for GameController class
 */
class GameControllerTest {
  Controller ctrl;
  Controller ctrl2;
  View display;
  View display2;
  Model data;
  Model data2;
  StringBuilder output;
  StringReader input;
  StringReader input2;

  /**
   * Initializes data for tests
   */
  @BeforeEach
  void initData() {
    data = new BattleIntel(15, 6);
    data2 = new BattleIntel(15, 6);
    input = new StringReader("""
        5 5
        6 6
        1 3 4 1
        1 1 1 1
        0 0
        1 0
        2 0
        3 0
        4 0
        5 0
        0 1
        1 1
        2 1
        3 1
        4 1
        5 1
        0 2
        1 2
        2 2
        3 2
        4 2
        5 2
        0 3
        1 3
        2 3
        3 3
        4 3
        5 3
        0 4
        1 4
        2 4
        3 4
        5 4
        0 5
        1 5
        2 5
        3 5
        4 5
        5 5
        """);
    input2 = new StringReader("""
        6 6
        1 1 1 1
        0 1
        1 1
        2 1
        3 1
        4 1
        5 1
        5 0
        5 2
        5 3
        5 4
        5 5
        3 2
        3 3
        3 4
        3 5
        4 2
        4 3
        4 4
        4 5
        0 5
        1 5
        2 5
        2 4
        """);
    output = new StringBuilder();
    display = new SalvoDisplay(input, output);
    ctrl = new GameController(display, data, new Random(1));
    display2 = new SalvoDisplay(input2, output);
    ctrl2 = new GameController(display2, data2, new Random(1));

  }

  /**
   * Tests that run does not throw
   */
  @Test
  void runDoesNotThrow() {
    assertDoesNotThrow(() -> ctrl.run());
  }

  /**
   * Tests run w/ console player loss
   */
  @Test
  void run1() {
    ctrl.run();
    assertEquals("""
                
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
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
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O - - - - -\s
           O - - - - -\s
           O - - - - -\s
           O - - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           X - O - - -\s
           S - O - - -\s
           C C X C C C\s
           B B B B B -\s
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X - - - -\s
           O X - - - -\s
           O - - - - -\s
           O - - - - -\s
           O - - - - -\s
           X - - - - -\s
                
        Your Board:
           - - - - - -\s
           X - D D D D\s
           X - O - - -\s
           S - O - - -\s
           C X X X C C\s
           B B X B B -\s
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X - - - -\s
           O X - - - -\s
           O X - - - -\s
           O X - - - -\s
           O X - - - -\s
           X X - - - -\s
                
        Your Board:
           - - - - - -\s
           X - D D D D\s
           X O O - - -\s
           X - O - - -\s
           C X X X C C\s
           B X X X B -\s
        Please enter 3 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X O - - -\s
           O X O - - -\s
           O X O - - -\s
           O X - - - -\s
           O X - - - -\s
           X X - - - -\s
                
        Your Board:
           - - - - - -\s
           X - D D D D\s
           X O O - - -\s
           X O O O - -\s
           X X X X C C\s
           B X X X B -\s
        Please enter 3 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X O - - -\s
           O X O - - -\s
           O X O - - -\s
           O X X - - -\s
           O X X - - -\s
           X X X - - -\s
                
        Your Board:
           O - - - - -\s
           X O D D D D\s
           X O O - - -\s
           X O O O - -\s
           X X X X X C\s
           B X X X B -\s
        Please enter 3 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X O O - -\s
           O X O O - -\s
           O X O O - -\s
           O X X - - -\s
           O X X - - -\s
           X X X - - -\s
                
        Your Board:
           O - - - - -\s
           X O D D D D\s
           X O O - - -\s
           X O O O O -\s
           X X X X X C\s
           X X X X X -\s
        Please enter 2 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X O O - -\s
           O X O O - -\s
           O X O O - -\s
           O X X X - -\s
           O X X X - -\s
           X X X - - -\s
                
        Your Board:
           O - - O - -\s
           X O D D D D\s
           X O O - - -\s
           X O O O O -\s
           X X X X X X\s
           X X X X X O\s
        Please enter 1 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X O O - -\s
           O X O O - -\s
           O X O O - -\s
           O X X X - -\s
           O X X X - -\s
           X X X X - -\s
                
        Your Board:
           O - - O O -\s
           X O X D D D\s
           X O O - - -\s
           X O O O O O\s
           X X X X X X\s
           X X X X X O\s
        Please enter 1 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X O O O -\s
           O X O O - -\s
           O X O O - -\s
           O X X X - -\s
           O X X X - -\s
           X X X X - -\s
                
        Your Board:
           O - O O O -\s
           X O X X X D\s
           X O O - - -\s
           X O O O O O\s
           X X X X X X\s
           X X X X X O\s
        Please enter 1 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           O X O O O -\s
           O X O O O -\s
           O X O O - -\s
           O X X X - -\s
           O X X X - -\s
           X X X X - -\s
                
        Your Board:
           O - O O O -\s
           X O X X X X\s
           X O O O O -\s
           X O O O O O\s
           X X X X X X\s
           X X X X X O\s
        The opponent sunk all the your ships! You LOSE!
        """, output.toString());
  }

  /**
   * Tests run w/ console player win
   */
  @Test
  void run2() {
    ctrl2.run();
    assertEquals("""
                
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
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
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - X - - - -\s
           - X - - - -\s
           - X - - - -\s
           - X - - - -\s
           - - - - - -\s
           - - - - - -\s
                
        Your Board:
           - - - - - -\s
           S - D D D D\s
           X - O - - -\s
           S - O - - -\s
           C C X C C C\s
           B B B B B -\s
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - X - - - -\s
           - X - - - -\s
           - X - - - -\s
           - X - - - -\s
           - X - - - -\s
           X X X - - -\s
                
        Your Board:
           - - - - - -\s
           X - D D D D\s
           X - O - - -\s
           S - O - - -\s
           C X X X C C\s
           B B X B B -\s
        Please enter 4 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - X - - - -\s
           - X - - - -\s
           - X - - - -\s
           - X X - - -\s
           - X - - - -\s
           X X X X X X\s
                
        Your Board:
           - - - - - -\s
           X - D D D D\s
           X O O - - -\s
           X - O - - -\s
           C X X X C C\s
           B X X B B -\s
        Please enter 3 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - X - - - -\s
           - X - - - -\s
           - X - - - -\s
           - X X X X X\s
           - X - - - -\s
           X X X X X X\s
                
        Your Board:
           - - - - - -\s
           X - D D D D\s
           X O O - - -\s
           X O O - - -\s
           C X X X C C\s
           B X X X B -\s
        Please enter 3 shots:
        ------------------------------------------------------
        ------------------------------------------------------
        Opponent Board Data:
           - X - - - -\s
           - X - - - -\s
           - X - - - -\s
           - X X X X X\s
           - X X X X -\s
           X X X X X X\s
                
        Your Board:
           - - - - - -\s
           X - D D D D\s
           X O O - - -\s
           X O O - - -\s
           X X X X C C\s
           B X X X B -\s
        You sunk all the opponents ships! You WIN!
        """, output.toString());
  }
}