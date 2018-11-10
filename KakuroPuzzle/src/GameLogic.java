import java.util.ArrayList;

/**
 * This class is able to return the horizontal and vertical sum of an open tile,
 * keep track of the row and column index of an open tile, and generate the
 * possible set of numbers that a user could enter for a specific open tile.
 * 
 * @author skyso, matthewri
 * @version 1.0
 * @since 11-16-2017
 *
 */
public class GameLogic {
	private SumGenerator sumGenerator;
	private Tile[][] board;

	/**
	 * This method constructs this GameLogic object which uses a SumGenerator object
	 * to create the clue for an open tile
	 * 
	 * @param board
	 *            - a 2-d Array representing the tiles in a board
	 */
	public GameLogic(Tile[][] board) {
		sumGenerator = new SumGenerator();
		this.board = board.clone();

	}

	/**
	 * This method returns the horizontal sum of an open tile
	 * 
	 * @param rowInd
	 *            - an int representing the the row index of this open tile
	 * @param colInd
	 *            - an int representing the column index of this open tile
	 * 
	 * @return returns an int representing the horizontal sum of an open tile
	 */
	private int getHorizTarget(int rowInd, int colInd) {
		for (int j = colInd; j >= 0; j--) { //iterating through col = left to right (row)
			if (board[rowInd][j] instanceof SumTile) { //iterate until we hit sumtile
				Tile t = board[rowInd][j];
				SumTile st = (SumTile) t;
				return st.getHorizSum(); 
			}
		}
		return 0;
	}

	/**
	 * This method returns the vertical sum of an open tile
	 * 
	 * @param rowInd
	 *            - an int representing the the row index of this open tile
	 * @param colInd
	 *            - an int representing the column index of this open tile
	 * 
	 * @return returns an int representing the vertical sum of an open tile
	 */
	private int getVertTarget(int rowInd, int colInd) {
		for (int i = rowInd; i >= 0; i--) {
			if (board[i][colInd] instanceof SumTile) {
				Tile t = board[i][colInd];
				SumTile st = (SumTile) t;
				return st.getVertSum();
			}
		}
		return 0;
	}

	/**
	 * This method returns the number of open tiles in a specific row of this board
	 * 
	 * @param rowInd
	 *            - an int representing the the row index of this open tile
	 * @param colInd
	 *            - an int representing the column index of this open tile
	 * 
	 * @return returns an int representing the number of open tiles in a specific
	 *         row in this board
	 */
	private int countRow(int rowInd, int colInd) {
		int count = 0;

		for (int j = colInd; j >= 0; j--) { //iterate right to left
			if (board[rowInd][j] instanceof OpenTile) //counts open tiles in a row
				count++;
			else
				break;
		}

		for (int j = colInd + 1; j < board[rowInd].length; j++) {
			if (board[rowInd][j] instanceof OpenTile)
				count++;
			else
				break;
		}

		return count;
	}

	/**
	 * This method returns the number of open tiles in a specific column of this
	 * board
	 * 
	 * @param rowInd
	 *            - an int representing the the row index of this open tile
	 * @param colInd
	 *            - an int representing the column index of this open tile
	 * 
	 * @return returns an int representing the number of open tiles in a specific
	 *         column in this board
	 */
	private int countColumn(int rowInd, int colInd) {
		int count = 0;

		for (int i = rowInd; i >= 0; i--) {
			if (board[i][colInd] instanceof OpenTile)
				count++;
			else
				break;
		}

		for (int i = rowInd + 1; i < board.length; i++) {
			if (board[i][colInd] instanceof OpenTile)
				count++;
			else
				break;
		}

		return count;
	}

	/**
	 * This method calculates the possible values that could be entered in this open
	 * tile based on its horizontal and vertical sums
	 * 
	 * @param rowInd
	 *            - an int representing the row index of this open tile
	 * @param colInd
	 *            - an int representing the column index of this open tile
	 * 
	 * @return returns an ArrayList of integers that represent all the possible
	 *         values that can be entered in this open tile
	 */
	public ArrayList<Integer> calcTile(int rowInd, int colInd) {
		int rowCount = countRow(rowInd, colInd);
		int colCount = countColumn(rowInd, colInd);

		int rowTarget = getHorizTarget(rowInd, colInd);
		int colTarget = getVertTarget(rowInd, colInd);

		ArrayList<ArrayList<Integer>> rowSums = sumGenerator.genSums(rowTarget, rowCount); //need to find intersection
		ArrayList<ArrayList<Integer>> colSums = sumGenerator.genSums(colTarget, colCount);

		ArrayList<Integer> rowInput = getRowInput(rowInd, colInd);
		ArrayList<Integer> colInput = getColumnInput(rowInd, colInd);

		ArrayList<Integer> possibleRow;
		ArrayList<Integer> possibleColumn;

		if (rowInput.size() > 0) //if there are values already inputed by user
			possibleRow = possibleVals(rowSums, rowInput);
		else
			possibleRow = sumGenerator.coalesce(rowSums); //if all open tiles blank

		if (colInput.size() > 0)
			possibleColumn = possibleVals(colSums, colInput);
		else
			possibleColumn = sumGenerator.coalesce(colSums);

		ArrayList<Integer> result = intersect(possibleRow, possibleColumn);

		for (int i = 0; i < result.size(); i++) {
			if (rowInput.contains(result.get(i)) || colInput.contains(result.get(i))) {
				result.remove(i); //remove the repeats
				i = 0;
			}
		}
		return result;
	}

