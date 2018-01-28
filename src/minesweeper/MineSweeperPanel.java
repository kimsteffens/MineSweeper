package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*******************************************************************************
 * MineSweeperPanel provides the GUI behind the MineSweeperGame
 * 
 * @author Kimberlin Steffens
 * @version 16 February 2016
 ******************************************************************************/
public class MineSweeperPanel extends JPanel {

	// JButtons
	private JButton[][] board;
	private JButton quitButton;
	private JButton resetButton;
	private JButton newGameButton;

	// JPanels
	private JPanel center;
	private JPanel top;
	private JPanel bottom;

	// ImageIcons
	private ImageIcon flagIcon;
	private ImageIcon mineIcon;

	// JLabels
	private JLabel wins;
	private JLabel losses;

	private Cell iCell;
	private int boardSize;
	private int numMines;

	// the game
	private MineSweeperGame game;

	public MineSweeperPanel(int boardSize, int numMines) {

		this.boardSize = boardSize;
		this.numMines = numMines;

		// create panels
		center = new JPanel();
		top = new JPanel();
		bottom = new JPanel();

		// create listener
		MyListener listener = new MyListener();

		// create game
		game = new MineSweeperGame(boardSize, numMines);

		// create the board
		center.setLayout(new GridLayout(boardSize, boardSize));
		top.setLayout(new BorderLayout());
		board = new JButton[boardSize][boardSize];

		// create buttons for each Cell
		for (int row = 0; row < boardSize; row++)
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = new JButton("");
				board[row][col].setPreferredSize(new Dimension(45, 35));
				board[row][col].addMouseListener(listener);
				center.add(board[row][col]);
			}

		// create three buttons
		quitButton = new JButton("Quit");
		resetButton = new JButton("Reset");
		newGameButton = new JButton("New Game");

		// add listeners
		resetButton.addMouseListener(listener);
		quitButton.addMouseListener(listener);
		newGameButton.addMouseListener(listener);

		// add to bottom panel
		bottom.add(quitButton);
		bottom.add(resetButton);
		bottom.add(newGameButton);

		// create labels on top panel
		wins = new JLabel("Wins: " + game.getWinCount());
		losses = new JLabel("Losses: " + game.getLossCount());
		top.add(new JLabel("MineSweeper"));
		top.add(wins);
		top.add(losses);

		// set layouts for top and bottom panels
		top.setLayout(new GridLayout(3, 1));
		bottom.setLayout(new GridLayout(3, 1));

		// add all panels to contentPane
		add(top, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		// create icons to be used
		flagIcon = new ImageIcon("red-flag.png");
		mineIcon = new ImageIcon("mine.png");

		displayBoard();

	}

	/***************************************************************************
	 * Displays the cells with correct numbers or icons (flag/ mine icons)
	 **************************************************************************/
	private void displayBoard() {

		// find MineCount/ number of neighbors for each Cell
		game.findNeighbors();

		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++) {

				iCell = game.getCell(r, c);

				board[r][c].setIcon(null);
				board[r][c].setText("");
				
				// if a flag, give a flag Icon
				if (iCell.isFlagged()) {
					board[r][c].setIcon(flagIcon);

					// set blank text to ensure nothing gets written on it
					board[r][c].setText("");
				}

				// Set mine icon
				if ((iCell.isMine() && iCell.isExposed()) || (iCell.isMine() && game.getGameStatus() != GameStatus.NotOverYet)) {
					// remove flag icon if needed
					board[r][c].setIcon(null);
					board[r][c].setIcon(mineIcon);

					// set blank text to ensure nothing gets written on it
					board[r][c].setText("");
				}


				if (iCell.isExposed() && !iCell.isFlagged()) {
					board[r][c].setEnabled(false);
					if (iCell.getMinecount() > 0 && !iCell.isMine())

						// put mineCount on exposed Cells with neighbor mines
						board[r][c].setText("" + iCell.getMinecount());
				} else
					board[r][c].setEnabled(true);

			} 
	}
		
	/***************************************************************************
	 * Checks the game status and shows an appropriate message when game is
	 * lost/won.
	 **************************************************************************/
		private void checkStatus () {
			
			// if game is lost
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null, 
						"You Lose. \n The game will reset.");

				// update the number of games lost
				game.incrementLossCount();
				losses.setText("Losses: " + game.getLossCount());
				game.reset();
				displayBoard();

			}

			// if game is won
			if (game.getGameStatus() == GameStatus.Won) {
				displayBoard();
				JOptionPane
						.showMessageDialog(null,
								"You Win: all mines have been found!\n The game will reset");

				// update number of games won
				game.incrementWinCount();
				wins.setText("Wins: " + game.getWinCount());
				game.reset();
				displayBoard();
			}
		
		}
	

	/***************************************************************************
	 * Contains actions to take when any button is clicked.
	 **************************************************************************/
	private class MyListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// leave blank- unneeded
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// leave blank- unneeded
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// leave blank- unneeded
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// leave blank- unneeded
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			// if left-click
			if (e.getButton() == MouseEvent.BUTTON1) {

				for (int r = 0; r < boardSize; r++)
					for (int c = 0; c < boardSize; c++)

						// if it is a Cell button
						if (board[r][c] == e.getSource())
							game.select(r, c);

				displayBoard();
				checkStatus();
				
				// if "Quit" is clicked
				if (quitButton == e.getSource()) {

					// exit if they confirm "Yes", do nothing if they say "No"
					if (JOptionPane
							.showConfirmDialog(
									null,
									"Are you sure you want to quit the game? You will lose all progress.",
									"WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}

				// if "Reset" is clicked, reset the board
				if (resetButton == e.getSource()) {
					if (JOptionPane
							.showConfirmDialog(
									null,
									"Are you sure you want to reset the game? You will lose all progress.",
									"WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						game.reset();
						displayBoard();
					}
					
				}

				// if "New Game" is clicked, start a new completely new game
				if (newGameButton == e.getSource()) {
					JOptionPane.showMessageDialog(null,
							"A new game will start in a new window.");
					String[] args = {};
					MineSweeper.main(args);

				}

			}

			// if right-click
			if (e.getButton() == MouseEvent.BUTTON3) {

				for (int r = 0; r < boardSize; r++) {
					for (int c = 0; c < boardSize; c++) {

						Cell iCell = game.getCell(r, c);

						// if button clicked is a Cell
						if (board[r][c] == e.getSource())

							// if is not flagged yet, flag it
							if (!iCell.isFlagged() && !iCell.isExposed())
								iCell.setFlagged(true);

							// if is already flagged, unflag it
							else
								iCell.setFlagged(false);					

					}
				}
				displayBoard();
				game.setGameStatus();
				checkStatus();
			}
			
			// update board
			displayBoard();
			
		}
	}
}
