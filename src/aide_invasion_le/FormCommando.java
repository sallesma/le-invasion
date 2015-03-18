package aide_invasion_le;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormCommando extends FormAbstract {
	
	private static final long serialVersionUID = 1L;
	
	private ILEInterface leInterface;
	private int mapId;

	private JLabel typeLabel = new JLabel("Type de commando");
	private String commandoFilePath = Paths.get("data", "commando.list").toString();

	private JButton[] commandoTypeButtons;
	
	private JLabel groupLabel = new JLabel("Groupe");
	private JButton[] commandoGroupButtons;
	private JButton groupeAll = new JButton("All");
	private JButton groupeNone = new JButton("None");
	
	private JLabel ordreLabel = new JLabel("Ordre");
	private JButton ordreAjouter = new JButton("Déploiement !");
	private JButton ordreGo = new JButton("Chargez !");
	private JButton ordreFree = new JButton("Dispersion !");
	private JButton ordreStop = new JButton("Halte !");
	
	private int commandoType = 1;
	private boolean[] groupIds = new boolean[10];
	
	public enum CommandoOrder {
		Add,
		Go,
		DoNothing
	}
	private CommandoOrder order = CommandoOrder.DoNothing;
	
	public FormCommando(final ILEInterface leInterface, final int mapId) {
		this.leInterface = leInterface;
		this.mapId = mapId;
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8, 1));
		
		JPanel ligne1 = new JPanel();
		ligne1.add(typeLabel);
		panel.add(ligne1);
		
		List<String> commandos = this.getCommandosFromFile();
		commandoTypeButtons = new JButton[commandos.size()];
		JPanel typeLine = new JPanel();
		int i = 0;
		for (String commando : commandos) {
			if ( (i % 5) == 4) {
				panel.add(typeLine);
			}
			if ( (i % 5) == 0) {
				typeLine = new JPanel();
			}
			final JButton button = new JButton(commando);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setCommandoType(Integer.parseInt(button.getText()));
					activeButtonCommando(button);
				}
			});
			commandoTypeButtons[i] = button;
			typeLine.add(commandoTypeButtons[i]);
			i++;
		}
		panel.add(typeLine);
		
		JPanel ligne3 = new JPanel();
		ligne3.add(groupLabel);
		
		JPanel ligne4 = new JPanel();
		commandoGroupButtons = new JButton[groupIds.length];
		for(int j = 0 ; j < 10 ; j++) {
			final int index = j;
			commandoGroupButtons[index] = new JButton(String.valueOf(index+1));
			commandoGroupButtons[index].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					groupIds[index] = !groupIds[index];
					activeButtonGroup();
				}
			});
			ligne4.add(commandoGroupButtons[index]);
		}
		groupeAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0 ; i< groupIds.length ;  i++)
					groupIds[i] = true;
				activeButtonGroup();
			}
		});
		groupeNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0 ; i< groupIds.length ;  i++)
					groupIds[i] = false;
				activeButtonGroup();
			}
		});
		JPanel ligne5 = new JPanel();
		ligne5.add(groupeAll);
		ligne5.add(groupeNone);
		
		JPanel ligne6 = new JPanel();
		ligne6.add(ordreLabel);
		
		JPanel ligne7 = new JPanel();
		ordreAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setOrder(CommandoOrder.Add);
            	activeButtonOrder(ordreAjouter);
            }
        });
		ordreGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setOrder(CommandoOrder.Go);
            	activeButtonOrder(ordreGo);
            }
        });
		ordreFree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setOrder(CommandoOrder.DoNothing);
            	activeButtonOrder(ordreFree);
            	for (int groupId : getGroupIds()) {
            		FormCommando.this.leInterface.commandoFree(FormCommando.this.mapId, groupId);
				}
            }
        });
		ordreStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	setOrder(CommandoOrder.DoNothing);
            	activeButtonOrder(ordreStop);
            	for (int groupId : getGroupIds()) {
            		FormCommando.this.leInterface.commandoStop(FormCommando.this.mapId, groupId);
				}
            }
        });
		ligne7.add(ordreAjouter);
		ligne7.add(ordreGo);
		ligne7.add(ordreFree);
		ligne7.add(ordreStop);

		panel.add(ligne3);
		panel.add(ligne4);
		panel.add(ligne5);
		panel.add(ligne6);
		panel.add(ligne7);
		this.add(panel);
	}

	private List<String> getCommandosFromFile() {
		List<String> result = new ArrayList<String>();
		try{
			InputStream ips=new FileInputStream(commandoFilePath); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			String str[]= new String[4];
			while ((ligne=br.readLine())!=null){
				str=ligne.split(";");
				result.add(str[0]);
			}
			br.close(); 
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	private void activeButtonCommando(JButton b)
	{
		for (int i=0; i<commandoTypeButtons.length; i++) {
			passiveButton(commandoTypeButtons[i]);
		}
		activeButton(b);
	}
	
	private void activeButtonGroup()
	{
		int count = 0;
		for(int i=0 ; i< groupIds.length ;  i++)
			if(groupIds[i]) {
				activeButton(commandoGroupButtons[i]);
				count++;
			} else {
				passiveButton(commandoGroupButtons[i]);
			}
		if (count>=2) {
			ordreAjouter.setEnabled(false);
			passiveButton(ordreAjouter);
			setOrder(CommandoOrder.DoNothing);
		} else
			ordreAjouter.setEnabled(true);
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
	
	public List<Integer> getGroupIds() {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0 ; i < groupIds.length ; i++) {
			if (groupIds[i])
				result.add(i+1);
		}
		return result;
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
