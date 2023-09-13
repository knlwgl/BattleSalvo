package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "coordinates": [{}, {}...]
 * }
 * </code>
 * </p>
 *
 * @param coordinates the list of coordinates in the volley
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<Coord> coordinates) {
}
