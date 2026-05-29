package org.lcappuccio.classici.teavm;

import com.github.xpenatan.gdx.teavm.backends.web.WebApplication;
import com.github.xpenatan.gdx.teavm.backends.web.WebApplicationConfiguration;
import org.lcappuccio.classici.Core42Game;

/**
 * TeaVM launcher for WASM/JS web deployment.
 */
public final class TeaVMLauncher {

  private TeaVMLauncher() {
  }

  /** Application entry point. */
  public static void main(String[] args) {
    WebApplicationConfiguration config = new WebApplicationConfiguration("canvas");
    config.width = 0;
    config.height = 0;
    new WebApplication(new Core42Game(), config);
  }
}
