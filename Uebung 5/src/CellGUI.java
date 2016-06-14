import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

class CellGUI{
    protected JPanel panel;
    protected JFrame frame;

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
        frame.add(top);

        JLabel rounds = new JLabel("Rounds");
        top.add(rounds);

        JTextfield roundCount = new JTextfield();
        top.add(roundCount);

        JButton start = new JButton("Start");
        top.add(start);

    }

    public void pack(){
        frame.pack();
        frame.setVisible(true); 
    }

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
    	CellAutomat t = new CellAutomat(32);
        CellGUI gui = new CellGUI()
        gui.loadComponents(fileName);
        gui.pack();
	}

}

class Board extends AppDrawPanel{

}
  
