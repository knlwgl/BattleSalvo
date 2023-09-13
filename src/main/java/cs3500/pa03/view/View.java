package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import java.util.List;

/**
 * Represents the View component of M-V-C.
 */
public interface View {

  /**
   * Requests the user for two integers representing height and width
   *
   * @param repeated is this query being repeated
   * @return coord representing the bottom-right most cell of desired board
   */
  Coord requestBoardSize(boolean repeated);

  /**
   * Requests a list of integers of desired length from the user
   *
   * @param size     how many types of ships
   * @param max      maximum total number of ships
   * @param repeated is this query being repeated
   * @return list of integers representing number of each
   */
  List<Integer> requestFleetSize(int size, int max, boolean repeated);

  /**
   * Requests the user for valid coordinate locations of given size
   *
   * @param size     how many coordinates are desired
   * @param repeated is this query being repeated
   * @return a list of coordinates of given size
   */
  List<Coord> requestCoords(int size, boolean repeated);

  /**
   * Displays the given boards to the user
   *
   * @param opp opponent board
   * @param own own board
   */
  void displayBoards(char[][] opp, char[][] own);

  /**
   * Displays the ending message based on the given result
   *
   * @param result of the game
   */
  void displayResult(GameResult result);
}
