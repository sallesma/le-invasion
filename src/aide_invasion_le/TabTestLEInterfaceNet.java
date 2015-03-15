package aide_invasion_le;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TabTestLEInterfaceNet extends JPanel {

	private static final long serialVersionUID = 1L;

	LEInterfaceNet interfaceNet;

	private TextField serveradr = new TextField("jeu.landes-eternelles.com", 10);
	private TextField port = new TextField("3001", 10);
	private JButton connect = new JButton("Connect");

	private TextField pseudo = new TextField("test_interf", 10);
	private TextField pwd = new TextField("azerty", 10);
	private JButton login = new JButton("Login");

	private TextField message = new TextField("", 30);
	private JButton send = new JButton("Send");

	private JButton close = new JButton("Close");

	public TabTestLEInterfaceNet() {
		JPanel bloc = new JPanel();
		bloc.setLayout(new GridLayout(8, 1));

		JPanel ligne1 = new JPanel();
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfaceNet  = new LEInterfaceNet(
						"",
						"",
						serveradr.getText(),
						Integer.parseInt(port.getText()));
			}
		});

		ligne1.add(serveradr);
		ligne1.add(port);
		ligne1.add(connect);
		bloc.add(ligne1);

		JPanel ligne2 = new JPanel();
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfaceNet.login(pseudo.getText(), pwd.getText());
			}
		});

		ligne2.add(pseudo);
		ligne2.add(pwd);
		ligne2.add(login);
		bloc.add(ligne2);

		JPanel ligne3 = new JPanel();
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfaceNet.sendRawText(message.getText());
			}
		});

		ligne3.add(message);
		ligne3.add(send);
		bloc.add(ligne3);

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfaceNet.close();
			}
		});
		bloc.add(close);

		this.add(bloc);
	}

	public void close() {
		if (interfaceNet != null)
			interfaceNet.close();
	}
}
