public class CellAutomat{
	private boolean[][] board;
	private int width, height;
	private ImageLoader image;
	private int time = 0;

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

	private void setBoard(boolean[][] newBoard){
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

	private void calcNext(){
		time++;
		for(int i=0; i < board.length; i++){
			for(int j=0; j<board[i].length; j++){
				int neighbours = countNeighbours(i, j);
				board[i][j] = (neighbours % 2 == 1);
			}
		}
	}

	private int countNeighbours(int i, int j){
		int count = 0;
		for(int x = i - 1; x < i + 2; x++){
			for (int y =j - 1; y < y + 2; y++) {
				if(i==x && j==y)
					continue;
				count += (board[(height+i)%height][(width+j)%width])?1:0;
			}
		}
		return count;
	}

	public Dimension getSize(){
		//int size = image.getDimension();
		new Dimension();
	}

}