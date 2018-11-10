import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * This class represents a Kakuro Puzzle game which is able to display clues to
 * a user, save the current state of a game, start a new game, open a saved game
 * and allow the user to continue playing, or exit and allow the user to
 * "continue" where they left off
 * 
 * @author skyso, matthewri
 * @version 1.0
 * @since 11-16-2017
 * 
 *        DECLARATION: All code submitted is our (Skylar Soehl, Matthew Ritchie)
 *        own and does not violate the UNCW Academic Honor Code.
 *
 */
public class KakuroPuzzle extends JFrame {
	private Container cp;
	private JPanel statusBar;
	private JLabel statusLabel;
	private JPanel titleCard;
	private JLabel title;

	private JMenuBar menuBar;
	private JMenu gameMenu;
	private EventHandler eh;
	private JFileChooser fileChooser;
	
	private Fonts aFont;

	private Board board;

	/**
	 * This method creates a new, visible Kakuro Puzzle game
	 * 
	 * @param args
	 *            - an array of command-line arguments which are strings
	 */
	public static void main(String[] args) {
		KakuroPuzzle kp = new KakuroPuzzle();
		kp.setVisible(true);
	}

	/**
	 * This method constructs a Kakuro Puzzle where a user has the ability to play
	 * the Kakuro Puzzle game that gives the user hints, save the current state of
	 * the game, open a previous game state, and exit and continue a previous game
	 * without saving
	 */
	public KakuroPuzzle() {
		setTitle("Kakuro Puzzle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		aFont.loadFonts();

		eh = new EventHandler();
		fileChooser = new JFileChooser();

		titleCard = createTitleCard(); //title for game
		cp.add(titleCard, BorderLayout.NORTH);
		statusBar = createStatusBar();

		cp.add(statusBar, BorderLayout.SOUTH);

		buildMenu();
		pack();
	}
	
	
	private JPanel createTitleCard()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		title = new JLabel("Kakuro Puzzle");
		Font rakoon = new Font("Rakoon Personal Use", Font.PLAIN, 100);
		title.setFont(rakoon);
		title.setIcon(new ImageIcon("./Resources/pictures/abg.jpg")); // sets background image of title
		title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); //centers text and bg
		title.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		panel.add(title, BorderLayout.CENTER);
		
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		return panel;
	}

	/**
	 * This method creates a status bar which displays clues to the user
	 *
	 * @return returns a new JPanel, panel which displays clues
	 */
	private JPanel createStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		statusLabel = new JLabel("Ready");
		Font alba = new Font("Alba Super", Font.PLAIN, 30);
		statusLabel.setFont(alba);
		statusLabel.setForeground(Color.white); //makes font white
		panel.setBackground(Color.gray);
		panel.add(statusLabel, BorderLayout.CENTER);

		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		return panel;
	}

	/**
	 * This method creates a menu with a "Game" submenu that has the ability to load
	 * a new puzzle, save the current state of a puzzle, reload a previous game
	 * state, or exit the program and continue from the last state of the game.
	 * 
	 */
	private void buildMenu() {
		Color color = new Color(170,242,255); //custom color
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.gray);
		gameMenu = new JMenu("Game");
		Font advert = new Font("Advert", Font.PLAIN, 30); //create new font
		gameMenu.setFont(advert);
		gameMenu.setForeground(Color.white); //sets font white
		
		
		JMenuItem menuItem = new JMenuItem("New");
		menuItem.addActionListener(eh);
		Font caviar = new Font("Caviar Dreams", Font.PLAIN, 20);
		menuItem.setFont(caviar);
		menuItem.setBackground(color);
		gameMenu.add(menuItem);

		menuItem = new JMenuItem("Continue");
		menuItem.addActionListener(eh);
		menuItem.setFont(caviar);
		menuItem.setBackground(color);
		gameMenu.add(menuItem);

		menuItem = new JMenuItem("Read");
		menuItem.addActionListener(eh);
		menuItem.setFont(caviar);
		menuItem.setBackground(color);
		gameMenu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(eh);
		menuItem.setFont(caviar);
		menuItem.setBackground(color);
		gameMenu.add(menuItem);

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(eh);
		menuItem.setFont(caviar);
		menuItem.setBackground(color);
		gameMenu.add(menuItem);

		menuBar.add(gameMenu);
		setJMenuBar(menuBar);
	}

	/**
	 * This class handles events related to when a user selects an option in the
	 * menu
	 * 
	 * @author skyso, matthewri
	 * @version 1.0
	 * @since 11-16-2017
	 *
	 */
	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals("Exit")) {
				statusLabel.setText("Exiting");

				File outFile = new File("Resources/auto.txt"); //auto saves the very last state of the came
				PrintWriter textStream;

				try {
					textStream = new PrintWriter(outFile);
					textStream.print(board);
					textStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				System.exit(0);
			}

			if (arg0.getActionCommand().equals("Continue")) {
				int row = 0;
				int column = 0; //opens what was auto saved
				String fileStr = "";
				Scanner s = null;
				File inFile = new File("Resources/auto.txt");

				try {
					FileInputStream fis = new FileInputStream(inFile);
					s = new Scanner(fis);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				while (s.hasNextLine()) { //reads auto save file
					row++;
					String line = s.nextLine();
					StringTokenizer tIzer = new StringTokenizer(line, "\t");
					column = tIzer.countTokens();

					while (tIzer.hasMoreTokens()) {
						fileStr = fileStr + tIzer.nextToken() + "\t";
					}

				}

				if (board != null)
					cp.remove(board);
				board = new Board(row, column, fileStr, statusLabel);
				cp.add(board, BorderLayout.CENTER);
				pack();

				s.close();
			}

			if (arg0.getActionCommand().equals("Save")) {
				System.out.println(board);
				int returnVal = fileChooser.showSaveDialog(KakuroPuzzle.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File outFile = fileChooser.getSelectedFile();
					PrintWriter textStream;

					try {
						textStream = new PrintWriter(outFile);
						textStream.print(board);
						textStream.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}

			if (arg0.getActionCommand().equals("New")) {
				statusLabel.setText("Ready");
				board.clearBoard();
			}

			if (arg0.getActionCommand().equals("Read")) {
				int returnVal = fileChooser.showOpenDialog(KakuroPuzzle.this);
				int row = 0;
				int column = 0;
				String fileStr = "";

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File inFile = fileChooser.getSelectedFile();
					Scanner s = null;

					try {
						FileInputStream fis = new FileInputStream(inFile);
						s = new Scanner(fis);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

					while (s.hasNextLine()) {
						row++;
						String line = s.nextLine();
						StringTokenizer tIzer = new StringTokenizer(line, "\t");
						column = tIzer.countTokens();

						while (tIzer.hasMoreTokens()) {
							fileStr = fileStr + tIzer.nextToken() + "\t";
						}

					}

					if (board != null)
						cp.remove(board);
					board = new Board(row, column, fileStr, statusLabel);
					cp.add(board, BorderLayout.CENTER);
					pack();

					s.close();
				}
			}

		}

	}
}
