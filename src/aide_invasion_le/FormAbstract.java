package aide_invasion_le;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FormAbstract extends JPanel {

	private static final long serialVersionUID = 1L;

	public FormAbstract() {
		super();
		this.setPreferredSize (new Dimension(600, 250));
	}

	protected void activeButton (JButton b)
	{
	    b.setBackground(Color.blue);
	    b.setForeground(Color.white);
	}
	protected void passiveButton (JButton b)
	{
	    b.setBackground(null);
	    b.setForeground(null);
	}
}
