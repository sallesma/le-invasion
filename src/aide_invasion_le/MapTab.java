package aide_invasion_le;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapTab extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel image;
	LEInterface leInterface;
	
	FormClassic formC = new FormClassic();
	FormCommando formCom = new FormCommando();
	
	int numForm = 1;

	
	//Gauche de l'image
	JLabel carte = new JLabel("Num Carte");
	private TextField zoneCarte = new TextField("1",10);
	JLabel carteT = new JLabel("Taille Carte");
	private TextField zoneCarteT = new TextField("384",10);
	JLabel clear = new JLabel("Clear invasions");
	private static JButton clearPonct = new JButton("Ponctuel");
	private static JButton clearPerm = new JButton("Permanent");
	private static JButton clearPeri = new JButton("Périssable");
	private static JButton clearAuto = new JButton("Automatique");
	
	private final static int TAILLE_CARTE_AFFICHE = 400;
	
	
	public MapTab(final LEInterface leInterface, Path mapFile)
	{
		JPanel ligne1 = new JPanel();
	    
	    
	   
	    JPanel ligneImage = new JPanel();
	    
	    //ligneImage.setLayout(new GridLayout(1, 2));
	    
	    JPanel ligneImageG = new JPanel();
	    
	    ligneImageG.setLayout(new GridLayout(15, 1));
		ligneImageG.add(carte);
		ligneImageG.add(zoneCarte);
		ligneImageG.add(carteT);
		ligneImageG.add(zoneCarteT);
		ligneImageG.add(clear);
		ligneImageG.add(clearPonct);
		ligneImageG.add(clearPerm);
		ligneImageG.add(clearPeri);
		ligneImageG.add(clearAuto);
		
		//Clear
		clearPonct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int mapId = Integer.parseInt(zoneCarte.getText());
        		leInterface.clearInvasion(LEInterface.INVASION_TYPE_PONCT, mapId);
            }
		});  
		clearPerm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int mapId = Integer.parseInt(zoneCarte.getText());
        		leInterface.clearInvasion(LEInterface.INVASION_TYPE_PERM, mapId);
        	}
        });  
		clearPeri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int mapId = Integer.parseInt(zoneCarte.getText());
            	leInterface.clearInvasion(LEInterface.INVASION_TYPE_PERI, mapId);
        	}
        });  
		clearAuto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int mapId = Integer.parseInt(zoneCarte.getText());
            	leInterface.clearInvasion(LEInterface.INVASION_TYPE_AUTO, mapId);
            }
		});  
	    
	    JPanel ligneImageD = new JPanel();
	    
	    ImageIcon carteIc = new ImageIcon( mapFile.toString() );
	    Image zoom = Resize_image.scaleImage(carteIc.getImage(), TAILLE_CARTE_AFFICHE);//taille en pixels
	    Icon iconScaled = new ImageIcon(zoom);
	    image = new JLabel(iconScaled);
	    //pan.setLayout(new BorderLayout, CENTER);
	    image.addMouseListener(this);
	    ligneImageD.add(image);
	    
	    ligneImage.add(ligneImageG);
	    ligneImage.add(ligneImageD);

	    if (numForm == 0)
	    {
	    	FormClassic formC= new FormClassic();
	    	formC.setPreferredSize (new Dimension(600, 250));
	    	this.add(formC);
	    } else if (numForm == 1)
	    {
	    	FormCommando formCom= new FormCommando();
	    	formCom.setPreferredSize (new Dimension(600, 250));
	    	this.add(formCom);
	    }
	    
	    this.add(ligneImage);
	    
		this.leInterface = leInterface;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*Integer.parseInt(zoneCarteT.getText())/TAILLE_CARTE_AFFICHE;
		int yPos = Integer.parseInt(zoneCarteT.getText())-((int)(e.getPoint().getY())*Integer.parseInt(zoneCarteT.getText())/TAILLE_CARTE_AFFICHE);
		leInterface.addInvasion(formC.getType_inva(), xPos, yPos, Integer.parseInt(zoneCarte.getText()), formC.getType_mob(), formC.getNb_monstres());
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
