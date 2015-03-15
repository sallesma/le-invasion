package aide_invasion_le;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class TabGame extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	
	private final static int CASE_AFFICHEES = 50;
	private final static int ZONE_CARTE = 400;

	private JLabel image;
	private JLabel croix;
	private LEInterfaceWindowed leInterface;
	private int mapId = 0;
	private int mapSize = 192;
	private int casesAffichees = 30;
	private int posX = 96;
	private int posY = 96;
	private int sizeBigMap = 0;
	String mapFile = "images\\1_trepont.jpg";
	String croixPath = "images\\croix.png";
	
	private TextArea tchat = new TextArea(5, 30);
	private TextField tchatInput = new TextField(30);
	
	
	private final static int DISPLAYED_MAP_SIZE = 400;
	
	public TabGame(final LEInterfaceWindowed leInterface)
	{
		this.leInterface = leInterface;
		
		JPanel topPanel = new JPanel();
		
		JPanel ligne1 = new JPanel();
		//topPanel.setSize(50, 10);
		ligne1.add(tchat);
		topPanel.add(ligne1);
		
		JPanel ligne2 = new JPanel();
		//tchatInput.setSize(50, 10);
		ligne2.add(tchatInput);
		topPanel.add(ligne2);
		
	    add(topPanel);
		
		//========= Bot ==========
	    
		JPanel botPanel = new JPanel();
		
	    
	    JLayeredPane layeredPane = new JLayeredPane();
	    layeredPane.setPreferredSize(new Dimension(400, 400));
	    
	    ImageIcon croixIcon = new ImageIcon( croixPath.toString() );
	    croix = new JLabel(croixIcon);
	    croix.setBounds(0, 0, 15, 15);
	    layeredPane.add(croix);
	    
	    ImageIcon mapIcon = new ImageIcon( mapFile.toString() );
	    Image scaledMapImage = Resize_image.scaleImage(mapIcon.getImage(), 400);//size in pixels
	    sizeBigMap = ZONE_CARTE*mapSize/CASE_AFFICHEES;
	    Image scaledMapImage2 = Resize_image.scaleImage(scaledMapImage, sizeBigMap);//size in pixels
	    Icon scaledMapIcon = new ImageIcon(scaledMapImage2);
	    this.image = new JLabel(scaledMapIcon);
	    this.image.addMouseListener(this);
	    image.setBounds(0, 0, sizeBigMap, sizeBigMap);
	    centerMapOnPlayer();
		
	    layeredPane.add(image);
	    
	    //layeredPane.add(new Cross());
	    
	    botPanel.add(layeredPane);
	    
	    add(botPanel);
	}
	
	public void centerMapOnPlayer()
	{
		//image.setLocation( (sizeBigMap/mapSize*posX) - ZONE_CARTE/2, (sizeBigMap/mapSize*posY) - ZONE_CARTE/2);
		
		image.setLocation(ZONE_CARTE/2 - (sizeBigMap/mapSize*posX),-sizeBigMap+ZONE_CARTE/2 + (sizeBigMap/mapSize*posY));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*mapSize/DISPLAYED_MAP_SIZE;
		int yPos = mapSize-((int)(e.getPoint().getY())*mapSize)/DISPLAYED_MAP_SIZE;
		
		int sizeCroix = croix.getSize().height/2;
		
		croix.setLocation((int)(e.getPoint().getX()-sizeCroix+image.getLocation().getX()), (int) (e.getPoint().getY()-sizeCroix+image.getLocation().getY()));
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

