package aide_invasion_le;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import aide_invasion_le.FormCommando.CommandoOrder;

public class TabMap extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JLabel image;
	private LEInterface leInterface;
	
	private JPanel ligneFormSelect = new JPanel();
	private JLabel formButtonLabel = new JLabel("Type de formulaire :");
	private JRadioButton classicButton;
	private JRadioButton commandoButton;
	
	private JPanel formPanel = new JPanel();
	private FormClassic formClassic;
	private FormCommando formCommando;
	
	private JPanel bottomPanel = new JPanel();
	private JLabel mapIdLabel = new JLabel("Numéro Carte :");
	private TextField mapIdValue = new TextField("1",10);
	private JLabel mapSizeLabel = new JLabel("Taille Carte :");
	private TextField mapSizeValue = new TextField("384",10);
	private JLabel clearLabel = new JLabel("Clear invasions");
	private JButton clearPonct = new JButton("Ponctuel");
	private JButton clearPerm = new JButton("Permanent");
	private JButton clearPeri = new JButton("Périssable");
	private JButton clearAuto = new JButton("Automatique");
	
	private final static int DISPLAYED_MAP_SIZE = 400;
	
	public TabMap(final LEInterface leInterface, Path mapFile, int defautSize, int defautNumber)
	{
		this.leInterface = leInterface;
		this.formClassic = new FormClassic();
		this.formCommando = new FormCommando(leInterface, defautNumber);
        this.requestFocus();

		classicButton = new JRadioButton("Classique");
	    commandoButton = new JRadioButton("Commando");

	    ButtonGroup group = new ButtonGroup();
	    group.add(classicButton);
	    group.add(commandoButton);
	    
	    classicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				formPanel.removeAll();
				formPanel.add(formClassic);
				formPanel.updateUI();
			}
		});
	    commandoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				formPanel.removeAll();
				formPanel.add(formCommando);
				formPanel.updateUI();
			}
		});
	    ligneFormSelect.add(formButtonLabel);
	    ligneFormSelect.add(classicButton);
	    ligneFormSelect.add(commandoButton);
	    this.add(ligneFormSelect);
	    this.add(formPanel);
	    classicButton.doClick();
		
		mapIdValue.setText(Integer.toString(defautNumber));
		mapIdValue.setEditable(false);
		mapSizeValue.setText(Integer.toString(defautSize));
		mapSizeValue.setEditable(false);
		
	    JPanel bottomLeftPanel = new JPanel();
	    bottomLeftPanel.setLayout(new GridLayout(15, 1));
		bottomLeftPanel.add(mapIdLabel);
		bottomLeftPanel.add(mapIdValue);
		bottomLeftPanel.add(mapSizeLabel);
		bottomLeftPanel.add(mapSizeValue);
		bottomLeftPanel.add(clearLabel);
		bottomLeftPanel.add(clearPonct);
		bottomLeftPanel.add(clearPerm);
		bottomLeftPanel.add(clearPeri);
		bottomLeftPanel.add(clearAuto);
		
		clearPonct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int mapId = Integer.parseInt(mapIdValue.getText());
        		leInterface.clearInvasion(LEInterface.INVASION_TYPE_PONCT, mapId);
            }
		});  
		clearPerm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int mapId = Integer.parseInt(mapIdValue.getText());
        		leInterface.clearInvasion(LEInterface.INVASION_TYPE_PERM, mapId);
        	}
        });  
		clearPeri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int mapId = Integer.parseInt(mapIdValue.getText());
            	leInterface.clearInvasion(LEInterface.INVASION_TYPE_PERI, mapId);
        	}
        });  
		clearAuto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int mapId = Integer.parseInt(mapIdValue.getText());
            	leInterface.clearInvasion(LEInterface.INVASION_TYPE_AUTO, mapId);
            }
		});  
	    
	    JPanel bottomRightPanel = new JPanel();
	    ImageIcon mapIcon = new ImageIcon( mapFile.toString() );
	    Image scaledMapImage = Resize_image.scaleImage(mapIcon.getImage(), DISPLAYED_MAP_SIZE);//size in pixels
	    Icon scaledMapIcon = new ImageIcon(scaledMapImage);
	    this.image = new JLabel(scaledMapIcon);
	    this.image.addMouseListener(this);
	    bottomRightPanel.add(image);
	    
	    bottomPanel.add(bottomLeftPanel);
	    bottomPanel.add(bottomRightPanel);
	    
	    this.add(bottomPanel);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*Integer.parseInt(mapSizeValue.getText())/DISPLAYED_MAP_SIZE;
		int yPos = Integer.parseInt(mapSizeValue.getText())-((int)(e.getPoint().getY())*Integer.parseInt(mapSizeValue.getText())/DISPLAYED_MAP_SIZE);
		
		System.out.println(xPos + " - " + yPos);
		if(commandoButton.isSelected())
		{
			if (formCommando.getOrder() == CommandoOrder.Add)
			{
				leInterface.commandoAjouter(xPos, yPos, Integer.parseInt(mapIdValue.getText()), formCommando.getCommandoType(), formCommando.getGroupId());
			}
			else if (formCommando.getOrder() == CommandoOrder.Go)
			{
				leInterface.commandoGo(xPos, yPos, Integer.parseInt(mapIdValue.getText()), -1, formCommando.getGroupId());
			}
		}
		else
			leInterface.addInvasion(formClassic.getInvasionType(), xPos, yPos, Integer.parseInt(mapIdValue.getText()), formClassic.getMonsterType(), formClassic.getMonsterNumber());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}

