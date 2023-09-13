package cs3500.pa03.model;

import java.util.List;
import java.util.Objects;

/**
 * Represents the Model for a BattleSalvo application
 */
public class BattleIntel implements Model {
  int upperBound;
  int lowerBound;
  int row;
  int col;
  Board player1;
  Board player2;

  /**
   * Constructor for BattleIntel
   *
   * @param upper the upper bound for board dimensions
   * @param lower the lower bound for board dimensions
   */
  public BattleIntel(int upper, int lower) {
    upperBound = upper;
    lowerBound = lower;
    row = 0;
    col = 0;
  }

  /**
   * Checks if the given board sizes are valid.
   *
   * @param h the desired board height
   * @param w the desired board width
   * @return if the dimensions are valid
   */
  @Override
  public boolean validSize(int h, int w) {
    if ((h >= lowerBound && h <= upperBound) && (w >= lowerBound && w <= upperBound)) {
      row = h;
      col = w;
      return true;
    }
    return false;
  }

  /**
   * Checks if the given fleet distribution is valid:
   * at least one of each ship, add up to
   *
   * @param fleet the desired distribution of ships
   * @return if the fleet distribution is valid
   */
  @Override
  public boolean validFleet(List<Integer> fleet) {
    if (row == 0 || col == 0) {
      throw new IllegalStateException("validSize must be called first.");
    }
    int sum = 0;
    if (fleet.size() > 4) {
      return false;
    }
    for (int i : fleet) {
      if (i <= 0) {
        return false;
      }
      sum += i;
    }
    return sum <= Math.min(row, col);
  }

  /**
   * Adds the players boards
   *
   * @param p1 player one's board
   * @param p2 player two's board
   */
  @Override
  public void addBoards(Board p1, Board p2) {
    player1 = p1;
    player2 = p2;
  }

  /**
   * Is the game over?
   *
   * @return if the game is over
   */
  @Override
  public boolean isGameOver() {
    if (Objects.isNull(player1) || Objects.isNull(player2)) {
      throw new IllegalStateException("Boards haven't been initialized yet!");
    }
    int p1 = player1.howManyShots();
    int p2 = player2.howManyShots();
    return p1 == 0 || p2 == 0;
  }

  /**
   * Returns the result of player one
   *
   * @return the result of player one (from which player 2 can be inferred)
   */
  @Override
  public GameResult getResult() {
    if (!isGameOver()) {
      throw new IllegalStateException("GAME IS NOT OVER YET!");
    }
    int p1 = player1.howManyShots();
    int p2 = player2.howManyShots();
    if (p1 == 0) {
      if (p2 == 0) {
        return GameResult.DRAW;
      } else {
        return GameResult.LOSE;
      }
    } else {
      return GameResult.WIN;
    }
  }
}
