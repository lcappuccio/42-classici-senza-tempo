## Why

Initial scaffold of the 42 Classici Senza Tempo web application, establishing the Gradle multi-module project structure, core architecture (Game interface, Menu/Play screens, multiplayer system), and the first playable game (Tic-Tac-Toe).

## What Changes

- Gradle multi-module project (core + lwjgl3 + teavm) with JDK 21
- Core42Game ApplicationListener with screen stack
- Game interface with lifecycle, rendering, input, and multiplayer contracts
- MenuScreen: grid of colored rectangles launching games
- PlayScreen: wraps any Game, routes touch/keyboard events, shows turn UI
- Player and HotSeatManager for local hot-seat multiplayer
- TicTacToeGame: 3x3 grid, X/O rendering via ShapeRenderer, win/draw detection
- DesktopLauncher (LWJGL3) for local development
- TeaVMLauncher for WASM web deployment
- Docker + nginx multi-stage build serving static WASM output
- Checkstyle configuration (Google Java Style)

## Capabilities

### New Capabilities

- `project-scaffold`: Multi-module Gradle project with LWJGL3 and TeaVM targets
- `game-interface`: Core Game interface and GameManager registry
- `tic-tac-toe`: Tic-Tac-Toe implementation with geometric rendering
- `hot-seat-multiplayer`: Player and HotSeatManager for local turn-based play

### Modified Capabilities

None (initial project).

## Impact

- New Gradle multi-module project structure
- Brought in LibGDX 1.14.1, TeaVM, LWJGL3 dependencies
- Docker Compose for deployment
