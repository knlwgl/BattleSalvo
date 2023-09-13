package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an abstract player with common methods
 */
public abstract class AbstractPlayer implements Player {
  protected String name;
  protected Board opponent;
  protected Board own;
  protected Random rng;

  AbstractPlayer(String str, Board opp, Board self, Random rand) {
    name = str;
    opponent = opp;
    own = self;
    rng = rand;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    opponent.initBoard(height, width);
    own.initBoard(height, width);
    for (ShipType s : ShipType.values()) {
      for (int i = 0; i < specifications.get(s); i++) {
        boolean isHorizontal = rng.nextBoolean();
        List<Coord> posn = place(isHorizontal, height, width, s);
        Ship curr = new Ship(s, posn);
        own.placeShip(curr);
      }
    }
    return own.getFleet();
  }

  /**
   * Places a ship of the given type on the board
   *
   * @param isHorizontal should the ship be places horizontally? or vertically
   * @param height       the height of the board
   * @param width        the width of the board
   * @param ship         the type of ship to place
   * @return the location of the ship on the board
   */
  private List<Coord> place(boolean isHorizontal, int height, int width, ShipType ship) {
    List<Coord> posn = new ArrayList<>();
    TileOccupant[][] board = own.getBoard();
    int bound = isHorizontal ? (width - ship.getLength() + 1) : (height - ship.getLength() + 1);
    int otherDimension = isHorizontal ? height : width;
    // getting possible coords
    List<Coord> possibleCoords = possibleStarts(isHorizontal, bound, otherDimension);
    // randomly choosing
    Coord curr = possibleCoords.remove(rng.nextInt(possibleCoords.size()));
    int rowNum = curr.row;
    int colNum = curr.col;
    while (obstructed(isHorizontal, board, rowNum, colNum, ship.getLength())) {
      if (possibleCoords.isEmpty()) { // if can't be placed in this orientation, try other way
        return place(!isHorizontal, height, width, ship);
      }
      curr = possibleCoords.remove(rng.nextInt(possibleCoords.size()));
      rowNum = curr.row;
      colNum = curr.col;
    }
    // add coords and return
    int advanced = isHorizontal ? colNum : rowNum;
    for (int i = advanced; i < ship.getLength() + advanced; i++) {
      posn.add(isHorizontal ? new Coord(rowNum, i) : new Coord(i, colNum));
      if (isHorizontal) {
        board[rowNum][i] = ShipType.represent(ship);
      } else {
        board[i][colNum] = ShipType.represent(ship);
      }
    }
    return posn;
  }

  /**
   * All possible starting positions given a orientation and a restricted dimension
   *
   * @param isHorizontal   is the ship horizontal
   * @param boundDimension the dimension that is restricted by length of ship
   * @param otherDimension the non-restricted dimension
   * @return list of possible starting coords
   */
  private List<Coord> possibleStarts(boolean isHorizontal, int boundDimension, int otherDimension) {
    List<Coord> possibleCoords = new ArrayList<>();
    for (int i = 0; i < otherDimension; i++) {
      for (int j = 0; j < boundDimension; j++) {
        Coord curr = isHorizontal ? new Coord(i, j) : new Coord(j, i);
        possibleCoords.add(curr);
      }
    }
    return possibleCoords;
  }

  /**
   * Checks if placing a ship of given length at the given location will be obstructed
   *
   * @param isH    is the ship horizontal
   * @param board  the board the ship is trying to be placed on
   * @param row    the row of the starting location
   * @param col    the column of the starting location
   * @param length the length of the desired ship
   * @return if the ship will be blocked
   */
  private boolean obstructed(boolean isH, TileOccupant[][] board, int row, int col, int length) {
    int advanced = isH ? col : row;
    for (int i = advanced; i < length + advanced; i++) {
      TileOccupant tile = isH ? board[row][i] : board[i][col];
      if (tile != TileOccupant.EMPTY) {
        return true;
      }
    }
    return false;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *     ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    own.addSalvo(opponentShotsOnBoard);
    List<Coord> hits = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      for (Ship ship : own.getFleet()) {
        if (ship.takeHit(shot)) {
          hits.add(shot);
        }
      }
    }
    own.addHits(hits);
    return hits;
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
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public abstract void endGame(GameResult result, String reason);
}
