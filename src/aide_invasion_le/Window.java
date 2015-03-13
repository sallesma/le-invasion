package aide_invasion_le;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;

public class Window  extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private LEInterface leInterface;
	private File mapFolder;
	private TabbedPaneClosable tabbedPane;
	private HelpMapCompletion mapCompletion;
	
    public Window() {
		JFrame fenetre = new JFrame();
	    fenetre.setTitle("Gestionnaire Invasion");
	    fenetre.setSize(600, 800);
	    fenetre.setLocationRelativeTo(null); // Center window
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    leInterface = new LEInterface();
	    mapCompletion = new HelpMapCompletion();
	    mapFolder = new File("images/");
	    
        tabbedPane = new TabbedPaneClosable();
        tabbedPane.setUI(new Tabbed());
        TabMain mainTab = new TabMain(this, leInterface);
        tabbedPane.addTab("Main Tab", mainTab);
        
        requestFocus();
        
        this.add(tabbedPane);
        this.setSize(600, 800);
        this.setVisible(true);
    }

	public void openMapTab(String mapName) {
		Path mapFile = Paths.get( this.mapFolder.getAbsolutePath(), mapName);
		
		TabMap mapTab = new TabMap(leInterface, mapFile, mapCompletion.getMapSize(mapName), mapCompletion.getMapId(mapName));
		tabbedPane.addTab(mapFile.getFileName().toString(), mapTab);
	}
	
	public File getMapFolder(){
		return this.mapFolder;
	}
	
	public void setMapFolder(File mapFolder){
		this.mapFolder = mapFolder;
	}
}
