package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a Computer-controlled player
 */
public class ArtificialPlayer extends AbstractPlayer {
  List<Coord> prioritize;

  /**
   * Constructor for an Artificial Player.
   *
   * @param str the name of the player
   * @param opp the opponents board information
   * @param self the players board information
   * @param rand the random number generator to use
   */
  public ArtificialPlayer(String str, Board opp, Board self, Random rand) {
    super(str, opp, self, rand);
    prioritize = new ArrayList<>();
  }

  /**
   * Constructor for an Artificial Player.
   *
   * @param str the name of the player
   * @param opp the opponents board information
   * @param self the players board information
   */
  public ArtificialPlayer(String str, Board opp, Board self) {
    this(str, opp, self, new Random());
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> list = new ArrayList<>();
    int howMany = own.howManyShots();
    while (!prioritize.isEmpty() && list.size() < howMany) {
      list.add(prioritize.remove(0));
    }
    while (!opponent.getNotTaken().isEmpty() && list.size() < howMany) {
      int random = rng.nextInt(opponent.getNotTaken().size());
      list.add(opponent.getNotTaken().remove(random));
    }
    return list;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    opponent.addHits(shotsThatHitOpponentShips);
    for (Coord c : shotsThatHitOpponentShips) {
      Coord above = new Coord(c.row - 1, c.col);
      prioritize(above);
      Coord below = new Coord(c.row + 1, c.col);
      prioritize(below);
      Coord left = new Coord(c.row, c.col - 1);
      prioritize(left);
      Coord right = new Coord(c.row, c.col + 1);
      prioritize(right);
    }
  }

  /**
   * Prioritizes the given coordinate to be shot next, if it is valid and hasn't already been shot
   *
   * @param c the coordinate to prioritize
   */
  private void prioritize(Coord c) {
    if (opponent.getNotTaken().contains(c)) {
      opponent.getNotTaken().remove(c);
      prioritize.add(c);
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    System.out.println(reason);
  }
}
