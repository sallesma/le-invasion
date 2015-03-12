package aide_invasion_le;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileFilter;

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

	private JLabel mapFolderLabel = new JLabel("Cartes");
	private JLabel currentMapFolderTitle = new JLabel("Dossier courant :");
	private JLabel currentMapFolder = new JLabel("Cartes de l'outil");
	private static JButton mapFolderChooser = new JButton("Utiliser mon propre dossier de cartes");
	
	private JLabel openMapLabel = new JLabel("Ouvrir une carte");
	private JComboBox<String> openMapComboBox = new JComboBox<String>();
	
	public MainTab(Fenetre parentWindow, LEInterface leInterface) {
		this.parentWindow = parentWindow;
		
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(15, 1));
	    
	    pseudo.setFont(new Font(pseudo.getName(), Font.PLAIN, 20));
	    server.setFont(new Font(server.getName(), Font.PLAIN, 20));
	    mapFolderLabel.setFont(new Font(mapFolderLabel.getName(), Font.PLAIN, 20));
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
        		    MainTab.this.parentWindow.setMapFolder(folder);
        		    updateMapList(folder);
        		}
        	}
        });
		
		this.updateMapList(this.parentWindow.getMapFolder());
		openMapComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mapName = (String)openMapComboBox.getSelectedItem();
				MainTab.this.parentWindow.openMapTab(mapName);
			}
		});

		panel.add(pseudo);
		panel.add(zonePseudo);
		panel.add(server);
		panel.add(zoneServer);
		panel.add(mapFolderLabel);
		panel.add(currentMapFolderTitle);
		panel.add(currentMapFolder);
		panel.add(mapFolderChooser);
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
	
	private void updateMapList(File folder) {
		for (File mapFile : folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith(".jpg"))
					return true;
				return false;
			}
		})) {
			this.openMapComboBox.addItem(mapFile.getName());
		}
	}
}
