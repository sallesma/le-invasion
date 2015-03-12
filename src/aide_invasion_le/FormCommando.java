package aide_invasion_le;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormCommando extends FormAbstract {
	
	private static final long serialVersionUID = 1L;
	
	LEInterface leInterface;
	MapTab parent;

	private JLabel choixCom = new JLabel("Commando");
	String commandoFilePath = Paths.get("data", "commando.list").toString();
	
	private static JButton[] tablButtonCommando = new JButton[50];
	ArrayList<String> valButtonCommando = new ArrayList<String>();
	
	private JLabel choixGroupe = new JLabel("Groupe");
	//Nombres
	private JButton groupe1 = new JButton("1");
	private JButton groupe2 = new JButton("2");
	private JButton groupe3 = new JButton("3");
	private JButton groupe4 = new JButton("4");
	private JButton groupe5 = new JButton("5");
	private JButton groupe6 = new JButton("6");
	private JButton groupe7 = new JButton("7");
	private JButton groupe8 = new JButton("8");
	private JButton groupe9 = new JButton("9");
	private JButton groupe10 = new JButton("10");
	
	private JLabel choixOrdre = new JLabel("Ordre");
	private JButton ordreAjouter = new JButton("Déploiement !");
	private JButton ordreGo = new JButton("Chargez !");
	private JButton ordreFree = new JButton("Dispersion !");
	private JButton ordreStop = new JButton("Halte !");
	
	private int commandoType = 1;
	private int groupId = 1;
	
	public enum CommandoOrder {
		Add,
		Go,
		DoNothing
	}
	private CommandoOrder order = CommandoOrder.DoNothing;
	
	public FormCommando(final LEInterface leInterface, final MapTab parent) {
		// TODO search in file
		this.leInterface = leInterface;
		this.parent = parent;
		
		this.recupCommandos();
		
		JPanel bloc = new JPanel();
		bloc.setLayout(new GridLayout(8, 1));
		
		
		JPanel ligne1 = new JPanel();
		ligne1.add(choixCom);
		
		bloc.add(ligne1);
		
		JPanel prov = new JPanel();
		for (int i=0; i<valButtonCommando.size(); i++) {
			
			if ( (i % 5) == 0 && i>0)
			{
				bloc.add(prov);
				prov = new JPanel();
				
			}
			final JButton jb = new JButton(valButtonCommando.get(i));
			jb.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e){
	                setCommandoType(Integer.parseInt(jb.getText())); activeButtonCommando(jb); }});
			tablButtonCommando[i] = jb;
			prov.add(tablButtonCommando[i]);

		}
		bloc.add(prov);

		
		JPanel ligne3 = new JPanel();
		ligne3.add(choixGroupe);
		
		JPanel ligne4 = new JPanel();
		groupe1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(1); activeButtonGroup(groupe1); }});
		groupe2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(2); activeButtonGroup(groupe2); }});
		groupe3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(3); activeButtonGroup(groupe3); }});
		groupe4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(4); activeButtonGroup(groupe4); }});
		groupe5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(5); activeButtonGroup(groupe5); }});
		groupe6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(6); activeButtonGroup(groupe6); }});
		groupe7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(7); activeButtonGroup(groupe7); }});
		groupe8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(8); activeButtonGroup(groupe8); }});
		groupe9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(9); activeButtonGroup(groupe9); }});
		groupe10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setGroupId(10); activeButtonGroup(groupe10); }});
		ligne4.add(groupe1);
		ligne4.add(groupe2);
		ligne4.add(groupe3);
		ligne4.add(groupe4);
		ligne4.add(groupe5);
		ligne4.add(groupe6);
		ligne4.add(groupe7);
		ligne4.add(groupe8);
		ligne4.add(groupe9);
		ligne4.add(groupe10);
		
		JPanel ligne5 = new JPanel();
		ligne5.add(choixOrdre);
		
		JPanel ligne6 = new JPanel();
		ordreAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
           	setOrder(CommandoOrder.Add); activeButtonOrder(ordreAjouter); }});
		ordreGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setOrder(CommandoOrder.Go); activeButtonOrder(ordreGo); }});
		ordreFree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setOrder(CommandoOrder.DoNothing); activeButtonOrder(ordreFree); leInterface.commandoFree(parent.getNumCarte(), -1, getGroupId()); }});
		ordreStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            setOrder(CommandoOrder.DoNothing); activeButtonOrder(ordreStop); leInterface.commandoStop(parent.getNumCarte(), -1, getGroupId()); }});
		ligne6.add(ordreAjouter);
		ligne6.add(ordreGo);
		ligne6.add(ordreFree);
		ligne6.add(ordreStop);
		
		bloc.add(ligne3);
		bloc.add(ligne4);
		bloc.add(ligne5);
		bloc.add(ligne6);
		this.add(bloc);
		this.setPreferredSize (new Dimension(600, 250));
	}

	private void recupCommandos() {
		try{
			InputStream ips=new FileInputStream(commandoFilePath); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			String str[]= new String[4];
			while ((ligne=br.readLine())!=null){
				str=ligne.split(";");
				valButtonCommando.add(str[0]);
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	private void activeButtonCommando(JButton b)
	{
		for (int i=0; i<valButtonCommando.size(); i++) {
			passiveButton(tablButtonCommando[i]);
		}
		activeButton(b);
	}
	
	private void activeButtonGroup(JButton b)
	{
		passiveButton(groupe1);
		passiveButton(groupe2);
		passiveButton(groupe3);
		passiveButton(groupe4);
		passiveButton(groupe5);
		passiveButton(groupe6);
		passiveButton(groupe7);
		passiveButton(groupe8);
		passiveButton(groupe9);
		passiveButton(groupe10);
		
		activeButton(b);
	}
	
	private void activeButtonOrder(JButton b)
	{
		passiveButton(ordreAjouter);
		passiveButton(ordreGo);
		passiveButton(ordreFree);
		passiveButton(ordreStop);
		
		activeButton(b);
		
		if (b.equals(ordreFree)||b.equals(ordreStop))
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			passiveButton(b);
		}
	}
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public CommandoOrder getOrder() {
		return order;
	}

	public void setOrder(CommandoOrder order) {
		this.order = order;
	}
	
	public int getCommandoType() {
		return commandoType;
	}

	public void setCommandoType(int commandoType) {
		this.commandoType = commandoType;
	}
}
