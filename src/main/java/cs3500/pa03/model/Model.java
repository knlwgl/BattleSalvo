package cs3500.pa03.model;

import java.util.List;

/**
 * Represents the model component of the M-V-C
 */
public interface Model {

  /**
   * Checks if the given board sizes are valid.
   *
   * @param h the desired board height
   * @param w the desired board width
   * @return if the dimensions are valid
   */
  boolean validSize(int h, int w);

  /**
   * Checks if the given fleet distribution is valid:
   * at least one of each ship, add up to
   *
   * @param fleet the desired distribution of ships
   * @return if the fleet distribution is valid
   */
  boolean validFleet(List<Integer> fleet);

  /**
   * Adds the players boards
   *
   * @param p1 player one's board
   * @param p2 player two's board
   */
  void addBoards(Board p1, Board p2);

  /**
   * Is the game over?
   *
   * @return if the game is over
   */
  boolean isGameOver();

  /**
   * Returns the result of player one
   *
   * @return the result of player one (from which player 2 can be inferred)
   */
  GameResult getResult();
}
