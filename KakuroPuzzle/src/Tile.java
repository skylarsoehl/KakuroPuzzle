import javax.swing.JPanel;

/**
 * This class represents a tile of size 40 pixels where each tile contains a row
 * index and column index, and contains the method to return a string
 * representation of itself
 * 
 * @author skyso, matthewri
 * @version 1.0
 * @since 11-16-2017
 *
 */
public abstract class Tile extends JPanel {
	protected int tileSize = 70;
	protected int rowInd;
	protected int colInd;
	

	public abstract String toString(); //must have so we can write to files to save games
}
