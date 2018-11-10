
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * This class creates the sum tiles of a Kakuro Puzzle which hold the vertical
 * and horizontal sum and has the ability to return its horizontal and vertical
 * sum, as well as return a string representation of itself
 * 
 * @author skyso, matthewri
 * @version 1.0
 * @since 11-16-2017
 *
 */
public class SumTile extends Tile {
	private Fonts aFont;
	private String verticalSum, horizontalSum;

	private JLabel sumLabel;

	/**
	 * This method creates a sum tile which is a jPanel that contains a jLabel that
	 * displays the vertical and horizontal sums that the user must use to solve the
	 * puzzle
	 * 
	 * @param leftSum
	 *            - a string representing the vertical sum
	 * @param rightSum
	 *            - a string representing the horizontal sum
	 * @param rowIndex
	 *            - an int representing the row index of this SumTile
	 * @param columnIndex
	 *            - an int representing the column index of this SumTile
	 */
	public SumTile(String leftSum, String rightSum, int rowIndex, int columnIndex) {
		rowInd = rowIndex;
		colInd = columnIndex;
		this.verticalSum = leftSum;
		this.horizontalSum = rightSum;
		
		Color color = new Color(227,218,3);
		this.setBackground(color);
		this.setPreferredSize(new Dimension(tileSize, tileSize));
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		aFont.loadFonts();
		Font sumFont = new Font("Vinilo", Font.PLAIN, 30);

		sumLabel = new JLabel(leftSum + " \\ " + rightSum);
		sumLabel.setFont(sumFont);
		sumLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		add(sumLabel);
	}
	

	/**
	 * This method returns the vertical sum of this SumTile
	 * 
	 * @return returns the vertical sum of this SumTile
	 */
	public int getVertSum() {
		return Integer.parseInt(verticalSum);
	}

	/**
	 * This method returns the horizontal sum of this SumTile
	 * 
	 * @return returns the horizontal sum of this SumTile
	 */
	public int getHorizSum() {
		return Integer.parseInt(horizontalSum);
	}

	/**
	 * This method returns a string representation of this sum tile
	 * 
	 * @return returns a string representation of this sum tile
	 */
	public String toString() {
		return verticalSum + "\\" + horizontalSum + "\t";
	}
}
