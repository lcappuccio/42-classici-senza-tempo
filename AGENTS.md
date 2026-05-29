# 42 Classici Senza Tempo — Agents Guide

## Project Overview

Recreation of the Nintendo DS compilation *42 Classici Senza Tempo* as a web application.
All 42 games rendered with geometric shapes only — no textures, bitmaps, or sprites.

## Tech Stack

| Component | Detail |
|---|---|
| Framework | **LibGDX 1.14.1** |
| Web target | **TeaVM** (WASM — browser only) |
| Desktop dev | **LWJGL3** (fast local iteration) |
| Build | **Gradle** multi-module |
| JDK | **21** |
| Deployment | **Docker + nginx** multi-stage build |
| Graphics | `ShapeRenderer` + `SpriteBatch` (BitmapFont only) |
| Multiplayer | Local hot-seat (turn-based, pass-the-device) |
| Language | Java |
| Package | `org.lcappuccio.classici` |
| License | GPL-3.0 |

## Module Layout

```
/
├── build.gradle              # root
├── settings.gradle           # includes :core, :lwjgl3, :teavm
├── gradle.properties         # single source for all versions
├── gradlew / gradle/
│
├── core/                     # ALL shared game logic
│   ├── build.gradle
│   └── src/main/java/org/lcappuccio/classici/
│       ├── Core42Game.java
│       ├── screen/
│       │   ├── MenuScreen.java
│       │   └── PlayScreen.java
│       ├── game/
│       │   ├── Game.java
│       │   ├── GameManager.java
│       │   └── impl/
│       │       └── TicTacToeGame.java
│       └── multiplayer/
│           ├── Player.java
│           └── HotSeatManager.java
│
├── lwjgl3/                   # Desktop launcher
│   ├── build.gradle
│   └── src/main/java/org/lcappuccio/classici/
│       └── DesktopLauncher.java
│
├── teavm/                    # WASM web target
│   ├── build.gradle
│   ├── src/main/java/org/lcappuccio/classici/teavm/
│   │   └── TeaVMLauncher.java
│   └── src/main/webapp/
│       └── index.html
│
├── docker/
│   ├── Dockerfile
│   └── nginx.conf
│
├── docker-compose.yml
├── openspec/
├── AGENTS.md
└── .gitignore
```

## Core Architecture

### Game Interface (`core/.../game/Game.java`)

Every game implements this contract:

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

### GameManager (`core/.../game/GameManager.java`)

Registry of all games. Adding a new game means:
1. Create the class implementing `Game`
2. Register it: `GameManager.register("Chess", ChessGame::new);`

### Core42Game (`core/.../Core42Game.java`)

LibGDX `ApplicationListener`. Owns the screen stack (`MenuScreen`, `PlayScreen`).
Transitions: `MenuScreen` → user picks a game → `PlayScreen` wraps it → BACK returns to menu.

### Screens

- **MenuScreen**: grid of colored rectangles with game titles. Click to launch.
- **PlayScreen**: wraps the current `Game` instance, routes touch events, shows turn indicator + "Press SPACE to pass device" prompt.

### Player & HotSeatManager

- `Player`: id, name, color, score
- `HotSeatManager`: tracks current player index, provides `nextTurn()`, checks if all players have passed

## Adding a New Game (Scaffolding Checklist)

1. Create class `src/main/java/org/lcappuccio/classici/game/impl/<Name>Game.java`
2. Implement the `Game` interface
3. Register in `GameManager` static block or `GameManager.register()`
4. Use `ShapeRenderer` for all visuals (lines, circles, rectangles, polygons)
5. Use `BitmapFont` via `SpriteBatch` for text overlays (score, labels)
6. Test via `./gradlew lwjgl3:run`
7. Verify WASM builds with `./gradlew teavm:build`

### Rendering Rules

- **Never import or use texture/image/sprit batch draw calls with regions**
- `ShapeRenderer` must use `ShapeType.Filled` or `ShapeType.Line`
- Color palette: hardcoded `com.badlogic.gdx.graphics.Color` constants in each game
- Text: only `BitmapFont` default font, drawn via `SpriteBatch`

## Key Commands

```bash
# Desktop (fast iteration)
./gradlew lwjgl3:run

# WASM production build
./gradlew teavm:build

# Docker
docker compose up --build

# Clean all
./gradlew clean
```

## Coding Style: Google Java Style

This project follows the **Google Java Style Guide** with these additional rules:

- **Indentation**: 2 spaces (no tabs)
- **Line length**: 100 characters max
- **Braces**: Egyptian/OTBS (opening brace on same line), always used even for single-line bodies
- **Javadoc**: Required for all public classes and public methods. Package-private and private may have inline comments.
- **Imports**: No wildcard imports. Static imports allowed for constants only.
- **Naming**:
  - Classes: `UpperCamelCase`
  - Methods/variables: `lowerCamelCase`
  - Constants: `UPPER_SNAKE_CASE`
  - Parameters: `lowerCamelCase`, single-letter only in lambdas
- **Annotations**: Each annotation on its own line
- **Blank lines**: One between methods, one around logical blocks
- **No trailing whitespace**
- **No `final` on local variables or parameters** (method parameters are never `final`)
- **Visibility**: Prefer private, then package-private, protected, public
- **Organize members**: static fields → instance fields → constructors → static methods → instance methods → inner classes

### Per-file Layout

```
1. License header (GPL-3.0)
2. Package statement
3. Import statements (no wildcards, grouped: static → gdx → project → java)
4. Class Javadoc
5. Class declaration
6. Constants
7. Fields
8. Constructor
9. Public methods
10. Private methods
11. Inner classes
```

## Testing Guardrails

- **No test framework is configured yet** — do not add test dependencies or test files unless explicitly asked.
- All manual testing is done via `./gradlew lwjgl3:run`.
- When testing a game: verify win conditions, draw conditions, turn alternation, input mapping, and edge cases (resize, rapid clicks).
- WASM builds are verified structurally (`./gradlew teavm:build` succeeds) but not run in browser automatically.

## TeaVM Caveats

- Java 11+ language features work, but **no reflection**, no `Class.forName()`, no dynamic proxies
- No `long` in hot paths (emulated as two ints)
- All GWT/TeaVM dependencies use `:sources` classifier in `teavm/build.gradle`
- WASM output is at `teavm/build/dist/` after build
- Serve with `python3 -m http.server` for quick browser testing

## Docker

```bash
docker compose up --build
# → http://localhost:8080
# → http://<lan-ip>:8080 from any device on the network
```

The Dockerfile uses a multi-stage build:
1. `gradle:jdk21` builds the project
2. `nginx:alpine` serves the WASM output
