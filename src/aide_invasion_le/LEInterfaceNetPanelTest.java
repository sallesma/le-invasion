package aide_invasion_le;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LEInterfaceNetPanelTest  extends JPanel{

	private static final long serialVersionUID = 1L;

	LEInterfaceNet interfaceNet = new LEInterfaceNet();
	
	private TextField serveradr = new TextField("jeu.landes-eternelles.com",10);
	private TextField port = new TextField("3001",10);
	private JButton connect = new JButton("Connect");
	
	private TextField pseudo = new TextField("test_interf",10);
	private TextField pwd = new TextField("azerty",10);
	private JButton login = new JButton("Login");
	
	private TextField message = new TextField("",30);
	private JButton send = new JButton("Send");

	private JButton ping = new JButton("Ping");
	private JButton close = new JButton("Close");
	
	public LEInterfaceNetPanelTest() {
		JPanel bloc = new JPanel();
		bloc.setLayout(new GridLayout(8, 1));
		
		JPanel ligne1 = new JPanel();
		connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	connection(); }});
		
		ligne1.add(serveradr);
		ligne1.add(port);
		ligne1.add(connect);
		bloc.add(ligne1);
		
		JPanel ligne2 = new JPanel();
		login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	loginFunc(); }});
		
		ligne2.add(pseudo);
		ligne2.add(pwd);
		ligne2.add(login);
		bloc.add(ligne2);
		
		JPanel ligne3 = new JPanel();
		send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	sendMessage(); }});
		
		ligne3.add(message);
		ligne3.add(send);
		bloc.add(ligne3);
		
		ping.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	ping(); }});
		bloc.add(ping);
		
		close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	close(); }});
		bloc.add(close);
		
		this.add(bloc);
	}
	
	public void connection()
	{
		interfaceNet.connection(serveradr.getText(), Integer.parseInt(port.getText()));
		interfaceNet.startHeart_Beat();
	}
	
	public void loginFunc()
	{
		interfaceNet.login(pseudo.getText(), pwd.getText());
	}
	
	public void ping()
	{
		interfaceNet.ping();
	}
	
	public void close()
	{
		interfaceNet.close();
	}
	
	public void sendMessage()
	{
		if (message.getText().length()>0)
			interfaceNet.sendMessage(0, message.getText());
	}

}
