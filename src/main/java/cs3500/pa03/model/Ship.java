package cs3500.pa03.model;

import java.util.List;

/**
 * Represents a ship in a BattleSalvo game
 */
public class Ship {
  private final ShipType type;
  private final ShipOrientation direction;
  private final List<Coord> position;

  Ship(ShipType t, List<Coord> posn) {
    type = t;
    position = posn;
    direction = checkOrientation();
  }

  private ShipOrientation checkOrientation() {
    // since ships are more than 1 length to have direction, they have at least 2 coordinates
    Coord first = position.get(0);
    Coord second = position.get(1);
    return first.row < second.row ? ShipOrientation.VERTICAL : ShipOrientation.HORIZONTAL;
  }

  /**
   * Has this ship sunk?
   *
   * @return if this ship is sunk.
   */
  public boolean isSunk() {
    return position.isEmpty();
  }

  /**
   * Checks if this ship is hit by the given shot, and registers the hit.
   *
   * @param shot the shot to check
   * @return if the given shot hits this ship
   */
  public boolean takeHit(Coord shot) {
    return position.remove(shot);
  }

  /**
   * What is the position of this ship
   *
   * @return list of coords occupied by the ship (that haven't been hit yet)
   */
  public List<Coord> getPosn() {
    return position;
  }

  /**
   * What is the type of this ship
   *
   * @return type of the ship
   */
  public ShipType getType() {
    return type;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param other an object to compare to this Coord
   * @return if the other object is equal to this Coord
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Ship s) {
      return this.type == s.type && this.position.equals(s.position);
    }
    return false;
  }

  /**
   * Returns a hash code value for the Ship.
   *
   * @return the hash code value for this Coord
   */
  @Override
  public int hashCode() {
    int code = type.ordinal();
    int factor = 100;
    for (Coord c : position) {
      code += c.hashCode() * factor;
      factor *= 10;
    }
    return code;
  }

  public ShipOrientation getDirection() {
    return direction;
  }
}
