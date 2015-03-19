package aide_invasion_le;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LEInterfaceNet implements ILEInterface {

	private Socket socket;
	private DataInputStream in;
	private BufferedOutputStream out;
	private Timer heartBeatTimer;
	private TabMap callBackCheckInvasion;
	private TabMap callBackCheckPlayers;

	final static private byte LOG_IN_TYPE = (byte) 140;
	final static private byte HEART_BEAT = 14;
	final static private byte PING_REQUEST = 60;
	final static private byte YOU_DONT_EXIST  = (byte) 249;
	final static private byte LOG_IN_OK = (byte) 250;
	final static private byte LOG_IN_NOT_OK = (byte) 251;
	final static private byte NEW_MINUTE = 5;
	final static private byte GET_ACTIVE_SPELL_LIST = 45;
	final static private byte SYNC_CLOCK = 4;
	final static private byte YOU_ARE = 3;
	final static private byte CHANGE_MAP = 7;
	final static private byte HERE_YOUR_INVENTORY = 19;
	final static private byte RAW_TEXT = 0;
	final static private byte HERE_YOUR_STATS = 18;
	
	private boolean login_verif = false;
	
	private boolean isInvasionChecking = false;
	private ArrayList<String[]> res_check_order = new ArrayList<String[]>();
	
	private boolean isPlayersChecking = false;
	private ArrayList<String[]> res_check_players_order = new ArrayList<String[]>();
	
	public boolean open(String serverAdr, String serverPort, String pseudo, String password) {
		try {
			InetAddress ServeurAdresse = InetAddress.getByName(serverAdr);
			int port = Integer.parseInt(serverPort);
			System.out.println("L'adresse du serveur est : " + ServeurAdresse
					+ " ; Port " + port);
			System.out.println("Demande de connexion");
			socket = new Socket(ServeurAdresse, port);

			in = new DataInputStream(socket.getInputStream());
			out = new BufferedOutputStream(socket.getOutputStream());

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Byte type = 0;
							while (true) {
								try {
									type = in.readByte();
									if (type != null)
										break;
								} catch (EOFException e) {
								}
							}
							int length = in.readUnsignedByte()
									+ in.readUnsignedByte() * 256;
							int dataLength = length - 1;
							byte[] data = new byte[dataLength];
							for (int i = 0; i < dataLength; i++) {
								data[i] = in.readByte();
							}
							reception(type, data);
						} catch (Exception e) {
							e.printStackTrace();
							if ("Socket closed".equals(e.getMessage()))
								break;
						}
					}
				}
			});
			thread.start();

			this.startHeart_Beat();
			this.login(pseudo, password);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void close()
	{
		System.out.println("Close Socket");
        try {
        	if (socket != null && !socket.isClosed()) {
        		in.close();
        		out.close();
        		socket.close();
        		this.stopHeart_Beat();
        	}
        	else
        		System.out.println("Unable to close socket");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRawText(String message)
	{
		System.out.println("Message : " + message);
		
		byte[] data = new byte[Math.min(message.length(), 255)];
		for (int i=0; i<data.length ; i++)
		{
			data[i] = (byte)message.charAt(i);
		}
		
		send(RAW_TEXT, data);
	}
	
	public void addInvasion(String invasionType, int xPos, int yPos, int mapId,
			String monsterType, int monsterNumber) {
		String command = new String("#inv " + invasionType + " " + xPos + " " + yPos + " " + mapId + " " + monsterType + " " + monsterNumber);
		this.sendRawText(command);
	}

	public void clearInvasion(String invasionType, int mapId) {
		String command = "#clear_inv " + invasionType +" " + mapId;
		this.sendRawText(command);
	}

	public void sendCheckInvasion(TabMap callBack) {
		this.callBackCheckInvasion = callBack;
		this.isInvasionChecking = true;
		this.sendRawText("#check_invasion");
	}
	
	public void sendCheckPlayers(TabMap callBack, int map) {
		this.callBackCheckPlayers = callBack;
		this.isPlayersChecking = true;
		this.sendRawText("#rapport " + map + " attaque 0 300 0");
	}
	
	public void commandoAjouter(int xPos, int yPos, int mapId,
			int commandoType, int commandoGroup) {
		String command = new String("#comdo_script_ajouter " + mapId + " " + xPos + " " + yPos + " " + commandoType + " " + commandoGroup);
		this.sendRawText(command);
	}

	public void commandoGo(int xPos, int yPos, int mapId,
			int commandoGroup) {
		String command = new String("#comdo_script_go " + mapId + " " + xPos + " " + yPos + " -1 " + commandoGroup);
		this.sendRawText(command);
	}

	public void commandoFree(int mapId, int commandoGroup) {
		String command = new String("#commando_free " + mapId + " -1 " + commandoGroup);
		this.sendRawText(command);
	}

	public void commandoStop(int mapId, int commandoGroup) {
		String command = new String("#commando_stop " + mapId + " -1 " + commandoGroup);
		this.sendRawText(command);
	}
	
	private void login(String pseudo, String pwd)
	{
		String stringData = pseudo + " " + pwd;
		System.out.println("Login : " + stringData);
		
		byte[] data = new byte[255];
		for (int i=0; i<stringData.length() && i<254 ; i++)
		{
			data[i] = (byte)stringData.charAt(i);
		}
		data[stringData.length()] = 0;
			
		send(LOG_IN_TYPE, data);
	}
	
	private void startHeart_Beat()
	{
		System.out.println("Start Heart Beat");
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				send(HEART_BEAT, null);
			}	
		};
		
		heartBeatTimer = new Timer();
		heartBeatTimer.scheduleAtFixedRate(task, 0, 25000);
	}
	
	private void stopHeart_Beat()
	{
		System.out.println("Stop Heart Beat");
		heartBeatTimer.cancel();
	}
	
	private void send(byte type, byte[] data)
	{
		int dataLength = 0;
		if (data != null)
			dataLength = data.length;
		byte[] message = new byte[dataLength+3];

		message[0] = type;
		message[1] = (byte) ((dataLength+1) % 256);
		message[2] = (byte) ((dataLength+1) / 256);
		
		if(data != null) {
			for (int i=0; i<data.length ; i++)
			{
				message[i+3] = data[i];
			}
		}
		
		System.out.println("Sent : " + new String(message) + " (");
		for(int i = 0; i<message.length;i++)
		{
			System.out.print(";" + message[i]);
		}
		System.out.println(")\n\n");
		
	    try {
	    	out.write(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void reception(byte type, byte[] data)
	{
		System.out.println("RECEIVED");
		System.out.println("Type: " + type);
		
		if (type==PING_REQUEST) {
			System.out.println("PING_REQUEST");
			send(PING_REQUEST, data);
		}
		else if (type == YOU_DONT_EXIST) {
			System.out.println("LOGIN not exist");
			login_verif=false;
		}
		else if (type == LOG_IN_OK) {
			System.out.println("LOGIN OK");
			login_verif=true;
		}
		else if (type == LOG_IN_NOT_OK) {
			System.out.println("LOGIN fail");
			login_verif=false;
		}
		else if (type == NEW_MINUTE)
			System.out.println("NEW_MINUTE");
		else if (type == GET_ACTIVE_SPELL_LIST)
			System.out.println("GET_ACTIVE_SPELL_LIST");
		else if (type == SYNC_CLOCK)
			System.out.println("SYNC_CLOCK");
		else if (type == YOU_ARE)
			System.out.println("YOU_ARE");
		else if (type == CHANGE_MAP)
			System.out.println("CHANGE_MAP");
		else if (type == HERE_YOUR_INVENTORY)
			System.out.println("HERE_YOUR_INVENTORY");
		else if (type == RAW_TEXT)
		{
			System.out.println("RAW_TEXT");
			if (isInvasionChecking)
				parse_check_inva(data);
			if (isPlayersChecking)
				parse_check_play(data);
		}
		else if (type == HERE_YOUR_STATS)
			System.out.println("HERE_YOUR_STATS");
		
		System.out.print("ByteData: ");
		for(int i = 0; i<data.length;i++)
		{
			System.out.print(";" + (int)data[i]);
		}
		System.out.println();
		System.out.print("Textdata: ");
		for(int i = 0; i<data.length;i++)
		{
			System.out.print((char)data[i]);
		}
		System.out.println("\n\n");
	}

	private void parse_check_inva(byte[] data) {
		String s = new String(data);
		
		Pattern pattern = Pattern.compile("encore un (.+) (.+) en : ([0-9]+), ([0-9]+), ([0-9]+).$");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			System.out.println("Parse : " + matcher.group(1)+ matcher.group(3)+ matcher.group(3)+  matcher.group(4)+  matcher.group(5) );
			String[]resStrTab = {matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5)};
			res_check_order.add(resStrTab);
		}
		else 
		{
			pattern = Pattern.compile("(encore [0-9]+ monstre)|(plus de monstre d'invasion)");
			matcher = pattern.matcher(s);
			if (matcher.find()) {
				System.out.println("Fin Parse Inva");
				isInvasionChecking = false;
				callBackCheckInvasion.checkInvasionCallback(res_check_order);
				res_check_order = new ArrayList<String[]>();
			}
		}
	}
	
	private void parse_check_play(byte[] data) {
		String s = new String(data);
		
		System.out.println("TryParse play");
		
		Pattern pattern = Pattern.compile("^..([a-zA-Z_]+) *$");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			System.out.println("Parse play : " + matcher.group(1) );
			if(!matcher.group(1).equals("Aucun"))
			{
				String[]resStrTab = {matcher.group(1)};
				res_check_players_order.add(resStrTab);
			}
		}
		else 
		{
			pattern = Pattern.compile("pour le .* attaque");
			matcher = pattern.matcher(s);
			if (matcher.find()) {
				System.out.println("Fin Parse Players");
				isPlayersChecking = false;
				callBackCheckPlayers.checkPlayersCallback(res_check_players_order);
				res_check_players_order = new ArrayList<String[]>();
			}
		}
	}

	public boolean isLogin_verif() {
		return login_verif;
	}
}
