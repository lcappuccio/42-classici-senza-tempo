package org.lcappuccio.classici;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Desktop launcher for local development using LWJGL3.
 */
public final class DesktopLauncher {

  private DesktopLauncher() {
  }

  /** Application entry point. */
  public static void main(String[] args) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("42 Classici Senza Tempo");
    config.setWindowedMode(800, 600);
    config.setForegroundFPS(60);
    new Lwjgl3Application(new Core42Game(), config);
  }
}
