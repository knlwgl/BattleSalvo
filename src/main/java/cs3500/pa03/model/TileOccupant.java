package cs3500.pa03.model;

/**
 * Represents what is occupying a cell on the board
 */
public enum TileOccupant {
  CARRIER,
  BATTLESHIP,
  DESTROYER,
  SUBMARINE,
  HIT,
  MISS,
  EMPTY;

  /**
   * Represents a TileOccupant as a character
   *
   * @param t the TileOccupant to convert to it's char representation
   * @return the character representation of the occupant
   */
  public static char represent(TileOccupant t) {
    return switch (t) {
      case EMPTY -> '-';
      case CARRIER -> 'C';
      case BATTLESHIP -> 'B';
      case DESTROYER -> 'D';
      case SUBMARINE -> 'S';
      case HIT -> 'X';
      case MISS -> 'O';
    };
  }
}
