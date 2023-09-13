package cs3500.pa04.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.ArtificialPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.GameType;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.VolleyJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private Player player;
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().objectNode();


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.player = new ArtificialPlayer("Jozurf", new Board(), new Board(), new Random(1));
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  @Test
  void handleJoin() {
    // setup mocket
    MessageJson joinMessage = new MessageJson("join", VOID_RESPONSE);
    JsonNode messageJson = JsonUtils.serializeRecord(joinMessage);
    Mocket mocket = new Mocket(this.testLog, List.of(messageJson.toString()));
    // create controller
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    this.controller.run();
    // get message sent in reply (to server)
    MessageJson reply = responseToClass(MessageJson.class);
    assertEquals("join", reply.messageName());
    JoinJson replyJson = new JoinJson("Jozurf", GameType.SINGLE);
    assertEquals(JsonUtils.serializeRecord(replyJson), reply.arguments());
  }

  @Test
  void handleSetup() {
    // setup mocket
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 3);
    SetupJson setupJson = new SetupJson(6, 6, map);
    JsonNode sampleMessage = createSampleMessage("setup", setupJson);
    Mocket mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    // create controller
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    this.controller.run();
    // get message sent in reply (to server)
    MessageJson reply = responseToClass(MessageJson.class);
    assertEquals("setup", reply.messageName());
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(reply.arguments().toString());
      jsonParser.readValueAs(FleetJson.class);
    } catch (IOException e) {
      fail();
    }

  }

  @Test
  void handleTakeShots() {
    // Setup player board
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 3);
    this.player.setup(6, 6, map);
    // setup mocket
    MessageJson shotsRequest = new MessageJson("take-shots", VOID_RESPONSE);
    JsonNode messageJson = JsonUtils.serializeRecord(shotsRequest);
    Mocket mocket = new Mocket(this.testLog, List.of(messageJson.toString()));
    // create controller
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    this.controller.run();
    // get message sent in reply (to server)
    MessageJson reply = responseToClass(MessageJson.class);
    assertEquals("take-shots", reply.messageName());
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(reply.arguments().toString());
      jsonParser.readValueAs(VolleyJson.class);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  void handleReportDamage() {
    // Setup player board
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 3);
    this.player.setup(6, 6, map);
    // setup mocket
    VolleyJson shotsTaken = new VolleyJson(List.of(new Coord(1, 1), new Coord(2, 2),
        new Coord(3, 3), new Coord(4, 4), new Coord(5, 5), new Coord(0, 0)));
    JsonNode messageJson = createSampleMessage("report-damage", shotsTaken);
    Mocket mocket = new Mocket(this.testLog, List.of(messageJson.toString()));
    // create controller
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    this.controller.run();
    // get message sent in reply (to server)
    MessageJson reply = responseToClass(MessageJson.class);
    assertEquals("report-damage", reply.messageName());
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(reply.arguments().toString());
      jsonParser.readValueAs(VolleyJson.class);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  void handleSuccessfulHits() {
    // Setup player board
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 3);
    this.player.setup(6, 6, map);
    // setup mocket
    VolleyJson successfulHits = new VolleyJson(List.of(new Coord(1, 1),
        new Coord(2, 2), new Coord(4, 4)));
    JsonNode messageJson = createSampleMessage("successful-hits", successfulHits);
    Mocket mocket = new Mocket(this.testLog, List.of(messageJson.toString()));
    // create controller
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    this.controller.run();
    // get message sent in reply (to server)
    MessageJson reply = responseToClass(MessageJson.class);
    assertEquals("successful-hits", reply.messageName());
    assertEquals(VOID_RESPONSE, reply.arguments());
  }

  @Test
  void handleEndGame() {
    //WIN
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "You Won!");
    JsonNode sampleMessage = createSampleMessage("end-game", endGameJson);
    Mocket mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    this.controller.run();

    MessageJson reply = responseToClass(MessageJson.class);

    assertEquals(VOID_RESPONSE, reply.arguments());
    assertEquals("end-game", reply.messageName());

    //DRAW
    setup();
    endGameJson = new EndGameJson(GameResult.DRAW, "you had a draw!");
    sampleMessage = createSampleMessage("end-game", endGameJson);
    mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    this.controller.run();
    reply = responseToClass(MessageJson.class);

    assertEquals(VOID_RESPONSE, reply.arguments());
    assertEquals("end-game", reply.messageName());
    // LOSE
    setup();
    endGameJson = new EndGameJson(GameResult.LOSE, "you Lost!");
    sampleMessage = createSampleMessage("end-game", endGameJson);
    mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    this.controller.run();
    reply = responseToClass(MessageJson.class);

    assertEquals(VOID_RESPONSE, reply.arguments());
    assertEquals("end-game", reply.messageName());
  }

  @Test
  void unknownMethodRequest() {
    // setup mocket
    MessageJson unknownMsg = new MessageJson("hello-world", VOID_RESPONSE);
    JsonNode messageJson = JsonUtils.serializeRecord(unknownMsg);
    Mocket mocket = new Mocket(this.testLog, List.of(messageJson.toString()));
    // create controller
    try {
      this.controller = new ProxyController(mocket, this.player, GameType.SINGLE);
    } catch (IOException e) {
      fail();
    }
    // check that exception is thrown
    assertThrows(IllegalArgumentException.class, () -> this.controller.run());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   * @return object of type T
   */
  private <T> T responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    T returner = null;
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      returner = jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
    return returner;
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    JsonNode objectNode = JsonUtils.serializeRecord(messageObject);
    MessageJson messageJson = new MessageJson(messageName, objectNode);
    return JsonUtils.serializeRecord(messageJson);
  }
}