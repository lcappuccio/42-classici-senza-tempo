package org.lcappuccio.classici.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.lcappuccio.classici.multiplayer.Player;

/**
 * Interface that every game in the collection must implement.
 * All rendering must use ShapeRenderer for geometric shapes and SpriteBatch
 * for BitmapFont text only — no textures, bitmaps, or sprites.
 */
public interface Game {

  /** Initializes game state. */
  void init();

  /** Updates game logic. */
  void update(float delta);

  /** Renders the game using ShapeRenderer for geometry and SpriteBatch for text. */
  void render(SpriteBatch batch, ShapeRenderer shape);

  /** Called when the screen is resized. */
  void resize(int width, int height);

  /** Releases resources. */
  void dispose();

  /** Handles a touch down event. */
  boolean touchDown(int x, int y, int pointer, int button);

  /** Handles a touch drag event. */
  boolean touchDragged(int x, int y, int pointer);

  /** Handles a touch up event. */
  boolean touchUp(int x, int y, int pointer, int button);

  /** Returns the display title of this game. */
  String getTitle();

  /** Returns true if the game has ended. */
  boolean isGameOver();

  /** Returns the current player. */
  Player getCurrentPlayer();

  /** Advances to the next turn. */
  void nextTurn();

  /** Returns true if this game is turn-based. */
  boolean isTurnBased();

  /** Returns the number of players. */
  int getPlayerCount();
}
