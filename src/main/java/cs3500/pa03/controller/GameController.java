package cs3500.pa03.controller;


import cs3500.pa03.model.ActualPlayer;
import cs3500.pa03.model.ArtificialPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Model;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.View;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Controller for the BattleSalvo application
 */
public class GameController implements Controller {
  Player p1;
  Player p2;
  View display;
  Model data;
  Random rng;

  /**
   * Seeded constructor for the game controller
   *
   * @param view the location to display to the user
   * @param model the data
   * @param r the random number generator for this game
   */
  public GameController(View view, Model model, Random r) {
    display = view;
    data = model;
    rng = r;
    initPlayers();
  }

  /**
   * Constructor for the game controller
   *
   * @param view the location to display to the user
   * @param model the data
   */
  public GameController(View view, Model model) {
    this(view, model, new Random());
  }

  /**
   * Executes the BattleSalvo application
   */
  @Override
  public void run() {
    setupGame();
    List<Coord> p1Salvo = p1.takeShots();
    List<Coord> p2Salvo = p2.takeShots();
    while (!data.isGameOver()) {
      List<Coord> p1Hit = p2.reportDamage(p1Salvo);
      List<Coord> p2Hit = p1.reportDamage(p2Salvo);
      p1.successfulHits(p1Hit);
      p2.successfulHits(p2Hit);
      if (data.isGameOver()) {
        break;
      }
      p1Salvo = p1.takeShots();
      p2Salvo = p2.takeShots();
    }
    endGame(data.getResult());
  }

  /**
   * Runs the setup for the game
   */
  private void setupGame() {
    Coord nums = display.requestBoardSize(false);
    while (!data.validSize(nums.col, nums.row)) {
      nums = display.requestBoardSize(true);
    }
    List<Integer> fleetSize = display.requestFleetSize(4, Math.min(nums.col, nums.row), false);
    while (!data.validFleet(fleetSize)) {
      fleetSize = display.requestFleetSize(4, Math.min(nums.col, nums.row), true);
    }
    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, fleetSize.get(0));
    fleet.put(ShipType.BATTLESHIP, fleetSize.get(1));
    fleet.put(ShipType.DESTROYER, fleetSize.get(2));
    fleet.put(ShipType.SUBMARINE, fleetSize.get(3));
    p1.setup(nums.col, nums.row, fleet);
    p2.setup(nums.col, nums.row, fleet);
  }

  /**
   * Initializes the players of the game
   */
  private void initPlayers() {
    Board playerOneOpp = new Board();
    Board playerOneOwn = new Board();
    p1 = new ActualPlayer("RealPlayer", playerOneOpp, playerOneOwn, display, rng);
    Board playerTwoOpp = new Board();
    Board playerTwoOwn = new Board();
    p2 = new ArtificialPlayer("FakePlayer", playerTwoOpp, playerTwoOwn, rng);
    data.addBoards(playerOneOwn, playerTwoOwn);
  }

  /**
   * Ends the game based on player1's result
   *
   * @param g player1's game result
   */
  private void endGame(GameResult g) {
    if (g == GameResult.WIN) {
      p1.endGame(GameResult.WIN, "You sunk all the opponent's ships!");
      p2.endGame(GameResult.LOSE, "All your ships sunk!");
    } else if (g == GameResult.LOSE) {
      p1.endGame(GameResult.LOSE, "All your ships sunk!");
      p2.endGame(GameResult.WIN, "You sunk all the opponent's ships!");
    } else {
      p1.endGame(GameResult.DRAW, "Both players sunk all ships!");
      p2.endGame(GameResult.DRAW, "Both players sunk all ships!");
    }
  }
}
