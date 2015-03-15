package aide_invasion_le;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import aide_invasion_le.FormCommando.CommandoOrder;

public class PanelJeu extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JLabel image;
	private LEInterface leInterface;
	private int mapId = 109;
	private int mapSize = 384;
	private int casesAffichees = 30;
	private int posX = 100;
	private int posY = 100;
	String mapFile = "images\\2_aeth_aelfan.jpg";
	
	
	
	private final static int DISPLAYED_MAP_SIZE = 400;
	
	public PanelJeu(final LEInterface leInterface)
	{
		this.leInterface = leInterface;
	
		
	    JPanel mapPanel = new JPanel();
	    ImageIcon mapIcon = new ImageIcon( mapFile.toString() );
	    Image scaledMapImage = Resize_image.scaleImage(mapIcon.getImage(), DISPLAYED_MAP_SIZE);//size in pixels
	    Icon scaledMapIcon = new ImageIcon(scaledMapImage);
	    this.image = new JLabel(scaledMapIcon);
	    this.image.addMouseListener(this);
	    mapPanel.add(image);
	    
	    add(mapPanel);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*mapSize/DISPLAYED_MAP_SIZE;
		int yPos = mapSize-((int)(e.getPoint().getY())*mapSize)/DISPLAYED_MAP_SIZE;
		
		
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

