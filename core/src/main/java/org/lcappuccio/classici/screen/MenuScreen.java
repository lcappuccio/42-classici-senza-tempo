package org.lcappuccio.classici.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import org.lcappuccio.classici.Core42Game;
import org.lcappuccio.classici.game.Game;
import org.lcappuccio.classici.game.GameManager;

/**
 * Main menu screen displaying available games as a grid of colored rectangles.
 */
public class MenuScreen implements Screen, InputProcessor {

  private static final Color COLOR_BG = new Color(0.1f, 0.1f, 0.12f, 1f);
  private static final Color COLOR_CARD = new Color(0.2f, 0.22f, 0.25f, 1f);

  private final Core42Game coreGame;
  private final SpriteBatch batch;
  private final ShapeRenderer shape;
  private final BitmapFont font;
  private final java.util.List<String> gameTitles;
  private int cols;
  private int cardW;
  private int cardH;
  private int gap;
  private int startX;

  /** Creates the menu screen. */
  public MenuScreen(Core42Game coreGame) {
    this.coreGame = coreGame;
    this.batch = new SpriteBatch();
    this.shape = new ShapeRenderer();
    this.font = new BitmapFont();
    this.gameTitles = GameManager.getGameTitles();
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void render(float delta) {
    int w = Gdx.graphics.getWidth();

    cols = Math.max(3, w / 200);
    cardW = Math.min(180, (w - 40) / cols);
    cardH = 120;
    gap = 16;
    int totalRowW = cols * cardW + (cols - 1) * gap;
    startX = (w - totalRowW) / 2;

    shape.setProjectionMatrix(batch.getProjectionMatrix());

    shape.begin(ShapeType.Filled);
    shape.setColor(COLOR_BG);
    shape.rect(0, 0, w, Gdx.graphics.getHeight());
    shape.end();

    for (int i = 0; i < gameTitles.size(); i++) {
      int col = i % cols;
      int row = i / cols;
      int cx = startX + col * (cardW + gap);
      int cy = Gdx.graphics.getHeight() - 80 - row * (cardH + gap) - cardH;

      shape.begin(ShapeType.Filled);
      shape.setColor(COLOR_CARD);
      shape.rect(cx, cy, cardW, cardH);
      shape.end();
    }

    batch.begin();
    font.setColor(Color.WHITE);
    font.draw(batch, "42 Classici Senza Tempo", 20, Gdx.graphics.getHeight() - 20);

    for (int i = 0; i < gameTitles.size(); i++) {
      int col = i % cols;
      int row = i / cols;
      int cx = startX + col * (cardW + gap);
      int cy = Gdx.graphics.getHeight() - 80 - row * (cardH + gap) - cardH;

      font.draw(batch, gameTitles.get(i),
          cx + (cardW - font.draw(batch, gameTitles.get(i), cx, cy).width) / 2,
          cy + cardH / 2f);
    }
    batch.end();
  }

  @Override
  public boolean touchDown(int x, int y, int pointer, int button) {
    int h = Gdx.graphics.getHeight();
    int w = Gdx.graphics.getWidth();
    int adjustedY = h - y;

    for (int i = 0; i < gameTitles.size(); i++) {
      int col = i % cols;
      int row = i / cols;
      int cx = startX + col * (cardW + gap);
      int cy = h - 80 - row * (cardH + gap) - cardH;

      if (x >= cx && x <= cx + cardW && adjustedY >= cy && adjustedY <= cy + cardH) {
        String title = gameTitles.get(i);
        Game game = GameManager.create(title);
        coreGame.setScreen(new PlayScreen(coreGame, game));
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean keyDown(int keycode) {
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
  public boolean touchUp(int x, int y, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int x, int y, int pointer) {
    return false;
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
    batch.dispose();
    shape.dispose();
    font.dispose();
  }
}
