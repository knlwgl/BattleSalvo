package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipOrientation;

/**
 * Represents a ship, adapted to another format
 */
public class ShipAdapter {
  private final Coord start;
  private final int length;
  private final ShipOrientation dir;

  /**
   * Constructor for ShipAdaptor
   *
   * @param c the starting coordinate of the ship
   * @param l the length of the ship
   * @param dir the direction of the ship
   */
  public ShipAdapter(@JsonProperty("coord") Coord c, @JsonProperty("length") int l,
                     @JsonProperty("direction") ShipOrientation dir) {
    this.start = c;
    this.length = l;
    this.dir = dir;
  }

  /**
   * Constructor for changing Ship to ShipAdapter
   *
   * @param ship the ship to adapt
   */
  public ShipAdapter(Ship ship) {
    this(ship.getPosn().get(0), ship.getType().getLength(), ship.getDirection());
  }

  /**
   * Gets the starting coord of the ship
   *
   * @return Starting coordinate
   */
  public Coord getCoord() {
    return start;
  }

  /**
   * Gets the length of the ship
   *
   * @return the ship length
   */
  public int getLength() {
    return length;
  }

  /**
   * Gets the orientation of the ship
   *
   * @return the ShipOrientation
   */
  public ShipOrientation getDirection() {
    return dir;
  }
}
