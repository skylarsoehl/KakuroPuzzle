import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents the board of a Kakuro Puzzle which is made up of tiles.
 * This class is able to clear itself, return a string representation of itself,
 * and create clues for when a user hovers into an open tile.
 * 
 * @author skyso, matthewri
 * @version 1.0
 * @since 11-16-2017
 */
public class Board extends JPanel {
	private int row, column;
	private Tile[][] tiles;
	private GameLogic gameLogic;
	private EventHandler eh;
	private JLabel statusLabel;

	/**
	 * This method constructs a board from a given string of tiles and the
	 * dimensions from "row" and "columns." This method also initializes the status
	 * bar where the board will display clues to the user.
	 * 
	 * @param rows
	 *            - an integer representing the number of rows in the board
	 * @param columns
	 *            - an integer representing the number of columns in the board
	 * @param inString
	 *            - a string representation of the tiles to be placed in the board
	 * @param statusLabel
	 *            - a JLabel that will display clues to the user
	 */
	public Board(int rows, int columns, String inString, JLabel statusLabel) {
		row = rows;
		column = columns;
		this.statusLabel = statusLabel; //will display the clues
		eh = new EventHandler(); // handles key input

		setSize(600, 600);
		setBackground(Color.YELLOW); //will let us know if the panels are too small
		setLayout(new GridLayout(row, column));
		tiles = new Tile[row][column];

		StringTokenizer tIzer = new StringTokenizer(inString, "\t");
		for (int i = 0; i < row; i++) { //board is like a matrix, iterate through 2-d
			for (int j = 0; j < column; j++) {
				String token = tIzer.nextToken(); // goes through all the tokens
				if (token.contains("\\")) { // backslash sum tile signature
					StringTokenizer tIzer2 = new StringTokenizer(token, "\\"); //splits the horizontal and vert sum
					SumTile sumTile = new SumTile(tIzer2.nextToken(), tIzer2.nextToken(), i, j);
					add(sumTile);
					tiles[i][j] = sumTile;
				} else {
					OpenTile openTile = new OpenTile(token, i, j);
					openTile.addMouseListener(eh); // to send clues
					add(openTile);
					tiles[i][j] = openTile;
				}
			}
		}
		gameLogic = new GameLogic(tiles);

		setBorder(BorderFactory.createLineBorder(Color.black, 6));
	}

	/**
	 * This method clears any input in the open tiles of the board
	 */
	public void clearBoard() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (tiles[i][j] instanceof OpenTile) {
					((OpenTile) tiles[i][j]).clearText();
					OpenTile newTile = new OpenTile("0", i, j); /* 0 = empty open tile for toString reading 
					                                             * and reading in text files
					                                             */
					newTile.addMouseListener(eh);
					tiles[i][j] = newTile;
				}
			}
		}
	}

	/**
	 * This method creates a string representation of this board
	 * 
	 * @return returns a string representation of this board
	 */
	public String toString() {
		String resultStr = "";

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				resultStr = resultStr + tiles[i][j].toString();
			}
			resultStr = resultStr + "\n"; // new row = new line
		}
		return resultStr;
	}

	/**
	 * This class handles when the user hovers into an open tile and displays the
	 * clue for that tile
	 * 
	 * @author skyso, matthewri
	 * @version 1.0
	 * @since 11-16-2017
	 *
	 */
	private class EventHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		/**
		 * This method displays the clues to the user based on the open tile they hover
		 * in.
		 * 
		 * @param arg0
		 *            - a MouseEvent that fires when the user enters an open tile
		 */
		@Override
		public void mouseEntered(MouseEvent arg0) {
			OpenTile t = (OpenTile) arg0.getSource();
			ArrayList<Integer> tileSet = gameLogic.calcTile(t.rowInd, t.colInd);
			String clue = tileSet.toString();
			clue = clue.replace('[', ' '); //remove array brackets
			clue = clue.replace(']', ' ');
			statusLabel.setText("Your clue is : " + clue);
			if (!t.getText().equals("")) {
				if (t.getText().length() == 1)
					tileSet.add(Integer.parseInt(t.getText()));
				else
					tileSet.add(Integer.parseInt(Character.toString(t.getText().charAt(0))));
			}
			t.setValid(tileSet);

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
