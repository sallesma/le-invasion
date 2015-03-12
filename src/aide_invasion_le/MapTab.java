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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MapTab extends JPanel implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	JLabel image;
	LEInterface leInterface;
	
	private JPanel ligneFormSelect = new JPanel();
	private JLabel formButtonLabel = new JLabel("Type de formulaire :");
	private JRadioButton classicButton;
	private JRadioButton commandoButton;
	
	private JPanel formPane = new JPanel();
	private FormClassic formC = new FormClassic();
	private FormCommando formCom;
	
	//Gauche de l'image
	private JPanel ligneImage = new JPanel();
	private JLabel carte = new JLabel("Num Carte");
	private TextField zoneCarte = new TextField("1",10);
	private JLabel carteT = new JLabel("Taille Carte");
	private TextField zoneCarteT = new TextField("384",10);
	private JLabel clear = new JLabel("Clear invasions");
	private static JButton clearPonct = new JButton("Ponctuel");
	private static JButton clearPerm = new JButton("Permanent");
	private static JButton clearPeri = new JButton("Périssable");
	private static JButton clearAuto = new JButton("Automatique");
	
	private final static int TAILLE_CARTE_AFFICHE = 400;
	
	
	public MapTab(final LEInterface leInterface, Path mapFile, int defautSize, int defautNumber)
	{
		this.leInterface = leInterface;
		formCom = new FormCommando(leInterface);
		 // ajoute un écouteur d'événements
		addKeyListener(this);
        requestFocus();

		classicButton = new JRadioButton("Classique");
	    commandoButton = new JRadioButton("Commando");

	    ButtonGroup group = new ButtonGroup();
	    group.add(classicButton);
	    group.add(commandoButton);
	    
	    
	    classicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				formPane.removeAll();
				formPane.add(formC);
				formPane.updateUI();
			}
		});
	    commandoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				formPane.removeAll();
				formPane.add(formCom);
				formPane.updateUI();
			}
		});
	    classicButton.doClick();
	    ligneFormSelect.add(formButtonLabel);
	    ligneFormSelect.add(classicButton);
	    ligneFormSelect.add(commandoButton);
	    this.add(ligneFormSelect);
	    this.add(formPane);
		
		zoneCarte.setText(Integer.toString(defautNumber));
		zoneCarteT.setText(Integer.toString(defautSize));
		
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
	    image.addMouseListener(this);
	    ligneImageD.add(image);
	    
	    ligneImage.add(ligneImageG);
	    ligneImage.add(ligneImageD);
	    
	    this.add(ligneImage);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*Integer.parseInt(zoneCarteT.getText())/TAILLE_CARTE_AFFICHE;
		int yPos = Integer.parseInt(zoneCarteT.getText())-((int)(e.getPoint().getY())*Integer.parseInt(zoneCarteT.getText())/TAILLE_CARTE_AFFICHE);
		
		if(commandoButton.isSelected())
		{
			if (formCom.getOrdre() == 1)
			{
				leInterface.commandoAjouter(xPos, yPos, Integer.parseInt(zoneCarte.getText()), formCom.getType(), formCom.getGroupe());
			}
			else if (formCom.getOrdre() == 2)
			{
				leInterface.commandoGo(xPos, yPos, Integer.parseInt(zoneCarte.getText()), formCom.getType(), formCom.getGroupe());
			}
		}
		else
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
	
    public void keyTyped(KeyEvent e) {
        // on ne fait rien
    	
    }

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		   System.out.println("test");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	


}

