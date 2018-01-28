package minesweeper;

import java.util.*;

/*******************************************************************************
 * MineSweeperGame provides the logic and game-play rules behind MineSweeper. It
 * is utilized by the MineSweeperPanel class to create a GUI display. To win the
 * game the player must click all Cells that are not Mines. The player loses if
 * a mine is clicked.
 * 
 * @author Kimberlin Steffens
 * @version 16 February 2016
 ******************************************************************************/
public class MineSweeperGame {

	/** creates a two dimensional array of Cells */
	private Cell[][] board;

	/** the current game status: Won, Lost, or NotOverYet */
	private GameStatus status;

	/** the size of the square shaped board */
	private int boardSize;

	/** the number of mines to be placed on the board */
	private int numMines;

	/** number of cells yet to be revealed when revealing all blank cells */
	private int revealCount;

	/** the number of games won so far */
	private int winCount;

	/** the number of games lost so far */
	private int lossCount;

	/***************************************************************************
	 * Constructor for MineSweeperGame: initializes all instance variables and
	 * creates board with correct sizing, then lays mines on the board
	 * 
	 * @param boardSize
	 *            the number the user enters in MineSweeper to set for "this"
	 *            boardSize
	 * @param numMines
	 *            the number of mines the user enters in MineSweeper to set for
	 *            "this" numMines
	 **************************************************************************/
	public MineSweeperGame(int boardSize, int numMines) {

		// set boardSize and number of mines to be laid
		this.boardSize = boardSize;
		this.numMines = numMines;

		status = GameStatus.NotOverYet;

		// instantiate two dimensional array of Cells with the given board size
		board = new Cell[boardSize][boardSize];

		setEmpty();

		// place given number of mines on the board
		layMines(numMines);

		this.revealCount = 0;
		this.winCount = 0;
		this.lossCount = 0;
	}

	/***************************************************************************
	 * Sets all cells for the board to not be a mine and to not be exposed- they
	 * are all blank cells
	 **************************************************************************/
	private void setEmpty() {
		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++)

