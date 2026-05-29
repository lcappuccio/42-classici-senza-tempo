## Why

Second game for the 42 Classici Senza Tempo collection: Four in a Row (Connect Four). Adds a vertical board game with column-drop mechanics and gravity-based piece placement, expanding the library beyond Tic-Tac-Toe with a new game type.

## What Changes

- New `FourInARowGame` implementing the `Game` interface
- 7×6 grid with column-drop mechanic (pieces fall to lowest empty cell)
- Win detection: 4 consecutive pieces horizontally, vertically, or diagonally
- Draw detection: board full with no winner
- Column hover highlight for touch target feedback
- Registration in `GameManager`

## Capabilities

### New Capabilities

- `four-in-a-row`: Full implementation of Four in a Row with column-drop physics, win detection across all 4 directions, and draw detection

### Modified Capabilities

None.

## Impact

- New file: `core/.../game/impl/FourInARowGame.java`
- Modified: `GameManager` static block registers the new game
