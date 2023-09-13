package cs3500.pa03.model;

/**
 * Represents types of ships in a BattleSalvo game
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int length;

  ShipType(int size) {
    length = size;
  }

  /**
   * What is the length of this type of ship
   *
   * @return the length of this ship type
   */
  public int getLength() {
    return length;
  }

  /**
   * Represents the given ShipType as a TileOccupant
   *
   * @param s the desired shiptype
   * @return the TileOccupant representative of the ShipType.
   */
  public static TileOccupant represent(ShipType s) {
    return switch (s) {
      case CARRIER -> TileOccupant.CARRIER;
      case BATTLESHIP -> TileOccupant.BATTLESHIP;
      case DESTROYER -> TileOccupant.DESTROYER;
      case SUBMARINE -> TileOccupant.SUBMARINE;
    };
  }
}
