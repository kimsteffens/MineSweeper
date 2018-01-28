package minesweeper;

/*******************************************************************************
 * Cell provides functionality for each cell of the Minesweeper board. Each cell
 * contains information about whether the cell has been clicked on, whether the
 * cell is a mine, and what number should appear in the cell if it is adjacent
 * to any mine cells.
 * 
 * @author Kimberlin Steffens
 * @version 16 February 2016
 ******************************************************************************/
public class Cell {
	private int minecount;
	private boolean isFlagged;
	private boolean isExposed;
	private boolean isMine;
	private boolean isZero;
	private boolean isRevealed;

	/***************************************************************************
	 * Constructor for Cell. Sets all booleans to false.
	 **************************************************************************/
	public Cell() {
		isExposed = false;
		isMine = false;
		isFlagged = false;
		isExposed = false;
		isZero = false;
		isRevealed = false;
	}

	/***************************************************************************
	 * Getter method for minecount.
	 **************************************************************************/
	public int getMinecount() {
		return minecount;
	}

	/***************************************************************************
	 * Setter method for minecount.
	 **************************************************************************/
	public void setMinecount(int minecount) {
		this.minecount = minecount;
	}

	/***************************************************************************
	 * Getter method for isFlagged
	 **************************************************************************/
	public boolean isFlagged() {
		return isFlagged;
	}

	/***************************************************************************
	 * Setter method for isFlagged
	 **************************************************************************/
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	/***************************************************************************
	 * Getter method for isExposed
	 **************************************************************************/
	public boolean isExposed() {
		return isExposed;
	}

	/***************************************************************************
	 * Setter method for isExposed
	 **************************************************************************/
	public void setExposed(boolean exposed) {
		isExposed = exposed;
	}

	/***************************************************************************
	 * Getter method for isMine
	 **************************************************************************/
	public boolean isMine() {
		return isMine;
	}

	/***************************************************************************
	 * Setter method for isMine
	 **************************************************************************/
	public void setMine(boolean mine) {
		isMine = mine;
	}

	/***************************************************************************
	 * Getter method for isZero
	 **************************************************************************/
	public boolean isZero() {
		return isZero;
	}

	/***************************************************************************
	 * Setter method for isZero
	 **************************************************************************/
	public void setIsZero(boolean isZero) {
		this.isZero = isZero;
	}

	/***************************************************************************
	 * Getter method for isRevealed
	 **************************************************************************/
	public boolean isRevealed() {
		return isRevealed;
	}

	/***************************************************************************
	 * Setter method for isRevealed
	 **************************************************************************/
	public void setIsRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}

}