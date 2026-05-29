package org.lcappuccio.classici;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.lcappuccio.classici.screen.MenuScreen;

/**
 * Main game class. Owns the screen stack and delegates to MenuScreen / PlayScreen.
 */
public class Core42Game implements ApplicationListener {

  private Screen screen;

  @Override
  public void create() {
    setScreen(new MenuScreen(this));
  }

  @Override
  public void resize(int width, int height) {
    if (screen != null) {
      screen.resize(width, height);
    }
  }

  @Override
  public void render() {
    if (screen != null) {
      screen.render(Gdx.graphics.getDeltaTime());
    }
  }

  @Override
  public void pause() {
    if (screen != null) {
      screen.pause();
    }
  }

  @Override
  public void resume() {
    if (screen != null) {
      screen.resume();
    }
  }

  @Override
  public void dispose() {
    if (screen != null) {
      screen.dispose();
    }
  }

  /** Sets the active screen, disposing the previous one. */
  public void setScreen(Screen newScreen) {
    if (screen != null) {
      screen.dispose();
    }
    screen = newScreen;
    screen.show();
    screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }
}
