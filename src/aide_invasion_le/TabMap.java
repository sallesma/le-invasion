package aide_invasion_le;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import aide_invasion_le.FormCommando.CommandoOrder;

public class TabMap extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JLabel image;
	private ILEInterface leInterface;
	private int mapId;
	private int mapSize;
	
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
	private JLabel clearLabel = new JLabel("Clear invasions");
	private JButton clearPonct = new JButton("Ponctuel");
	private JButton clearPerm = new JButton("Permanent");
	private JButton clearPeri = new JButton("Périssable");
	private JButton clearAuto = new JButton("Automatique");
	
	private JLayeredPane layeredPane = new JLayeredPane();
	private ArrayList<JLabel> crossList = new ArrayList<JLabel>();
	
	private final static int DISPLAYED_MAP_SIZE = 400;

	private String croixPath = "images\\croixRed.png";
	
	public TabMap(ILEInterface leInterface, Path mapFile, int mapSize, int mapId)
	{
		this.leInterface = leInterface;
		this.mapId = mapId;
		this.mapSize = mapSize;
		this.formClassic = new FormClassic();
		this.formCommando = new FormCommando(leInterface, mapId);
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
		
		mapIdValue.setText(Integer.toString(mapId));
		mapIdValue.setEditable(false);
		
	    JPanel bottomLeftPanel = new JPanel();
	    bottomLeftPanel.setLayout(new GridLayout(15, 1));
		bottomLeftPanel.add(mapIdLabel);
		bottomLeftPanel.add(mapIdValue);
		bottomLeftPanel.add(clearLabel);
		bottomLeftPanel.add(clearPonct);
		bottomLeftPanel.add(clearPerm);
		bottomLeftPanel.add(clearPeri);
		bottomLeftPanel.add(clearAuto);
		
		clearPonct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
        		TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_PONCT, TabMap.this.mapId);
            }
		});  
		clearPerm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_PERM, TabMap.this.mapId);
        	}
        });  
		clearPeri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_PERI, TabMap.this.mapId);
        	}
        });  
		clearAuto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	TabMap.this.leInterface.clearInvasion(LEInterfaceWindowed.INVASION_TYPE_AUTO, TabMap.this.mapId);
            }
		});  
	    
		
	    layeredPane.setPreferredSize(new Dimension(400, 400)); 
	    
	    JPanel bottomRightPanel = new JPanel();
	    ImageIcon mapIcon = new ImageIcon( mapFile.toString() );
	    Image scaledMapImage = Resize_image.scaleImage(mapIcon.getImage(), DISPLAYED_MAP_SIZE);//size in pixels
	    Icon scaledMapIcon = new ImageIcon(scaledMapImage);
	    this.image = new JLabel(scaledMapIcon);
	    this.image.addMouseListener(this);
	    
	    
	    image.setBounds(0, 0, 400, 400);
	    layeredPane.add(image);
	    bottomRightPanel.add(layeredPane);

	    
	    
	    bottomPanel.add(bottomLeftPanel);
	    bottomPanel.add(bottomRightPanel);
	    
	    this.add(bottomPanel);
	}
	
	public void setLEIinterface(ILEInterface leInterface) {
		this.leInterface = leInterface;
	}
	
	public void addPoint(int x,int y, int color)
	{
	    ImageIcon croixIcon = new ImageIcon( croixPath.toString() );
	    JLabel croix;
	    croix = new JLabel(croixIcon);
	    croix.setBounds(0, 0, 15, 15);
	    croix.setLocation((x*DISPLAYED_MAP_SIZE/mapSize)-7,(y*DISPLAYED_MAP_SIZE/mapSize)-7);
	    crossList.add(croix);
	    layeredPane.add(croix);
	    layeredPane.moveToFront(croix);
	}
	
	public void removePoints()
	{
	    for(int i = 0; i < crossList.size(); i++)
	    {
	    	layeredPane.remove(crossList.get(i));
	    } 
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = (int)(e.getPoint().getX())*mapSize/DISPLAYED_MAP_SIZE;
		int yPos = mapSize-((int)(e.getPoint().getY())*mapSize)/DISPLAYED_MAP_SIZE;
		
		System.out.println(xPos + " - " + yPos);
		if(commandoButton.isSelected())
		{
			if (formCommando.getOrder() == CommandoOrder.Add)
			{
				leInterface.commandoAjouter(xPos, yPos, mapId, formCommando.getCommandoType(), formCommando.getGroupId());
			}
			else if (formCommando.getOrder() == CommandoOrder.Go)
			{
				leInterface.commandoGo(xPos, yPos, mapId, -1, formCommando.getGroupId());
			}
		}
		else
			leInterface.addInvasion(formClassic.getInvasionType(), xPos, yPos, mapId, formClassic.getMonsterType(), formClassic.getMonsterNumber());
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

