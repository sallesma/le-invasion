package aide_invasion_le;

import java.io.File;
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

	    FormClassic formC= new FormClassic();
	    tabbedPane.addTab("Test Form 1", null, formC, "Form 1 Tab");
	    FormCommando formCom= new FormCommando();
	    tabbedPane.addTab("Test Form 2", null, formCom, "Form 2 Tab");

	    
	    fenetre.add(tabbedPane);
	    
	    fenetre.setVisible(true);
	}

	public void openMapTab(Path mapFile) {
		MapTab mapTab = new MapTab(leInterface, mapFile);
	    tabbedPane.addTab(mapFile.getFileName().toString(), null, mapTab, "Map Tab");
	}
}
