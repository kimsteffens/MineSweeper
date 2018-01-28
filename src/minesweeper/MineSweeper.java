package minesweeper;

import javax.swing.*;

/*******************************************************************************
 * MineSweeper starts up the game, by first asking the user for a board size.
 * 
 * @author Kimberlin Steffens
 * @version 16 February 2016
 ******************************************************************************/
public class MineSweeper {
	public static void main(String[] args) {
		int boardSize = 10;
		int numMines = 10;

		// ask for board size
		String x = JOptionPane.showInputDialog(null,
				"Enter in the size of the board. Choose a number between 3 and 20: ");
		try {
			boardSize = Integer.parseInt(x);
			if (boardSize < 3 || boardSize > 20) {
				JOptionPane.showMessageDialog(null,
						"You can only enter a number between 3 and 20. Board Size will be set to 10.", "Warning",
						JOptionPane.WARNING_MESSAGE);
				boardSize = 10;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"You can only enter a number between 3 and 20. Board Size will be set to 10.", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}

		// ask for number of mines
		String y = JOptionPane.showInputDialog(null, "Enter in the number of mines ");

		try {
			numMines = Integer.parseInt(y);
			if (numMines < 0 || numMines > (boardSize * boardSize)) {
				JOptionPane.showMessageDialog(null,
						"That is not a valid number of mines. Mine number will be set to default.", "Warning",
						JOptionPane.WARNING_MESSAGE);
				if (boardSize > 3)
					numMines = 10;
				else
					numMines = 3;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"That is not a valid number of mines. Mine number will be set to default.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			if (boardSize > 3)
				numMines = 10;
			else
				numMines = 3;

		}

		// start up the game
		JFrame frame = new JFrame("MineSweeper!");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel(boardSize, numMines);

		frame.getContentPane().add(panel);

		if (boardSize > 10) {
			frame.setSize(1300, 900);
		} else if (boardSize > 5) {
			frame.setSize(800, 800);
		} else {
			frame.setSize(500, 300);
		}

		frame.setVisible(true);

	}
}
