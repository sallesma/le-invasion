package aide_invasion_le;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JSeparator;

public class TabMain extends JPanel {

	private static final long serialVersionUID = 1L;

	private Window parentWindow;
	private ILEInterface leInterface;
	private MapsManager mapsManager;
	private String configFile = Paths.get("data", "config.properties").toString();
	
	private JPanel ligneInterfaceSelect = new JPanel();
	private JLabel interfaceRadioLabel = new JLabel("Interface vers LE :");
	private JRadioButton windowedButton;
	private JRadioButton netButton;
	private JLabel interfaceDescription = new JLabel("");
	
	JPanel interfacePanel = new JPanel();
	
	private JLabel pseudoWindowedLabel = new JLabel("Pseudo :");
	private TextField zonePseudo = new TextField("",10);
	private JLabel serverWindowedLabel = new JLabel("Serveur :");
	private JComboBox<String> zoneServer = new JComboBox<String>(new String[]{"main", "test"});

	private JLabel serverAddressLabel = new JLabel("Adresse serveur :");
	private TextField serverAddress = new TextField("jeu.landes-eternelles.com", 10);
	private JLabel serverPortLabel = new JLabel("Port :");
	private TextField serverPort = new TextField("3001", 10);
	private JLabel pseudoNetLabel = new JLabel("Pseudo :");
	private TextField pseudoNet = new TextField("", 10);
	private JLabel passwordLabel = new JLabel("Password : ");
	private TextField password = new TextField("", 10);
	
	private JButton interfaceValidateButton = new JButton("Valider l'interface");

	JPanel mapOpenPanel = new JPanel();
	private JLabel mapFolderLabel = new JLabel("Cartes");
	private JComboBox<String> mapsComboBox = new JComboBox<String>();
	private JButton openMapButton = new JButton("Ouvrir la carte");
	
	public TabMain(Window parentWindow) {
		this.parentWindow = parentWindow;
		this.leInterface = null;
		this.mapsManager = new MapsManager();

		this.readConfigFile();
		this.setLayout(new GridLayout(15, 1));

		windowedButton = new JRadioButton("Fenêtre de jeu");
		netButton = new JRadioButton("Interaction directe");
		ButtonGroup group = new ButtonGroup();
		group.add(windowedButton);
		group.add(netButton);

		windowedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfacePanel.removeAll();
				interfacePanel.add(pseudoWindowedLabel);
				interfacePanel.add(zonePseudo);
				interfacePanel.add(serverWindowedLabel);
				interfacePanel.add(zoneServer);
				interfacePanel.setLayout(new GridLayout(1, 1));
				interfacePanel.updateUI();
				interfaceDescription.setText("Les commandes à envoyer seront copiées dans ta fenêtre du jeu et envoyées");
			}
		});
	    netButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfacePanel.removeAll();
				interfacePanel.add(pseudoNetLabel);
				interfacePanel.add(pseudoNet);
				interfacePanel.add(serverPortLabel);
				interfacePanel.add(serverPort);
				interfacePanel.add(serverAddressLabel);
				interfacePanel.add(serverAddress);
				interfacePanel.add(passwordLabel);
				interfacePanel.add(password);
				interfacePanel.setLayout(new GridLayout(2, 1));
				interfacePanel.updateUI();
				interfaceDescription.setText("Les commandes seront envoyées directement au serveur par le logiciel en utilisant le compte indiqué");
			}
		});
	    windowedButton.doClick();
	    ligneInterfaceSelect.add(interfaceRadioLabel);
	    ligneInterfaceSelect.add(windowedButton);
	    ligneInterfaceSelect.add(netButton);
	    this.add(ligneInterfaceSelect);
		interfaceDescription.setFont(new Font(interfaceDescription.getFont()
				.getName(), Font.ITALIC, interfaceDescription.getFont()
				.getSize()));
	    this.add(interfaceDescription);
	    this.add(interfacePanel);
	    this.add(interfaceValidateButton);
	    
		interfaceValidateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(windowedButton.isSelected() && isValidWindowedConfig()) {
					if (leInterface != null)
						TabMain.this.leInterface.close();
					TabMain.this.leInterface = new LEInterfaceWindowed(
							zonePseudo.getText(),
							zoneServer.getSelectedItem().toString());
					updateConfigFile();
					TabMain.this.parentWindow.updateLEInterface(leInterface);
				} else if(netButton.isSelected() && isValidNetConfig()) {
					if (leInterface != null)
						TabMain.this.leInterface.close();
					TabMain.this.leInterface = new LEInterfaceNet(
							pseudoNet.getText(),
							password.getText(),
							serverAddress.getText(),
							Integer.parseInt(serverPort.getText()));
					TabMain.this.parentWindow.createGame(leInterface);
					leInterface.open(serverAddress.getText(),
							Integer.parseInt(serverPort.getText()),
							pseudoNet.getText(),
							password.getText());
					updateConfigFile();
					TabMain.this.parentWindow.updateLEInterface(leInterface);
				} else {
					JOptionPane.showMessageDialog(TabMain.this, "The config is not valid", "Error", JOptionPane.ERROR_MESSAGE);
				}
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
				if (TabMain.this.leInterface != null) {
					String mapName = (String)mapsComboBox.getSelectedItem();
					System.out.println("mapname : " + mapName);
					if(mapName != null) {
						Path mapFile = mapsManager.getMapFilePath(mapName);
						int mapId = mapsManager.getMapId(mapName);
						int mapSize = mapsManager.getMapSize(mapName);
						TabMain.this.parentWindow.openMapTab(TabMain.this.leInterface, mapFile, mapId, mapSize);
					}
				} else {
					JOptionPane.showMessageDialog(TabMain.this, "The config is not properly set", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		mapOpenPanel.add(mapFolderLabel);
		mapOpenPanel.add(mapsComboBox);
		mapOpenPanel.add(openMapButton);
	    this.add(new JSeparator());
		this.add(mapOpenPanel);
	}

	private void updateConfigFile()
	{
		Properties properties = new Properties();
		OutputStream outputStream = null;

		try {
			outputStream = new FileOutputStream(configFile);
			properties.setProperty("pseudo", zonePseudo.getText());
			properties.setProperty("server", zoneServer.getSelectedItem().toString());
			properties.setProperty("serverAdress", serverAddress.getText());
			properties.setProperty("port", serverPort.getText());
			properties.setProperty("pseudoNet", pseudoNet.getText());
			properties.setProperty("password", password.getText());
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

	private void readConfigFile() {
		if (new File(configFile).exists()) {
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(configFile);
				Properties properties = new Properties();
				properties.load(inputStream);
				zonePseudo.setText(properties.getProperty("pseudo"));
				zoneServer.setSelectedItem(properties.getProperty("server"));
				serverAddress.setText(properties.getProperty("serverAdress"));
				serverPort.setText(properties.getProperty("port"));
				pseudoNet.setText(properties.getProperty("pseudoNet"));
				password.setText(properties.getProperty("password"));
			} catch (IOException e) {
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
	}
	
	private boolean isValidWindowedConfig() {
		if (zonePseudo.getText().equals(""))
			return false;
		return true;
	}
	
	private boolean isValidNetConfig() {
		if (pseudoNet.getText().equals("")
				|| serverAddress.getText().equals("")
				|| serverPort.getText().equals("")
				|| password.getText().equals(""))
			return false;
		try {
			Integer.parseInt(serverPort.getText());
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
