package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the display in a BattleSalvo game.
 */
public class SalvoDisplay implements View {
  Scanner input;
  Appendable output;

  /**
   * Constructor for a salvo display
   *
   * @param in the desired input location
   * @param out the desired output location
   */
  public SalvoDisplay(Readable in, Appendable out) {
    input = new Scanner(in);
    output = out;
  }

  /**
   * Requests the user for two integers representing height and width
   *
   * @param repeated is this query being repeated
   * @return array of int of size 2, representing height and width of a board
   */
  @Override
  public Coord requestBoardSize(boolean repeated) {
    String errorMsg = """
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        """;
    if (!repeated) {
      appender("""
                    
          Hello! Welcome to the OOD BattleSalvo Game!
          Please enter a valid height and width below:
          ------------------------------------------------------
          """);
    } else {
      appender(errorMsg);
    }
    boolean invalid = true;
    Coord dimensions = null;
    while (invalid) {
      try {
        int h = input.nextInt();
        int w = input.nextInt();
        tooManyArgs();
        dimensions = new Coord(h, w);
        invalid = false;
      } catch (InputMismatchException e) {
        input.nextLine();
        appender(errorMsg);
      } catch (IllegalArgumentException e) {
        appender(errorMsg);
      }
    }
    return dimensions;
  }

  /**
   * Requests a list of integers of desired length from the user
   *
   * @param size     how many types of ships
   * @param max      maximum total number of ships
   * @param repeated is this query being repeated
   * @return list of number for each ship type, sum of which is less than given max
   */
  @Override
  public List<Integer> requestFleetSize(int size, int max, boolean repeated) {
    String errorMsg = String.format("""
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size %s.
        ------------------------------------------------------
        """, max);
    if (!repeated) {
      appender(String.format("""
          ------------------------------------------------------
          Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
          Remember, your fleet may not exceed size %s.
          ------------------------------------------------------
          """, max));
    } else {
      appender(errorMsg);
    }
    boolean unreturned = true;
    List<Integer> nums = new ArrayList<>(size);
    while (unreturned) {
      try {
        for (int i = 0; i < size; i++) {
          nums.add(input.nextInt());
        }
        tooManyArgs();
        unreturned = false;
      } catch (InputMismatchException e) {
        input.nextLine();
        nums.clear();
        appender(errorMsg);
      } catch (IllegalArgumentException e) {
        nums.clear();
        appender(errorMsg);
      }
    }
    return nums;
  }


  /**
   * Requests the user for coordinate locations of given size
   *
   * @param size     how many coordinates are desired
   * @param repeated is this query being repeated
   * @return a list of coordinates of given size
   */
  @Override
  public List<Coord> requestCoords(int size, boolean repeated) {
    List<Integer> listOfInt = new ArrayList<>();
    List<Coord> list = new ArrayList<>();
    String errorMsg = String.format("""
        ------------------------------------------------------
        Invalid shots entered. Please enter %s shots:
        ------------------------------------------------------
        """, size);
    if (!repeated) {
      appender(String.format("""
          Please enter %s shots:
          ------------------------------------------------------
          """, size));
    } else {
      appender(errorMsg);
    }
    while (listOfInt.size() < 2 * size) {
      try {
        listOfInt.add(input.nextInt());
        if (listOfInt.size() == 2 * size) {
          tooManyArgs();
        }
      } catch (InputMismatchException e) {
        input.nextLine();
        listOfInt.clear();
        appender(errorMsg);
      } catch (IllegalArgumentException e) {
        listOfInt.clear();
        appender(errorMsg);
      }
    }
    for (int i = 0; i < listOfInt.size() - 1; i += 2) {
      list.add(new Coord(listOfInt.get(i), listOfInt.get(i + 1)));
    }
    return list;
  }

  /**
   * Checks if the user provided too many arguments.
   */
  private void tooManyArgs() {
    Scanner rest = new Scanner(input.nextLine()); // to check if more than 2 args provided
    if (rest.hasNext()) {
      throw new IllegalArgumentException("Too many arguments provided.");
    }
  }

  /**
   * Displays the given boards to the user
   *
   * @param opp opponent's board
   * @param own own board
   */
  @Override
  public void displayBoards(char[][] opp, char[][] own) {
    appender("""
        ------------------------------------------------------
        """);
    appender("Opponent Board Data:\n");
    showBoard(opp);
    appender("\n");
    appender("Your Board:\n");
    showBoard(own);
  }

  /**
   * Displays the given board
   *
   * @param board board to draw
   */
  private void showBoard(char[][] board) {
    for (char[] row : board) {
      appender("   ");
      for (char c : row) {
        appender(c + " ");
      }
      appender("\n");
    }
  }

  /**
   * Displays the ending message based on the given result
   *
   * @param result of the game
   */
  @Override
  public void displayResult(GameResult result) {
    if (result == GameResult.WIN) {
      appender("""
          You sunk all the opponents ships! You WIN!
          """);
    } else if (result == GameResult.LOSE) {
      appender("""
          The opponent sunk all the your ships! You LOSE!
          """);
    } else {
      appender("""
          You both sunk all of each other's ships! You DRAW!
          """);
    }
  }

  /**
   * Displays the given message
   *
   * @param str representing the msg
   */
  private void appender(String str) {
    try {
      output.append(str);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
