package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Board
 */
public class Board {
  private TileOccupant[][] board;
  private final List<Coord> salvos;
  private final List<Coord> hits;
  private final List<Coord> notTaken;
  private final List<Ship> fleet;

  /**
   * Constructor for a Board
   *
   * @param row number of rows
   * @param col number of columns
   */
  public Board(int row, int col) {
    salvos = new ArrayList<>();
    notTaken = new ArrayList<>();
    hits = new ArrayList<>();
    fleet = new ArrayList<>();
    initBoard(row, col);
  }

  /**
   * Constructor for an uninitialized Board
   */
  public Board() {
    this(0, 0);
  }

  /**
   * Initialize the array board
   *
   * @param y the number of rows
   * @param x the number of columns
   */
  public void initBoard(int y, int x) {
    /*if (this.board.length != 0) {
      throw new IllegalStateException("Board has already been initialized!");
    }*/
    TileOccupant[][] board = new TileOccupant[y][x];
    for (TileOccupant[] row : board) {
      Arrays.fill(row, TileOccupant.EMPTY);
    }
    for (int i = 0; i < y; i++) {
      for (int j = 0; j < x; j++) {
        notTaken.add(new Coord(i, j));
      }
    }
    this.board = board;
  }


  /**
   * Returns the board as an array of TileOccupants
   *
   * @return the array representation of the board
   */
  public TileOccupant[][] getBoard() {
    return board;
  }

  /**
   * Represents the board as a 2D array of chars
   *
   * @return char array board
   */
  public char[][] charBoard() {
    int y = board.length;
    int x = board[0].length;
    char[][] charBoard = new char[y][x];
    for (int i = 0; i < y; i++) {
      for (int j = 0; j < x; j++) {
        charBoard[i][j] = TileOccupant.represent(board[i][j]);
      }
    }
    return charBoard;
  }

  /**
   * Returns the fleet on this board
   *
   * @return the list of ships
   */
  public List<Ship> getFleet() {
    return fleet;
  }

  /**
   * Adds the given ship to the board
   *
   * @param ship ship to add
   */
  public void placeShip(Ship ship) {
    fleet.add(ship);
    List<Coord> pos = ship.getPosn();
    for (Coord c : pos) {
      board[c.row][c.col] = ShipType.represent(ship.getType());
    }
  }

  /**
   * Adds the given salvo to the board;
   * sets the positons in the array to TileOccupant.MISS
   *
   * @param shots the list of shots to add
   */
  public void addSalvo(List<Coord> shots) {
    for (Coord coord : shots) {
      salvos.add(coord);
      board[coord.row][coord.col] = TileOccupant.MISS;
    }
    notTaken.removeAll(shots);
  }

  /**
   * Adds the given hits to the board;
   * sets the positons in the array to TileOccupant.HIT
   *
   * @param successful the list of successful shots
   */
  public void addHits(List<Coord> successful) {
    for (Coord shot : successful) {
      hits.add(shot);
      board[shot.row][shot.col] = TileOccupant.HIT;
    }
  }

  /**
   * How many shots to shoot in the next salvo
   *
   * @return the number ships not sunk
   */
  public int howManyShots() {
    int count = 0;
    for (Ship s : fleet) {
      if (!s.isSunk()) {
        count += 1;
      }
    }
    int cellsLeft = (board.length * board[0].length) - salvos.size();
    return Math.min(count, cellsLeft);
  }

  /**
   * Checks if the given coordinate has already been shot
   *
   * @param c the Coord to check
   * @return whether that Coord has been shot
   */
  public boolean alreadyLaunched(Coord c) {
    return salvos.contains(c);
  }


  /**
   * Returns a list of all the shots not taken
   *
   * @return the list of coords not yet shot
   */
  public List<Coord> getNotTaken() {
    return notTaken;
  }
}
