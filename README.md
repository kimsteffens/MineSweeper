# MineSweeper
A brief overview of a MineSweeper game created in Java. 

## Files
**Source files**:
MineSweeper.java: sets up the game and GUI, first asking the user how large the board should be and how many mines should be in the game.

MineSweeperGame.java: contains the primary logic and code for game progression.

MineSweeperPanel.java: the GUI and button functionality.

GameStatus.java: an enum containing Won, Lost, and NotOverYet as possible statuses for the game.

Cell.java: a class to contain cell information. The game board is created from Cell objects.

**Image files**:
red-flag.png: image used for if a cell is flagged.
mine.png: image used to show mines.

## How to Play
The program will first ask the player to choose a board size between 3 and 20, and then an appropriate number of mines. If the player does not supply appropriate numbers, then default values will be chosen. 

This game generally works as most MineSweeper games do. The player must simply click cells in the game board, being sure to avoid clicking on a mine. If the user clicks a cell that adjacent to a mine (but that is not a mine itself), then a number appears on the cell showing how many mines it is adjacent to. The player can mark any cell as flagged if they believe it is a mine by right-clicking it. If a player clicks a mine, the game is over and the player has lost. To win, the player must click all non-mine cells or must mark all mines with a flag (and not have any non-mine cells flagged).

The number of wins and losses are tracked and can be viewed to the left of the game board. At any time, the player may restart the board, start a game in a new window, or quit all-together.

