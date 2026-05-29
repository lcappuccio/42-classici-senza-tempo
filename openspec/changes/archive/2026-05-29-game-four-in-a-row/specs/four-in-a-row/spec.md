## ADDED Requirements

### Requirement: Board rendering
The system SHALL render a 7 column × 6 row vertical board with circular cells arranged in a grid. The board SHALL be displayed at the center of the screen with an outer frame (rounded rectangle) and visible cell separators.

#### Scenario: Board displayed at game start
- **WHEN** Four in a Row game initializes
- **THEN** a 7×6 grid of empty circular cells is rendered in the center of the screen

#### Scenario: Board scales with window resize
- **WHEN** the window is resized
- **THEN** the board SHALL scale to fit the smaller dimension (width or height) with padding

### Requirement: Column-drop mechanic
The system SHALL allow players to drop a piece into a column by touching any cell in that column. The piece SHALL occupy the lowest empty row in the selected column.

#### Scenario: Piece drops to lowest empty cell
- **WHEN** a column has 3 empty cells and a player touches it
- **THEN** the piece appears in the bottommost empty cell (row index 5 if no pieces, row index 4 if bottom row occupied, etc.)

#### Scenario: Full column is rejected
- **WHEN** a player touches a column with all 6 cells filled
- **THEN** no piece is placed and the turn does not change

### Requirement: Column highlight
The system SHALL highlight the target column when the player touches down on a cell. The highlight SHALL show the lowest empty row in that column.

#### Scenario: Column highlights on touch
- **WHEN** the player touches any cell in a column
- **THEN** a semi-transparent preview of the piece appears at the lowest empty row in that column

### Requirement: Win detection
The system SHALL detect when a player has placed 4 of their pieces consecutively in any direction: horizontal, vertical, or diagonal (both ascending and descending).

#### Scenario: Horizontal win detected
- **WHEN** a player places their 4th consecutive piece in the same row
- **THEN** the game ends and that player is declared the winner

#### Scenario: Vertical win detected
- **WHEN** a player places their 4th consecutive piece in the same column
- **THEN** the game ends and that player is declared the winner

#### Scenario: Diagonal win detected (ascending)
- **WHEN** a player places their 4th consecutive piece on an ascending diagonal (/)
- **THEN** the game ends and that player is declared the winner

#### Scenario: Diagonal win detected (descending)
- **WHEN** a player places their 4th consecutive piece on a descending diagonal (\)
- **THEN** the game ends and that player is declared the winner

### Requirement: Draw detection
The system SHALL detect when the board is full with no winner and declare a draw.

#### Scenario: Draw declared on full board
- **WHEN** all 42 cells are filled and no player has 4 in a row
- **THEN** the game ends and a draw message is displayed

### Requirement: Turn management
The system SHALL alternate turns between the two players. Each player SHALL have a distinct color (typically red and yellow).

#### Scenario: Turn alternates after each move
- **WHEN** a player places a piece
- **THEN** the turn switches to the other player, unless the game has ended

#### Scenario: Current player shown on screen
- **WHEN** it is a player's turn
- **THEN** their name and color indicator are displayed on screen