	/**
	 * This method keeps track and returns all the values that a user enters in this
	 * row of open tiles
	 * 
	 * @param rowInd
	 *            - an int representing the row index of this open tile
	 * @param colInd
	 *            - an int representing the column index of this open tile
	 * 
	 * @return returns an ArrayList of integers that represent all the values
	 *         entered in this row of open tiles
	 */
	private ArrayList<Integer> getRowInput(int rowInd, int colInd) {
		ArrayList<Integer> rowInput = new ArrayList<Integer>();

		for (int j = colInd; j >= 0; j--) {
			if (board[rowInd][j] instanceof OpenTile) {
				OpenTile t = (OpenTile) board[rowInd][j];
				if (!t.getText().equals("")) {
					if (t.getText().length() == 1)
						rowInput.add(Integer.parseInt(t.getText()));
					else
						rowInput.add(Integer.parseInt(Character.toString(t.getText().charAt(0)))); /* if someone holds
																								    * a key = saves 
																								    * first instance
																								    */
					
				}
			} else
				break;
		}

		for (int j = colInd + 1; j < board[rowInd].length; j++) {
			if (board[rowInd][j] instanceof OpenTile) {
				OpenTile t = (OpenTile) board[rowInd][j];
				if (!t.getText().equals("")) {
					if (t.getText().length() == 1)
						rowInput.add(Integer.parseInt(t.getText()));
					else
						rowInput.add(Integer.parseInt(Character.toString(t.getText().charAt(0))));
				}
			} else
				break;
		}
		return rowInput;
	}

	/**
	 * This method keeps track and returns all the values that a user enters in this
	 * column of open tiles
	 * 
	 * @param rowInd
	 *            - an int representing the row index of this open tile
	 * @param colInd
	 *            - an int representing the column index of this open tile
	 * 
	 * @return returns an ArrayList of integers that represent all the values
	 *         entered in this column of open tiles
	 */
	private ArrayList<Integer> getColumnInput(int rowInd, int colInd) {
		ArrayList<Integer> columnInput = new ArrayList<Integer>();

		for (int i = rowInd; i >= 0; i--) {
			if (board[i][colInd] instanceof OpenTile) {
				OpenTile t = (OpenTile) board[i][colInd];
				if (!t.getText().equals("")) {
					if (t.getText().length() == 1)
						columnInput.add(Integer.parseInt(t.getText()));
					else
						columnInput.add(Integer.parseInt(Character.toString(t.getText().charAt(0))));
				}
			} else
				break;
		}

		for (int i = rowInd + 1; i < board.length; i++) {
			if (board[i][colInd] instanceof OpenTile) {
				OpenTile t = (OpenTile) board[i][colInd];
				if (!t.getText().equals("")) {
					if (t.getText().length() == 1)
						columnInput.add(Integer.parseInt(t.getText()));
					else
						columnInput.add(Integer.parseInt(Character.toString(t.getText().charAt(0))));
				}
			} else
				break;
		}

		return columnInput;
	}

	/**
	 * This method returns all the possible values for a sum that were not already
	 * entered by the user
	 * 
	 * @param partials
	 *            - a 2-d ArrayList of integers representing the partial sum of a
	 *            target value
	 * @param input
	 *            - a 2-d ArrayList of integers representing the values in a row or
	 *            column of open tiles that were already entered by the user
	 * 
	 * @return returns an ArrayList of integers that represent ll the possible
	 *         values for a sum that were not already entered by the user
	 */
	private ArrayList<Integer> possibleVals(ArrayList<ArrayList<Integer>> partials, ArrayList<Integer> input) {
		for (int i = 0; i < input.size(); i++) {
			for (int j = 0; j < partials.size(); j++) {
				ArrayList<Integer> partial = partials.get(j);
				int value = input.get(i);

				if (!partial.contains(value)) { //eliminates sets with numbers already used
					partials.remove(j);
					j = 0;
				}
			}
		}

		ArrayList<Integer> result = sumGenerator.coalesce(partials);

		for (int i = 0; i < result.size(); i++) {
			if (input.contains(result.get(i))) {
				result.remove(i);
				i = 0;
			}
		}

		return result;
	}

	/**
	 * This method returns all the values that are found in both ArrayList "one" and
	 * ArrayList "two"
	 * 
	 * @param one
	 *            - an ArrayList of numbers representing the values that add up to
	 *            one target value
	 * @param two
	 *            - an ArrayList of numbers representing the values that add up to a
	 *            second target value
	 * 
	 * @return returns an ArrayList of integers that are found both ArrayList "one"
	 *         and ArrayList "two"
	 */
	private ArrayList<Integer> intersect(ArrayList<Integer> one, ArrayList<Integer> two) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		for (int oneVal : one) {
			if (two.contains(oneVal))
				result.add(oneVal);
		}

		return result;
	}
}
