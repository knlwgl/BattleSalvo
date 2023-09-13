package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameType;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "name": "player-name",
 *   "game-type": "type of game"
 * }
 * </code>
 * </p>
 *
 * @param name the name of the player
 * @param gameType the type of game mode for the game
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType) {
}
