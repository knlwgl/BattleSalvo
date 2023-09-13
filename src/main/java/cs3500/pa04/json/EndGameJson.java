package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "result": "game result",
 *   "reason": "result reason"
 * }
 * </code>
 * </p>
 *
 * @param gameResult the result of the game
 * @param reason the reason for the result
 */
public record EndGameJson(
    @JsonProperty("result") GameResult gameResult,
    @JsonProperty("reason") String reason) {

}


