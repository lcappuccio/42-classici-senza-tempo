# Scaffold Project + Tic-Tac-Toe

## Overview

Recreate the Nintendo DS compilation "42 Classici Senza Tempo" as a web application using LibGDX + TeaVM. This spec covers the initial project scaffold and the first game: Tic-Tac-Toe.

## Tech Stack

| Component | Choice |
|---|---|
| Framework | LibGDX 1.14.1 |
| Web target | TeaVM (WASM — browser only) |
| Desktop dev | LWJGL3 (fast local iteration) |
| Build | Gradle (multi-module) |
| JDK | 21 |
| Deployment | Docker + nginx (multi-stage build) |
| Graphics | 100% geometric shapes via `ShapeRenderer` |
| Multiplayer | Local hot-seat (turn-based, pass-the-device) |
| Target devices | Desktop browsers, tablets, phones |
| Language | Java |
| License | GPL-3.0 |

## Constraints

- **No textures, bitmaps, sprites, or image files** — purely geometric rendering
- No external dependencies beyond LibGDX core + backends
- All rendering through `ShapeRenderer` (lines, rectangles, circles) and `SpriteBatch` for `BitmapFont` text
- TeaVM target only — no GWT
- WASM output must serve as static files via nginx
- Game registry must be extensible (add a class + register in `GameManager`)
- Color scheme: vibrant retro-inspired palette (hardcoded constants)

## Architecture

### Module Layout

```
42-classici-senza-tempo/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew / gradlew.bat / gradle/
│
├── core/
│   ├── build.gradle
│   └── src/main/java/org/lcappuccio/classici/
│       ├── Core42Game.java           ← ApplicationListener
│       ├── screen/
│       │   ├── MenuScreen.java       ← game grid selector
│       │   └── PlayScreen.java       ← wraps active game, routes input
│       ├── game/
│       │   ├── Game.java             ← interface
│       │   ├── GameManager.java      ← registry of all games
│       │   └── impl/
│       │       └── TicTacToeGame.java
│       └── multiplayer/
│           ├── Player.java
│           └── HotSeatManager.java
│
├── lwjgl3/
│   ├── build.gradle
│   └── src/main/java/org/lcappuccio/classici/
│       └── DesktopLauncher.java
│
├── teavm/
│   ├── build.gradle
│   ├── src/main/java/org/lcappuccio/classici/teavm/
│   │   └── TeaVMLauncher.java
│   └── src/main/webapp/
│       └── index.html
│
├── docker/
│   ├── Dockerfile
│   ├── nginx.conf
│   └── .gitkeep
│
└── docker-compose.yml
```

### Game Interface

```java
public interface Game {
    void init();
    void update(float delta);
    void render(SpriteBatch batch, ShapeRenderer shape);
    void resize(int width, int height);
    void dispose();
    boolean touchDown(int x, int y, int pointer, int button);
    boolean touchDragged(int x, int y, int pointer);
    boolean touchUp(int x, int y, int pointer, int button);
    String getTitle();
    boolean isGameOver();
    Player getCurrentPlayer();
    void nextTurn();
    boolean isTurnBased();
    int getPlayerCount();
}
```

### Screens

- **MenuScreen**: grid of colored rectangles with game titles, click to launch
- **PlayScreen**: wraps the active `Game`, routes touch events, shows turn indicator + "Press SPACE to pass device" prompt

### Tic-Tac-Toe Specifics

- 3x3 grid: 4 lines via `ShapeRenderer.rectLine()`
- X: two diagonal lines
- O: `ShapeRenderer.circle()`
- Touch maps pixel coords → grid cell: `col = x / cellWidth`, `row = y / cellHeight`
- State: `int[][] board = new int[3][3]` (0=empty, 1=P1, 2=P2)
- Win check: rows, columns, diagonals on every move
- Draw detection: all 9 cells filled, no winner
- Hot-seat: two players alternate, banner shows whose turn, SPACE to confirm pass

## Deliverables

- Fully buildable Gradle project (core + lwjgl3 + teavm)
- Playable Tic-Tac-Toe via `./gradlew lwjgl3:run`
- WASM production build via `./gradlew teavm:build`
- Docker Compose serving the game at `http://localhost:8080`

## Out of Scope

- Online multiplayer
- Sprite/texture-based rendering
- 3D rendering
- Gamepad support
- Mobile apps (web only)
- Any game beyond Tic-Tac-Toe
- Sound/audio
