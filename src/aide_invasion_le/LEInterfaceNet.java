package aide_invasion_le;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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

	final static private byte LOG_IN_TYPE = (byte) 140;
	final static private byte HEART_BEAT = 14;
	final static private byte PING_REQUEST = 60;
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
	
	final static private int CHECK_INVASION = 1;
	final static private int NO_CHECK = 0;
	private int check_order = NO_CHECK;
	private ArrayList<String[]> res_check_order = new ArrayList<String[]>();
	
	public LEInterfaceNet(String pseudo, String password, String serverAdress, int serverPort) {
		this.open(serverAdress, serverPort, pseudo, password);
	}

	public void open(String serverAdr, int port, String pseudo, String password)
	{
		try {
			InetAddress ServeurAdresse= InetAddress.getByName(serverAdr);
	        System.out.println("L'adresse du serveur est : "+ServeurAdresse+ " ; Port " + port);
	        System.out.println("Demande de connexion");
		    socket = new Socket(ServeurAdresse, port);
	
		    in = new DataInputStream (socket.getInputStream());
		    out = new BufferedOutputStream(socket.getOutputStream());
		    
		    Thread thread = new Thread(new Reception(in, this));
		    thread.start();
		    
			this.startHeart_Beat();
			
			this.login(pseudo, password);
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
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

	public void addInvasion(String invasionType, int xPos, int yPos, int mapId,
			String monsterType, int monsterNumber) {
		System.out.println("LEInterfaceNet addInvasion does nothing");
	}

	public void clearInvasion(String invasionType, int mapId) {
		System.out.println("LEInterfaceNet clearInvasion does nothing");
	}
	
	public ArrayList<String[]> checkInvasion() {
		return res_check_order;
	}

	public void commandoAjouter(int xPos, int yPos, int mapId,
			int commandoType, int commandoGroup) {
		System.out.println("LEInterfaceNet commandoAjouter does nothing");
	}

	public void commandoGo(int xPos, int yPos, int mapId, int commandoType,
			int commandoGroup) {
		System.out.println("LEInterfaceNet commandoGo does nothing");
	}

	public void commandoFree(int mapId, int commandoType, int commandoGroup) {
		System.out.println("LEInterfaceNet commandoFree does nothing");
	}

	public void commandoStop(int mapId, int commandoType, int commandoGroup) {
		System.out.println("LEInterfaceNet commandoStop does nothing");
	}
	
	public void sendMessage(String str, int order)
	{
		check_order=order;
		sendRawText(str);
	}
	
	public void clearResCheckOrder() {
		res_check_order = new ArrayList<String[]>();
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
	
	public void reception(byte type, byte[] data)
	{
		System.out.println("RECEIVED");
		System.out.println("Type: " + type);
		
		if (type==PING_REQUEST)
		{
			System.out.println("PING_REQUEST");
			send(PING_REQUEST, data);
		} else if (type == LOG_IN_OK)
			System.out.println("LOGIN OK");
		else if (type == LOG_IN_NOT_OK)
			System.out.println("LOGIN fail");
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
			
			if (check_order==CHECK_INVASION)
				parse_check_inva(data);
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
		
		Pattern pattern = Pattern.compile("encore un (.)+ (automatique|périssable|ponctuel|permanent) en : ([0-9])+, ([0-9])+, ([0-9])+.$");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			System.out.println(matcher.group(1)+ matcher.group(3)+  matcher.group(4)+  matcher.group(5) );
			String[]resStrTab = {matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4)};
			res_check_order.add(resStrTab);
		}
		else 
		{
			pattern = Pattern.compile("encore ([0-9])+ monstres d'invasion.$");
			matcher = pattern.matcher(s);
			if (matcher.find()) {
				System.out.println(Integer.parseInt(matcher.group(1)));
				check_order = NO_CHECK;
			}
		}
	}
}
