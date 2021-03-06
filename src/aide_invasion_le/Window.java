package aide_invasion_le;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.JFrame;

public class Window  extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private ILEInterface leInterface;
	private TabbedPaneClosable tabbedPane;
	
    public Window() {
	    this.setTitle("Gestionnaire Invasion");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (leInterface != null)
					leInterface.close();
			}
		});
	    
        tabbedPane = new TabbedPaneClosable();
        tabbedPane.setUI(new Tabbed());
        TabMain mainTab = new TabMain(this);
        tabbedPane.addTab("Main Tab", mainTab);
        tabbedPane.selectLast();
        
        requestFocus();
        
        this.add(tabbedPane);
        this.setSize(700, 900);
        this.setVisible(true);
    }

	public void openMapTab(ILEInterface leInterface, Path mapPath, int mapId, int mapSize) {
		TabMap mapTab = new TabMap(leInterface, mapPath, mapSize, mapId);
		tabbedPane.addTab(mapPath.getFileName().toString(), mapTab);
		tabbedPane.selectLast();
	}
	
	public void updateLEInterface(ILEInterface leInterface) {
		this.leInterface = leInterface;
		for (TabMap tabMap : this.tabbedPane.getTabMaps()) {
			tabMap.setLEIinterface(leInterface);
		}
	}
}
