package aide_invasion_le;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WindowHelp extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel imageCanvas;
	private Image image;
	
	private final static String HELP_FILE = Paths.get("images", "help.jpg").toString();

	public WindowHelp() {
		this.setTitle("Help");
		this.setLayout(new BorderLayout());

		ImageIcon imageIcon = new ImageIcon(HELP_FILE);
		this.image = ResizeImage.scaleImage(imageIcon.getImage(), 1850);
		
		this.imageCanvas = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, null);
			}
		};
		imageCanvas.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
		
		JScrollPane scrollPane = new JScrollPane(imageCanvas);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		this.add(scrollPane, BorderLayout.CENTER);

		this.setSize(1000, 700);
		this.setVisible(true);
	}
}
