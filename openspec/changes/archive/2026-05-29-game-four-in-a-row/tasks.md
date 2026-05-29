## 1. Create Game Class

- [x] 1.1 Create `FourInARowGame.java` implementing `Game` interface
- [x] 1.2 Define constants: `COLS=7`, `ROWS=6`, `WIN_COUNT=4`, color palette
- [x] 1.3 Declare board as `int[COLS][ROWS]` and initialize to 0

## 2. Board Rendering

- [x] 2.1 Draw outer board frame (rounded rectangle) centered on screen
- [x] 2.2 Draw grid of circular cell holes with background-color fill
- [x] 2.3 Render player pieces as filled circles inside cells
- [x] 2.4 Implement column highlight preview on touch

## 3. Game Logic

- [x] 3.1 Implement column-drop: `touchDown` maps x → col, finds lowest empty row
- [x] 3.2 Implement win check: scan from last move in all 4 directions
- [x] 3.3 Implement draw detection: full board with no winner
- [x] 3.4 Handle full column rejection (no piece placed, turn unchanged)
- [x] 3.5 Display turn indicator and game-over message

## 4. Registration

- [x] 4.1 Register `FourInARowGame` in `GameManager` static block
