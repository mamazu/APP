package breakthroughPP;

import breakthroughPP.gui.MenuGUI;
import breakthroughPP.preset.Position;

import javax.swing.*;
import java.io.File;
import java.net.URL;

/**
 * Main executable class for AGOS
 *
 * @author faulersack
 */
public final class SplinterTheOmniscientRat {
    public final static boolean isJar;
    public static boolean DEBUG = false;

    static {
        isJar = SplinterTheOmniscientRat.class.getResource("SplinterTheOmniscientRat.class").toString().startsWith("jar:");
    }

    private SplinterTheOmniscientRat() {}

    /**
     * Main function
     */
    public static void main(String args[]) {
        int[] size;
        boolean console = false;
        DEBUG = false;

        if (args.length != 0) {
            switch (args[0]) {
                case "-v":
                case "--version":
                    System.out.println("A Game of Stones v 1.1.3 r163");
                    return;
                case "-h":
                case "--help":
                    System.out.println("\nPlay AGoS: [-d|--debug] [width height]");
                    System.out.println("\twidth must be 2-" + Position.getAlphabet().length() + ", height 6-" + Position.getAlphabet().length());
                    System.out.println("\tSpecify width and height to play in console,\n\tif not set the GUI is launched by default");
                    System.out.println("-v|--version\tfor version info");
                    System.out.println("-h|--help   \tfor this help");
                    System.out.print("\n");
                    return;
                case "-d":
                case "--debug":
                    DEBUG = true;
            }
        }

        if (args.length > 1 + (DEBUG ? 1 : 0)) {
            try {
                if (DEBUG) size = new int[]{Integer.parseInt(args[1]), Integer.parseInt(args[2])};
                else size = new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1])};
            } catch (Exception e) {
                System.out.println("Invalid width or height specified");
                size = new int[]{8, 8};
            }
            console = true;
        } else size = new int[]{8, 8};

        if (console) {
			//launch game with two console players
			try{
				Game game = new Game(false, size[0], size[1], Game.PLAYER_CONSOLE, Game.PLAYER_CONSOLE);
				if(game.play(-1)==Game.PLAY_ERROR)
					System.err.println("game.play encountered an error");
				game.shutdown();
			}catch(Exception e){
				if(DEBUG) e.printStackTrace();
				else System.out.println("An error occurred :/");
			}
        } else {
            //DEFAULT->GUI
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new MenuGUI();
                frame.pack();
                frame.setVisible(true);
            });
        }
    }

    /**
	* Retrieve URL for file within JAR or FS
	* @param file Filename within raw subfolder
	*/
	public static URL get(String file){
		try{
			if(isJar) return SplinterTheOmniscientRat.class.getResource("/raw/" +file);
			else return new File("raw/" +file).toURI().toURL();
		}catch(Exception e){
			if(DEBUG)
				e.printStackTrace();
			return null;
		}
	}
}
