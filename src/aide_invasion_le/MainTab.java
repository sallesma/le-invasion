package aide_invasion_le;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainTab extends JPanel {
	JLabel image;
	LEInterface leInterface;
	
	//Gauche de l'image
	JLabel pseudo = new JLabel("Pseudo");
	private TextField zonePseudo = new TextField("",10); 
	JLabel server = new JLabel("Serveur");
	JComboBox<String> zoneServer = new JComboBox<String>(new String[]{"main", "test"}); 
	
	public MainTab(LEInterface leInterface)
	{
	    JPanel ligneImage = new JPanel();
	    
	    ligneImage.setLayout(new GridLayout(15, 1));
		ligneImage.add(pseudo);
		ligneImage.add(zonePseudo);
		ligneImage.add(server);
		ligneImage.add(zoneServer);
		zonePseudo.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateLEInterface();
			}
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		zoneServer.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateLEInterface();
			}
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
	    //pan.add(ligneImage);
	    this.add(ligneImage);
	    
		this.leInterface = leInterface;
		this.updateLEInterface();
	}

	private void updateLEInterface() {
		this.leInterface.setPseudo(zonePseudo.getText());
		this.leInterface.setServer(zoneServer.getSelectedItem().toString());
	}
}
