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
 * Four in a Row (Connect Four): 7x6 vertical grid, two players alternate dropping
 * pieces that fall to the lowest empty cell in a column. First to get 4 in a row
 * (horizontally, vertically, or diagonally) wins.
 */
public class FourInARowGame implements Game {

  private static final int COLS = 7;
  private static final int ROWS = 6;
  private static final int WIN_COUNT = 4;

  private static final Color COLOR_BG = new Color(0.15f, 0.15f, 0.15f, 1f);
  private static final Color COLOR_BOARD = new Color(0.2f, 0.4f, 0.8f, 1f);
  private static final Color COLOR_P1 = new Color(0.9f, 0.2f, 0.2f, 1f);
  private static final Color COLOR_P2 = new Color(0.95f, 0.85f, 0.1f, 1f);
  private static final Color COLOR_TEXT = new Color(1f, 1f, 1f, 1f);
  private static final Color COLOR_HIGHLIGHT = new Color(1f, 1f, 1f, 0.25f);

  private final int[][] board;
  private final HotSeatManager hotSeat;
  private final BitmapFont font;
  private boolean gameOver;
  private int winner;
  private int cellSize;
  private int offsetX;
  private int offsetY;
  private int highlightCol;

  /** Creates a new Four in a Row game. */
  public FourInARowGame() {
    this.board = new int[COLS][ROWS];
    this.hotSeat = new HotSeatManager();
    this.font = new BitmapFont();
    this.gameOver = false;
    this.winner = 0;
    this.highlightCol = -1;
  }

