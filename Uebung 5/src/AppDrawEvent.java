import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

class AppFrame extends JFrame { 
	/**
	* Constuctor of the AppFrame
	* Sets the tile and the default closing action
	* @param title Tile of the window
	**/
    public AppFrame(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } 
}

class AppDrawPanel extends JPanel {  
	private ImageLoader image = null;
	private int[][] pixels = null;
	private boolean patternMode = false;

	/**
	* Gets the prefered size of the object
	* @return dimension of the perfered size
	**/
    public Dimension getPreferredSize() {
    	if(image==null)
        	return new Dimension(500, 200);
        //Getting image dimension
        return image.getDimension(2);
    }

    /**
    * Paints the component
    * @param g Graphics object
    **/
    protected void paintComponent(Graphics g){
    	if(pixels==null)
    		return;
    	if(!patternMode){
			System.out.println("Normal Mode");
    		//Draws every pixel in a 2x2 block
	    	for(int i = 0; i<pixels.length; i++){
	    		for(int j=0; j<pixels[0].length; j++){
	    			g.setColor(new Color(pixels[i][j]));
	    			g.fillRect(i*2, j*2, (i+1)*2, (j+1)*2);
	    		}
	    	}
	    	return;
	    }
	    paintPattern(g);
    }

	/**
	* Paints the component with a specified pattern
	* @param g Graphics object
	*/
    public void paintPattern(Graphics g){
    	//Setting up pattern painting
    	g.setColor(new Color(255, 255, 255));
    	g.fillRect(0,0,pixels.length*2, pixels[0].length*2);
    	System.out.println("Pattern mode");
	    g.setColor(new Color(0,0,0));
	    //Looping through the pixels
    	for(int i = 0; i<pixels.length; i++){
    		for(int j=0; j<pixels[0].length; j++){
    			int redValue = new Color(pixels[i][j]).getRed(); //Contains the grayscale value
    			//Calcuating texture id
    			int id = 4 - (redValue / 52);
    			int x = i*2;
    			int y = j*2;
    			//Coloring the individual textures
    			if(id == 0)
    				continue;
    			if(id == 1 || id == 2 || id == 4){
    				g.fillRect(x, y, 1, 1);
    			}else{
    				g.fillRect(x+1, y, 1, 1);
    			}
    			
    			if(id > 1){
    				g.fillRect(x+1, y+1, 1, 1);
    			}
    			if(id > 2){
    				g.fillRect(x, y+1, 1, 1);	
    			}
    		}

    	}
    }

    /**
	* Loads the Image to the pixel array
	* @param fileName Name of the image file
	**/
    public void loadImage(String fileName){
    	image = new ImageLoader(fileName);
    	pixels = image.getPixels();
    	repaint();
    }

    /**
    * Reverts the diplayed image back to normal
    **/
    public void setOriginal(){
    	pixels = image.getPixels();
    	patternMode = false;
    	repaint();
    }

    /**
    * Displays the grayscale version of the image
    */
    public void setGrayScale(){
    	for(int i=0; i < pixels.length; i++)
			for(int j=0; j< pixels[0].length; j++){
				Color c =new Color(pixels[i][j]);
				//Calculating the average
				int avg = (c.getRed()+c.getGreen()+c.getBlue())/3;
				c = new Color(avg, avg, avg);
				//Saving the new value into the array
				pixels[i][j] = c.getRGB();
			}
		patternMode = false;
		repaint();
    }

    /**
    * Draws the pattern version of the image
    */
    public void setPattern(){
    	setGrayScale();
    	patternMode = true;
    	repaint();
    }

}

public class AppDrawEvent{ 
	//Main method
	public static void main(String[] args) {
    	if(args.length != 1){
    		//Error message when no parameter is specified
    		JOptionPane.showMessageDialog(null, 
    			"Invalid paramerter count (one parater as the file name!)", 
    			"Missing paramerter", 
    			JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	//starts the application
    	APPExercise t = new APPExercise();
    	t.loadComponents(args[0]);
    	t.show();
	}
}