package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "width": "width of board",
 *   "height": "height of board",
 *   "fleet-spec": {
 *     "CARRIER": "n of ship",
 *     "BATTLESHIP": "n of ship",
 *     "DESTROYER": "n of ship",
 *     "SUBMARINE": "n of ship"
 *     }
 * }
 * </code>
 * </p>
 *
 * @param width width of the board
 * @param height height of the board
 * @param fleet the quantity of each ship type mapped
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleet) {
}
