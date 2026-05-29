package org.lcappuccio.classici.multiplayer;

import com.badlogic.gdx.graphics.Color;

/**
 * Represents a player in a game.
 */
public class Player {

  private final int id;
  private final String name;
  private final Color color;
  private int score;

  /** Creates a player with the given id, name, and color. */
  public Player(int id, String name, Color color) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.score = 0;
  }

  /** Returns the player's unique id. */
  public int getId() {
    return id;
  }

  /** Returns the player's display name. */
  public String getName() {
    return name;
  }

  /** Returns the player's color. */
  public Color getColor() {
    return color;
  }

  /** Returns the player's current score. */
  public int getScore() {
    return score;
  }

  /** Adds points to the player's score. */
  public void addScore(int points) {
    this.score += points;
  }

  /** Resets the player's score to zero. */
  public void resetScore() {
    this.score = 0;
  }
}
