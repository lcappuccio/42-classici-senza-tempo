package org.lcappuccio.classici.game.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import org.lcappuccio.classici.game.Game;
import org.lcappuccio.classici.multiplayer.HotSeatManager;
import org.lcappuccio.classici.multiplayer.Player;

/**
 * Tic-Tac-Toe game: 3x3 grid, two players, hot-seat local multiplayer.
 */
public class TicTacToeGame implements Game {

  private static final int BOARD_SIZE = 3;
  private static final Color COLOR_BG = new Color(0.15f, 0.15f, 0.15f, 1f);
  private static final Color COLOR_GRID = new Color(0.4f, 0.4f, 0.4f, 1f);
  private static final Color COLOR_X = new Color(0.9f, 0.3f, 0.3f, 1f);
  private static final Color COLOR_O = new Color(0.3f, 0.6f, 0.9f, 1f);
  private static final Color COLOR_TEXT = new Color(1f, 1f, 1f, 1f);

  private final int[][] board;
  private final HotSeatManager hotSeat;
  private final BitmapFont font;
  private boolean gameOver;
  private int winner;
  private int cellSize;
  private int offsetX;
  private int offsetY;

  /** Creates a new Tic-Tac-Toe game. */
  public TicTacToeGame() {
    this.board = new int[BOARD_SIZE][BOARD_SIZE];
    this.hotSeat = new HotSeatManager();
    this.font = new BitmapFont();
    this.gameOver = false;
    this.winner = 0;
  }

  @Override
  public void init() {
    for (int r = 0; r < BOARD_SIZE; r++) {
      for (int c = 0; c < BOARD_SIZE; c++) {
        board[r][c] = 0;
      }
    }
    gameOver = false;
    winner = 0;
    hotSeat.reset();

    if (hotSeat.getPlayerCount() == 0) {
      hotSeat.addPlayer(new Player(1, "Player 1", Color.RED));
      hotSeat.addPlayer(new Player(2, "Player 2", Color.BLUE));
    }
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public void render(SpriteBatch batch, ShapeRenderer shape) {
    shape.setProjectionMatrix(batch.getProjectionMatrix());
    int w = Gdx.graphics.getWidth();
    int h = Gdx.graphics.getHeight();

    shape.begin(ShapeType.Filled);
    shape.setColor(COLOR_BG);
    shape.rect(0, 0, w, h);
    shape.end();

    int gridSize = Math.min(w, h) - 80;
    cellSize = gridSize / BOARD_SIZE;
    offsetX = (w - gridSize) / 2;
    offsetY = (h - gridSize) / 2;

    shape.begin(ShapeType.Line);
    shape.setColor(COLOR_GRID);
    for (int i = 1; i < BOARD_SIZE; i++) {
      shape.line(offsetX + i * cellSize, offsetY,
          offsetX + i * cellSize, offsetY + gridSize);
      shape.line(offsetX, offsetY + i * cellSize,
          offsetX + gridSize, offsetY + i * cellSize);
    }
    shape.end();

    shape.begin(ShapeType.Line);
    for (int r = 0; r < BOARD_SIZE; r++) {
      for (int c = 0; c < BOARD_SIZE; c++) {
        if (board[r][c] == 1) {
          drawX(shape, r, c);
        } else if (board[r][c] == 2) {
          drawO(shape, r, c);
        }
      }
    }
    shape.end();

    batch.begin();
    font.setColor(COLOR_TEXT);
    String turnText = gameOver
        ? (winner == 0 ? "Draw!" : getCurrentPlayer().getName() + " wins!")
        : getCurrentPlayer().getName() + "'s turn";
    font.draw(batch, turnText, 10, h - 10);
    batch.end();
  }

  private void drawX(ShapeRenderer shape, int row, int col) {
    shape.setColor(COLOR_X);
    int pad = cellSize / 4;
    int x1 = offsetX + col * cellSize + pad;
    int y1 = offsetY + row * cellSize + pad;
    int x2 = offsetX + (col + 1) * cellSize - pad;
    int y2 = offsetY + (row + 1) * cellSize - pad;
    shape.line(x1, y1, x2, y2);
    shape.line(x1, y2, x2, y1);
  }

  private void drawO(ShapeRenderer shape, int row, int col) {
    shape.setColor(COLOR_O);
    int cx = offsetX + col * cellSize + cellSize / 2;
    int cy = offsetY + row * cellSize + cellSize / 2;
    int radius = cellSize / 2 - cellSize / 4;
    shape.circle(cx, cy, radius);
  }

  @Override
  public boolean touchDown(int x, int y, int pointer, int button) {
    if (gameOver) {
      return false;
    }
    int gridSize = cellSize * BOARD_SIZE;
    int adjustedY = Gdx.graphics.getHeight() - y;
    if (x < offsetX || x >= offsetX + gridSize
        || adjustedY < offsetY || adjustedY >= offsetY + gridSize) {
      return false;
    }
    int col = (x - offsetX) / cellSize;
    int row = (adjustedY - offsetY) / cellSize;
    if (board[row][col] != 0) {
      return false;
    }
    board[row][col] = hotSeat.getCurrentPlayer().getId();
    if (checkWin(row, col)) {
      gameOver = true;
      winner = hotSeat.getCurrentPlayer().getId();
    } else if (isBoardFull()) {
      gameOver = true;
      winner = 0;
    } else {
      hotSeat.nextTurn();
    }
    return true;
  }

  private boolean checkWin(int row, int col) {
    int id = board[row][col];
    boolean win;
    win = true;
    for (int c = 0; c < BOARD_SIZE; c++) {
      if (board[row][c] != id) {
        win = false;
        break;
      }
    }
    if (win) {
      return true;
    }
    win = true;
    for (int r = 0; r < BOARD_SIZE; r++) {
      if (board[r][col] != id) {
        win = false;
        break;
      }
    }
    if (win) {
      return true;
    }
    if (row == col) {
      win = true;
      for (int i = 0; i < BOARD_SIZE; i++) {
        if (board[i][i] != id) {
          win = false;
          break;
        }
      }
      if (win) {
        return true;
      }
    }
    if (row + col == BOARD_SIZE - 1) {
      win = true;
      for (int i = 0; i < BOARD_SIZE; i++) {
        if (board[i][BOARD_SIZE - 1 - i] != id) {
          win = false;
          break;
        }
      }
      return win;
    }
    return false;
  }

  private boolean isBoardFull() {
    for (int r = 0; r < BOARD_SIZE; r++) {
      for (int c = 0; c < BOARD_SIZE; c++) {
        if (board[r][c] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void dispose() {
    font.dispose();
  }

  @Override
  public boolean touchDragged(int x, int y, int pointer) {
    return false;
  }

  @Override
  public boolean touchUp(int x, int y, int pointer, int button) {
    return false;
  }

  @Override
  public String getTitle() {
    return "Tic-Tac-Toe";
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public Player getCurrentPlayer() {
    return hotSeat.getCurrentPlayer();
  }

  @Override
  public void nextTurn() {
    hotSeat.nextTurn();
  }

  @Override
  public boolean isTurnBased() {
    return true;
  }

  @Override
  public int getPlayerCount() {
    return hotSeat.getPlayerCount();
  }
}
