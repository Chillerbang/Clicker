package Client;

import javax.swing.JFrame;
/**
 * 
 * @author Lance 215018300
 * CleintFrame creates the client GUI frame
 */

public class CleintFrame extends JFrame{
	
	public CleintFrame(){
	    super("Clicker walker"); // setting name
		add(new Clientpanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

}
