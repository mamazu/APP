import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

public class CellAutomat{
	private boolean[][] board;
	private int width, height;
	private ImageLoader image;
	private int time = 0;

	public CellAutomat(){
		width = 0;
		height = 0;
	}

	public CellAutomat(int size){
		board = new boolean[size][size];
		height = size;
		width = size;
		reset();
	}

	private void reset(){
		for(int i=0; i < height; i++)
			for(int j=0; j< width; j++){
				board[i][j] = false;
			}
	}

	public int setPattern(String patternFile){
		ImageLoader img = new ImageLoader(patternFile);
		height= img.getHeight();
		width = img.getWidth();
		int[][] pixels = img.getPixels();
		int cells = 0;
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				if(pixels[i][j] == Color.black.getRGB()){
					board[i][j] = true;
					cells++;
				}
		return cells;
	}

	public void setBoard(boolean[][] newBoard){
		reset();
		height = newBoard.length;
		width = 0;
		for(int i=0; i < newBoard.length; i++){
			for(int j=0; j<newBoard[i].length; j++){
				board[i][j] = newBoard[i][j];
			}
			width = Math.max(newBoard[i].length, width);
		}
	}

	public void calcNext(){
		time++;
		boolean[][] newBoard = new boolean[board.length][board[0].length];
		for(int i=0; i < board.length; i++){
			for(int j=0; j<board[i].length; j++){
				int neighbours = countNeighbours(i, j);
				newBoard[i][j] = (neighbours % 2 == 1);
			}
		}
		board = newBoard;
	}

	public boolean[][] getState(){return board;}

	private int countNeighbours(int i, int j){
		int count = 0;
		for(int x = i - 1; x < i + 2; x++){
			for (int y =j - 1; y < j + 2; y++) {
				if(i==x && j==y)
					continue;
				count += (board[(height+x)%height][(width+y)%width])?1:0;
			}
		}
		return count;
	}

	public int getSize(){
		return Math.max(width, height);
	}

}