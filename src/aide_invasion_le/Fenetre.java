package aide_invasion_le;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre  extends JFrame implements MouseListener {
	JLabel image;
	LEInterface leInterface;
	
	//Mobs Ligne 1
	private static final long serialVersionUID = 1L;
	private static final Color Color = null;
	private static JPanel pan = new JPanel();
	private static JButton boutonMonstre1 = new JButton("Créature 1");
	private static JButton boutonMonstre2 = new JButton("Créature 2");
	private static JButton boutonMonstre3 = new JButton("Créature 3");
	private static JButton boutonMonstre4 = new JButton("Créature 4");
	private static JButton boutonMonstre5 = new JButton("Créature 5");
	private TextField tm1 = new TextField("lapin_brun",10);
	private TextField tm2 = new TextField("lapin_blanc",10); 
	private TextField tm3 = new TextField("rat",10); 
	private TextField tm4 = new TextField("farfadet",10); 
	private TextField tm5 = new TextField("lutin",10); 
	
	//Mobs Ligne 2
	private static JButton boutonMonstre6 = new JButton("Créature 6");
	private static JButton boutonMonstre7 = new JButton("Créature 7");
	private static JButton boutonMonstre8 = new JButton("Créature 8");
	private static JButton boutonMonstre9 = new JButton("Créature 9");
	private static JButton boutonMonstre10 = new JButton("Créature10");
	private TextField tm6 = new TextField("squelette",10);
	private TextField tm7 = new TextField("squelette_faible",10); 
	private TextField tm8 = new TextField("squelette_brille",10); 
	private TextField tm9 = new TextField("squelette_boum",10); 
	private TextField tm10 = new TextField("squelette_horreur",10); 
	
	//Nombres
	private final static Dimension TAILLE_NB_BUTTON = new Dimension(57,26);
	private static JButton nbMonstre1 = new JButton("1");
	private static JButton nbMonstre5 = new JButton("5");
	private static JButton nbMonstre10 = new JButton("10");
	private static JButton nbMonstre15 = new JButton("15");
	private static JButton nbMonstre20 = new JButton("20");
	private static JButton nbMonstre30 = new JButton("30");
	private static JButton nbMonstre50 = new JButton("50");
	private static JButton nbMonstre100 = new JButton("100");
	
	//type inva
	private static JButton ponctB = new JButton("Ponctuel");
	private static JButton permB = new JButton("Permanent");
	private static JButton periB = new JButton("Périssable");
	private static JButton autoB = new JButton("Automatique");
	
	//Gauche de l'image
	JLabel pseudo = new JLabel("Burno");
	private TextField zonePseudo = new TextField("",10); 
	JLabel server = new JLabel("Serveur");
	JComboBox<String> zoneServer = new JComboBox<String>(new String[]{"main", "test"}); 
	JLabel carte = new JLabel("Num Carte");
	private TextField zoneCarte = new TextField("1",10);
	JLabel clear = new JLabel("Clear");
	private static JButton clearPonct = new JButton("Ponct");
	private static JButton clearPerm = new JButton("Perm");
	private static JButton clearPeri = new JButton("Peri");
	private static JButton clearAuto = new JButton("Auto");
	JLabel imageAf = new JLabel("Image");
	private static JButton choixImage = new JButton("...");
	
	private final static int TAILLE_CARTE_AFFICHE = 400;
	private final static int TAILLE_CARTE = 384;
	
	private String type_inva = "ponct";
	private String type_mob = "rat";
	private int nb_monstres = 1;
	
	public Fenetre() {
		JFrame fenetre = creerFenetre();
		this.leInterface = new LEInterface();
		this.updateLEInterface();
	}
	
	private void updateLEInterface() {
		this.leInterface.setPseudo(zonePseudo.getText());
		this.leInterface.setServer(zoneServer.getSelectedItem().toString());
	}

	JFrame creerFenetre()
	{
		
		JFrame fenetre = new JFrame();
	    fenetre.setTitle("Gestionnaire Invasion");
	    fenetre.setSize(600, 800);
	    //Nous demandons maintenant à notre fenêtre de se positionner au centre
	    fenetre.setLocationRelativeTo(null);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JPanel ligne1 = new JPanel();
	    ligne1.add(tm1);
	    ligne1.add(tm2);
	    ligne1.add(tm3);
	    ligne1.add(tm4);
	    ligne1.add(tm5);
	    
	    JPanel ligne2 = new JPanel();
	    ligne2.add(boutonMonstre1);
	    boutonMonstre1.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e){
            	type_mob = tm1.getText(); activeButtonMob(boutonMonstre1); }});  
	    ligne2.add(boutonMonstre2);
	    boutonMonstre2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm2.getText(); activeButtonMob(boutonMonstre2); }});
	    ligne2.add(boutonMonstre3);
	    boutonMonstre3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm3.getText(); activeButtonMob(boutonMonstre3); }});
	    ligne2.add(boutonMonstre4);
	    boutonMonstre4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm4.getText(); activeButtonMob(boutonMonstre4); }});
	    ligne2.add(boutonMonstre5);
	    boutonMonstre5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm5.getText(); activeButtonMob(boutonMonstre5); }});
	    
	    JPanel ligne3 = new JPanel();
	    ligne3.add(tm6);
	    ligne3.add(tm7);
	    ligne3.add(tm8);
	    ligne3.add(tm9);
	    ligne3.add(tm10);
	    
	    JPanel ligne4 = new JPanel();
	    ligne4.add(boutonMonstre6);
	    boutonMonstre6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm6.getText(); activeButtonMob(boutonMonstre6); }});  
	    ligne4.add(boutonMonstre7);
	    boutonMonstre7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm7.getText(); activeButtonMob(boutonMonstre7); }});  
	    ligne4.add(boutonMonstre8);
	    boutonMonstre8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm8.getText(); activeButtonMob(boutonMonstre8); }});  
	    ligne4.add(boutonMonstre9);
	    boutonMonstre9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm9.getText(); activeButtonMob(boutonMonstre9); }});  
	    ligne4.add(boutonMonstre10);
	    boutonMonstre10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	type_mob = tm10.getText(); activeButtonMob(boutonMonstre10); }});  
	    
	    JPanel ligne5 = new JPanel();
		nbMonstre1.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre5.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre10.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre15.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre20.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre30.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre50.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre100.setPreferredSize(TAILLE_NB_BUTTON);
		ligne5.add(nbMonstre1);
		nbMonstre1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	nb_monstres = 1; activeButtonNb(nbMonstre1); }});  
		ligne5.add(nbMonstre5);
		nbMonstre5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            nb_monstres = 5; activeButtonNb(nbMonstre5); }});  
		ligne5.add(nbMonstre10);
		nbMonstre10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            nb_monstres = 10; activeButtonNb(nbMonstre10); }});  
		ligne5.add(nbMonstre15);
		nbMonstre15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            nb_monstres = 15; activeButtonNb(nbMonstre15); }});  
		ligne5.add(nbMonstre20);
		nbMonstre20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            nb_monstres = 20; activeButtonNb(nbMonstre20); }});  
		ligne5.add(nbMonstre30);
		nbMonstre30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            nb_monstres = 30; activeButtonNb(nbMonstre30); }});  
		ligne5.add(nbMonstre50);
		nbMonstre50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            nb_monstres = 50; activeButtonNb(nbMonstre50); }});  
		ligne5.add(nbMonstre100);
		nbMonstre100.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            nb_monstres = 100; activeButtonNb(nbMonstre100); }});  
	    
		
		JPanel ligne6 = new JPanel();
		ligne6.add(ponctB);
		ponctB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	type_inva = LEInterface.INVASION_TYPE_PONCT;
            	activeButtonType(ponctB);
            }
        }); 
		ligne6.add(permB);
		permB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	type_inva = LEInterface.INVASION_TYPE_PERM;
            	activeButtonType(permB);
            }
        }); 
		ligne6.add(periB);
		periB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	type_inva = LEInterface.INVASION_TYPE_PERI;
            	activeButtonType(periB);
            }
        }); 
		ligne6.add(autoB);
		autoB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	type_inva = LEInterface.INVASION_TYPE_AUTO;
            	activeButtonType(autoB);
            }
        }); 
		
	    JPanel ligneImage = new JPanel();
	    
	    //ligneImage.setLayout(new GridLayout(1, 2));
	    
	    JPanel ligneImageG = new JPanel();
	    
	    ligneImageG.setLayout(new GridLayout(15, 1));
		ligneImageG.add(pseudo);
		ligneImageG.add(zonePseudo);
		ligneImageG.add(server);
		ligneImageG.add(zoneServer);
		ligneImageG.add(carte);
		ligneImageG.add(zoneCarte);
		ligneImageG.add(clear);
		ligneImageG.add(clearPonct);
		ligneImageG.add(clearPerm);
		ligneImageG.add(clearPeri);
		ligneImageG.add(clearAuto);
		ligneImageG.add(imageAf);
		choixImage.setPreferredSize(TAILLE_NB_BUTTON);
		ligneImageG.add(choixImage);
		
		zonePseudo.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateLEInterface();
			}
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		zoneServer.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateLEInterface();
			}
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
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
	    
	    ImageIcon carteIc = new ImageIcon( "images/vide.jpg");
	    Image zoom = Resize_image.scaleImage(carteIc.getImage(), TAILLE_CARTE_AFFICHE);//taille en pixels
	    Icon iconScaled = new ImageIcon(zoom);
	    image = new JLabel(iconScaled);
	    //pan.setLayout(new BorderLayout, CENTER);
	    image.addMouseListener(this);
	    ligneImageD.add(image);
	    
		//Choix Image
		choixImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
        		JFileChooser dialogue = new JFileChooser(new File(".\\images"));
        		File fichier2;
        		
        		if (dialogue.showOpenDialog(null)== 
        		    JFileChooser.APPROVE_OPTION) {
        		    fichier2 = dialogue.getSelectedFile();
        		    System.out.println(dialogue.getName(fichier2));
        		    ImageIcon carteIc = new ImageIcon( "images/" + dialogue.getName(fichier2));
        		    Image zoom = Resize_image.scaleImage(carteIc.getImage(), TAILLE_CARTE_AFFICHE);//taille en pixels
        		    Icon iconScaled = new ImageIcon(zoom);
        		    image.setIcon(iconScaled);
        		    choixImage.setText(dialogue.getName(fichier2));
        		} }});  
	    
	    ligneImage.add(ligneImageG);
	    ligneImage.add(ligneImageD);

	    pan.add(ligne1);
	    pan.add(ligne2);
	    pan.add(ligne3);
	    pan.add(ligne4);
	    pan.add(ligne5);
	    pan.add(ligne6);
	    pan.add(ligneImage);
	    fenetre.setContentPane(pan);
	    
	    fenetre.setVisible(true);
	    
	    return fenetre;
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*TAILLE_CARTE/TAILLE_CARTE_AFFICHE;
		int yPos = TAILLE_CARTE-((int)(e.getPoint().getY())*TAILLE_CARTE/TAILLE_CARTE_AFFICHE);
		leInterface.addInvasion(type_inva, xPos, yPos, Integer.parseInt(zoneCarte.getText()), type_mob, nb_monstres);
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
	
	private void activeButtonMob (JButton b)
	{
		passiveButton(boutonMonstre1);
		passiveButton(boutonMonstre2);
		passiveButton(boutonMonstre3);
		passiveButton(boutonMonstre4);
		passiveButton(boutonMonstre5);
		passiveButton(boutonMonstre6);
		passiveButton(boutonMonstre7);
		passiveButton(boutonMonstre8);
		passiveButton(boutonMonstre8);
		passiveButton(boutonMonstre9);
		passiveButton(boutonMonstre10);
		
		activeButton(b);
	}
	
	private void activeButtonNb(JButton b)
	{
		passiveButton(nbMonstre1);
		passiveButton(nbMonstre5);
		passiveButton(nbMonstre10);
		passiveButton(nbMonstre15);
		passiveButton(nbMonstre20);
		passiveButton(nbMonstre30);
		passiveButton(nbMonstre50);
		passiveButton(nbMonstre100);
		
		activeButton(b);
	}

	private void activeButtonType(JButton b)
	{
		passiveButton(ponctB);
		passiveButton(permB);
		passiveButton(periB);
		passiveButton(autoB);
		
		activeButton(b);
	}
	private void activeButton (JButton b)
	{
	    b.setBackground(Color.blue);
	    b.setForeground(Color.white);
	}
	private void passiveButton (JButton b)
	{
	    b.setBackground(null);
	    b.setForeground(null);
	}
	
	
}
