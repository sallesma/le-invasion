package aide_invasion_le;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import aide_invasion_le.FormCommando.CommandoOrder;

public class TabMap extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JLabel image;
	private ILEInterface leInterface;
	private int mapId;
	private int mapSize;
	
	private JPanel ligneFormSelect = new JPanel();
	private JLabel formButtonLabel = new JLabel("Type de formulaire :");
	private JRadioButton classicButton;
	private JRadioButton commandoButton;
	
	private JPanel formPanel = new JPanel();
	private FormClassic formClassic;
	private FormCommando formCommando;
	
	private JPanel bottomPanel = new JPanel();
	private JLabel mapIdLabel = new JLabel("Numéro Carte :");
	private TextField mapIdValue = new TextField("1",10);
	private JButton helpButton = new JButton("Help");
	private JCheckBox checkInvaButton = new JCheckBox("Check inva");
	private JCheckBox checkPlayerButton = new JCheckBox("Check players");
	private JLabel clearLabel = new JLabel("Clear invasions :");
	private JButton clearPonct = new JButton("Ponctuel");
	private JButton clearPerm = new JButton("Permanent");
	private JButton clearAuto = new JButton("Automatique");
	
	private JLabel nbMonster = new JLabel("0",SwingConstants.CENTER);
	private JLabel nbPlayer = new JLabel("0",SwingConstants.CENTER);
	
	private JLayeredPane layeredPane = new JLayeredPane();
	private ArrayList<JLabel> crossList = new ArrayList<JLabel>();
	
	private final static int DISPLAYED_MAP_SIZE = 400;

	private String croixPath = Paths.get("images", "croixRed.png").toString();
	private String croixPathG = Paths.get("images", "croixGreen.png").toString();
	
	private Timer checkInvaTimer;
	private Timer checkPlayerTimer;
	
	private WindowMonsters winMons;
	private WindowPlayers winPlay;
	
	public TabMap(ILEInterface leInterface, Path mapFile, int mapSize, int mapId)
	{
		
		this.leInterface = leInterface;
		this.mapId = mapId;
		this.mapSize = mapSize;
		this.formClassic = new FormClassic();
		this.formCommando = new FormCommando(leInterface, mapId);
        this.requestFocus();

		classicButton = new JRadioButton("Classique");
	    commandoButton = new JRadioButton("Commando");

	    ButtonGroup group = new ButtonGroup();
	    group.add(classicButton);
	    group.add(commandoButton);
	    
	    classicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				formPanel.removeAll();
				formPanel.add(formClassic);
				formPanel.updateUI();
			}
		});
	    commandoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				formPanel.removeAll();
				formPanel.add(formCommando);
				formPanel.updateUI();
			}
		});
	    ligneFormSelect.add(formButtonLabel);
	    ligneFormSelect.add(classicButton);
	    ligneFormSelect.add(commandoButton);
	    this.add(ligneFormSelect);
	    this.add(formPanel);
	    classicButton.doClick();
		
		mapIdValue.setText(Integer.toString(mapId));
		mapIdValue.setEditable(false);
		
	    JPanel bottomLeftPanel = new JPanel();
	    bottomLeftPanel.setLayout(new GridLayout(15, 1));
		bottomLeftPanel.add(mapIdLabel);
		bottomLeftPanel.add(mapIdValue);
		bottomLeftPanel.add(helpButton);
		bottomLeftPanel.add(checkInvaButton);
		bottomLeftPanel.add(checkPlayerButton);
		bottomLeftPanel.add(clearLabel);
		bottomLeftPanel.add(clearPonct);
		bottomLeftPanel.add(clearPerm);
		bottomLeftPanel.add(clearAuto);

		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowHelp();
			}
		});
		checkInvaButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					stopCheckInvaTimer();
				} else if (e.getStateChange() == ItemEvent.SELECTED) {
					startCheckInvaTimer();
				}
			}
		});
		checkPlayerButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					stopCheckPlayerTimer();
				} else if (e.getStateChange() == ItemEvent.SELECTED) {
					startCheckPlayerTimer();
				}
			}
		});
		clearPonct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
        		TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_PONCT, TabMap.this.mapId);
            }
		});  
		clearPerm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_PERM, TabMap.this.mapId);
        	}
        });  
		clearAuto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_AUTO, TabMap.this.mapId);
            }
		});
		
	    layeredPane.setPreferredSize(new Dimension(400, 400)); 
	    
	    JPanel bottomRightPanel = new JPanel();
	    ImageIcon mapIcon = new ImageIcon( mapFile.toString() );
	    Image scaledMapImage = ResizeImage.scaleImage(mapIcon.getImage(), DISPLAYED_MAP_SIZE);//size in pixels
	    Icon scaledMapIcon = new ImageIcon(scaledMapImage);
	    this.image = new JLabel(scaledMapIcon);
	    this.image.addMouseListener(this);
	    
	    image.setBounds(0, 0, 400, 400);
	    layeredPane.add(image);
	    
	    nbMonster.setBounds(0, 0, 50, 20);
	    Font f = new Font("Serif", Font.PLAIN, 24); // par exemple 
	    nbMonster.setForeground(new Color(255, 0 , 0));
	    nbMonster.setFont(f);
	    nbMonster.setOpaque(true);
	    nbMonster.setBackground(Color.BLACK);
	    winMons = new WindowMonsters();
	    nbMonster.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				winMons.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

		});
	    layeredPane.add(nbMonster);
	    layeredPane.moveToFront(nbMonster);
	    
	    nbPlayer.setBounds(0, 0, 50, 20);
	    Font f2 = new Font("Serif", Font.PLAIN, 24); // par exemple 
	    nbPlayer.setForeground(new Color(0, 0 , 255));
	    nbPlayer.setLocation(DISPLAYED_MAP_SIZE-40, 0);
	    nbPlayer.setFont(f2);
	    nbPlayer.setOpaque(true);
	    nbPlayer.setBackground(Color.BLACK);
		winPlay = new WindowPlayers();
	    nbPlayer.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				winPlay.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

		});
	    layeredPane.add(nbPlayer);
	    layeredPane.moveToFront(nbPlayer);
	    
	    bottomRightPanel.add(layeredPane);

	    bottomPanel.add(bottomLeftPanel);
	    bottomPanel.add(bottomRightPanel);

	    this.add(bottomPanel);
	}
	
	public void setLEIinterface(ILEInterface leInterface) {
		this.leInterface = leInterface;
	}
	
	public void startCheckInvaTimer() {
		final TabMap tab = this;
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				leInterface.sendCheckInvasion(tab);
			}	
		};
		checkInvaTimer = new Timer();
		checkInvaTimer.scheduleAtFixedRate(task, 0, 10000);
	}
	
	public void checkInvasionCallback(ArrayList<String[]> invasions)
	{
		System.out.println("Call Back : " + invasions.size());
		
		removePoints();
		String selectedMonster = winMons.selectedMonster;
		int nbm = 0;
		for(int i = 0; i < invasions.size(); i++)
		{
		  	System.out.println(invasions.get(i));
		 	if(Integer.parseInt(invasions.get(i)[4]) == mapId)
		 	{
		 		if(selectedMonster.length() > 1 && invasions.get(i)[0].equals(selectedMonster))
			  		addPoint(Integer.parseInt(invasions.get(i)[2]),Integer.parseInt(invasions.get(i)[3]),1);
		 		else
		 			addPoint(Integer.parseInt(invasions.get(i)[2]),Integer.parseInt(invasions.get(i)[3]),0);
		  		nbm++;
		 	} 
		}
	 	nbMonster.setText(nbm + "");
	 	winMons.setList(invasions, mapId);
		layeredPane.repaint();
	}

	public void startCheckPlayerTimer() {
		final TabMap tab = this;
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				leInterface.sendCheckPlayers(tab, mapId);
			}	
		};
		checkPlayerTimer = new Timer();
		checkPlayerTimer.scheduleAtFixedRate(task, 0, 10000);
	}
	
	public void checkPlayersCallback(ArrayList<String[]> players)
	{
		System.out.println("Call Back Players : " + players.size());
		
		//removePoints();
		int nbp = 0;
		for(int i = 0; i < players.size(); i++)
		{
		  	nbp++;
		}
	 	nbPlayer.setText(nbp + "");
	 	winPlay.setList(players);
		layeredPane.repaint();
	}

	public void stopCheckInvaTimer()
	{
		System.out.println("Stop Check Inva");
		checkInvaTimer.cancel();
		removePoints();
		nbMonster.setText("0");
		winMons.setList(new ArrayList<String[]>(), 0);
	}

	public void stopCheckPlayerTimer()
	{
		System.out.println("Stop Check Player");
		checkPlayerTimer.cancel();
		nbPlayer.setText("0");
		winPlay.setList(new ArrayList<String[]>());
	}
	
	public void addPoint(int x,int y, int color)
	{
		ImageIcon croixIcon;
		if (color == 1)
			croixIcon = new ImageIcon( croixPathG.toString() );
		else
			croixIcon = new ImageIcon( croixPath.toString() );
	    JLabel croix;
	    croix = new JLabel(croixIcon);
	    croix.setBounds(0, 0, 15, 15);
	    croix.setLocation((x*DISPLAYED_MAP_SIZE/mapSize)-7,DISPLAYED_MAP_SIZE-(y*DISPLAYED_MAP_SIZE/mapSize)+7);
	    crossList.add(croix);
	    layeredPane.add(croix);
	    layeredPane.moveToFront(croix);
	}
	
	public void removePoints() {
		for (int i = 0; i < crossList.size(); i++) {
			layeredPane.remove(crossList.get(i));
		}
		layeredPane.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*mapSize/DISPLAYED_MAP_SIZE;
		int yPos = mapSize-((int)(e.getPoint().getY())*mapSize)/DISPLAYED_MAP_SIZE;
		
		System.out.println(xPos + " - " + yPos);
		if(commandoButton.isSelected())
		{
			for (int groupId : formCommando.getGroupIds())
				if (formCommando.getOrder() == CommandoOrder.Add)
					leInterface.commandoAjouter(xPos, yPos, mapId, formCommando.getCommandoType(), groupId);
				else if (formCommando.getOrder() == CommandoOrder.Go)
					leInterface.commandoGo(xPos, yPos, mapId, groupId);
		}
		else
			leInterface.addInvasion(formClassic.getInvasionType(), xPos, yPos, mapId, formClassic.getMonsterType(), formClassic.getMonsterNumber());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}

