package aide_invasion_le;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Fenetre  extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public Fenetre() {
		JFrame fenetre = new JFrame();
	    fenetre.setTitle("Gestionnaire Invasion");
	    fenetre.setSize(600, 800);
	    fenetre.setLocationRelativeTo(null); // Center window
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    LEInterface leInterface = new LEInterface();

	    JTabbedPane tabbedPane = new JTabbedPane();
	    MainTab mainTab = new MainTab(leInterface);
	    tabbedPane.addTab("Main", null, mainTab, "Main Tab");
	    MapTab mapTab = new MapTab(leInterface);
	    tabbedPane.addTab("Map", null, mapTab, "Map Tab");
	    FormClassic formC= new FormClassic();
	    tabbedPane.addTab("Form 1", null, formC.getFormClassic(), "Form 1 Tab");
	    FormCommando formCom= new FormCommando();
	    tabbedPane.addTab("Form 2", null, formCom.getFormCommando(), "Form 2 Tab");
	    
	    fenetre.add(tabbedPane);
	    
	    fenetre.setVisible(true);
	}
}
