package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameType;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipAdapter;
import cs3500.pa04.json.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyController implements Controller {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  private final GameType gameType;

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().objectNode();

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if server input and/or output streams cannot be accessed
   */
  public ProxyController(Socket server, Player player, GameType type) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
    this.gameType = type;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }

  /**
   * Determines the type of request the server has sent ("join", "setup", "take-shots,
   * "report-damage", "successful-hits", or "end-game") and delegates to the corresponding helper
   * method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();
    switch (name) {
      case "join" -> handleJoin();
      case "setup" -> handleSetup(arguments);
      case "take-shots" -> handleTakeShots();
      case "report-damage" -> handleReportDamage(arguments);
      case "successful-hits" -> handleSuccessfulHits(arguments);
      case "end-game" -> handleEndGame(arguments);
      default -> throw new IllegalArgumentException("Invalid method name.");
    }
  }

  /**
   * Replies to the server with the player name and game type in MessageJson form.
   */
  private void handleJoin() {
    String name = this.player.name();
    JoinJson response = new JoinJson(name, this.gameType);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageResponse = new MessageJson("join", jsonResponse);
    JsonNode reply = JsonUtils.serializeRecord(messageResponse);
    this.out.println(reply);
  }

  /**
   * Parses the given message arguments as a SetupJson type, gets ship placements from the player,
   * serializes them as a FleetJson type and replies to the server in MessageJson form.
   *
   * @param arguments the Json representation of a SetupJson
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);
    List<Ship> ships = this.player.setup(setupArgs.height(), setupArgs.width(), setupArgs.fleet());
    List<ShipAdapter> fleet = ships.stream().map(ShipAdapter::new).toList();
    FleetJson fleetJson = new FleetJson(fleet);
    JsonNode responseArgs = JsonUtils.serializeRecord(fleetJson);
    MessageJson messageResponse = new MessageJson("setup", responseArgs);
    JsonNode reply = JsonUtils.serializeRecord(messageResponse);
    this.out.println(reply);
  }

  /**
   * Generates a salvo, serializes them as a VolleyJson type and replies to the server in
   * MessageJson form.
   */
  private void handleTakeShots() {
    List<Coord> shotsTaken = this.player.takeShots();
    VolleyJson shotsTakenJson = new VolleyJson(shotsTaken);
    JsonNode responseArgument = JsonUtils.serializeRecord(shotsTakenJson);
    MessageJson messageResponse = new MessageJson("take-shots", responseArgument);
    JsonNode reply = JsonUtils.serializeRecord(messageResponse);
    this.out.println(reply);
  }

  /**
   * Parses the given message arguments as a VolleyJson type, gets the shots that were successful,
   * serializes them as a VolleyJson type and replies to the server in MessageJson form.
   *
   * @param arguments the Json representation of a VolleyJson
   */
  private void handleReportDamage(JsonNode arguments) {
    VolleyJson volleyJson = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> damaged = this.player.reportDamage(volleyJson.coordinates());
    VolleyJson damagedJson = new VolleyJson(damaged);
    JsonNode responseArgument = JsonUtils.serializeRecord(damagedJson);
    MessageJson messageResponse = new MessageJson("report-damage", responseArgument);
    JsonNode reply = JsonUtils.serializeRecord(messageResponse);
    this.out.println(reply);
  }

  /**
   * Parses the given message arguments as a VolleyJson type, and void replies to the server in
   * MessageJson form.
   *
   * @param arguments the Json representation of a VolleyJson
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    VolleyJson volleyJson = this.mapper.convertValue(arguments, VolleyJson.class);
    player.successfulHits(volleyJson.coordinates());
    MessageJson messageResponse = new MessageJson("successful-hits", VOID_RESPONSE);
    JsonNode reply = JsonUtils.serializeRecord(messageResponse);
    this.out.println(reply);
  }

  /**
   * Parses the given message arguments as a EndGameJson type, and void replies to the server in
   * MessageJson form.
   *
   * @param arguments the Json representation of a EndGameJson
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameArgs = this.mapper.convertValue(arguments, EndGameJson.class);
    player.endGame(endGameArgs.gameResult(), endGameArgs.reason());
    MessageJson messageResponse = new MessageJson("end-game", VOID_RESPONSE);
    JsonNode reply = JsonUtils.serializeRecord(messageResponse);
    this.out.println(reply);
    try {
      this.server.close();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
