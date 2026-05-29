package org.lcappuccio.classici.teavm;

import com.github.xpenatan.gdx.backends.teavm.TeaVMApplication;
import com.github.xpenatan.gdx.backends.teavm.TeaVMApplicationConfiguration;

import org.lcappuccio.classici.Core42Game;

/**
 * TeaVM launcher for WASM/JS web deployment.
 */
public final class TeaVMLauncher {

  private TeaVMLauncher() {
  }

  public static void main(String[] args) {
    TeaVMApplicationConfiguration config = new TeaVMApplicationConfiguration("canvas");
    config.width = 0;
    config.height = 0;
    new TeaVMApplication(new Core42Game(), config);
  }
}
