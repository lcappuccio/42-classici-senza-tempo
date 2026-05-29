## Context

Adding Four in a Row as the second game. Unlike Tic-Tac-Toe (3×3 grid, click-to-place), this game features a 7×6 vertical board with gravity: pieces fall to the lowest empty cell in a column. Win detection must check 4 directions.

## Goals / Non-Goals

**Goals:**
- Playable Four in a Row with two-player hot-seat
- Column-drop physics (piece falls to lowest empty row)
- Win detection: 4-in-a-row horizontally, vertically, diagonally (both directions)
- Draw detection: board full with no winner
- Column highlight on touch (preview where piece will land)
- Geometric rendering only (circles + grid)

**Non-Goals:**
- Piece drop animation (instant placement)
- AI opponent
- Sound effects
- Animated win celebrations

## Decisions

- **Board as `int[COLS][ROWS]`** (cols-major): Column indexing is the primary operation (find lowest empty row in a column), so cols-first layout avoids transposing
- **Drop mechanic**: `touchDown` maps x → column index, then scans bottom-up for first empty row. Reject if column full
- **Win check from last move**: Only scan from the last placed piece, checking all 4 directions (H, V, diag /, diag \). Count consecutive same-color pieces in each direction. If any reach 4 → win
- **Board frame**: Rounded rectangle with circular holes exposing the background color. Pieces are filled circles rendered inside the holes
- **Color palette**: Player 1 = Red, Player 2 = Yellow, Board frame = Blue, Background = dark grey
- **Column highlight**: On touch-down within a column, draw a semi-transparent circle at the drop position before the move is committed. On touch-up, place the piece or cancel
- **No animation**: Pieces snap instantly. Keeps code simple and aligns with the TicTacToe precedent

## Risks / Trade-offs

- [No drop animation] May feel less satisfying — animation can be added later as a visual polish change
- [7×6 on mobile] Vertical board may need tighter cell sizing on small screens. Min dimension used for scaling, same as TicTacToe
- [Touch column detection] Narrow columns on phone screens could cause mis-taps — column highlight mitigates this
