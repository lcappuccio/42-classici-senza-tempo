package org.lcappuccio.classici.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.lcappuccio.classici.Core42Game;
import org.lcappuccio.classici.game.Game;

/**
 * Wraps a Game instance, routes input events, and shows turn/pass-device UI.
 */
public class PlayScreen implements Screen, InputProcessor {

  private final Core42Game coreGame;
  private final Game game;
  private final SpriteBatch batch;
  private final ShapeRenderer shape;
  private final BitmapFont font;

  /** Creates the play screen wrapping the given game. */
  public PlayScreen(Core42Game coreGame, Game game) {
    this.coreGame = coreGame;
    this.game = game;
    this.batch = new SpriteBatch();
    this.shape = new ShapeRenderer();
    this.font = new BitmapFont();
    game.init();
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void render(float delta) {
    game.update(delta);
    game.render(batch, shape);

    if (game.isTurnBased() && !game.isGameOver()) {
      batch.begin();
      font.setColor(1, 1, 0, 1);
      font.draw(batch, "Press SPACE to pass device",
          Gdx.graphics.getWidth() / 2f - 80, 20);
      batch.end();
    } else if (game.isGameOver() && Gdx.input.justTouched()) {
      coreGame.setScreen(new MenuScreen(coreGame));
    }
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Input.Keys.SPACE && game.isTurnBased() && !game.isGameOver()) {
      game.nextTurn();
      return true;
    }
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int x, int y, int pointer, int button) {
    return game.touchDown(x, y, pointer, button);
  }

  @Override
  public boolean touchUp(int x, int y, int pointer, int button) {
    return game.touchUp(x, y, pointer, button);
  }

  @Override
  public boolean touchDragged(int x, int y, int pointer) {
    return game.touchDragged(x, y, pointer);
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }

  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public void resize(int width, int height) {
    game.resize(width, height);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    game.dispose();
    batch.dispose();
    shape.dispose();
    font.dispose();
  }
}
