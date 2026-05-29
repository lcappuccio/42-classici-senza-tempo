package org.lcappuccio.classici.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.lcappuccio.classici.game.impl.FourInARowGame;
import org.lcappuccio.classici.game.impl.TicTacToeGame;

/**
 * Registry of all available games.
 * Games are registered here and displayed in the menu.
 */
public final class GameManager {

  private static final Map<String, Supplier<Game>> GAMES = new LinkedHashMap<>();

  static {
    register("Tic-Tac-Toe", TicTacToeGame::new);
    register("Four in a Row", FourInARowGame::new);
  }

  private GameManager() {
  }

  /** Registers a game with the given title. */
  public static void register(String title, Supplier<Game> factory) {
    GAMES.put(title, factory);
  }

  /** Returns a list of all registered game titles. */
  public static List<String> getGameTitles() {
    return new ArrayList<>(GAMES.keySet());
  }

  /** Creates a new game instance by title. */
  public static Game create(String title) {
    Supplier<Game> factory = GAMES.get(title);
    if (factory == null) {
      throw new IllegalArgumentException("Unknown game: " + title);
    }
    return factory.get();
  }

  /** Returns the number of registered games. */
  public static int getGameCount() {
    return GAMES.size();
  }
}
