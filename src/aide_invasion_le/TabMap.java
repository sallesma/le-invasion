package aide_invasion_le;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JLabel clearLabel = new JLabel("Clear invasions");
	private JButton clearPonct = new JButton("Ponctuel");
	private JButton clearPerm = new JButton("Permanent");
	private JButton clearPeri = new JButton("Périssable");
	private JButton clearAuto = new JButton("Automatique");
	
	private JLabel nbMonster = new JLabel("0",SwingConstants.CENTER);
	private JLabel nbPlayer = new JLabel("0",SwingConstants.CENTER);
	
	private JButton check = new JButton("Check");
	private Boolean checkB = false;
	
	private JLayeredPane layeredPane = new JLayeredPane();
	private ArrayList<JLabel> crossList = new ArrayList<JLabel>();
	
	private final static int DISPLAYED_MAP_SIZE = 400;

	private String croixPath = Paths.get("images", "croixRed.png").toString();
	
	private Timer checkInvaTimer;
	
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
		bottomLeftPanel.add(clearLabel);
		bottomLeftPanel.add(clearPonct);
		bottomLeftPanel.add(clearPerm);
		bottomLeftPanel.add(clearPeri);
		bottomLeftPanel.add(clearAuto);
		bottomLeftPanel.add(check);
		
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
		clearPeri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_PERI, TabMap.this.mapId);
        	}
        });  
		clearAuto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_AUTO, TabMap.this.mapId);
            }
		});  
		check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	if(checkB)
            	{
            		stopCheckInvaTimer();
	            	checkB = false;
	            	passiveButton(check);
            	}
            	else
            	{
	            	startCheckInvaTimer();
	            	checkB = true;
	            	activeButton(check);
            	}
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
	    //nbMonster.setLocation(15, 10);
	    nbMonster.setFont(f);
	    nbMonster.setOpaque(true);
	    nbMonster.setBackground(Color.BLACK);
	    layeredPane.add(nbMonster);
	    layeredPane.moveToFront(nbMonster);
	    
	    nbPlayer.setBounds(0, 0, 50, 20);
	    Font f2 = new Font("Serif", Font.PLAIN, 24); // par exemple 
	    nbPlayer.setForeground(new Color(0, 0 , 255));
	    nbPlayer.setLocation(DISPLAYED_MAP_SIZE-40, 0);
	    nbPlayer.setFont(f2);
	    nbPlayer.setOpaque(true);
	    nbPlayer.setBackground(Color.BLACK);
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
				System.out.println("startcheckinva");
				leInterface.sendCheckInvasion(tab);
				//check_invasion_Delay();
			}	
		};
		
		checkInvaTimer = new Timer();
		checkInvaTimer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	public void checkInvasionCallback(ArrayList<String[]> invasions)
	{
		System.out.println("Call Back : " + invasions.size());
		
		removePoints();
		int nbm = 0;
		for(int i = 0; i < invasions.size(); i++)
		{
		  	System.out.println(invasions.get(i));
		 	if(Integer.parseInt(invasions.get(i)[4]) == mapId)
		 	{
		  		addPoint(Integer.parseInt(invasions.get(i)[2]),Integer.parseInt(invasions.get(i)[3]),1);
		  		nbm++;
		 	} 
		}
	 	nbMonster.setText(nbm + "");
	 	
	 	//leInterface.sendCheckPlayers(this, mapId);
		
		layeredPane.repaint();
	}
	
	public void checkPlayersCallback(ArrayList<String[]> players)
	{
		System.out.println("Call Back Players : " + players.size());
		
		removePoints();
		int nbp = 0;
		for(int i = 0; i < players.size(); i++)
		{
		  	nbp++;
		}
	 	nbPlayer.setText(nbp + "");
	 	
	 	leInterface.sendCheckInvasion(this);
		
		layeredPane.repaint();
	}

	public void stopCheckInvaTimer()
	{
		System.out.println("Stop Check Inva");
		checkInvaTimer.cancel();
		removePoints();
	}
	
	public void addPoint(int x,int y, int color)
	{
	    ImageIcon croixIcon = new ImageIcon( croixPath.toString() );
	    JLabel croix;
	    croix = new JLabel(croixIcon);
	    croix.setBounds(0, 0, 15, 15);
	    croix.setLocation((x*DISPLAYED_MAP_SIZE/mapSize)-7,DISPLAYED_MAP_SIZE-(y*DISPLAYED_MAP_SIZE/mapSize)+7);
	    crossList.add(croix);
	    layeredPane.add(croix);
	    layeredPane.moveToFront(croix);
	}
	
	public void removePoints()
	{
	    for(int i = 0; i < crossList.size(); i++)
	    {
	    	layeredPane.remove(crossList.get(i));
	    } 
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*mapSize/DISPLAYED_MAP_SIZE;
		int yPos = mapSize-((int)(e.getPoint().getY())*mapSize)/DISPLAYED_MAP_SIZE;
		
		System.out.println(xPos + " - " + yPos);
		if(commandoButton.isSelected())
		{
			if (formCommando.getOrder() == CommandoOrder.Add)
			{
				leInterface.commandoAjouter(xPos, yPos, mapId, formCommando.getCommandoType(), formCommando.getGroupId());
			}
			else if (formCommando.getOrder() == CommandoOrder.Go)
			{
				leInterface.commandoGo(xPos, yPos, mapId, -1, formCommando.getGroupId());
			}
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
	
	protected void activeButton (JButton b)
	{
	    b.setBackground(Color.blue);
	    b.setForeground(Color.white);
	}
	protected void passiveButton (JButton b)
	{
	    b.setBackground(null);
	    b.setForeground(null);
	}
}

