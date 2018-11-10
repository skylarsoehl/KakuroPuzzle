import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * This class represents an open tile that has the ability to create a string
 * representation of itself, handle user input, return a value entered in this
 * tile by the user, clear itself. This class also only saves user input that
 * satisfies the constraints of this tile's horizontal and vertical sum
 * 
 * @author skyso, matthewri
 * @version 1.0
 * @since 11-16-2017
 *
 */
public class OpenTile extends Tile {
	private Fonts aFont;
	private JTextField textField;
	private int tfSize = 3;
	private KeyHandler keyHandler;
	private ArrayList<Integer> validValues;

	/**
	 * This method constructs this OpenTile object with a row and column index, as
	 * well as a label that is initially blank
	 * 
	 * @param value
	 *            - a string representing the value in this OpenTile
	 * @param rowIndex
	 *            - an int representing the row index of this OpenTile
	 * @param columnIndex
	 *            - - an int representing the column index of this OpenTile
	 */
	public OpenTile(String value, int rowIndex, int columnIndex) {
		rowInd = rowIndex;
		colInd = columnIndex;
		this.setBackground(Color.white);
		keyHandler = new KeyHandler();
		aFont.loadFonts();
		Font tfFont = new Font("Advert", Font.PLAIN, 30);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));

		if (value.equals("0"))
			textField = new JTextField("", tfSize);
		else
			textField = new JTextField(value, tfSize);
		
		
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setFont(tfFont);
		textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textField.addKeyListener(keyHandler);
		this.add(textField);

		this.setPreferredSize(new Dimension(tileSize, tileSize));
	}
	

	/**
	 * This method returns a string representation of this open tile
	 * 
	 * @return returns a string representation of this open tile
	 */
	public String toString() {
		if (textField.getText().equals(""))
			return "0\t";

		else {
			return textField.getText() + "\t";
		}
	}

	/**
	 * This method returns the text stored in the text field this OpenTile
	 * 
	 * @return returns a string representing the text stored in this OpenTile
	 */
	public String getText() {
		return textField.getText();
	}

	/**
	 * This method clears the text stored in the text field of this OpenTile
	 */
	public void clearText() {
		textField.setText("");
	}

	@SuppressWarnings("unchecked")
	/**
	 * This method makes a copy of this ArrayList of valid numbers for an OpenTile
	 * 
	 * @param valid
	 *            - an ArrayList of ints representing the valid numbers that can go
	 *            in this OpenTile
	 */
	public void setValid(ArrayList<Integer> valid) {
		validValues = (ArrayList<Integer>) valid.clone();
	}

	/**
	 * This class handles when a user enters a value from their keyboard into this
	 * open tile
	 * 
	 * @author skyso, matthewri
	 * @version 1.0
	 * @since 11-16-2017
	 *
	 */
	public class KeyHandler implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {

		}

		@Override
		/**
		 * This method stores the value entered by the user in this open tile only if
		 * the value is an integer and satisfies the constraints of this open tile's
		 * horizontal and vertical sum
		 */
		public void keyReleased(KeyEvent arg0) {
			try {
				int value = Integer.parseInt(Character.toString(arg0.getKeyChar()));

				if (!validValues.contains(value))
					clearText();
				else
					textField.setText(Integer.toString(value));
			} catch (NumberFormatException e) {
				clearText();
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}

	}
}
