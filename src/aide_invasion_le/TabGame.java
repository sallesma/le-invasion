package aide_invasion_le;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class TabGame extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	private final static int CASE_AFFICHEES = 50;
	private final static int ZONE_CARTE = 400;
	private final static int UNIT_MAP = ZONE_CARTE / CASE_AFFICHEES;

	private Actor avatar;
	private JLabel image;
	private JLabel croix;
	private ILEInterface leInterface;
	private int mapId = 0;
	private int mapSize = 192;
	private int sizeBigMap = 0;
	String avatarFile = Paths.get("images","actors","guy.png").toString();
	String rabbitFile = Paths.get("images","actors","white_rabbit.png").toString();
	String impFile = Paths.get("images","actors","imp.png").toString();
	String deerFile = Paths.get("images","actors","deer.png").toString();
	String beaverFile = Paths.get("images","actors","beaver.png").toString();
	String falconFile = Paths.get("images","actors","falcon.png").toString();
	String brownieFile = Paths.get("images","actors","brownie.png").toString();
	String ratFile = Paths.get("images","actors","rat.png").toString();
	String otherFile = Paths.get("images","actors","other.png").toString();
	String mapFile = Paths.get("images","1_trepont.jpg").toString();
	String croixPath = Paths.get("images", "croix.png").toString();
	
	private MapsManager mapsMan = new MapsManager();
	private JLayeredPane layeredPane = new JLayeredPane();
	
	private JLabel tchat = new JLabel("test1\ntest2");
	private TextField tchatInput = new TextField(30);
	
	private LinkedList<Actor> actorList = new LinkedList<Actor>();
	
	private final static int DISPLAYED_MAP_SIZE = 400;
	
	public TabGame(ILEInterface leInterface)
	{
		this.leInterface = leInterface;
		leInterface.gameAdd(this);
		
		JPanel topPanel = new JPanel();
		
		JPanel ligne1 = new JPanel();
		ligne1.add(tchat);
		topPanel.add(ligne1);
		
		JPanel ligne2 = new JPanel();
		ligne2.add(tchatInput);
		tchatInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = tchatInput.getText();
				if(message != "") {
					TabGame.this.leInterface.sendRawText(message);
					//tchat.append("\n" + message);
				}
				tchatInput.setText("");
			}
		});
		topPanel.add(ligne2);
		
	    add(topPanel);
		
		//========= Bot ==========
	    
		JPanel botPanel = new JPanel();
	    
	    layeredPane.setPreferredSize(new Dimension(400, 400));
	    
	    ImageIcon croixIcon = new ImageIcon( croixPath.toString() );
	    croix = new JLabel(croixIcon);
	    croix.setBounds(0, 0, 15, 15);
	    layeredPane.add(croix);
	    
	    ImageIcon avatarIcon = new ImageIcon( avatarFile.toString() );
	    Image scaledAvatarImage = ResizeImage.scaleImage(avatarIcon.getImage(), UNIT_MAP*3);//size in pixels
	    Icon scaledAvatarIcon = new ImageIcon(scaledAvatarImage);
	    this.avatar = new Actor(scaledAvatarIcon, 0, 85, 133);
	    avatar.setBounds(0, 0, UNIT_MAP*3, UNIT_MAP*3);
	    this.avatar.setLocation(DISPLAYED_MAP_SIZE/2-avatar.getHeight()/2, DISPLAYED_MAP_SIZE/2-avatar.getWidth()/2);
	    layeredPane.add(avatar);
	    
	    /*ImageIcon mapIcon = new ImageIcon( mapFile.toString() );
	    Image scaledMapImage = ResizeImage.scaleImage(mapIcon.getImage(), 400);//size in pixels
	    sizeBigMap = ZONE_CARTE*mapSize/CASE_AFFICHEES;
	    Image scaledMapImage2 = ResizeImage.scaleImage(scaledMapImage, sizeBigMap);//size in pixels
	    Icon scaledMapIcon = new ImageIcon(scaledMapImage2);
	    this.image = new JLabel(scaledMapIcon);
	    this.image.addMouseListener(this);*/
	    this.image = new JLabel("");
	    image.setBounds(0, 0, sizeBigMap, sizeBigMap);
	    centerMapOnPlayer();
		
	    layeredPane.add(image);
	    
	    botPanel.add(layeredPane);
	    add(botPanel);
	}
	
	public void centerMapOnPlayer()
	{
		int moveX = image.getX();
		int moveY = image.getY();
		image.setLocation(ZONE_CARTE/2 - (getRelativeSquarre(avatar.getPosX())),-sizeBigMap+ZONE_CARTE/2 + (getRelativeSquarre((int) avatar.getPosY())));
	
		moveX-=image.getX();
		moveY-=image.getY();
		
		for(int i = 0; i < actorList.size(); i++)
			actorList.get(i).setLocation(actorList.get(i).getX()+moveX, actorList.get(i).getY()+moveY);
		
		croix.setLocation(croix.getX()+moveX, croix.getY()+moveY);
		
		
		layeredPane.repaint();
	}
	
	public void centerMapOnPlayerPlayerMove()
	{
		int moveX = image.getX();
		int moveY = image.getY();
		image.setLocation(ZONE_CARTE/2 - (getRelativeSquarre(avatar.getPosX())),-sizeBigMap+ZONE_CARTE/2 + (getRelativeSquarre((int) avatar.getPosY())));
	
		moveX-=image.getX();
		moveY-=image.getY();
		
		for(int i = 0; i < actorList.size(); i++)
			actorList.get(i).setLocation(actorList.get(i).getX()-moveX, actorList.get(i).getY()-moveY);
		
		croix.setLocation(croix.getX()-moveX, croix.getY()-moveY);
		
		
		layeredPane.repaint();
	}
	
	public int getRelativeSquarre(int x)
	{
		return sizeBigMap/mapSize*x;
	}
	
	public void changeMap(String data)
	{
		System.out.println("TabGame : changeMap");
		Pattern pattern = Pattern.compile("maps/(.*).elm");
		Matcher matcher = pattern.matcher(data);
		if (matcher.find()) {
			String mapName = matcher.group(1) + ".jpg";
			System.out.println("Parse : " + mapName);
			
			mapSize = mapsMan.getMapSize(mapName);
			mapId = mapsMan.getMapId(mapName);

			mapFile = Paths.get("images",mapName).toString();
			
			System.out.println("mapFile : " + mapFile);
			
			layeredPane.remove(image);
			ImageIcon mapIcon = new ImageIcon( mapFile.toString() );
		    //Image scaledMapImage = ResizeImage.scaleImage(mapIcon.getImage(), ZONE_CARTE);//size in pixels
		    sizeBigMap = ZONE_CARTE*mapSize/CASE_AFFICHEES;
		    Image scaledMapImage2 = ResizeImage.scaleImage(mapIcon.getImage(), sizeBigMap);//size in pixels
		    Icon scaledMapIcon = new ImageIcon(scaledMapImage2);
		    this.image = new JLabel(scaledMapIcon);
		    this.image.addMouseListener(this);
		    image.setBounds(0, 0, sizeBigMap, sizeBigMap);
		    centerMapOnPlayer();
		    layeredPane.add(image);
		}
	}
	
	public void myId(int data)
	{
		avatar.setActorId(data);
		System.out.println("My ID : " + data);
	}
	
	public void newActor(byte[] data)
	{
		int actorId = (data[0]&0xFF)+(data[1]&0xFF)*255;
		int x = (data[2]&0xFF)+(data[3]&0x07)*255;
		int y = (data[4]&0xFF)+(data[5]&0x07)*255;
		int rot = (data[8]&0xFF)+(data[9]&0xFF)*255;
		int type = data[10]&0xFF;
		int frame = data[11]&0xFF;
		int maxHealth = (data[12]&0xFF)+(data[13]&0xFF)*255;
		int curHealth = (data[14]&0xFF)+(data[15]&0xFF)*255;
		int kindOfActor = data[16]&0xFF;
		String actorName = "";
		int i=17;
		for (i=17;i<data.length-1;i++)
			actorName+=(char)data[i];
		//int ACTOR_SCALE_BASE = 1;
		//int scale = (data[i+1]&0xFF);
		
		System.out.println("New Actor : id=" + actorId + " x=" + x + " y=" + y + " rot=" + rot + " type=" + type + " frame=" + frame + " maxHealth=" + maxHealth + " curHealth=" + curHealth + " kingOfActor=" + kindOfActor + " actorName=" + actorName);
		
		if(actorId == avatar.getActorId())
		{
			avatar.setPosX(x);
			avatar.setPosY(y);
			System.out.println("Loca : " + avatar.getLocation());
			centerMapOnPlayer();
			
			System.out.println("X=" + x + " Y=" + y + "XPer=" + avatar.getPosX() + " YPer=" + avatar.getPosY());
			
		}
		else
		{
			ImageIcon actorIcon;
			if(type == 62)
				actorIcon = new ImageIcon( brownieFile.toString() );
			else if(type == 61)
				actorIcon = new ImageIcon( impFile.toString() );
			else if(type == 83)
				actorIcon = new ImageIcon( falconFile.toString() );
			else if(type == 19)
				actorIcon = new ImageIcon( rabbitFile.toString() );
			else if(type == 8)
				actorIcon = new ImageIcon( beaverFile.toString() );
			else if(type == 15)
				actorIcon = new ImageIcon( deerFile.toString() );
			else if(type == 9)
				actorIcon = new ImageIcon( ratFile.toString() );
			else
				actorIcon = new ImageIcon( otherFile.toString() );
		    Image scaledActorImage = ResizeImage.scaleImage(actorIcon.getImage(), UNIT_MAP*3);//size in pixels
		    Icon scaledActorIcon = new ImageIcon(scaledActorImage);
		    Actor act = new Actor(scaledActorIcon, actorId, x, y);
		    act.setBounds(0, 0, UNIT_MAP*3, UNIT_MAP*3);
		    act.setLocation(UNIT_MAP*x+image.getX(),UNIT_MAP*(mapSize-y)+image.getY());
		    layeredPane.add(act);
		    layeredPane.moveToFront(act);
		    System.out.println("X=" + x + " Y=" + y + "XPer=" + act.getPosX() + " YPer=" + act.getPosY());
			actorList.add(act);
		}
	}
	
	public void removeActor(byte[] data)
	{
		int actorId = (data[0]&0xFF)+(data[1]&0xFF)*255;
		for(int i = 0; i < actorList.size(); i++)
		      if(actorList.get(i).getActorId()==actorId)
		    	  layeredPane.remove(actorList.get(i));
		
		layeredPane.repaint();
	}
	
	public void newEnhancedActor(byte[] data)
	{
		int actorId = (data[0]&0xFF)+(data[1]&0xFF)*255;
		int x = (data[2]&0xFF)+(data[3]&0x07)*255;
		int y = (data[4]&0xFF)+(data[5]&0x07)*255;
		int rot = (data[8]&0xFF)+(data[9]&0xFF)*255;
		int type = data[10]&0xFF;
		int frame = data[11]&0xFF;

		System.out.println("New Actor : id=" + actorId + " x=" + x + " y=" + y + " rot=" + rot + " type=" + type + " frame=" + frame);
		
		if(actorId == avatar.getActorId())
		{
			avatar.setPosX(x);
			avatar.setPosY(y);
			System.out.println("Loca : " + avatar.getLocation());
			centerMapOnPlayer();
			
			System.out.println("X=" + x + " Y=" + y + "XPer=" + avatar.getPosX() + " YPer=" + avatar.getPosY());
			
		}
		else
		{
		    ImageIcon actorIcon = new ImageIcon( avatarFile.toString() );
		    Image scaledActorImage = ResizeImage.scaleImage(actorIcon.getImage(), UNIT_MAP*3);//size in pixels
		    Icon scaledActorIcon = new ImageIcon(scaledActorImage);
		    Actor act = new Actor(scaledActorIcon, actorId, x, y);
		    act.setBounds(0, 0, UNIT_MAP*3, UNIT_MAP*3);
		    act.setLocation(UNIT_MAP*x+image.getX(),UNIT_MAP*(mapSize-y)+image.getY());
		    layeredPane.add(act);
		    layeredPane.moveToFront(act);
		    System.out.println("X=" + x + " Y=" + y + "XPer=" + act.getX() + " YPer=" + act.getY() + "XAv=" + image.getX() + " YAv=" + image.getY());
			actorList.add(act);
		}
	}
	
	public void actorCommand(byte[] data)
	{
		int actorId = (data[0]&0xFF)+(data[1]&0xFF)*255;
		int command = data[2]&0xFF;
		
		Actor act=null;
		if(actorId==avatar.getActorId())
			act=avatar;
		else
		for(int i = 0; i < actorList.size(); i++)
		      if(actorList.get(i).getActorId()==actorId)
		    	  act = actorList.get(i);
		
		if(command >= 20 && command <= 27)
			move(actorId, act, command);
		else
			System.out.println("actorCommand : id=" + actorId + " command=" + command);
		
	}
	
	public void move(int actorId, Actor act, int command)
	{
		int moveX = 0, moveY = 0;
		switch (command)
		{
		  case 20:
		    //move_n	20
			  moveY++;
		    break;  
		  case 21:
		    //move_ne	21
			  moveX++;
			  moveY++;
		    break;  
		  case 22:
		    //move_e	22
			  moveX++;
		    break;  
		  case 23:
		    //move_se	23
			  moveX++;
			  moveY--;
		    break;  
		  case 24:
		    //move_s	24
			  moveY--;
		    break;  
		  case 25:
		    //move_sw	25
			  moveX--;
			  moveY--;
		    break;  
		  case 26:
		    //move_w	26
			  moveX--;
		    break;  
		  case 27:
		    //move_nw	27
			  moveX--;
			  moveY++;
		    break;        
		  default:
			  /*Action*/;             
		}
		
		act.setPosX(act.getPosX()+moveX);
		act.setPosY(act.getPosY()+moveY);
		
		if(actorId==avatar.getActorId())
			centerMapOnPlayerPlayerMove();
		else
		{
			act.setLocation(act.getX()+moveX*UNIT_MAP, act.getY()-moveY*UNIT_MAP);
		}
		System.out.println("moveX=" + moveX + " moveY=" + moveY + " act=" + act.getLocation());
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())/UNIT_MAP;
		int yPos = (int)(sizeBigMap-e.getPoint().getY())/UNIT_MAP;
		
		int sizeCroix = croix.getSize().height/2;
		
		croix.setLocation((int)(e.getPoint().getX()-sizeCroix+image.getLocation().getX()), (int) (e.getPoint().getY()-sizeCroix+image.getLocation().getY()));

		System.out.println("mouse X=" + xPos + " Y=" + yPos + "XPer=" + avatar.getPosX() + " YPer=" + avatar.getPosY());

		//marche pas prov
		Point p = new Point(xPos,yPos);
		for(int i = 0; i < actorList.size(); i++)
		{
			System.out.println("mouse X=" + e.getPoint().getX() + " Y=" + e.getPoint().getY() + "XAct=" + actorList.get(i).getX() + " YAct=" + actorList.get(i).getY() + " YAct=" + actorList.get(i).getSize().getHeight());
			if(e.getPoint().getX()>=actorList.get(i).getX() &&
				e.getPoint().getY()>=actorList.get(i).getY() &&
				e.getPoint().getX()<=actorList.get(i).getX()+actorList.get(i).getSize().getHeight() &&
				e.getPoint().getY()<=actorList.get(i).getY()+actorList.get(i).getSize().getWidth())
			{
				leInterface.attackActor(actorList.get(i).getActorId());
				System.out.println("Attack : " + actorList.get(i).getActorId());
				return;
			}
		}
		leInterface.moveTo(xPos, yPos);
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
