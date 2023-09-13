package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "fleet": [{}, {}...]
 * }
 * </code>
 * </p>
 *
 * @param fleet the fleet of ships (and their placement)
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipAdapter> fleet) {
}
