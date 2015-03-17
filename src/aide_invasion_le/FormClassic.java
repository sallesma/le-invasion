package aide_invasion_le;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FormClassic extends FormAbstract {

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
	
	//Mobs Ligne 3
	private JButton boutonMonstre11 = new JButton("Créature11");
	private JButton boutonMonstre12 = new JButton("Créature12");
	private JButton boutonMonstre13 = new JButton("Créature13");
	private JButton boutonMonstre14 = new JButton("Créature14");
	private JButton boutonMonstre15 = new JButton("Créature15");
	private TextField tm11 = new TextField("squelette",10);
	private TextField tm12 = new TextField("squelette_faible",10); 
	private TextField tm13 = new TextField("squelette_brille",10); 
	private TextField tm14 = new TextField("squelette_boum",10); 
	private TextField tm15 = new TextField("squelette_horreur",10); 
	
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
	
	private String invasionType = LEInterfaceWindowed.INVASION_TYPE_PONCT;
	private String monsterType = "rat";
	private int monsterNumber = 1;

	public FormClassic() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JPanel ligne1 = new JPanel();
	    ligne1.add(tm1);
	    ligne1.add(tm2);
	    ligne1.add(tm3);
	    ligne1.add(tm4);
	    ligne1.add(tm5);
	    
	    boutonMonstre1.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e){
            	setMonsterType(tm1.getText()); activeButtonMob(boutonMonstre1); }});  
	    boutonMonstre2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm2.getText()); activeButtonMob(boutonMonstre2); }});
	    boutonMonstre3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm3.getText()); activeButtonMob(boutonMonstre3); }});
	    boutonMonstre4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm4.getText()); activeButtonMob(boutonMonstre4); }});
	    boutonMonstre5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm5.getText()); activeButtonMob(boutonMonstre5); }});
	    JPanel ligne2 = new JPanel();
	    ligne2.add(boutonMonstre1);
	    ligne2.add(boutonMonstre2);
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
           	setMonsterType(tm6.getText()); activeButtonMob(boutonMonstre6); }});
	    boutonMonstre7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm7.getText()); activeButtonMob(boutonMonstre7); }});
	    boutonMonstre8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm8.getText()); activeButtonMob(boutonMonstre8); }});
	    boutonMonstre9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm9.getText()); activeButtonMob(boutonMonstre9); }});
	    boutonMonstre10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm10.getText()); activeButtonMob(boutonMonstre10); }});
	    JPanel ligne4 = new JPanel();
	    ligne4.add(boutonMonstre6);
	    ligne4.add(boutonMonstre7);
	    ligne4.add(boutonMonstre8);
	    ligne4.add(boutonMonstre9);
	    ligne4.add(boutonMonstre10);
	    
	    JPanel ligne5 = new JPanel();
	    ligne5.add(tm11);
	    ligne5.add(tm12);
	    ligne5.add(tm13);
	    ligne5.add(tm14);
	    ligne5.add(tm15);
	    
	    boutonMonstre11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm11.getText()); activeButtonMob(boutonMonstre11); }});
	    boutonMonstre12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm12.getText()); activeButtonMob(boutonMonstre12); }});
	    boutonMonstre13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm13.getText()); activeButtonMob(boutonMonstre13); }});
	    boutonMonstre14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm14.getText()); activeButtonMob(boutonMonstre14); }});
	    boutonMonstre15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterType(tm15.getText()); activeButtonMob(boutonMonstre15); }});
	    JPanel ligne6 = new JPanel();
	    ligne6.add(boutonMonstre11);
	    ligne6.add(boutonMonstre12);
	    ligne6.add(boutonMonstre13);
	    ligne6.add(boutonMonstre14);
	    ligne6.add(boutonMonstre15);
	    
	    JPanel ligne7 = new JPanel();
		nbMonstre1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setMonsterNumber(1); activeButtonNb(nbMonstre1); }});
		nbMonstre5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setMonsterNumber(5); activeButtonNb(nbMonstre5); }});
		nbMonstre10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setMonsterNumber(10); activeButtonNb(nbMonstre10); }});
		nbMonstre15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setMonsterNumber(15); activeButtonNb(nbMonstre15); }});
		nbMonstre20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setMonsterNumber(20); activeButtonNb(nbMonstre20); }});
		nbMonstre30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setMonsterNumber(30); activeButtonNb(nbMonstre30); }});
		nbMonstre50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setMonsterNumber(50); activeButtonNb(nbMonstre50); }});
		nbMonstre100.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setMonsterNumber(100); activeButtonNb(nbMonstre100); }});
		nbMonstre1.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre5.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre10.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre15.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre20.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre30.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre50.setPreferredSize(TAILLE_NB_BUTTON);
		nbMonstre100.setPreferredSize(TAILLE_NB_BUTTON);
		ligne7.add(nbMonstre1);
		ligne7.add(nbMonstre5);
		ligne7.add(nbMonstre10);
		ligne7.add(nbMonstre15);
		ligne7.add(nbMonstre20);
		ligne7.add(nbMonstre30);
		ligne7.add(nbMonstre50);
		ligne7.add(nbMonstre100);
		
		JPanel ligne8 = new JPanel();
		ponctB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setInvasionType(LEInterfaceWindowed.INVASION_TYPE_PONCT);
            	activeButtonType(ponctB);
            }
        });
		permB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setInvasionType(LEInterfaceWindowed.INVASION_TYPE_PERM);
            	activeButtonType(permB);
            }
        });
		ligne8.add(ponctB);
		ligne8.add(permB);
		
		
		ligne1.setBounds(0, 0, 500, 30);
		ligne1.setLocation(0, 0);
		ligne2.setBounds(0, 0, 500, 30);
		ligne2.setLocation(0, 30);
		ligne3.setBounds(0, 0, 500, 30);
		ligne3.setLocation(0, 60);
		ligne4.setBounds(0, 0, 500, 30);
		ligne4.setLocation(0, 90);
		ligne5.setBounds(0, 0, 500, 30);
		ligne5.setLocation(0, 120);
		ligne6.setBounds(0, 0, 500, 30);
		ligne6.setLocation(0, 150);
		ligne7.setBounds(0, 0, 500, 30);
		ligne7.setLocation(0, 180);
		ligne8.setBounds(0, 0, 500, 30);
		ligne8.setLocation(0, 210);
		
		this.add(ligne1);
		this.add(ligne2);
		this.add(ligne3);
		this.add(ligne4);
		this.add(ligne5);
		this.add(ligne6);
		this.add(ligne7);
		this.add(ligne8);
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
		
		activeButton(b);
	}

	public String getInvasionType() {
		return this.invasionType;
	}

	public void setInvasionType(String invasionType) {
		this.invasionType = invasionType;
	}

	public String getMonsterType() {
		return this.monsterType;
	}

	public void setMonsterType(String monsterType) {
		this.monsterType = monsterType;
	}

	public int getMonsterNumber() {
		return this.monsterNumber;
	}

	public void setMonsterNumber(int monsterNumber) {
		this.monsterNumber = monsterNumber;
	}

}
