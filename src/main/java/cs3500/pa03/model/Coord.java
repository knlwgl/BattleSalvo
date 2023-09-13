package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a coordinate position
 */
public class Coord {
  @JsonProperty("x") public final int col;
  @JsonProperty("y") public final int row;

  /**
   * Constructor for a coordinate position
   *
   * @param y the row number
   * @param x the column number
   */
  @JsonCreator
  public Coord(@JsonProperty("y") int y, @JsonProperty("x") int x) {
    row = y;
    col = x;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param other an object to compare to this Coord
   * @return if the other object is equal to this Coord
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Coord c) {
      return this.row == c.row && this.col == c.col;
    }
    return false;
  }

  /**
   * Returns a hash code value for the Coord.
   *
   * @return the hash code value for this Coord
   */
  @Override
  public int hashCode() {
    return (row * 1000) + col;
  }
}
