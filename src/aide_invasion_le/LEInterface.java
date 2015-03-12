package aide_invasion_le;

import java.io.IOException;

public class LEInterface {
	public static final String INVASION_TYPE_PONCT = "ponct";
	public static final String INVASION_TYPE_PERM = "perm";
	public static final String INVASION_TYPE_PERI = "peri";
	public static final String INVASION_TYPE_AUTO = "auto";

	private String pseudo = "";
	private String server = "";

	public void addInvasion(String invasionType, int xPos, int yPos, int mapId, String monsterType, int monsterNumber)
	{
		String command = new String("&inv " + invasionType + " " + xPos + " " + yPos + " " + mapId + " " + monsterType + " " + monsterNumber);
		String windowName ="(" + pseudo + " sur " + server + ") Landes Eternelles";
		this.sendCommandToLE(command, windowName);
	}

	public void clearInvasion(String invasionType, int mapId)
	{
		String command = "&clear_inv " + invasionType +" " + mapId;
		String windowName ="(" + pseudo + " sur " + server + ") Landes Eternelles";
		this.sendCommandToLE(command, windowName);
	}
	
	public void commandoAjouter(int xPos, int yPos, int mapId, int commandoType, int commandoGroup)
	{
		String command = new String("&commando_ajouter " + xPos + " " + yPos + " " + mapId + " " + commandoType + " " + commandoGroup);
		String windowName ="(" + pseudo + " sur " + server + ") Landes Eternelles";
		this.sendCommandToLE(command, windowName);
	}
	
	public void commandoGo(int xPos, int yPos, int mapId, int commandoType, int commandoGroup)
	{
		String command = new String("&commando_go " + xPos + " " + yPos + " " + mapId + " " + commandoType + " " + commandoGroup);
		String windowName ="(" + pseudo + " sur " + server + ") Landes Eternelles";
		this.sendCommandToLE(command, windowName);
	}
	
	public void commandoFree (int mapId, int commandoType, int commandoGroup)
	{
		String command = new String("&commando_free " + mapId + " " + commandoType + " " + commandoGroup);
		String windowName ="(" + pseudo + " sur " + server + ") Landes Eternelles";
		this.sendCommandToLE(command, windowName);
	}
	
	public void commandoStop(int mapId, int commandoType, int commandoGroup)
	{
		String command = new String("&commando_stop " + mapId + " " + commandoType + " " + commandoGroup);
		String windowName ="(" + pseudo + " sur " + server + ") Landes Eternelles";
		this.sendCommandToLE(command, windowName);
	}
	
	private void sendCommandToLE(String command, String windowName)
	{
		System.out.println(windowName + " - " + command);
		try {
			if (System.getProperty("os.name").startsWith("Windows"))
				Runtime.getRuntime().exec("wscript scripts\\focusApp.vbs \"" + windowName + "\" \"" + command + "\" \"Gestionnaire Invasion\"");
			else if (System.getProperty("os.name").startsWith("Linux"))
				Runtime.getRuntime().exec(new String[]{"scripts/focusApp.sh", windowName, command});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPseudo(String pseudo)
	{
		this.pseudo = pseudo;
	}
	
	public void setServer(String server)
	{
		this.server = server;
	}
}
