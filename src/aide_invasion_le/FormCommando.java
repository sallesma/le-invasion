package aide_invasion_le;

import java.awt.Dimension;
import java.awt.GridLayout;

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
	
	private static JButton[] tablButtonCommando = new JButton[50];
	private static int[] valButtonCommando = {1,2,1010,1011,1012,1013,1014,1015,1016,1017}; //prov

	public FormCommando() {
		// TODO search in file
		
		JPanel bloc = new JPanel();
		bloc.setLayout(new GridLayout(10, 1));
		
		
		JPanel ligne1 = new JPanel();
		ligne1.add(choixCom);
		
		bloc.add(ligne1);
		
		JPanel prov = new JPanel();
		for (int i=0; i<valButtonCommando.length; i++) {
			
			if ( (i % 5) == 0)
			{
				bloc.add(prov);
				prov = new JPanel();
				
			}
			tablButtonCommando[i] = new JButton(String.valueOf(valButtonCommando[i]));
			prov.add(tablButtonCommando[i]);

		}
		bloc.add(prov);

		this.add(bloc);
		this.setPreferredSize (new Dimension(600, 250));
	}
}
