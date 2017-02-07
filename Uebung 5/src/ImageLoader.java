import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

public class ImageLoader{
	private BufferedImage img = null;

	/**
	* Constructor of the image loader
	* @param filepath String of the file name
	**/
	public ImageLoader(String filepath){
		File f = new File(filepath);
		//If the file doesnt exist => error
		if(!f.exists()){
			JOptionPane.showMessageDialog(null, 
    			"The file specified was not found", 
    			"File not Found", 
    			JOptionPane.ERROR_MESSAGE);
			System.out.println(f.getAbsolutePath());
			return;
		}
		//Try to load the image if there are any reading issues => error
		try{
			img = ImageIO.read(f);
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, 
    			"Something went wrong whilst reading the file", 
    			"IO Error", 
    			JOptionPane.ERROR_MESSAGE);
		}
	}

	public int getHeight(){return img.getHeight();}
	public int getWidth(){return img.getWidth();}

	/**
	* Gets the graphics object of the image
	* @return Graphics object of the image
	**/
	public Graphics getGraphics(){
		if(img == null){
			System.err.println("Drawing null");
			return null;
		}
		return img.getGraphics();
	}

	/**
	* Returns the pixel array of the image
	* @return Pixel array of the individual colors
	**/
	public int[][] getPixels(){
		int width = img.getWidth();
		int height = img.getHeight();
		int[][] result = new int[width][height];
		for(int i=0; i < width; i++)
			for(int j=0; j< height; j++)
				result[i][j] = img.getRGB(i,j);
		return result;
	}

	/**
	* Returns the dimension of the image with scale 1
	* @return Dimension object of the Image
	**/
	public Dimension getDimension(){
		return getDimension(1);
	}

	/**
	* Gets the dimension object based on a scale factor
	* @param scale Scale factor of the image
	* @return Dimension object
	**/
	public Dimension getDimension(int scale){
		return new Dimension(img.getWidth()*scale, img.getHeight()*scale);
	}

	/**
	* Gets an image object from the buffered image
	* @return Image of the BufferedImage
	**/
	public Image getImage(){
		return (Image) img;
	}
	
}