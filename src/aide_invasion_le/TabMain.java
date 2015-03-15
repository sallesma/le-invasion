package aide_invasion_le;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TabMain extends JPanel {

	private static final long serialVersionUID = 1L;

	private Window parentWindow;
	private LEInterfaceWindowed leInterface;
	private MapsManager mapsManager;
	private String configFile = Paths.get("data", "config.properties").toString();
	
	private JPanel ligneInterfaceSelect = new JPanel();
	private JLabel interfaceRadioLabel = new JLabel("Interface vers LE :");
	private JRadioButton windowedButton;
	private JRadioButton netButton;
	
	JPanel interfacePanel = new JPanel();
	
	private JLabel pseudoWindowed = new JLabel("Pseudo");
	private TextField zonePseudo = new TextField("",10);
	private JLabel server = new JLabel("Serveur");
	private JComboBox<String> zoneServer = new JComboBox<String>(new String[]{"main", "test"});

	private TextField serverAdress = new TextField("jeu.landes-eternelles.com", 10);
	private TextField port = new TextField("3001", 10);
	private TextField pseudoNet = new TextField("test_interf", 10);
	private TextField password = new TextField("azerty", 10);

	JPanel mapOpenPanel = new JPanel();
	private JLabel mapFolderLabel = new JLabel("Cartes");
	private JComboBox<String> mapsComboBox = new JComboBox<String>();
	private JButton openMapButton = new JButton("Ouvrir la carte");
	
	public TabMain(Window parentWindow, LEInterfaceWindowed leInterface) {
		this.parentWindow = parentWindow;
		this.leInterface = leInterface;
	    this.mapsManager = new MapsManager();
		
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
	    
	    windowedButton = new JRadioButton("Fenêtre de jeu");
	    netButton = new JRadioButton("Commandes serveur");

	    ButtonGroup group = new ButtonGroup();
	    group.add(windowedButton);
	    group.add(netButton);
	    
	    windowedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfacePanel.removeAll();
				interfacePanel.add(pseudoWindowed);
				interfacePanel.add(zonePseudo);
				interfacePanel.add(server);
				interfacePanel.add(zoneServer);
				interfacePanel.updateUI();
			}
		});
	    netButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfacePanel.removeAll();
				interfacePanel.add(pseudoNet);
				interfacePanel.add(port);
				interfacePanel.add(serverAdress);
				interfacePanel.add(password);
				interfacePanel.updateUI();
			}
		});
	    windowedButton.doClick();
	    ligneInterfaceSelect.add(interfaceRadioLabel);
	    ligneInterfaceSelect.add(windowedButton);
	    ligneInterfaceSelect.add(netButton);
	    this.add(ligneInterfaceSelect);
	    
	    this.add(interfacePanel);
	    
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

		String[] maps = this.mapsManager.getMapNames();
		Arrays.sort(maps);
		for (String map : maps) {
			this.mapsComboBox.addItem(map);
		}
		
		openMapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isValidConfig()) {
					String mapName = (String)mapsComboBox.getSelectedItem();
					System.out.println("mapname : " + mapName);
					if(mapName != null) {
						Path mapFile = mapsManager.getMapFilePath(mapName);
						int mapId = mapsManager.getMapId(mapName);
						int mapSize = mapsManager.getMapSize(mapName);
						TabMain.this.parentWindow.openMapTab(mapFile, mapId, mapSize);
					}
				} else {
					JOptionPane.showMessageDialog(TabMain.this, "The config is not properly set", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		mapOpenPanel.add(mapFolderLabel);
		mapOpenPanel.add(mapsComboBox);
		mapOpenPanel.add(openMapButton);
		this.add(mapOpenPanel);

		this.updateLEInterface();
	}

	private void updateLEInterface() {
		this.leInterface.setPseudo(zonePseudo.getText());
		this.leInterface.setServer(zoneServer.getSelectedItem().toString());
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
	
	private boolean isValidConfig() {
		if (zonePseudo.getText().equals(""))
			return false;
		return true;
	}
}
