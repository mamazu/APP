import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

public class APPExercise extends JFrame{
	protected JPanel panel;
	protected JFrame frame;

	/**
	* Constrctor of the Object and setting the layout
	**/
	public APPExercise(){
		frame = new AppFrame("Allgemeines Programmierpraktikum"); 
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		frame.add(panel);
	}

	/**
	* Sets the panel to the current pannel
	* @param p Panel that should be set
	**/
	public void resetPanel(JPanel p){
		panel = p;
	}

	/**
	* Loads the components on the JFrame
	* @param filename of the image
	**/
	public void loadComponents(String fileName){
		JPanel top = new JPanel();
		panel.add(top);

		//Draw panel
		final AppDrawPanel drawPanel = new AppDrawPanel();
		drawPanel.loadImage(fileName);
		panel.add(drawPanel);

		//Original button
		JButton original = new JButton("Original");
		original.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				drawPanel.setOriginal();	
			}
		});
		top.add(original);

		//Grayscale button
		JButton grayscaleButton = new JButton("Grayscale");
		grayscaleButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				drawPanel.setGrayScale();	
			}
		});
		top.add(grayscaleButton);

		//Pattern button
		JButton pattern = new JButton("Pattern");
		pattern.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				drawPanel.setPattern();	
			}
		});
		top.add(pattern);

		//Close Button
		JButton closeButton = new JButton("Close");
		closeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		closeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		panel.add(closeButton);
	}

	/**
	* Shows the JFrame
	**/
	public void show(){
		frame.pack();
		frame.setVisible(true); 
	}

}