				// makes board totally clear
				board[r][c] = new Cell();
	}
	
	/***************************************************************************
	 * Sets a new game status as necessary. If all mines are flagged (and no
	 * non-mine cells are flagged)- or if all non-mine spaces are exposed, then
	 * the game is Won. Otherwise, it is NotOverYet.
	 **************************************************************************/
	public void setGameStatus (){
		status = GameStatus.Won;

		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++)

				// if not all non-mine cells are revealed, game isn't over
				if (!board[r][c].isExposed() && !board[r][c].isMine())
					status = GameStatus.NotOverYet;
		
		boolean checkFlagged = true;
		
		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++)

				
				// if all mine cells (and no other cells) are flagged, game is over
				if ((board[r][c].isFlagged() && !board[r][c].isMine()) || (!board[r][c].isFlagged() && board[r][c].isMine()))
					checkFlagged = false;
		
		if (checkFlagged) {
			status = GameStatus.Won;
		}
	}

	/***************************************************************************
	 * Gets a desired Cell on the board
	 * 
	 * @param row
	 *            the row the desired cell is in
	 * @param col
	 *            the column the desired cell is in
	 * @return returns the cell in the given row and column
	 **************************************************************************/
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/***************************************************************************
	 * Selects a Cell to reveal (unless cell is flagged). Updates the GameStatus
	 * as needed.
	 * 
	 * @param row
	 *            the row the Cell clicked is in
	 * @param col
	 *            the column the Cell clicked is in
	 **************************************************************************/
	public void select(int row, int col) {

		// the Cell given as parameter to be selected
		Cell iCell;
		iCell = getCell(row, col);

		// if the Cell is not Flagged, expose the Cell
		if (!iCell.isFlagged())
			board[row][col].setExposed(true);

		// if the Cell is not a mine or flag, reveal any other necessary Cells
		if (!board[row][col].isMine() && !board[row][col].isFlagged()) {

			revealCells(row, col);

			// set the number of Cells that still need to be revealed
			setRevealCount();

			// continue to reveal Cells as needed
			while (revealCount > 0) {
				for (int i = 0; i < boardSize; i++) {
					for (int j = 0; j < boardSize; j++) {
						iCell = getCell(i, j);

						// only need to reveal un-revealed blank Cells
						if (iCell.isZero() && !iCell.isRevealed()) {
							revealCells(i, j);

						}
					}
				}

				// update the number of cells that need to be revealed
				setRevealCount();
			}
		}

		// if mine is clicked the game is lost (flagged cells can't be clicked)
		if (board[row][col].isMine() && !board[row][col].isFlagged())
			status = GameStatus.Lost;

		// otherwise check if game status is won or not over yet
		else {
			setGameStatus();
		  }

	}

	/***************************************************************************
	 * Gets the current status of the game
	 * 
	 * @return returns the status
	 **************************************************************************/
	public GameStatus getGameStatus() {
		return status;
	}

	/***************************************************************************
	 * Resets the GameBoard by setting it empty and relaying mines
	 **************************************************************************/
	public void reset() {

		// update game status
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines(numMines);

	}

	/***************************************************************************
	 * Places the given number of mines on the board randomly
	 * 
	 * @param numMines
	 *            the number of mines to be placed
	 **************************************************************************/
	private void layMines(int numMines) {
		int i = 0;

		Random random = new Random();
		while (i < numMines) {

			// place in random row and column
			int c = random.nextInt(boardSize);
			int r = random.nextInt(boardSize);

			// set only if there is not already a mine there
			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}

	/***************************************************************************
	 * isValidCell ensures that the Cell being looked at it is in the bounds of
	 * the board
	 * 
	 * @param row
	 *            the row of the Cell being looked at
	 * @param col
	 *            the column of the Cell being looked at
	 * @return returns true if it is valid and false if invalid
	 **************************************************************************/
	public boolean isValidCell(int row, int col) {

		// check if out of bounds
		if (row > boardSize - 1 || row < 0 || col > boardSize - 1 || col < 0)
			return false;
		return true;
	}

	/***************************************************************************
	 * Finds the number of neighboring mines for all the cells on the board
	 **************************************************************************/
	public void findNeighbors() {
		Cell checkCell;

		// loop through each Cell
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				int numNeighbors = 0;

				// check for neighboring mines
				for (int r = i - 1; r <= i + 1; r++) {
					for (int s = j - 1; s <= j + 1; s++) {

						// ensure that it doesn't check out of bounds
						if (isValidCell(r, s)) {
							checkCell = getCell(r, s);
							if (checkCell.isMine() && !(r == i && s == j))
								numNeighbors++;
						}

					}
				}

				// set the Cell's Minecount to the number of neighbors found
				board[i][j].setMinecount(numNeighbors);
			}
		}
	}

	/***************************************************************************
	 * Reveals Cells. Reveals only Cell clicked if the Cell has neighbors. If
	 * not, it reveals surrounding Cells of the Cell clicked
	 * 
	 * @param row
	 *            the row of the Cell to be revealed
	 * @param col
	 *            the column of the Cell to be revealed
	 **************************************************************************/
	public void revealCells(int row, int col) {

		// Cell given as parameter
		Cell iCell = getCell(row, col);

		// current surrounding Cell being checked
		Cell checkCell;

		// only reveal the clicked cell if it has neighbors
		if (!iCell.isMine() && !iCell.isFlagged()) {
			iCell.setExposed(true);

			// reveal surrounding cells for a blank cell
			if (iCell.getMinecount() == 0) {

				for (int r = row - 1; r <= row + 1; r++) {
					for (int c = col - 1; c <= col + 1; c++) {

						// ensure cell is in bounds
						if (isValidCell(r, c)) {
							checkCell = getCell(r, c);

							// ensure that flags are left alone
							if (!checkCell.isFlagged()) {
								checkCell.setExposed(true);

								if (checkCell.getMinecount() == 0)

									// the Cell has no neighbors, so is "Zero"
									checkCell.setIsZero(true);

							}
						}
					}
				}

				// this cell has been revealed, so set isRevealed to true
				iCell.setIsRevealed(true);
			}
		}
	}

	/***************************************************************************
	 * RevealCount counts how many blank cells still need to be revealed.
	 * 
	 * This is determined by the boolean values for two Cell variables isZero
	 * and isRevealed: isZero is true for any Cell found to be blank in the
	 * revealCells method while isRevealed is true if a cell has been revealed
	 * in the revealCells method.
	 **************************************************************************/
	public void setRevealCount() {
		revealCount = 0;

		// the current cell being checked
		Cell iCell;

		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				iCell = getCell(i, j);

				// increment only if the Cell is zero and hasn't been revealed
				if (iCell.isZero() && !iCell.isRevealed())
					revealCount++;
			}
		}
	}

	/***************************************************************************
	 * Gets the value of revealCount
	 * 
	 * @return the revealCount
	 **************************************************************************/
	public int getRevealCount() {
		return revealCount;
	}

	/***************************************************************************
	 * Gets the winCount
	 * 
	 * @return returns winCount- the number of times won so far
	 **************************************************************************/
	public int getWinCount() {
		return winCount;
	}

	/***************************************************************************
	 * Increments winCount by one
	 **************************************************************************/
	public void incrementWinCount() {
		winCount++;
	}

	/***************************************************************************
	 * Gets the lossCount
	 * 
	 * @return returns the lossCount- the number of games lost so far
	 **************************************************************************/
	public int getLossCount() {
		return lossCount;
	}

	/***************************************************************************
	 * Increments the lossCount by one
	 **************************************************************************/
	public void incrementLossCount() {
		lossCount++;
	}

}