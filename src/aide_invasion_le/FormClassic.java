package aide_invasion_le;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FormClassic extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//Mobs Ligne 1
	private JButton boutonMonstre1 = new JButton("Créature 1");
	private JButton boutonMonstre2 = new JButton("Créature 2");
	private JButton boutonMonstre3 = new JButton("Créature 3");
	private JButton boutonMonstre4 = new JButton("Créature 4");
	private JButton boutonMonstre5 = new JButton("Créature 5");
	private TextField tm1 = new TextField("lapin_brun",10);
	private TextField tm2 = new TextField("lapin_blanc",10); 
	private TextField tm3 = new TextField("rat",10); 
	private TextField tm4 = new TextField("farfadet",10); 
	private TextField tm5 = new TextField("lutin",10); 
	
	//Mobs Ligne 2
	private JButton boutonMonstre6 = new JButton("Créature 6");
	private JButton boutonMonstre7 = new JButton("Créature 7");
	private JButton boutonMonstre8 = new JButton("Créature 8");
	private JButton boutonMonstre9 = new JButton("Créature 9");
	private JButton boutonMonstre10 = new JButton("Créature10");
	private TextField tm6 = new TextField("squelette",10);
	private TextField tm7 = new TextField("squelette_faible",10); 
	private TextField tm8 = new TextField("squelette_brille",10); 
	private TextField tm9 = new TextField("squelette_boum",10); 
	private TextField tm10 = new TextField("squelette_horreur",10); 
	
	//Nombres
	private final static Dimension TAILLE_NB_BUTTON = new Dimension(60,26);
	private JButton nbMonstre1 = new JButton("1");
	private JButton nbMonstre5 = new JButton("5");
	private JButton nbMonstre10 = new JButton("10");
	private JButton nbMonstre15 = new JButton("15");
	private JButton nbMonstre20 = new JButton("20");
	private JButton nbMonstre30 = new JButton("30");
	private JButton nbMonstre50 = new JButton("50");
	private JButton nbMonstre100 = new JButton("100");
	
	//type inva
	private JButton ponctB = new JButton("Ponctuel");
	private JButton permB = new JButton("Permanent");
	private JButton periB = new JButton("Périssable");
	private JButton autoB = new JButton("Automatique");
	
	private String type_inva = "ponct";
	private String type_mob = "rat";
	private int nb_monstres = 1;

	public FormClassic() {
		JPanel ligne1 = new JPanel();
	    ligne1.add(tm1);
	    ligne1.add(tm2);
	    ligne1.add(tm3);
	    ligne1.add(tm4);
	    ligne1.add(tm5);
	    
	    boutonMonstre1.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e){
            	setType_mob(tm1.getText()); activeButtonMob(boutonMonstre1); }});  
	    boutonMonstre2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm2.getText()); activeButtonMob(boutonMonstre2); }});
	    boutonMonstre3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm3.getText()); activeButtonMob(boutonMonstre3); }});
	    boutonMonstre4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm4.getText()); activeButtonMob(boutonMonstre4); }});
	    boutonMonstre5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm5.getText()); activeButtonMob(boutonMonstre5); }});
	    JPanel ligne2 = new JPanel();
	    ligne2.add(boutonMonstre2);
	    ligne2.add(boutonMonstre1);
	    ligne2.add(boutonMonstre3);
	    ligne2.add(boutonMonstre4);
	    ligne2.add(boutonMonstre5);
	    
	    JPanel ligne3 = new JPanel();
	    ligne3.add(tm6);
	    ligne3.add(tm7);
	    ligne3.add(tm8);
	    ligne3.add(tm9);
	    ligne3.add(tm10);
	    
	    boutonMonstre6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm6.getText()); activeButtonMob(boutonMonstre6); }});
	    boutonMonstre7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm7.getText()); activeButtonMob(boutonMonstre7); }});
	    boutonMonstre8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm8.getText()); activeButtonMob(boutonMonstre8); }});
	    boutonMonstre9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm9.getText()); activeButtonMob(boutonMonstre9); }});
	    boutonMonstre10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setType_mob(tm10.getText()); activeButtonMob(boutonMonstre10); }});
	    JPanel ligne4 = new JPanel();
	    ligne4.add(boutonMonstre6);
	    ligne4.add(boutonMonstre7);
	    ligne4.add(boutonMonstre8);
	    ligne4.add(boutonMonstre9);
	    ligne4.add(boutonMonstre10);
	    
	    JPanel ligne5 = new JPanel();
		nbMonstre1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setNb_monstres(1); activeButtonNb(nbMonstre1); }});
		nbMonstre5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setNb_monstres(5); activeButtonNb(nbMonstre5); }});
		nbMonstre10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setNb_monstres(10); activeButtonNb(nbMonstre10); }});
		nbMonstre15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setNb_monstres(15); activeButtonNb(nbMonstre15); }});
		nbMonstre20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setNb_monstres(20); activeButtonNb(nbMonstre20); }});
		nbMonstre30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setNb_monstres(30); activeButtonNb(nbMonstre30); }});
		nbMonstre50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setNb_monstres(50); activeButtonNb(nbMonstre50); }});
		nbMonstre100.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setNb_monstres(100); activeButtonNb(nbMonstre100); }});
		nbMonstre1.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre5.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre10.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre15.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre20.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre30.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre50.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre100.setPreferredSize(TAILLE_NB_BUTTON);
		ligne5.add(nbMonstre1);
		ligne5.add(nbMonstre5);
		ligne5.add(nbMonstre10);
		ligne5.add(nbMonstre15);
		ligne5.add(nbMonstre20);
		ligne5.add(nbMonstre30);
		ligne5.add(nbMonstre50);
		ligne5.add(nbMonstre100);
		
		JPanel ligne6 = new JPanel();
		ponctB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setType_inva(LEInterface.INVASION_TYPE_PONCT);
            	activeButtonType(ponctB);
            }
        });
		permB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setType_inva(LEInterface.INVASION_TYPE_PERM);
            	activeButtonType(permB);
            }
        });
		periB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setType_inva(LEInterface.INVASION_TYPE_PERI);
            	activeButtonType(periB);
            }
        });
		autoB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setType_inva(LEInterface.INVASION_TYPE_AUTO);
            	activeButtonType(autoB);
            }
        });
		ligne6.add(ponctB);
		ligne6.add(permB);
		ligne6.add(periB);
		ligne6.add(autoB);
		
		this.add(ligne1);
		this.add(ligne2);
		this.add(ligne3);
		this.add(ligne4);
		this.add(ligne5);
		this.add(ligne6);
		this.setPreferredSize (new Dimension(600, 250));
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

	public String getType_inva() {
		return type_inva;
	}

	public void setType_inva(String type_inva) {
		this.type_inva = type_inva;
	}

	public String getType_mob() {
		return type_mob;
	}

	public void setType_mob(String type_mob) {
		this.type_mob = type_mob;
	}

	public int getNb_monstres() {
		return nb_monstres;
	}

	public void setNb_monstres(int nb_monstres) {
		this.nb_monstres = nb_monstres;
	}

}
