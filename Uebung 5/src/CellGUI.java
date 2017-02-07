import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

class Board extends JPanel{

    private final CellAutomat automat;
    private final ImageLoader image;
    private final int width, height;

    /**
    * Constructor for the board with the image that represents a cell that is alive
    * @param imagePath Path to the image that is representing a living cell
    **/
    public Board(String imagePath){
        automat = new CellAutomat(32);
        image = new ImageLoader(imagePath);
        width = image.getWidth();
        height = image.getHeight();
    }

    /**
    * Paints the Board
    * @param g Graphics object
    **/
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        boolean[][] state = automat.getState();
        g.setColor(new Color(0,0,0));
        for(int i = 0; i < state.length; i++)
            for(int j = 0; j < state[i].length; j++){
                g.drawRect(i*width, j*height, width, height);
                if(state[i][j]){
                    g.drawImage(image.getImage(), i*width, j*height, null);
                }
            }
        repaint();
    }

    public void next(){
        automat.calcNext();
        repaint();
    }

    public void setPattern(String fileName){
        automat.setPattern(fileName);
    }

    public Dimension getPreferredSize() {
        if(image==null)
            return new Dimension(500, 200);
        //Getting image dimension
        return image.getDimension(automat.getSize());
    }
}

class CellGUI{
    private JPanel panel;
    private JFrame frame;
    private Board board;
    private JTextField roundCount;
    private int round=0;

    /**
    * Constrctor of the Object and setting the layout
    **/
    public CellGUI(){
        frame = new AppFrame("Allgemeines Programmierpraktikum"); 
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);
    }

    public void loadComponents(String fileName){
        //Top panel
        JPanel top = new JPanel();
        panel.add(top);

        JLabel rounds = new JLabel("Rounds");
        top.add(rounds);

        roundCount = new JTextField("200");
        roundCount.setText("2000");
        top.add(roundCount);

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                board.next();
                round++;                
                roundCount.setText(Integer.toString(round));
            }
        });
        top.add(start);

        board = new Board(fileName);
        panel.add(board);
    }

    public void setPattern(String fileName){
        board.setPattern(fileName);
    }

    public void show(){
        frame.pack();
        frame.setVisible(true); 
    }

	public static void main(String[] args) {
        if(args.length < 1){
            //Error message when no parameter is specified
            JOptionPane.showMessageDialog(null, 
                "Invalid paramerter count (one parater as the file name!)", 
                "Missing paramerter", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }        
        //starts the application
        CellGUI gui = new CellGUI();
        gui.loadComponents(args[0]);
        if(args.length == 2){
           gui.setPattern(args[1]);
        }
        gui.show();
	}

}
  
