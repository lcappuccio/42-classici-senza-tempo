## 1. Project Scaffold

- [x] 1.1 Create Gradle multi-module project (core + lwjgl3 + teavm)
- [x] 1.2 Configure JDK 21, Checkstyle, SonarCloud
- [x] 1.3 Create DesktopLauncher (LWJGL3)
- [x] 1.4 Create TeaVMLauncher
- [x] 1.5 Create Docker + nginx multi-stage build
- [x] 1.6 Create docker-compose.yml

## 2. Core Architecture

- [x] 2.1 Create Game interface with lifecycle methods
- [x] 2.2 Create Core42Game ApplicationListener
- [x] 2.3 Create GameManager registry
- [x] 2.4 Create MenuScreen with game grid
- [x] 2.5 Create PlayScreen wrapping Game instances

## 3. Multiplayer System

- [x] 3.1 Create Player class with id, name, color, score
- [x] 3.2 Create HotSeatManager with turn switching

## 4. Tic-Tac-Toe

- [x] 4.1 Implement 3x3 grid rendering via ShapeRenderer
- [x] 4.2 Implement X and O drawing
- [x] 4.3 Implement touch-to-cell mapping
- [x] 4.4 Implement win detection (rows, columns, diagonals)
- [x] 4.5 Implement draw detection
- [x] 4.6 Implement turn display UI

## 5. Build Pipeline

- [ ] 5.1 Fix WASM compilation in teavm/build.gradle
- [ ] 5.2 Remove unnecessary lwjgl3 dependency from core/build.gradle
