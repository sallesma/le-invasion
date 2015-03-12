package aide_invasion_le;

import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Fenetre  extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private LEInterface leInterface;
	private JTabbedPane tabbedPane;
	
	public Fenetre() {
		JFrame fenetre = new JFrame();
	    fenetre.setTitle("Gestionnaire Invasion");
	    fenetre.setSize(600, 800);
	    fenetre.setLocationRelativeTo(null); // Center window
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    leInterface = new LEInterface();

	    tabbedPane = new JTabbedPane();
	    MainTab mainTab = new MainTab(this, leInterface);
	    tabbedPane.addTab("Main", null, mainTab, "Main Tab");
	    
	    fenetre.add(tabbedPane);
	    
	    fenetre.setVisible(true);
	}

	public void openMapTab(Path mapFile) {
		MapTab mapTab = new MapTab(leInterface, mapFile);
	    tabbedPane.addTab(mapFile.getFileName().toString(), null, mapTab, "Map Tab");
	}
}
