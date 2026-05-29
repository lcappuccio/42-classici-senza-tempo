package org.lcappuccio.classici.multiplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages hot-seat multiplayer turn switching.
 */
public class HotSeatManager {

  private final List<Player> players;
  private int currentPlayerIndex;

  /** Creates an empty hot-seat manager. */
  public HotSeatManager() {
    this.players = new ArrayList<>();
    this.currentPlayerIndex = 0;
  }

  /** Adds a player to the rotation. */
  public void addPlayer(Player player) {
    players.add(player);
  }

  /** Returns the current player, or null if no players exist. */
  public Player getCurrentPlayer() {
    if (players.isEmpty()) {
      return null;
    }
    return players.get(currentPlayerIndex);
  }

  /** Advances to the next player in the rotation. */
  public void nextTurn() {
    if (players.isEmpty()) {
      return;
    }
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  /** Resets to the first player. */
  public void reset() {
    currentPlayerIndex = 0;
  }

  /** Returns the number of players. */
  public int getPlayerCount() {
    return players.size();
  }

  /** Returns a copy of the player list. */
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }
}
