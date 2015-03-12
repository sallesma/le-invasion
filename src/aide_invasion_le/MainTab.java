package aide_invasion_le;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainTab extends JPanel {
	private Fenetre parentWindow;
	private LEInterface leInterface;
	
	private JLabel pseudo = new JLabel("Pseudo");
	private TextField zonePseudo = new TextField("",10);
	
	private JLabel server = new JLabel("Serveur");
	private JComboBox<String> zoneServer = new JComboBox<String>(new String[]{"main", "test"});
	
	private JLabel mapFolderLabel = new JLabel("Map Folder");
	private static JButton mapFolderChooser = new JButton("Définir");
	private JLabel currentMapFolderTitle = new JLabel("Dossier courant :");
	private JLabel currentMapFolder = new JLabel("Non défini...");
	
	private JLabel openMapLabel = new JLabel("Ouvrir une carte");
	private JComboBox<String> openMapComboBox = new JComboBox<String>();
	
	public MainTab(Fenetre parentWindow, LEInterface leInterface) {
		this.parentWindow = parentWindow;
		
	    JPanel panel = new JPanel();
	    
	    panel.setLayout(new GridLayout(15, 1));
		panel.add(pseudo);
		panel.add(zonePseudo);
		panel.add(server);
		panel.add(zoneServer);
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
		zoneServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateLEInterface();
			}
		});
		
		mapFolderChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
        		JFileChooser dialog = new JFileChooser(new File(".\\images"));
        		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        		dialog.setApproveButtonText("Selectionner");
        		dialog.setDialogTitle("Sélectionner le dossier contenant les cartes");
        		if (dialog.showOpenDialog(null)==  JFileChooser.APPROVE_OPTION) {
        			File folder = dialog.getSelectedFile();
        		    System.out.println(folder.getPath());
        		    currentMapFolder.setText(folder.getPath());
        		    MainTab.this.leInterface.setLeMapFolder(folder);
        		    for (File mapFile : folder.listFiles(new FileFilter() {
        				@Override
        				public boolean accept(File pathname) {
        					if (pathname.getName().endsWith(".jpg"))
        						return true;
        					return false;
        				}
        			})) {
        				openMapComboBox.addItem(mapFile.getName());
        			}
        		}
        	}
        });
		
		openMapComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mapName = (String)openMapComboBox.getSelectedItem();
				Path mapFile = Paths.get( MainTab.this.leInterface.getLeMapFolder().getAbsolutePath(), mapName);
				MainTab.this.parentWindow.openMapTab(mapFile);
			}
		});
		
		panel.add(mapFolderLabel);
		panel.add(mapFolderChooser);
		panel.add(currentMapFolderTitle);
		panel.add(currentMapFolder);
		panel.add(openMapLabel);
		panel.add(openMapComboBox);
		
	    this.add(panel);
	    
		this.leInterface = leInterface;
		this.updateLEInterface();
	}

	private void updateLEInterface() {
		this.leInterface.setPseudo(zonePseudo.getText());
		this.leInterface.setServer(zoneServer.getSelectedItem().toString());
	}
}
