import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

/**
 * This class is able to register and load custom fonts for use with JPanels
 * 
 * @author skyso, matthewri
 * @version 1.0
 * @since 11-16-17
 *
 */
public class Fonts {
	private static ArrayList<Fonts> fontList = new ArrayList<Fonts>();
	
	private static String fontPath;
	
	/**
	 * This method constructs a custom font by setting the font path and registering
	 * the font
	 * 
	 * @param filePath - a string representing the specific file name of the custom
	 * font
	 */
	public Fonts(String filePath) {
		Fonts.fontPath = "./Resources/fonts/" + filePath; //points to resources folder in project
		registerFont();
	}
	
	/**
	 * This method registers a custom font so it can be used during runtime
	 */
	private void registerFont() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method adds a custom font to an ArrayList of Fonts
	 * 
	 * @param font - a Fonts object representing the custom font
	 */
	private static void addFont(Fonts font) {
		fontList.add(font);
	}
	
	/**
	 * This method loads the fonts by these fonts to an ArrayList of fonts
	 */
	public static void loadFonts() {
		Fonts.addFont(new Fonts("ALBAS___.TTF"));
		Fonts.addFont(new Fonts("Rakoon_PersonalUse.ttf"));
		Fonts.addFont(new Fonts("Advert-Regular.ttf"));
		Fonts.addFont(new Fonts("CaviarDreams.ttf"));
		Fonts.addFont(new Fonts("vinilo.ttf"));
	}
	
	
}