  @Override
  public void init() {
    for (int c = 0; c < COLS; c++) {
      for (int r = 0; r < ROWS; r++) {
        board[c][r] = 0;
      }
    }
    gameOver = false;
    winner = 0;
    highlightCol = -1;
    hotSeat.reset();
    if (hotSeat.getPlayerCount() == 0) {
      hotSeat.addPlayer(new Player(1, "Player 1", COLOR_P1));
      hotSeat.addPlayer(new Player(2, "Player 2", COLOR_P2));
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

    int padding = 40;
    int gridWidth = Math.min(w, h) - padding * 2;
    cellSize = gridWidth / COLS;
    int boardWidth = cellSize * COLS;
    int boardHeight = cellSize * ROWS;
    offsetX = (w - boardWidth) / 2;
    offsetY = (h - boardHeight) / 2;

    shape.begin(ShapeType.Line);
    shape.setColor(COLOR_BOARD);
    shape.rect(offsetX - 3, offsetY - 3, boardWidth + 6, boardHeight + 6);
    shape.end();

    shape.begin(ShapeType.Filled);
    int cellRadius = cellSize / 2 - 4;
    for (int c = 0; c < COLS; c++) {
      for (int r = 0; r < ROWS; r++) {
        float cx = offsetX + c * cellSize + cellSize / 2f;
        float cy = offsetY + r * cellSize + cellSize / 2f;
        shape.setColor(COLOR_BOARD);
        shape.circle(cx, cy, cellRadius);
      }
    }
    shape.end();

    shape.begin(ShapeType.Line);
    for (int c = 0; c < COLS; c++) {
      for (int r = 0; r < ROWS; r++) {
        float cx = offsetX + c * cellSize + cellSize / 2f;
        float cy = offsetY + r * cellSize + cellSize / 2f;
        shape.setColor(Color.BLACK);
        shape.circle(cx, cy, cellRadius);
      }
    }
    shape.end();

    shape.begin(ShapeType.Filled);
    for (int c = 0; c < COLS; c++) {
      for (int r = 0; r < ROWS; r++) {
        if (board[c][r] == 0) {
          continue;
        }
        float cx = offsetX + c * cellSize + cellSize / 2f;
        float cy = offsetY + r * cellSize + cellSize / 2f;
        shape.setColor(board[c][r] == 1 ? COLOR_P1 : COLOR_P2);
        shape.circle(cx, cy, cellRadius - 2);
      }
    }
    shape.end();

    if (highlightCol >= 0 && !gameOver) {
      int row = findLowestEmptyRow(highlightCol);
      if (row >= 0) {
        shape.begin(ShapeType.Filled);
        shape.setColor(COLOR_HIGHLIGHT);
        float cx = offsetX + highlightCol * cellSize + cellSize / 2f;
        float cy = offsetY + row * cellSize + cellSize / 2f;
        shape.circle(cx, cy, cellRadius - 2);
        shape.end();
      }
    }

    batch.begin();
    font.setColor(COLOR_TEXT);
    String turnText = gameOver
        ? (winner == 0 ? "Draw!" : getCurrentPlayer().getName() + " wins!")
        : getCurrentPlayer().getName() + "'s turn";
    font.draw(batch, turnText, 10, h - 10);
    batch.end();
  }

  @Override
  public boolean touchDown(int x, int y, int pointer, int button) {
    if (gameOver) {
      return false;
    }
    int col = screenXToCol(x);
    if (col < 0 || col >= COLS) {
      highlightCol = -1;
      return false;
    }
    if (findLowestEmptyRow(col) < 0) {
      highlightCol = -1;
      return false;
    }
    highlightCol = col;
    return true;
  }

  @Override
  public boolean touchDragged(int x, int y, int pointer) {
    if (highlightCol < 0) {
      return false;
    }
    int col = screenXToCol(x);
    if (col < 0 || col >= COLS || findLowestEmptyRow(col) < 0) {
      highlightCol = -1;
    } else {
      highlightCol = col;
    }
    return true;
  }

  @Override
  public boolean touchUp(int x, int y, int pointer, int button) {
    if (highlightCol >= 0 && !gameOver) {
      int col = highlightCol;
      highlightCol = -1;
      placePiece(col);
      return true;
    }
    highlightCol = -1;
    return false;
  }

  private int screenXToCol(int screenX) {
    int w = Gdx.graphics.getWidth();
    int boardWidth = cellSize * COLS;
    int ox = (w - boardWidth) / 2;
    return (screenX - ox) / cellSize;
  }

  private int findLowestEmptyRow(int col) {
    for (int r = 0; r < ROWS; r++) {
      if (board[col][r] == 0) {
        return r;
      }
    }
    return -1;
  }

  private void placePiece(int col) {
    int row = findLowestEmptyRow(col);
    if (row < 0) {
      return;
    }
    board[col][row] = hotSeat.getCurrentPlayer().getId();
    if (checkWin(col, row)) {
      gameOver = true;
      winner = hotSeat.getCurrentPlayer().getId();
    } else if (isBoardFull()) {
      gameOver = true;
      winner = 0;
    } else {
      hotSeat.nextTurn();
    }
  }

  private boolean checkWin(int col, int row) {
    int id = board[col][row];

    int count = 1;
    for (int c = col - 1; c >= 0 && board[c][row] == id; c--) {
      count++;
    }
    for (int c = col + 1; c < COLS && board[c][row] == id; c++) {
      count++;
    }
    if (count >= WIN_COUNT) {
      return true;
    }

    count = 1;
    for (int r = row - 1; r >= 0 && board[col][r] == id; r--) {
      count++;
    }
    if (count >= WIN_COUNT) {
      return true;
    }

    count = 1;
    for (int c = col - 1, r = row - 1; c >= 0 && r >= 0 && board[c][r] == id; c--, r--) {
      count++;
    }
    for (int c = col + 1, r = row + 1; c < COLS && r < ROWS && board[c][r] == id; c++, r++) {
      count++;
    }
    if (count >= WIN_COUNT) {
      return true;
    }

    count = 1;
    for (int c = col - 1, r = row + 1; c >= 0 && r < ROWS && board[c][r] == id; c--, r++) {
      count++;
    }
    for (int c = col + 1, r = row - 1; c < COLS && r >= 0 && board[c][r] == id; c++, r--) {
      count++;
    }
    return count >= WIN_COUNT;
  }

  private boolean isBoardFull() {
    for (int c = 0; c < COLS; c++) {
      if (board[c][ROWS - 1] == 0) {
        return false;
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
  public String getTitle() {
    return "Four in a Row";
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
