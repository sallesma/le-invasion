package aide_invasion_le;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;

public class Actor extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int actorId, x, y, rot, type, frame, maxHealth, curHealth, kindOfActor, scale, attachmentType;
	private String actorName;
	

	public Actor(Icon image, int actorId, int x, int y) {
		// TODO Auto-generated constructor stub
		super();
		this.setIcon(image);
		this.setActorId(actorId);
		this.x = x;
		this.y = y;
	}


	public int getActorId() {
		return actorId;
	}


	public void setActorId(int actorId) {
		this.actorId = actorId;
	}


	public int getPosX() {
		return x;
	}


	public void setPosX(int x) {
		this.x = x;
	}


	public int getPosY() {
		return y;
	}


	public void setPosY(int y) {
		this.y = y;
	}
	
}
