package breakthroughPP.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
* Textfield for integer range with helper text
* @author faulersack
*/
public class DefaultIntField extends JTextField implements FocusListener, DocumentListener{
	private int min, max, lastValid;
	private String text;
	
	/**
	* Create DefaultIntField
	* @param text Default text value
	* @param min Allowed minimum int value
	* @param max Allowed maximum int value
	*/
	public DefaultIntField(String text, int min, int max){
		super(text);
		if(min>max){int m = max;max = min;min = m;}
		this.min = this.lastValid = min;
		this.max = max;
		this.text = text;
		addFocusListener(this);
		getDocument().addDocumentListener(this);
	}
	
	/**
	* Retrieve current value
	* @return Int value
	*/
	public int getValue(){
		return lastValid;
	}
	
	public void insertUpdate(DocumentEvent e){checkValue();}
	public void removeUpdate(DocumentEvent e){checkValue();}
	public void changedUpdate(DocumentEvent e){checkValue();}
	private void checkValue(){
		if(getText().equals("")||getText().equals(text))
			return;
		int newValue;
		boolean forceUpdate = false;
		try{
			newValue = Integer.parseInt(getText());
			if(newValue<min&& lastValid !=min)
				newValue = min;
			else if(newValue>max&& lastValid !=max)
				newValue = max;
		}catch(Exception ex){
			newValue = lastValid;
			forceUpdate = true;
		}
		if(forceUpdate||newValue!= lastValid){
			SwingUtilities.invokeLater(() -> setText(String.valueOf(lastValid)));
		}
		lastValid = newValue;
	}
	
	public void focusGained(FocusEvent e){
		if(getText().equals(text))
			setText("");
	}
	public void focusLost(FocusEvent e){
		if(getText().equals(""))
			setText(text);
	}
}

