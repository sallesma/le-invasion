package aide_invasion_le;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;

public class Fenetre  extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private LEInterface leInterface;
	private File mapFolder;
	private ClosableTabbedPane tabbedPane;
	private HelpMapCompletion mapCompletion = new HelpMapCompletion();
	
    public Fenetre() {
		JFrame fenetre = new JFrame();
	    fenetre.setTitle("Gestionnaire Invasion");
	    fenetre.setSize(600, 800);
	    fenetre.setLocationRelativeTo(null); // Center window
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    leInterface = new LEInterface();
	    mapFolder = new File("images/");
	    
        tabbedPane = new ClosableTabbedPane();
        tabbedPane.setUI(new Tabbed());
        MainTab mainTab = new MainTab(this, leInterface);
        tabbedPane.addTab("Main Tab", mainTab);
        
        requestFocus();
        
        this.add(tabbedPane);
        this.setSize(600, 800);
        this.setVisible(true);
    }

	public void openMapTab(String mapName) {
		Path mapFile = Paths.get( this.mapFolder.getAbsolutePath(), mapName);
		
		mapCompletion.setMap(mapName);
		
		MapTab mapTab = new MapTab(leInterface, mapFile, mapCompletion.getSize(), mapCompletion.getNumber());
		tabbedPane.addTab(mapFile.getFileName().toString(), mapTab);
	}
	
	public File getMapFolder(){
		return this.mapFolder;
	}
	
	public void setMapFolder(File mapFolder){
		this.mapFolder = mapFolder;
	}
}
