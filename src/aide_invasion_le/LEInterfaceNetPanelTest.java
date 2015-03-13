package aide_invasion_le;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LEInterfaceNetPanelTest  extends JPanel{

	LEInterfaceNet interfaceNet = new LEInterfaceNet();
	
	
	private TextField pseudo = new TextField("test_interf",10);
	private TextField pwd = new TextField("azerty",10);
	private JButton connect = new JButton("Connect");
	
	public LEInterfaceNetPanelTest() {
		// TODO Auto-generated constructor stub
		connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	connection(); }});
		
		JPanel ligne1 = new JPanel();
		ligne1.add(pseudo);
		ligne1.add(pwd);
		ligne1.add(connect);
		
		this.add(ligne1);
	}
	
	public void connection()
	{
		interfaceNet.connection();
	}

}
