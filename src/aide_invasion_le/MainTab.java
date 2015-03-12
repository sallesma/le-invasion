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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainTab extends JPanel {

	private static final long serialVersionUID = 1L;

	private Fenetre parentWindow;
	private LEInterface leInterface;
	private String configFile = Paths.get("data", "config.properties").toString();
	
	private JLabel pseudo = new JLabel("Pseudo");
	private TextField zonePseudo = new TextField("",10);
	
	private JLabel server = new JLabel("Serveur");
	private JComboBox<String> zoneServer = new JComboBox<String>(new String[]{"main", "test"});

	private JLabel mapFolderLabel = new JLabel("Cartes");
	private JLabel currentMapFolderTitle = new JLabel("Dossier courant :");
	private JLabel currentMapFolder = new JLabel("Par défaut, le logiciel utilise ses cartes");
	private static JButton mapFolderChooser = new JButton("Utiliser mon propre dossier de cartes");
	
	private JLabel openMapLabel = new JLabel("Ouvrir une carte");
	private JComboBox<String> openMapComboBox = new JComboBox<String>();
	
	public MainTab(Fenetre parentWindow, LEInterface leInterface) {
		this.parentWindow = parentWindow;
		
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(15, 1));
	    
	    if(new File(configFile).exists()) {
	    	InputStream inputStream = null;
	    	try {
	    		inputStream = new FileInputStream(configFile);
	            Properties properties = new Properties();
	            properties.load(inputStream);
	            zonePseudo.setText(properties.getProperty("pseudo"));
	            zoneServer.setSelectedItem(properties.getProperty("server"));
	        } catch(IOException e) {
	            e.printStackTrace();
	        } finally {
	        	if (inputStream != null) {
		        	try {
						inputStream.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	        	}
	        }
	    }
	    
	    pseudo.setFont(new Font(pseudo.getName(), Font.PLAIN, 20));
	    server.setFont(new Font(server.getName(), Font.PLAIN, 20));
	    mapFolderLabel.setFont(new Font(mapFolderLabel.getName(), Font.PLAIN, 20));
		zonePseudo.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateLEInterface();
				updateConfigFile();
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
				updateConfigFile();
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
        		    currentMapFolder.setText(folder.getPath());
        		    MainTab.this.parentWindow.setMapFolder(folder);
        		    updateMapList(folder);
        		}
        	}
        });
		
		this.updateMapList(this.parentWindow.getMapFolder());

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
		for (ActionListener actionListener : this.openMapComboBox.getActionListeners()) {
			this.openMapComboBox.removeActionListener(actionListener);
		};
		this.openMapComboBox.removeAllItems();
		File[] files = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith(".jpg"))
					return true;
				return false;
			}
		});
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
		for (File mapFile : files) {
			this.openMapComboBox.addItem(mapFile.getName());
		}
		openMapComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mapName = (String)openMapComboBox.getSelectedItem();
				System.out.println("mapname : "+ mapName);
				if(mapName != null)
					MainTab.this.parentWindow.openMapTab(mapName);
			}
		});
	}

	private void updateConfigFile()
	{
		Properties properties = new Properties();
		OutputStream outputStream = null;

		try {
			outputStream = new FileOutputStream(configFile);
			properties.setProperty("pseudo", zonePseudo.getText());
			properties.setProperty("server", zoneServer.getSelectedItem().toString());
			properties.store(outputStream, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
