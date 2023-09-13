package cs3500.pa04;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.GameController;
import cs3500.pa03.model.ArtificialPlayer;
import cs3500.pa03.model.BattleIntel;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.GameType;
import cs3500.pa03.model.Model;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.SalvoDisplay;
import cs3500.pa03.view.View;
import cs3500.pa04.client.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - host and port for online game or no arguments for local game
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      String host = args[0];
      try {
        int port = Integer.parseInt(args[1]);
        Driver.runClient(host, port);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid port provided.");
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    } else if (args.length == 0) {
      Driver.runLocal();
    } else {
      throw new IllegalArgumentException("Invalid number of arguments provided: "
          + "Please pass in 2 for a online game, or, 0 for a local game");
    }
  }

  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port)
      throws IOException {
    Socket server = new Socket(host, port);
    Board b1 = new Board();
    Board b2 = new Board();
    Player player = new ArtificialPlayer("Jozurf", b1, b2);
    ProxyController proxyCtrl = new ProxyController(server, player, GameType.SINGLE);
    proxyCtrl.run();
  }

  /**
   * This method runs the local version of BattleSalvo
   */
  private static void runLocal() {
    View view = new SalvoDisplay(new InputStreamReader(System.in), System.out);
    Model data = new BattleIntel(15, 6);
    Controller ctrlr = new GameController(view, data);
    ctrlr.run();
  }
}