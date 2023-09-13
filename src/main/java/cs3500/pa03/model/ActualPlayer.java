package cs3500.pa03.model;

import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an actual console player
 */
public class ActualPlayer extends AbstractPlayer {
  View ui;


  /**
   * Seedable (to control randomness) constructor for an Actual Player.
   *
   * @param str the name of the player
   * @param opp the opponents board information
   * @param self the players board information
   * @param display the place to display the game
   * @param rand the random number generator to use
   */
  public ActualPlayer(String str, Board opp, Board self, View display, Random rand) {
    super(str, opp, self, rand);
    ui = display;
  }

  /**
   * Constructor for an Actual Player.
   *
   * @param str the name of the player
   * @param opp the opponents board information
   * @param self the players board information
   * @param display the place to display the game
   */
  public ActualPlayer(String str, Board opp, Board self, View display) {
    this(str, opp, self, display, new Random());
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> salvo = new ArrayList<>();
    int howMany = own.howManyShots();
    if (howMany == 0) {
      return salvo;
    }
    ui.displayBoards(opponent.charBoard(), own.charBoard());
    salvo.addAll(ui.requestCoords(howMany, false));
    while (!validSalvo(salvo)) {
      salvo.clear();
      ui.displayBoards(opponent.charBoard(), own.charBoard());
      salvo.addAll(ui.requestCoords(howMany, true));
    }
    opponent.addSalvo(salvo);
    return salvo;
  }

  /**
   * Checks if the given salvo is valid (in context of the game data);
   * no repeated shots, no duplicates, and no shots not on the board.
   *
   * @param salvo the salvo to be checked
   * @return if the salvo is valid
   */
  private boolean validSalvo(List<Coord> salvo) {
    int rows = own.getBoard().length;
    int cols = own.getBoard()[0].length;
    for (int i = 0; i < salvo.size(); i++) {
      Coord c = salvo.get(i);
      // no duplicates in list
      for (int j = i + 1; j < salvo.size(); j++) {
        if (c.equals(salvo.get(j))) {
          return false;
        }
      }
      // not off the board
      if ((c.row < 0 || c.col < 0) || (c.row >= rows || c.col >= cols)) {
        return false;
      }
      // haven't shot already
      if (opponent.alreadyLaunched(c)) {
        return false;
      }
    }
    return true;
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
    ui.displayBoards(opponent.charBoard(), own.charBoard());
    ui.displayResult(result);
  }
}
