package aide_invasion_le;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormCommando extends JPanel {

	/*JLabel carte = new JLabel("Num Carte");
	private TextField zoneCarte = new TextField("1",10);
	JLabel clear = new JLabel("Clear invasions");
	private static JButton clearPonct = new JButton("Ponctuel");*/
	
	private static final long serialVersionUID = 1L;

	private JLabel choixCom = new JLabel("Commando");
	String commandoFilePath = Paths.get("data", "commando.list").toString();
	
	private static JButton[] tablButtonCommando = new JButton[50];
	ArrayList<String> valButtonCommando = new ArrayList<String>();
	
	public FormCommando() {
		// TODO search in file
		
		recupCommandos();
		
		JPanel bloc = new JPanel();
		bloc.setLayout(new GridLayout(10, 1));
		
		
		JPanel ligne1 = new JPanel();
		ligne1.add(choixCom);
		
		bloc.add(ligne1);
		
		JPanel prov = new JPanel();
		for (int i=0; i<valButtonCommando.size(); i++) {
			
			if ( (i % 5) == 0)
			{
				bloc.add(prov);
				prov = new JPanel();
				
			}
			tablButtonCommando[i] = new JButton(valButtonCommando.get(i));
			prov.add(tablButtonCommando[i]);

		}
		bloc.add(prov);

		this.add(bloc);
		this.setPreferredSize (new Dimension(600, 250));
	}

	private void recupCommandos() {
		// TODO Auto-generated method stub
		
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
}
