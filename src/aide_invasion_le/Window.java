package aide_invasion_le;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.JFrame;

public class Window  extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private LEInterfaceWindowed leInterface;
	private TabbedPaneClosable tabbedPane;
	private TabTestLEInterfaceNet testInt;
	private TabGame game;
	
    public Window() {
		JFrame fenetre = new JFrame();
	    fenetre.setTitle("Gestionnaire Invasion");
	    fenetre.setSize(600, 800);
	    fenetre.setLocationRelativeTo(null); // Center window
	    //fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	        	testInt.close();
	        	System.out.println("Close");
	            System.exit(0);
	          }
	        });
	    
	    leInterface = new LEInterfaceWindowed();
	    
        tabbedPane = new TabbedPaneClosable();
        tabbedPane.setUI(new Tabbed());
        TabMain mainTab = new TabMain(this, leInterface);
        tabbedPane.addTab("Main Tab", mainTab);
        tabbedPane.selectLast();
        
        testInt = new TabTestLEInterfaceNet();
        tabbedPane.addTab("Interf Tab", testInt);
        
        game = new TabGame(leInterface);
        tabbedPane.addTab("Game Tab", game);
        
        requestFocus();
        
        this.add(tabbedPane);
        this.setSize(600, 800);
        this.setVisible(true);
    }

	public void openMapTab(Path mapPath, int mapId, int mapSize) {
		TabMap mapTab = new TabMap(leInterface, mapPath, mapSize, mapId);
		tabbedPane.addTab(mapPath.getFileName().toString(), mapTab);
		tabbedPane.selectLast();
	}
}
