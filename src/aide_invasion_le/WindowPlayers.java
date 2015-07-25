package aide_invasion_le;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class WindowPlayers extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextArea listPlay = new JTextArea("----");

	public WindowPlayers() {
		this.setTitle("Players");
		this.setLayout(new BorderLayout());

		
		this.setSize(200, 100);
		this.setVisible(false);
		
		listPlay.setEditable(false);  
		listPlay.setCursor(null);  
		listPlay.setOpaque(false);  
		listPlay.setFocusable(false);
		listPlay.setLineWrap(true);
	    listPlay.setWrapStyleWord(true);
		this.add(listPlay);
		

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public void setList(ArrayList<String[]> players){
		if (players.size() == 0)
			listPlay.setText("----");
		else
		{
			listPlay.setText("");
			for(int i = 0; i < players.size(); i++)
			{
				listPlay.setText(listPlay.getText() + players.get(i)[0] + "\n");
			}
		}
	}
	
}
