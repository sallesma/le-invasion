package aide_invasion_le;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;

public class WindowMonsters extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextArea listMons = new JTextArea("----");
	private Hashtable<String, Integer>  monstersHash = new Hashtable<String, Integer> ();
	
	private JList<String> liste;
	public String selectedMonster = "";

	public WindowMonsters() {
		this.setTitle("Monsters");
		this.setLayout(new BorderLayout());

		this.setSize(200, 100);
		this.setVisible(false);
		
		/*listMons.setEditable(false);  
		listMons.setCursor(null);  
		listMons.setOpaque(false);  
		listMons.setFocusable(false);
		listMons.setLineWrap(true);
		listMons.setWrapStyleWord(true);
		this.add(listMons);*/

		liste = new JList<String>();
		liste.setSelectionModel(new DefaultListSelectionModel(){
			private static final long serialVersionUID = 1L;
			@Override
            public void setSelectionInterval(int index0, int index1) {
                if (index0==index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        selectedMonster = "";
                        return;
                    }
                }
                super.setSelectionInterval(index0, index1);
                selectedMonster =  liste.getSelectedValue().split(" : ")[0];
            }
            @Override
            public void addSelectionInterval(int index0, int index1) {
                if (index0==index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        selectedMonster = "";
                        return;
                    }
                super.addSelectionInterval(index0, index1);
                selectedMonster =  liste.getSelectedValue().split(" : ")[0];
                }
            }
        });
		this.add(liste);

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public void setList(ArrayList<String[]> mons, int map){
		monstersHash.clear();
		if (mons.size() == 0)
			listMons.setText("----");
		else
		{
			listMons.setText("");
			for(int i = 0; i < mons.size(); i++)
			{
				if(Integer.parseInt(mons.get(i)[4]) == map)
					monstersHash.merge(mons.get(i)[0], 1, Integer::sum);
					//listMons.setText(listMons.getText() + mons.get(i)[0] + "\n");
			}
			
			Enumeration<String> enumKey = monstersHash.keys();
			while(enumKey.hasMoreElements()) {
				String key = enumKey.nextElement();
			    Integer val = monstersHash.get(key);
			    listMons.setText(listMons.getText() + key + " : " + val + "\n");
			}
			liste.setListData(listMons.getText().split("\\r?\\n"));
			
			for(int i = 0; i < liste.getModel().getSize(); i++)
			{
				if(liste.getModel().getElementAt(i).split(" : ")[0].equals(selectedMonster))
				{
					liste.setSelectedIndex(i);
					return;
				}
			}
			selectedMonster = "";
		}
	}
}
